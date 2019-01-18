package com.example.nafissajid.basicknowledge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_question_answer.*

class QuestionAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, SubjectActivity::class.java)
            startActivity(intent)
        }
    }
}
