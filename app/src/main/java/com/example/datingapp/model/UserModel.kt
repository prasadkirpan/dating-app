package com.example.datingapp.model

data class UserModel(

    val number: String? = "",
    val name: String? = "",
    val city: String? = "",
    val email: String? = "",
    val gender: String? = "",
    val relationship: String? = "",
    val fcmToken: String? = "",
    val star: String? = "",
    val image: String = "",

    val age: String? = "",
    val status: String? = ""
) {
    companion object


}
