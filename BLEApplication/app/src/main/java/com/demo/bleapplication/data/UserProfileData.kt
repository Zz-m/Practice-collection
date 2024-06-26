package com.demo.bleapplication.data

import java.lang.IllegalArgumentException

data class UserProfileData(
    val IsMan: Int = 0,
    val IsLookingForMan: Int = 0,
    val FriendsToTalk: Int = 0,
    val LongRelationsship: Int = 0,
    val TouristInTown: Int = 0,
    val JustSexualIntentions: Int = 0,
    val ProfilSliderPosition: Int = 0,
    val Parameters: String = "",
    val LookingForSliderPosition: Int = 0
) {

    companion object {
        fun toByteArray(profileData: UserProfileData): ByteArray {
            val normal =  byteArrayOf(
                profileData.IsMan.toByte(),
                profileData.IsLookingForMan.toByte(),
                profileData.FriendsToTalk.toByte(),
                profileData.LongRelationsship.toByte(),
                profileData.TouristInTown.toByte(),
                profileData.JustSexualIntentions.toByte(),
                profileData.ProfilSliderPosition.toByte(),
                profileData.LookingForSliderPosition.toByte()
            )
            val param = profileData.Parameters.toByteArray(Charsets.UTF_8)

            return normal.plus(param)
        }

        fun fromByteArray(data: ByteArray): UserProfileData {
            var param: ByteArray = byteArrayOf()
            if (data.size >= 8) {
                param = data.slice(8..<data.size).toByteArray()
            }

            return UserProfileData(
                IsMan = data[0].toInt(),
                IsLookingForMan = data[1].toInt(),
                FriendsToTalk = data[2].toInt(),
                LongRelationsship = data[3].toInt(),
                TouristInTown = data[4].toInt(),
                JustSexualIntentions = data[5].toInt(),
                ProfilSliderPosition = data[6].toInt(),
                LookingForSliderPosition = data[7].toInt(),
                Parameters = param.toString(Charsets.UTF_8)
            )
        }
    }

}

