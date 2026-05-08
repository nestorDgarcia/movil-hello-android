package com.nesquid.helloandroidgarcianestor.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nesquid.helloandroidgarcianestor.model.Task

// Autor: Nesto
class TaskRepository(context: Context) {
    private val PREFS_NAME = "tasks_prefs"
    private val KEY_TASK_LIST = "task_list"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    private var tasksInMemory: MutableList<Task> = loadTasksFromPrefs()

    private fun loadTasksFromPrefs(): MutableList<Task> {
        val json = prefs.getString(KEY_TASK_LIST, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    private fun saveTasksToPrefs() {
        val json = gson.toJson(tasksInMemory)
        prefs.edit().putString(KEY_TASK_LIST, json).apply()
    }

    fun getAllTasks(): List<Task> = tasksInMemory.toList()

    fun addTask(task: Task) {
        tasksInMemory.add(task)
        saveTasksToPrefs()
    }

    fun updateTask(task: Task) {
        val index = tasksInMemory.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasksInMemory[index] = task
            saveTasksToPrefs()
        }
    }
}