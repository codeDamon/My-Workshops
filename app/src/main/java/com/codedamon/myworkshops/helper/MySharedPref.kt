package com.codedamon.myworkshops.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object MySharedPref {

    init {

    }

    private var sharedPref: SharedPreferences? = null

    private const val UNREGISTERED = "UNREGISTERED"
    private const val USERNAME = "USERNAME"

    fun initializeSharedPref(activity: Activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    }

    fun clearPref() {
        sharedPref = null
    }

    //USER login or sign-up for first time
    //store its username
    fun registerUser(type: String) {
        sharedPref?.edit()?.putString(USERNAME, type)?.apply()
    }

    fun deregisterUser(){
        sharedPref?.edit()?.putString(USERNAME, UNREGISTERED)?.apply()
    }

    fun checkRegisteredUser(): String?{
        return sharedPref?.getString(USERNAME, "FIRST_TIME")
    }
}