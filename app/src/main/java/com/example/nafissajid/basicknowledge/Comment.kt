package com.example.nafissajid.basicknowledge

import java.io.Serializable

class Comment : Serializable {
    var user: User? = null
    var commentId: String? = null
    var timeCreated: Long = 0
    var comment: String? = null

    constructor() {}

    constructor(user: User?, commentId: String?, timeCreated: Long, comment: String?) {
        this.user = user
        this.commentId = commentId
        this.timeCreated = timeCreated
        this.comment = comment
    }
}
