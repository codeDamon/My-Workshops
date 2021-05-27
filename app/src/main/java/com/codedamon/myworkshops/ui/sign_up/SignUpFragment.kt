package com.codedamon.myworkshops.ui.sign_up

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
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var navController: NavController
    private lateinit var username: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var mobNo: TextInputLayout
    private lateinit var password: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MySharedPref.initializeSharedPref(requireActivity())
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.username_et)
        email = view.findViewById(R.id.email_et)
        mobNo = view.findViewById(R.id.mobile_no_et)
        password = view.findViewById(R.id.password_et)
        //progressBar = view.findViewById(R.id.progress_bar)
        navController = Navigation.findNavController(view)

        view.findViewById<TextView>(R.id.login_tv).setOnClickListener {
            navController.navigateUp()
        }

        view.findViewById<TextView>(R.id.guest_tv).setOnClickListener {
            MySharedPref.registerUser("GUEST")
            navController.navigate(R.id.action_signUpFragment2_to_workshopsFragment2)
        }

        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            if (validateInputs()) {
                registerUser()
                Toast.makeText(context, "Successful Sign up", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_signUpFragment2_to_workshopsFragment2)
            }
        }
    }

    //to save the username in SharedPreference
    private fun registerUser() {
        val eUsername = username.editText?.text.toString()
        MySharedPref.registerUser(eUsername)
    }

    //to validate all user inputs
    private fun validateInputs(): Boolean {

        username.error = null
        email.error = null
        mobNo.error = null
        password.error = null

        val eUsername = username.editText?.text.toString()
        val eEmail = email.editText?.text.toString()
        val eMobNo = mobNo.editText?.text.toString()
        val ePass = password.editText?.text.toString()

        fun isValidEmail(email: String): Boolean {
            val regex = Regex("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")
            return email.matches(regex)
        }

        if (eUsername.isEmpty()) {
            username.error = "Please enter a username"
            username.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        }
        if (eEmail.isEmpty()) {
            email.error = "Please enter your email-id"
            email.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        } else if (!isValidEmail(eEmail)) {
            email.error = "Please enter valid email-id"
            email.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        }
        if (eMobNo.isEmpty()) {
            mobNo.error = "Please enter your mobile number"
            mobNo.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        } else if (eMobNo.length != 10) {
            mobNo.error = "Mobile Number should be of 10 digits"
            mobNo.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        }
        if (ePass.isEmpty()) {
            password.error = "Please enter password"
            password.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        } else if (ePass.length < 6) {
            password.error = "Password length should be at least of 6 characters "
            password.requestFocus()
            //progressBar.visibility = View.GONE
            return false
        }
        return true
    }
}