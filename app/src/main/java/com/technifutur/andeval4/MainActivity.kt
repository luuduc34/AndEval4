package com.technifutur.andeval4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.technifutur.andeval4.adapter.ExpenseAdapter
import com.technifutur.andeval4.database.DataBase
import com.technifutur.andeval4.databinding.ActivityMainBinding
import com.technifutur.andeval4.dbModel.Converters
import com.technifutur.andeval4.dbModel.Expense
import com.technifutur.andeval4.dbModel.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import androidx.recyclerview.widget.DividerItemDecoration


class MainActivity : AppCompatActivity() {

    val dateDuJour = Date()
    private lateinit var binding: ActivityMainBinding
    lateinit var db: DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // date converter
        val dateConverterInstance = Converters(dateDuJour)
        // expense database
        db = Room.databaseBuilder(this, DataBase::class.java, "Expense.db")
            .addTypeConverter(dateConverterInstance)
            .build()
        // button in tab bar to add new expense
        val addButton = binding.addButton
        addButton.setOnClickListener {
            val intent = Intent(this, AddExpense::class.java)
            startActivity(intent)
        }

        getExpense()
    }
    override fun onResume() {
        super.onResume()
        db.expenseDao().getAll().observeForever{
            setupRecyclerView(it)
        }

    }
    // recyclerView
    private fun setupRecyclerView(expenseList: List<Expense>) {
        val recyclerView = binding.recyclerView
        val adapter = ExpenseAdapter(expenseList)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    // call database actions in coroutines
    private fun getExpense() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                dbActions()
            } catch (e: Exception) {
                println("Exception : $e")
            }
        }
    }

    // actions in database
    suspend fun dbActions() {
        withContext(Dispatchers.Default) {
            val expenseDao = db.expenseDao()
            val typeDao = db.typeDao()
            val expTypeDao = db.expTypeDao()
            var expenses = expenseDao.getAll()

            Log.d("TEST", "Expenes : ${expenses}")
        }
    }
}