package com.it_academy.countries_app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.it_academy.countries_app.ext.showDialogWithOneButton

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    lateinit var sharedPreferences: SharedPreferences
    var status: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDialogueStatus()

        navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )

        val toolbar = findViewById<Toolbar>(R.id.mytool)
        setSupportActionBar(toolbar)

        status = showDialogWithOneButton(
            getString(R.string.first_fragment_dialog_title),
            getString(R.string.first_fragment_dialog_description),
            R.string.button_dialog_with_one_button, null
        ).isShowing
        saveDialogueStatus()
    }

    private fun saveDialogueStatus() {
        sharedPreferences = this.getSharedPreferences("dialog", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("status", status)
            apply()
        }
    }

    private fun getDialogueStatus() {
        sharedPreferences = this.getSharedPreferences("dialog", Context.MODE_PRIVATE)
        var result = sharedPreferences.getBoolean("status", false)
        status = result
    }
}
