package com.technifutur.andeval4

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.technifutur.andeval4.database.DataBase
import com.technifutur.andeval4.databinding.ActivityAddExpenseBinding
import com.technifutur.andeval4.databinding.ActivityMainBinding
import com.technifutur.andeval4.dbModel.Converters
import com.technifutur.andeval4.dbModel.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddExpense : AppCompatActivity() {
    private lateinit var editDateText: TextView
    private lateinit var binding: ActivityAddExpenseBinding
    lateinit var db: DataBase
    var date: Date = Date()
    private lateinit var editTypeText: TextView
    private val expenseTypes = arrayOf("Food", "Tax", "Car")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Date converter
        val dateConverterInstance = Converters(date)

        // Expense database
        db = Room.databaseBuilder(this, DataBase::class.java, "Expense.db")
            .addTypeConverter(dateConverterInstance)
            .build()

        editDateText = binding.editDateText
        editTypeText = binding.editTypeText

        val saveBtn = binding.saveButton
        saveBtn.setOnClickListener {
            if (!binding.editNameText.text.toString().isEmpty()
                && !editDateText.text.toString().isEmpty()
                && !binding.editAmountText.text.toString().isEmpty()
            ) {
                addExpense()
                finish()
            } else {
                showAlertDialog("Please fill all the form!")
            }
        }

        // click on dateEditText
        editDateText.setOnClickListener {
            showDatePickerDialog()
        }

        // Gérer le clic sur le champ de type de dépense
        editTypeText.setOnClickListener {
            showExpenseTypeDialog()
        }
    }

    // show DatePickerDialog
    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)

            val dateFormat = SimpleDateFormat("dd--MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            editDateText.text = formattedDate // update textView with selected date
        }, year, month, day)

        datePickerDialog.show()
    }
    private fun showExpenseTypeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Expense Type")

        builder.setItems(expenseTypes) { dialog, which ->
            val selectedType = expenseTypes[which]
            editTypeText.setText(selectedType) // Utilisez setText() pour définir le texte
            dialog.dismiss() // Fermer l'alerte
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
    private fun addExpense() {
        val name = binding.editNameText.text.toString()
        val amount = binding.editAmountText.text.toString().toFloat()
        val dateStr = editDateText.text.toString()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date = dateFormat.parse(dateStr) // Convert string to Date object


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expenseDao = db.expenseDao()
                expenseDao.insert(Expense(name = name, date = date, value = amount))
            } catch (e: Exception) {
                println("Exception : $e")
            }
        }
    }

    // alert when non all form filling
    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss() // close dialog
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}
