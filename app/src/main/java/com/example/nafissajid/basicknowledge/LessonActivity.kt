package com.example.nafissajid.basicknowledge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lesson.*

class LessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        setSupportActionBar(toolbar)
        floatingActionButton.setOnClickListener {
            finish()
            val intent = Intent(this, QuestionAnswerActivity::class.java)
            startActivity(intent)
        }
    }
}
