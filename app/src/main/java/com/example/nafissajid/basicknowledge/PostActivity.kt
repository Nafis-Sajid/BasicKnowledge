package com.example.nafissajid.basicknowledge

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post.*




class PostActivity : AppCompatActivity() {

    private val bundleComment = "comment"
    private var mPost: Post? = null
    private var mComment: Comment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        if (savedInstanceState != null) {
            mComment = savedInstanceState.getSerializable(bundleComment) as Comment
        }

        val intent = intent
        mPost = intent.getSerializableExtra(Constants.EXTRA_POST) as Post

        iv_send.setOnClickListener {
            sendComment()
        }

        initPost()
        setupCommentAdapter()
    }

    private fun sendComment() {
        val progressBar = ProgressBar(this)
        progressBar.visibility = View.VISIBLE
        progressBar.isIndeterminate = true

        mComment = Comment()
        val uid = FirebaseUtils.uid
        val strComment = et_comment.text

        mComment?.commentId = uid
        mComment?.comment = strComment.toString()
        mComment?.timeCreated = System.currentTimeMillis()
        FirebaseUtils.getUserRef(FirebaseUtils.currentUser!!.email!!.replace(".", ","))
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    FirebaseUtils.getCommentRef(mPost?.postId!!)
                        .child(uid)
                        .setValue(mComment)

                    FirebaseUtils.postRef.child(mPost?.postId!!)
                        .child(Constants.NUM_COMMENTS_KEY)
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
                                progressBar.visibility = View.GONE
                                FirebaseUtils.addToMyRecord(Constants.COMMENTS_KEY, uid)
                            }
                        })
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    progressBar.visibility = View.GONE
                }
            })

    }

    private fun initPost() {

        val postOwnerDisplayImageView = findViewById<View>(R.id.iv_post_owner_display) as ImageView
        val postOwnerUsernameTextView = findViewById<View>(R.id.tv_post_username) as TextView
        val postTimeCreatedTextView = findViewById<View>(R.id.tv_time) as TextView
        val postDisplayImageView = findViewById<View>(R.id.iv_post_display) as ImageView
        val postLikeLayout = findViewById<View>(R.id.like_layout) as LinearLayout
        val postCommentLayout = findViewById<View>(R.id.comment_layout) as LinearLayout
        val postNumLikesTextView = findViewById<View>(R.id.tv_likes) as TextView
        val postNumCommentsTextView = findViewById<View>(R.id.tv_comments) as TextView
        val postTextTextView = findViewById<View>(R.id.tv_post_text) as TextView

        postOwnerUsernameTextView.text = mPost?.user?.userName
        postTimeCreatedTextView.text = DateUtils.getRelativeTimeSpanString(mPost!!.timeCreated)
        postTextTextView.text = mPost?.postText
        postNumLikesTextView.text = mPost?.numLikes.toString()
        postNumCommentsTextView.text = mPost?.numComments.toString()

        GlideApp.with(this)
            .load(mPost?.user?.photoUrl)
            .into(postOwnerDisplayImageView)

        if (mPost!!.postImageUrl != null) {
            postDisplayImageView.visibility = View.VISIBLE
            val storageReference = FirebaseStorage.getInstance().getReference(mPost!!.postImageUrl!!)

            Glide.with(this)
                .load(storageReference)
                .into(postDisplayImageView)
        } else {
            postDisplayImageView.setImageBitmap(null)
            postDisplayImageView.visibility = View.GONE
        }
    }


    private fun setupCommentAdapter() {
        comment_recyclerview.layoutManager = LinearLayoutManager(this)

        val query = FirebaseDatabase.getInstance().reference

        val options = FirebaseRecyclerOptions.Builder<Comment>().setQuery(query, Comment::class.java).build()

        val commentAdapter = object : FirebaseRecyclerAdapter<Comment, CommentHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_comment, parent, false)

                return CommentHolder(view)
            }

            override fun onBindViewHolder(holder: CommentHolder, position: Int, model: Comment) {
                holder.usernameTextView.text = model.user?.userName
                holder.commentTextView.text = model.comment
                holder.timeTextView.text = DateUtils.getRelativeTimeSpanString(model.timeCreated)
                GlideApp.with(this@PostActivity)
                    .load(model.user?.photoUrl)
                    .into(holder.commentOwnerDisplay)
            }

        }
        comment_recyclerview.adapter = commentAdapter
    }



    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var commentOwnerDisplay: ImageView
        internal var usernameTextView: TextView
        internal var timeTextView: TextView
        internal var commentTextView: TextView

        init {
            commentOwnerDisplay = itemView.findViewById<View>(R.id.iv_comment_owner_display) as ImageView
            usernameTextView = itemView.findViewById<View>(R.id.tv_username) as TextView
            timeTextView = itemView.findViewById<View>(R.id.tv_time) as TextView
            commentTextView = itemView.findViewById<View>(R.id.tv_comment) as TextView
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(bundleComment, mComment)
        super.onSaveInstanceState(outState)
    }


}
