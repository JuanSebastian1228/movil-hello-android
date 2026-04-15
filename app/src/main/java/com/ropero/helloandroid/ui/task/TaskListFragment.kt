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

        val adapter = TaskAdapter(tasks)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


    }
}