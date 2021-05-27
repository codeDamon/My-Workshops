package com.codedamon.myworkshops.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.myworkshops.R
import com.codedamon.myworkshops.adapter.WorkshopAdapter

class DashboardFragment : Fragment(), WorkshopAdapter.WorkshopDetailInterface {

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel
    private lateinit var adapter: WorkshopAdapter
    private lateinit var navController: NavController
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        viewModel.allWorkshop.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        navController = Navigation.findNavController(view)
        initRecycleView()

        view.findViewById<TextView>(R.id.tv_1).setOnClickListener {
            navController.navigateUp()
        }

        activity?.let {
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
                .get(DashboardViewModel::class.java)
        }
    }

    private fun initRecycleView() {
        recyclerView.setHasFixedSize(true)
        adapter = WorkshopAdapter(requireContext(), true, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onApplyClicked(name: String) {
        //Nothing required
    }
}