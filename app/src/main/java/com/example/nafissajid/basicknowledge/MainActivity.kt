package com.example.nafissajid.basicknowledge


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        subject_button.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

        about_button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("About Us")
            dialogBuilder.setMessage(R.string.about_us)
            dialogBuilder.setPositiveButton("OK") { _, _ ->
            }
            dialogBuilder.create().show()
        }

        rate_button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Rate Us")
            dialogBuilder.setMessage(R.string.rate_us)
            dialogBuilder.setPositiveButton("OK") { _, _ ->
            }
            dialogBuilder.create().show()
        }
    }
}
