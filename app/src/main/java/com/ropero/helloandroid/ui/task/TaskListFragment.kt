package com.ropero.helloandroid.ui.task
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ropero.helloandroid.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ropero.helloandroid.data.task.TaskRepository
import androidx.recyclerview.widget.ItemTouchHelper
class TaskListFragment : Fragment() {

    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd = view.findViewById(R.id.btnAdd)
        recyclerView = view.findViewById(R.id.recyclerTasks)

        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_taskList_to_taskDetail)
        }

        val repository = TaskRepository(requireContext())
        val tasks = repository.getAllTasks()

        val adapter = TaskAdapter(tasks) { task ->
            val bundle = Bundle().apply {
                putInt("task_id", task.id)
            }
            findNavController().navigate(R.id.action_taskList_to_taskDetail, bundle)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val task = tasks[position]

                val repository = TaskRepository(requireContext())
                repository.deleteTask(task.id)

                Toast.makeText(requireContext(), "Tarea eliminada", Toast.LENGTH_SHORT).show()

                // refrescar lista
                val newTasks = repository.getAllTasks()
                recyclerView.adapter = TaskAdapter(newTasks) { t ->
                    val bundle = Bundle().apply {
                        putInt("task_id", t.id)
                    }
                    findNavController().navigate(R.id.action_taskList_to_taskDetail, bundle)
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
}