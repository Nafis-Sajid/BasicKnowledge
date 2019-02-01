package com.example.nafissajid.basicknowledge

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.create_post_dialog.view.*
import kotlinx.android.synthetic.main.fragment_forum.*


class ForumFragment : Fragment() {
    //variavles for creating post
    private val photoPicker = 1
    private var mPost: Post? = null
    private var progressBar: ProgressBar? = null
    private var mSelectedUri: Uri? = null

    private lateinit var alertLayoutInflater: View

    private var postAdapter: FirebaseRecyclerAdapter<Post, PostHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //progressBar = ProgressBar(context)
        return inflater.inflate(R.layout.fragment_forum, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPostAdapter()
        fab.setOnClickListener {
            createPost()
        }
    }

    // ---------------------------------------------------------------------------------------------------------------//

    private fun createPost() {
//        val dialog = PostCreateDialogFragment()
//        dialog.show(fragmentManager!!, null)

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Create new post")
        alertLayoutInflater = layoutInflater.inflate(R.layout.create_post_dialog, null)
        dialogBuilder.setView(alertLayoutInflater)
        dialogBuilder.create().show()
        alertLayoutInflater.post_dialog_select_imageButton.setOnClickListener {
            //Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
            selectImage()
        }
        alertLayoutInflater.post_dialog_send_imageButton.setOnClickListener {
            //Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
            uploadPost()
        }
    }

    //    select image for post
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), photoPicker)
    }

    //function to upload a post
    private fun uploadPost() {
        Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
        //progressBar?.isIndeterminate = true
        //progressBar?.visibility = View.VISIBLE

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
                    mPost?.postText = view?.post_dialog_edittext?.text.toString()

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
                    //progressBar?.visibility = View.GONE
                }
            })
    }

    //add post to my post list
    private fun addToMyPostList(postId: String) {
        FirebaseUtils.postRef.child(postId).setValue(mPost)

        FirebaseUtils.myPostRef.child(postId).setValue(true).addOnCompleteListener(activity!!) {
            //progressBar?.visibility = View.GONE
        }

        FirebaseUtils.addToMyRecord(Constants.POST_KEY, postId)
    }

    //set image to photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == photoPicker && resultCode == RESULT_OK && data != null && data.data != null) {
            mSelectedUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, mSelectedUri)
            alertLayoutInflater.post_dialog_display?.setImageBitmap(bitmap)
            //post_dialog_display.setImageURI(mSelectedUri)
        }
    }

    // ---------------------------------------------------------------------------------------------------------------//


    private fun setupPostAdapter() {
        recyclerview_post.layoutManager = LinearLayoutManager(context)
        //Query of posts
        val query = FirebaseDatabase.getInstance().reference.child("posts").limitToLast(50)

        //building recycler options
        val options = FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        postAdapter = object : FirebaseRecyclerAdapter<Post, PostHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_post_container, parent, false)

                return PostHolder(view)
            }

            override fun onBindViewHolder(holder: PostHolder, position: Int, model: Post) {

                holder.postNumCommentsTextView.text = model.numComments.toString()
                holder.postNumLikesTextView.text = model.numLikes.toString()
                holder.postTimeCreatedTextView.text = model.timeCreated.toString()
                holder.postOwnerUsernameTextView.text = model.user?.userName
                holder.postTextTextView.text = model.postText
                GlideApp.with(this@ForumFragment).load(model.user?.photoUrl).into(holder.postOwnerDisplayImageView)

                if (model.postImageUrl != null) {
                    holder.postDisplayImageVIew.visibility = View.VISIBLE

                    val storageReference = FirebaseStorage.getInstance().getReference(model.postImageUrl.toString())
                    GlideApp.with(this@ForumFragment)
                        .load(storageReference)
                        .into(holder.postDisplayImageVIew)
                } else {
                    holder.postDisplayImageVIew.setImageBitmap(null)
                    holder.postDisplayImageVIew.visibility = View.GONE
                }

                holder.postLikeLayout.setOnClickListener {
                    onLikeClicked(model.postId!!)
                }

                holder.postCommentLayout.setOnClickListener {
                    val intent = Intent(context, PostActivity::class.java)
                    intent.putExtra(Constants.EXTRA_POST, model)
                    startActivity(intent)
                }
            }
        }
        recyclerview_post.adapter = postAdapter
    }

    private fun onLikeClicked(postId: String) {
        FirebaseUtils.getPostLikedRef(postId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.value != null) {
                        //User liked
                        FirebaseUtils.postRef
                            .child(postId)
                            .child(Constants.NUM_LIKES_KEY)
                            .runTransaction(object : Transaction.Handler {
                                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                                    val num = mutableData.value as Long
                                    mutableData.value = num - 1
                                    return Transaction.success(mutableData)
                                }

                                override fun onComplete(
                                    databaseError: DatabaseError?,
                                    b: Boolean,
                                    dataSnapshot: DataSnapshot?
                                ) {
                                    FirebaseUtils.getPostLikedRef(postId)
                                        .setValue(null)
                                }
                            })
                    } else {
                        FirebaseUtils.postRef
                            .child(postId)
                            .child(Constants.NUM_LIKES_KEY)
                            .runTransaction(object : Transaction.Handler {
                                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                                    val num = mutableData.value as Long
                                    mutableData.value = num + 1
                                    return Transaction.success(mutableData)
                                }

                                override fun onComplete(
                                    databaseError: DatabaseError?,
                                    b: Boolean,
                                    dataSnapshot: DataSnapshot?
                                ) {
                                    FirebaseUtils.getPostLikedRef(postId)
                                        .setValue(true)
                                }
                            })
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var postOwnerDisplayImageView: ImageView
        internal var postOwnerUsernameTextView: TextView
        internal var postTimeCreatedTextView: TextView
        internal var postDisplayImageVIew: ImageView
        internal var postTextTextView: TextView
        internal var postLikeLayout: LinearLayout
        internal var postCommentLayout: LinearLayout
        internal var postNumLikesTextView: TextView
        internal var postNumCommentsTextView: TextView

        init {
            postOwnerDisplayImageView = itemView.findViewById(R.id.iv_post_owner_display) as ImageView
            postOwnerUsernameTextView = itemView.findViewById(R.id.tv_post_username) as TextView
            postTimeCreatedTextView = itemView.findViewById(R.id.tv_time) as TextView
            postDisplayImageVIew = itemView.findViewById(R.id.iv_post_display) as ImageView
            postLikeLayout = itemView.findViewById(R.id.like_layout) as LinearLayout
            postCommentLayout = itemView.findViewById(R.id.comment_layout) as LinearLayout
            postNumLikesTextView = itemView.findViewById(R.id.tv_likes) as TextView
            postNumCommentsTextView = itemView.findViewById(R.id.tv_comments) as TextView
            postTextTextView = itemView.findViewById(R.id.tv_post_text) as TextView
        }
    }

    override fun onStart() {
        super.onStart()
        postAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter?.stopListening()
    }
}
