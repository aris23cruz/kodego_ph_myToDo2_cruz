package com.kodego.mytodoapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kodego.mytodoapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var  adapter: TodoAdapter
    private val dao = ToDoDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            try{
                dao.addTask(ToDo(binding.etToDo.text.toString()))
                adapter.notifyDataSetChanged()
                Toast.makeText(applicationContext,"Saved!",Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                Toast.makeText(applicationContext,"Enter To-Do", Toast.LENGTH_SHORT).show()
            }
            view()
        }

    }

    private fun view() {
        dao.getTask().addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val toDoList: ArrayList<ToDo> = ArrayList()
                val dataFromDb = snapshot.children

                for(data in dataFromDb) {
                    val task = data.child("task").value.toString()
                    val taskDone = ToDo(task)
                    toDoList.add(taskDone)
                }
                    binding.myRecycler.adapter = adapter
                    binding.myRecycler.layoutManager = LinearLayoutManager(applicationContext)

                    adapter.onTaskDelete = { _: ToDo, _: Int ->
                        deleteData()
                        adapter.toDo.remove(ToDo("task"))
                        adapter.notifyDataSetChanged()
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun deleteData() {
        dao.remove("task")

    }
}