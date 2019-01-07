package com.example.nafissajid.basicknowledge

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        log_in_button.setOnClickListener {
            userLogin()
        }

        sign_up_button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this, "Sign up Clicked", Toast.LENGTH_SHORT).show()
        }

        auth = FirebaseAuth.getInstance()
    }

    fun userLogin() {
        val email = email_input.text.toString().trim()
        val password = pass_input.text.toString().trim()
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
            pass_input.error = "Please provide a password"
            pass_input.requestFocus()
            return
        }

        if (password.length < 6) {
            pass_input.error = "Password length should be at least 6"
            pass_input.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "User Logged in successfully", Toast.LENGTH_SHORT).show()
                val firebaseUser = auth.currentUser!!

                val intent = Intent(this, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
