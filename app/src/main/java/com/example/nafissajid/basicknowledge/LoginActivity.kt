package com.example.nafissajid.basicknowledge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        new_user_text.setOnClickListener {
            finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser!=null){
            finish()
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    fun userLogin() {
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
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                finish()
                Toast.makeText(applicationContext, "User Logged in successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
