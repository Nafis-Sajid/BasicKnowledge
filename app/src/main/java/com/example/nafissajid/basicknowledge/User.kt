package com.example.nafissajid.basicknowledge

import java.io.Serializable

class User : Serializable {
    var userName: String? = null
    var email: String? = null
    var photoUrl: String? = null
    var uid: String? = null
    var dob: String? = null
    var contact: String? = null
    var institute: String? = null

    constructor()

    constructor(
        userName: String?,
        email: String?,
        photoUrl: String?,
        uid: String?,
        dob: String?,
        contact: String?,
        institute: String?
    ) {
        this.userName = userName
        this.email = email
        this.photoUrl = photoUrl
        this.uid = uid
        this.dob = dob
        this.contact = contact
        this.institute = institute
    }
}