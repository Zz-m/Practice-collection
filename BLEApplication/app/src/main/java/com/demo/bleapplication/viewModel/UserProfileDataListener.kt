package com.demo.bleapplication.viewModel

import com.demo.bleapplication.data.UserProfileData

interface UserProfileDataListener {
    fun onUserProfileDataUpdated(data: UserProfileData)
}
