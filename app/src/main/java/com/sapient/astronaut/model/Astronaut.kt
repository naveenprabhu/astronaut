package com.sapient.astronaut.model

import com.google.gson.annotations.SerializedName

data class Astronaut (
    var id: Int = 0,
    var name: String = "N/A",
    var nationality: String = "N/A",
    var bio: String = "N/A",
    @SerializedName("profile_image_thumbnail") var profileImage: String ="N/A",
    @SerializedName("date_of_birth") var dateOfBirth: String = "N/A"
)