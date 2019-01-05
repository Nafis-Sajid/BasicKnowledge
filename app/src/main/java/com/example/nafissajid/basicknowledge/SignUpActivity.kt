package com.example.nafissajid.basicknowledge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        sign_up_button.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser (){
        val email = email_input.text.toString().trim()
        val password = pass_input.text.toString().trim()

        if(email.isEmpty()){
            email_input.error = "Please provide an email"
            email_input.requestFocus()
            return
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_input.error = "Please provide a valid email"
            email_input.requestFocus()
            return
        }

        if(password.isEmpty()){
            pass_input.error = "Please provide an email"
            pass_input.requestFocus()
            return
        }

        if(password.length<6){
            pass_input.error = "Password length should be at least 6"
            pass_input.requestFocus()
            return
        }
    }
}
