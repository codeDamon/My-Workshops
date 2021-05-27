package com.codedamon.myworkshops.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.myworkshops.R
import com.codedamon.myworkshops.helper.MySharedPref
import com.codedamon.myworkshops.model.Workshop
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var navController: NavController
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MySharedPref.initializeSharedPref(requireActivity())
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.email_et)
        password = view.findViewById(R.id.password_et)
        //progressBar = view.findViewById(R.id.progress_bar)
        navController = Navigation.findNavController(view)

        activity?.let {
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
                .get(LoginViewModel::class.java)
        }

        when (MySharedPref.checkRegisteredUser()) {

            "FIRST_TIME" -> {
                addWorkshops()
                MySharedPref.registerUser("UNREGISTERED")
            }
            "UNREGISTERED", "GUEST" -> {

            }
            else -> {
                navController.navigate(R.id.action_loginFragment2_to_workshopsFragment2)
            }
        }

        view.findViewById<TextView>(R.id.sign_up_tv).setOnClickListener {
            navController.navigate(R.id.action_loginFragment2_to_signUpFragment2)
        }

        view.findViewById<TextView>(R.id.guest_tv).setOnClickListener {
            MySharedPref.registerUser("GUEST")
            navController.navigate(R.id.action_loginFragment2_to_workshopsFragment2)
        }

        view.findViewById<Button>(R.id.login_button).setOnClickListener {

            if (validateInputs()) {
                val user = "USER"
                MySharedPref.registerUser(user)
                Toast.makeText(context, "Welcome Back !", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_loginFragment2_to_workshopsFragment2)
            }
        }
    }

    //to validate all inputs
    private fun validateInputs(): Boolean {

        email.error = null
        password.error = null

        fun isValidEmail(email: String): Boolean {
            val regex = Regex("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")
            return email.matches(regex)
        }
        if (email.editText?.text.toString().isEmpty()) {
            email.error = "Please enter your email-id"
            email.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        } else if (!isValidEmail(email.editText?.text.toString())) {
            email.error = "Please enter valid email-id"
            email.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        }
        if (password.editText?.text.toString().isEmpty()) {
            password.error = "Please enter password"
            password.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        }
        return true
    }

    private fun addWorkshops() {
        var workshop = Workshop(
            "Android Development Workshop",
            "https://3.bp.blogspot.com/-4AfIIjP-CpU/UENLoBOZuXI/AAAAAAAAAlQ/_TTh99AsI9w/s1600/android%2Bwallpaper%2Bblack%2B3.jpg",
            "Wed Jun 09 2021 at 10:00 am",
            "Dr. Raghav Sir",
            "English",
            false,
            100,
            "Online"
        )
        viewModel.addMedicine(workshop)

        workshop = Workshop(
            "Python Programming Workshop",
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/04459068-3dd6-4c10-90ea-ed1b8bead0aa/dcfh81e-4292bab0-5096-4ee7-8ec6-a8881ccc6bd9.png/v1/fill/w_1024,h_576,q_75,strp/python_wallpaper__free_download__4k___by_drelix-dcfh81e.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwic3ViIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl0sIm9iaiI6W1t7InBhdGgiOiIvZi8wNDQ1OTA2OC0zZGQ2LTRjMTAtOTBlYS1lZDFiOGJlYWQwYWEvZGNmaDgxZS00MjkyYmFiMC01MDk2LTRlZTctOGVjNi1hODg4MWNjYzZiZDkucG5nIiwid2lkdGgiOiI8PTEwMjQiLCJoZWlnaHQiOiI8PTU3NiJ9XV19.cS_ZN8WT0T0ixCe9L6ZSMZ_YmvpTeiagjtZI1xFY0OM",
            "Wed Jun 09 2021 at 01:00 pm",
            "Dr. Ritesh Sir",
            "English",
            false,
            100,
            "Online"
        )
        viewModel.addMedicine(workshop)

        workshop = Workshop(
            "Node JS Workshop",
            "https://hdwallpaperim.com/wp-content/uploads/2017/08/25/461263-node.js-JavaScript.jpg",
            "Thr Jun 10 2021 at 01:00 pm",
            "Dr. Mishra Sir",
            "English",
            false,
            100,
            "Online"
        )
        viewModel.addMedicine(workshop)

        workshop = Workshop(
            "JAVA Programming Workshop",
            "https://wallpapercave.com/wp/wp6599799.jpg",
            "Fri Jun 11 2021 at 01:00 pm",
            "Dr. Kamal Sir",
            "English",
            false,
            100,
            "Online"
        )
        viewModel.addMedicine(workshop)

        workshop = Workshop(
            "C++ Programming Workshop",
            "https://wallpapercave.com/wp/wp4009915.jpg",
            "Sat Jun 12 2021 at 01:00 pm",
            "Dr. Rohan Sir",
            "English",
            false,
            100,
            "Online"
        )
        viewModel.addMedicine(workshop)
    }
}