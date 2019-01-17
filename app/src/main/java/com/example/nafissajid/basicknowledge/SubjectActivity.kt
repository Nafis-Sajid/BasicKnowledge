package com.example.nafissajid.basicknowledge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_subject.*

class SubjectActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        buttonp1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, LessonActivity::class.java)
        startActivity(intent)
    }
}
