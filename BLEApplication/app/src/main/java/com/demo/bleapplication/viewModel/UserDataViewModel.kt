package com.demo.bleapplication.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.bleapplication.data.UserProfileData
import timber.log.Timber

class UserDataViewModel : ViewModel() {
    private val _profileData = MutableLiveData<UserProfileData>()
    val profileData: LiveData<UserProfileData> = _profileData

    fun fetchProfileData(context: Context) {
        GetData.getData(context).observeForever { data ->
            _profileData.value = data
        }
    }

    fun saveUserProfileData(context: Context, data: UserProfileData) {
        GetData.saveData(context, data)
    }

    fun saveTags(context: Context, tags: String) {
        GetData.saveTags(context, tags)
    }

    fun saveLookingForSliderPosition(context: Context, position: Int) {
        GetData.saveLookingForSliderPosition(context, position)
    }
}