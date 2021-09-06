package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.test_app.ext.showDialogWithOneButton

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val toolbar = findViewById<Toolbar>(R.id.mytool)
        setSupportActionBar(toolbar)

        showDialogWithOneButton(
            getString(R.string.first_fragment_dialog_title),
            getString(R.string.first_fragment_dialog_description),
            R.string.button_dialog_with_one_button, null
        )
    }

    /*override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
            super.onPause()
            super.onStop()
            super.onDestroy()
        }
    }*/
}
