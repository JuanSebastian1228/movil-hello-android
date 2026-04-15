package com.ropero.helloandroid.data.task

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskRepository(context: Context) {

    companion object {
        private const val PREFS_NAME = "tasks_prefs"
        private const val KEY_TASK_LIST = "task_list"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    // Lista en memoria
    private var tasksInMemory: MutableList<Task> = loadTasksFromPrefs()

    // Obtener todas las tareas
    fun getAllTasks(): List<Task> = tasksInMemory.toList()

    // Agregar tarea
    fun addTask(task: Task) {
        tasksInMemory.add(task)
        saveTasksToPrefs()
    }

    // Actualizar tarea
    fun updateTask(updated: Task) {
        val index = tasksInMemory.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            tasksInMemory[index] = updated
            saveTasksToPrefs()
        }
    }

    // Eliminar tarea
    fun deleteTask(taskId: Int) {
        tasksInMemory.removeAll { it.id == taskId }
        saveTasksToPrefs()
    }

    // ==========================
    // PERSISTENCIA
    // ==========================

    private fun loadTasksFromPrefs(): MutableList<Task> {
        val json = prefs.getString(KEY_TASK_LIST, null) ?: return mutableListOf()

        val type = object : TypeToken<List<Task>>() {}.type

        return try {
            val list: List<Task> = gson.fromJson(json, type)
            list.toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    private fun saveTasksToPrefs() {
        val json = gson.toJson(tasksInMemory)
        prefs.edit().putString(KEY_TASK_LIST, json).apply()
    }
}