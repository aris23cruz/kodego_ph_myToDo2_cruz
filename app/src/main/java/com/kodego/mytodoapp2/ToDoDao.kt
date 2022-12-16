package com.kodego.mytodoapp2

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ToDoDao {
    var dbReference : DatabaseReference = Firebase.database.reference

    fun addTask(toDo: ToDo){
        dbReference.push().setValue(toDo)
    }

    fun getTask(): Query {
        return dbReference.orderByKey()
    }

    fun remove(key:String){
        dbReference.child(key).removeValue()
    }

    fun update(key: String , map: Map<String,String>){
        dbReference.child(key).updateChildren(map)
    }
}