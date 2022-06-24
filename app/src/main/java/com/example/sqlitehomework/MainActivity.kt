package com.example.sqlitehomework

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlitehomework.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var personAdapter:PersonAdapter?=null
    private var personList = mutableListOf<Person>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainRv.hasFixedSize()
        personAdapter = PersonAdapter()

        loadData()

        binding.insert.setOnClickListener {
            insert()
        }

        binding.save.setOnClickListener {
            saveData()
        }

    }

    private fun insert(){
        if (checkForNull()) {
            personList.add(Person(binding.nameEt.text.toString(),
                binding.ageET.text.toString().toInt()))
            personAdapter?.personList = personList
            binding.mainRv.adapter = personAdapter

        }
        else{
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkForNull():Boolean{
        return binding.nameEt.text.isNotEmpty()&&
                binding.ageET.text.isNotEmpty()
    }

   
    private fun saveData(){
        if(personList.isNotEmpty()) {
            val sharedPrefs =
                getSharedPreferences("sharedPrefs", MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            val gson = Gson()
            val saveObj: String = gson.toJson(personList)
            editor.putString("MySaveData", saveObj)
            editor.apply()
            Toast.makeText(this, "Успешно сохранен", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Нечего сохранять", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(){
        val sharedPref = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("MySaveData", null)
        val type = object : TypeToken<MutableList<Person>>(){}.type
        val personListData:MutableList<Person> ?= gson.fromJson(json,type)
        if (personListData!=null){
            personList = personListData
            personAdapter?.personList = personList
            binding.mainRv.adapter = personAdapter
        }
        else{
            Toast.makeText(this, "Данные отсутствуют", Toast.LENGTH_SHORT).show()
        }
    }
}