package com.ropero.helloandroid.ui.task
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ropero.helloandroid.R
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ropero.helloandroid.data.task.Task
import com.ropero.helloandroid.data.task.TaskRepository
import android.os.SystemClock
class TaskDetailFragment : Fragment() {

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var cbReminder: CheckBox
    private lateinit var btnSave: Button
    private var taskId: Int? = null
    private var currentTask: Task? = null
    private lateinit var btnBack: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)
        cbReminder = view.findViewById(R.id.cbReminder)
        btnSave = view.findViewById(R.id.btnSave)
        btnBack = view.findViewById(R.id.btnBack)

        val repository = TaskRepository(requireContext())

        taskId = arguments?.getInt("task_id")

        if (taskId != null) {
            currentTask = repository.getAllTasks().find { it.id == taskId }

            currentTask?.let { task ->
                etTitle.setText(task.title)
                etDescription.setText(task.description)
                cbReminder.isChecked = task.hasReminder
            }
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        btnSave.setOnClickListener {

            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val reminder = cbReminder.isChecked

            val repository = TaskRepository(requireContext())

            if (currentTask != null) {
                // ACTUALIZAR
                val updatedTask = currentTask!!.copy(
                    title = title,
                    description = description,
                    hasReminder = reminder
                )

                repository.updateTask(updatedTask)

                Toast.makeText(requireContext(), "Tarea actualizada", Toast.LENGTH_SHORT).show()

            } else {
                // ➕ CREAR NUEVA
                val newTask = Task(
                    id = System.currentTimeMillis().toInt(),
                    title = title,
                    description = description,
                    hasReminder = reminder
                )

                repository.addTask(newTask)

                Toast.makeText(requireContext(), "Tarea guardada", Toast.LENGTH_SHORT).show()
            }

            //PROGRAMAR RECORDATORIO
            if (reminder) {

                Toast.makeText(requireContext(), "Recordatorio activado", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), TaskReminderReceiver::class.java)
                intent.putExtra("task_title", title)

                val requestCode = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    requestCode,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    if (!alarmManager.canScheduleExactAlarms()) {

                        Toast.makeText(requireContext(), "Permiso de alarma no concedido", Toast.LENGTH_LONG).show()

                        val intentPermiso = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        startActivity(intentPermiso)
                        return@setOnClickListener
                    }
                }

                val triggerTime = SystemClock.elapsedRealtime() + 5000

                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime,
                            pendingIntent
                        )
                    } else {
                        alarmManager.setExact(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime,
                            pendingIntent
                        )
                    }

                    Toast.makeText(requireContext(), "Alarma programada", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al programar alarma", Toast.LENGTH_LONG).show()
                }
            }

            // VOLVER A LA LISTA
            findNavController().navigateUp()
        }


    }
}