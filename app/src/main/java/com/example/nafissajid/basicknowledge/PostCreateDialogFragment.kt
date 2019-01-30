package com.example.nafissajid.basicknowledge

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_create_post_dialog.*



class PostCreateDialogFragment : DialogFragment() {

    private val RC_PHOTO_PICKER = 1
    private var mPost: Post? = null
    private var progressBar: ProgressBar? = null
    private var mSelectedUri: Uri? = null
    private var mRootView: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context!!)
        mPost = Post()
        progressBar = ProgressBar(context)

        mRootView = activity!!.layoutInflater.inflate(R.layout.fragment_create_post_dialog, null)
        builder.setView(mRootView)
        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post_dialog_send_imageview.setOnClickListener {
            Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
            sendPost()
        }

        post_dialog_select_imageview.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER)
    }

    private fun sendPost() {

        progressBar?.isIndeterminate = true
        progressBar?.visibility = View.VISIBLE

        FirebaseUtils.getUserRef(FirebaseUtils.currentUser!!.email!!.replace(".", ","))
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    val postId = FirebaseUtils.uid

                    mPost?.user = user
                    mPost?.numComments = 0
                    mPost?.numLikes = 0
                    mPost?.timeCreated = System.currentTimeMillis()
                    mPost?.postId = postId
                    mPost?.postText = post_dialog_edittext.text.toString()

                    if (mSelectedUri != null) {
                        FirebaseUtils.postImageRef
                            .child(mSelectedUri!!.lastPathSegment?.toString()!!)
                            .putFile(mSelectedUri!!)
                            .addOnSuccessListener(activity!!)
                            {
                                val url = Constants.POST_IMAGES + "/" + mSelectedUri!!.lastPathSegment
                                mPost?.postImageUrl = url
                                addToMyPostList(postId)
                            }
                    } else {
                        addToMyPostList(postId)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    progressBar?.visibility = View.GONE
                }
            })
    }


    private fun addToMyPostList(postId: String) {
        FirebaseUtils.postRef.child(postId)
            .setValue(mPost)
        FirebaseUtils.myPostRef.child(postId).setValue(true)
            .addOnCompleteListener(activity!!) {
                progressBar?.visibility = View.GONE
                dismiss()
            }

        FirebaseUtils.addToMyRecord(Constants.POST_KEY, postId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                mSelectedUri = data?.data
                post_dialog_display.setImageURI(mSelectedUri)
            }
        }
    }
}