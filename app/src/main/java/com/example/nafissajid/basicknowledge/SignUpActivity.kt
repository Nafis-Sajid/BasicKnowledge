package com.example.nafissajid.basicknowledge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_button.setOnClickListener {
            registerUser()
        }

        existing_user_text.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        auth = FirebaseAuth.getInstance()
    }

    private fun registerUser() {
        val email = email_input.text.toString().trim()
        val password = password_input.text.toString().trim()

        if (email.isEmpty()) {
            email_input.error = "Please provide an email"
            email_input.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_input.error = "Please provide a valid email"
            email_input.requestFocus()
            return
        }

        if (password.isEmpty()) {
            password_input.error = "Please provide a password"
            password_input.requestFocus()
            return
        }

        if (password.length < 6) {
            password_input.error = "Password length should be at least 6"
            password_input.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->

            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                finish()
                startActivity(Intent(this, ProfileActivity::class.java))
                Toast.makeText(applicationContext, "User registration successful", Toast.LENGTH_SHORT).show()

            } else {
                if (task.exception is FirebaseAuthUserCollisionException) {

                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
