package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class MainActivity : AppCompatActivity() {
    private var countClickToExit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.mytool)
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        countClickToExit++

        val handler = android.os.Handler()
        handler.postDelayed({countClickToExit = 0}, 5000)
        if (countClickToExit == 2) super.onBackPressed() else if (countClickToExit == 1) handler.postDelayed({countClickToExit = 0}, 5000)
    }
}
