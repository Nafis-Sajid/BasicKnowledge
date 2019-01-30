package com.example.nafissajid.basicknowledge

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FirebaseUtils {

    val profilePictureRef: StorageReference
        get() = FirebaseStorage.getInstance().getReference("profilePicture/"+System.currentTimeMillis()+".jpg")

    val postRef: DatabaseReference
        get() = FirebaseDatabase.getInstance()
            .getReference(Constants.POST_KEY)

    val postLikedRef: DatabaseReference
        get() = FirebaseDatabase.getInstance()
            .getReference(Constants.POST_LIKED_KEY)

    val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    val uid: String
        get() {
            val path = FirebaseDatabase.getInstance().reference.push().toString()
            return path.substring(path.lastIndexOf("/") + 1)
        }

    val postImageRef: StorageReference
        get() = FirebaseStorage.getInstance().getReference(Constants.POST_IMAGES)

    val myPostRef: DatabaseReference
        get() = FirebaseDatabase.getInstance().getReference(Constants.MY_POSTS)
            .child(currentUser!!.email!!.replace(".", ","))

    val myRecordRef: DatabaseReference
        get() = FirebaseDatabase.getInstance().getReference(Constants.USER_RECORD)
            .child(currentUser!!.email!!.replace(".", ","))

    fun getUserRef(email: String): DatabaseReference {
        return FirebaseDatabase.getInstance()
            .getReference(Constants.USERS_KEY)
            .child(email)
    }

    fun getPostLikedRef(postId: String): DatabaseReference {
        return postLikedRef.child(
            currentUser!!.email!!
                .replace(".", ",")
        )
            .child(postId)
    }

    fun getCommentRef(postId: String): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(Constants.COMMENTS_KEY)
            .child(postId)
    }

    fun addToMyRecord(node: String, id: String) {
        myRecordRef.child(node).runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val myRecordCollection: ArrayList<String>
                if (mutableData.value == null) {
                    myRecordCollection = ArrayList(1)
                    myRecordCollection.add(id)
                } else {
                    myRecordCollection = mutableData.value as ArrayList<String>
                    myRecordCollection.add(id)
                }
                mutableData.value = myRecordCollection
                return Transaction.success(mutableData)
            }
            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
            }
        })
    }
}