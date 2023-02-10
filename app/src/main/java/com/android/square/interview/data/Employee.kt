package com.android.square.interview.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for Employee object fields.
 */
data class Employee(
    @SerializedName("full_name")
    val name: String,
    @SerializedName("team")
    val team: String,
    @SerializedName("photo_url_small")
    val url: String,
    @SerializedName("email_address")
    val email: String
)
