package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val toolbar = findViewById<Toolbar>(R.id.mytool)
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        if (navController.popBackStack(R.id.mapFragment, true)) {
            navController.navigate(R.id.list_of_countries)
        } else if (!navController.popBackStack()) {
            super.onBackPressed()
            super.onPause()
            super.onStop()
            super.onDestroy()
        }
    }
}
