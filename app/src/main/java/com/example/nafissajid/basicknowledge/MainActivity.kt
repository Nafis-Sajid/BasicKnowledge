package com.example.nafissajid.basicknowledge


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("About Us")
            dialogBuilder.setMessage(R.string.about_us)
            dialogBuilder.setPositiveButton("OK"){dialog, which ->  
                
            }
            dialogBuilder.create().show()
            Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show()
        }

        rate_button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Rate Us")
            dialogBuilder.setMessage(R.string.rate_us)
            dialogBuilder.setPositiveButton("OK"){dialog, which ->

            }
            dialogBuilder.create().show()
            Toast.makeText(this, "Rate us Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
