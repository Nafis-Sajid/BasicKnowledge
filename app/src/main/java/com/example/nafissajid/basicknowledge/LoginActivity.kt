package com.example.nafissajid.basicknowledge

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this, "Log in Clicked", Toast.LENGTH_SHORT).show()
        }

        sign_up_button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this, "Sign up Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
