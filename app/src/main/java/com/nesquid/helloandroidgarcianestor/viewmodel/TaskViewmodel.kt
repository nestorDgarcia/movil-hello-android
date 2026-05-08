package com.nesquid.helloandroidgarcianestor.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nesquid.helloandroidgarcianestor.repository.TaskRepository
import com.nesquid.helloandroidgarcianestor.model.Task

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(application)

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> = _selectedTask

    init {
        loadTasks()
    }

    fun loadTasks() {
        _tasks.value = repository.getAllTasks()
    }

    fun selectTask(task: Task?) {
        _selectedTask.value = task
    }

    fun saveTask(title: String, description: String, timeInMillis: Long, hasReminder: Boolean) {
        val currentTask = _selectedTask.value
        if (currentTask == null) {
            val newId = (repository.getAllTasks().maxOfOrNull { it.id } ?: 0) + 1
            val newTask = Task(newId, title, description, timeInMillis, hasReminder)
            repository.addTask(newTask)
        } else {
            val updatedTask = currentTask.copy(
                title = title,
                description = description,
                timeInMillis = timeInMillis,
                hasReminder = hasReminder
            )
            repository.updateTask(updatedTask)
        }
        loadTasks()
    }
}