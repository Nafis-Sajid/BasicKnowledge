package com.example.nafissajid.basicknowledge

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_edit_profile.*


class EditProfileFragment : Fragment() {

    val PICK_IMAGE = 1
    var uriProfilePicture: Uri? = null
    var profilePictureUrl: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePicture.setOnClickListener {
            selectProfilePicture()
        }

        saveButton.setOnClickListener {
            saveUserInformation()
        }
    }

    fun selectProfilePicture(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE)
    }

    fun saveUserInformation(){
        val name = userName.text.toString().trim()
        if(name.isEmpty()){
            userName.error = "User name is required"
            userName.requestFocus()
            return
        }

        val user = auth.currentUser
        if (user != null) {
            val profile = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse(profilePictureUrl))
                .build()
            user.updateProfile(profile)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            uriProfilePicture = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, uriProfilePicture)
            profilePicture.setImageBitmap(bitmap)
            uploadImageToFirebase()
        }
    }

    private fun uploadImageToFirebase() {
        progressBar.visibility = View.VISIBLE
        if (uriProfilePicture != null) {
            FirebaseUtils.profilePictureRef.putFile(this.uriProfilePicture!!)
                .addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    profilePictureUrl = FirebaseUtils.profilePictureRef.downloadUrl.toString()
                    progressBar.visibility = View.GONE
                }
                .addOnFailureListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Profile Picture Uploading failed", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun loadUserInfo() {
        val user = auth.currentUser
        if (user==null) return
        if (user.photoUrl != null){
            GlideApp.with(this).load(user.photoUrl).into(profilePicture)
        }
    }
}
