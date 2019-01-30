package com.example.nafissajid.basicknowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home_profile.*


class HomeProfileFragment : Fragment(){

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val user = auth.currentUser
        if (user==null) return
//        if (user.photoUrl != null){
//            GlideApp.with(this).load(user.photoUrl.toString()).into(profilePicture)
//        }

        if(user.displayName!=null){
            userName.text = user.displayName
        }

        emailAddress.text = user.email
    }
}