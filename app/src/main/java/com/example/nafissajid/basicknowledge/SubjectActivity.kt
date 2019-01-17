package com.example.nafissajid.basicknowledge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_subject.*

class SubjectActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        buttonp1.setOnClickListener {
            val intent = Intent(this, LessonActivity::class.java)
            startActivity(intent)
        }
    }
}
