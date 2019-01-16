package com.example.nafissajid.basicknowledge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this, "Start Clicked", Toast.LENGTH_SHORT).show()
        }

        subject_button.setOnClickListener {
            val intent = Intent(this, SubjectActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this, "Subject Clicked", Toast.LENGTH_SHORT).show()
        }

        about_button.setOnClickListener {
            Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show()
        }

        rate_button.setOnClickListener {
            Toast.makeText(this, "Rate us Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
