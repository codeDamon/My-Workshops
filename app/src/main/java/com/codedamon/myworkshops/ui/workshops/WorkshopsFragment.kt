package com.codedamon.myworkshops.ui.workshops

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
import com.codedamon.myworkshops.helper.MySharedPref

class WorkshopsFragment : Fragment(), WorkshopAdapter.WorkshopDetailInterface {

    companion object {
        fun newInstance() = WorkshopsFragment()
    }

    private lateinit var viewModel: WorkshopsViewModel
    private lateinit var adapter: WorkshopAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MySharedPref.initializeSharedPref(requireActivity())
        return inflater.inflate(R.layout.fragment_workshops, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkshopsViewModel::class.java)

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

        view.findViewById<TextView>(R.id.tv_2).setOnClickListener {
            navController.navigate(R.id.action_workshopsFragment2_to_dashboardFragment)
        }

        activity?.let {
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
                .get(WorkshopsViewModel::class.java)
        }
    }

    private fun initRecycleView() {
        recyclerView.setHasFixedSize(true)
        adapter = WorkshopAdapter(requireContext(), false, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onApplyClicked(name: String) {
        if (MySharedPref.checkRegisteredUser() == "GUEST") {
            Toast.makeText(context, "Login Required to Apply", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_workshopsFragment2_to_loginFragment2)
        } else {
            Toast.makeText(context, "Applied", Toast.LENGTH_SHORT).show()
            viewModel.updateApplied(name, true)
        }
    }
}