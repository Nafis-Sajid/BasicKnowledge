package com.example.nafissajid.basicknowledge

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_question_answer.*


class QuestionAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)
        floatingActionButton.setOnClickListener {
            finish()
        }
        submitButton.setOnClickListener {
            checkAnswer()
        }
    }

    fun checkAnswer() {
        var score = 0;
        var selectedId = rgQuestion1.checkedRadioButtonId
        var radioButton = findViewById<View>(selectedId) as RadioButton

        if( radioButton.text == "Option 1"){
            score++
            answer1.text = "correct answer"
            answer1.setTextColor(resources.getColor(R.color.green))
        }

        selectedId = rgQuestion2.checkedRadioButtonId
        radioButton = findViewById<View>(selectedId) as RadioButton

        if( radioButton.text == "Option 2"){
            score++
            answer2.text = "correct answer"
            answer2.setTextColor(resources.getColor(R.color.green))
        }

        selectedId = rgQuestion3.checkedRadioButtonId
        radioButton = findViewById<View>(selectedId) as RadioButton

        if( radioButton.text == "Option 4"){
            score++
            answer3.text = "correct answer"
            answer3.setTextColor(resources.getColor(R.color.green))
        }

        selectedId = rgQuestion4.checkedRadioButtonId
        radioButton = findViewById<View>(selectedId) as RadioButton

        if( radioButton.text == "Option 3"){
            score++
            answer4.text = "correct answer"
            answer4.setTextColor(resources.getColor(R.color.green))
        }

        selectedId = rgQuestion5.checkedRadioButtonId
        radioButton = findViewById<View>(selectedId) as RadioButton

        if( radioButton.text == "Option 2"){
            score++
            answer5.text = "correct answer"
            answer5.setTextColor(resources.getColor(R.color.green))
        }
        scoreId.text = "Your score : " + score.toString()

        answer1.visibility = View.VISIBLE
        answer2.visibility = View.VISIBLE
        answer3.visibility = View.VISIBLE
        answer4.visibility = View.VISIBLE
        answer5.visibility = View.VISIBLE
        scoreId.visibility = View.VISIBLE
        submitButton.isClickable = false
    }
}
