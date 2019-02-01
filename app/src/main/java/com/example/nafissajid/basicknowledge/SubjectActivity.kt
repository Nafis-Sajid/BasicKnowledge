package com.example.nafissajid.basicknowledge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_subject.*

class SubjectActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        buttonp1.setOnClickListener(this)
        buttonp2.setOnClickListener(this)
        buttonp3.setOnClickListener(this)
        buttonp4.setOnClickListener(this)
        buttonp5.setOnClickListener(this)
        buttonc1.setOnClickListener(this)
        buttonc2.setOnClickListener(this)
        buttonc3.setOnClickListener(this)
        buttonc4.setOnClickListener(this)
        buttonc5.setOnClickListener(this)
        buttonm1.setOnClickListener(this)
        buttonm2.setOnClickListener(this)
        buttonm3.setOnClickListener(this)
        buttonm4.setOnClickListener(this)
        buttonm5.setOnClickListener(this)
        buttonb1.setOnClickListener(this)
        buttonb2.setOnClickListener(this)
        buttonb3.setOnClickListener(this)
        buttonb4.setOnClickListener(this)
        buttonb5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, LessonActivity::class.java)
        startActivity(intent)
    }
}
