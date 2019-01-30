package com.example.nafissajid.basicknowledge

import java.io.Serializable

class Post : Serializable {
    var user: User? = null
    var postText: String? = null
    var postImageUrl: String? = null
    var postId: String? = null
    var numLikes: Long = 0
    var numComments: Long = 0
    var timeCreated: Long = 0

    constructor(
        user: User?,
        postText: String?,
        postImageUrl: String?,
        postId: String?,
        numLikes: Long,
        numComments: Long,
        timeCreated: Long
    ) {
        this.user = user
        this.postText = postText
        this.postImageUrl = postImageUrl
        this.postId = postId
        this.numLikes = numLikes
        this.numComments = numComments
        this.timeCreated = timeCreated
    }

    constructor()
}