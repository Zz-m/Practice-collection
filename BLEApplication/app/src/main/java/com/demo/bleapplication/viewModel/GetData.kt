package com.demo.bleapplication.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.bleapplication.data.MyDatabase
import com.demo.bleapplication.data.UserProfileData
import com.demo.bleapplication.data.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

object GetData {

    var IsMan = 0
    var IsLookingForMan = 0
    var FriendsToTalk = 0
    var LongRelationsship = 0
    var TouristInTown = 0
    var JustSexualIntentions = 0
    var ProfilSliderPosition = 0
    var Parameters = ""
    var LookingForSliderPosition = 0

    fun getData(context: Context): LiveData<UserProfileData> {
        val data = MutableLiveData<UserProfileData>()

        CoroutineScope(Dispatchers.IO).launch {
            val vt = MyDatabase.getDatabaseAccess(context)
            val userCount = vt?.userDao()?.getUserCount() ?: 0

            if (userCount > 0) {
                val users = vt?.userDao()?.getAllUsers()
                if (users != null) {
                    if (users.isNotEmpty()) {
                        // Assuming the first user is the one we're interested in
                        val user = users.first()
                        val userProfileData = UserProfileData(
                            IsMan = user.IsMan,
                            IsLookingForMan = user.IsLookingForMan,
                            FriendsToTalk = user.FriendsToTalk,
                            LongRelationsship = user.LongRelationsship,
                            TouristInTown = user.TouristInTown,
                            JustSexualIntentions = user.JustSexualIntentions,
                            ProfilSliderPosition = user.ProfilSliderPosition,
                            Parameters = user.Parameters,
                            LookingForSliderPosition = user.LookingForSliderPosition
                        )
                        withContext(Dispatchers.Main) {
                            data.value = userProfileData
                        }
                    }
                }
            } else {
                val defaultUserProfileData = UserProfileData(
                    IsMan = 0,
                    IsLookingForMan = 0,
                    FriendsToTalk = 0,
                    LongRelationsship = 0,
                    TouristInTown = 0,
                    JustSexualIntentions = 0,
                    ProfilSliderPosition = 0,
                    Parameters = "",
                    LookingForSliderPosition = 0
                )

                withContext(Dispatchers.Main) {
                    data.value = defaultUserProfileData
                }
            }
        }
        return data
    }

    fun saveData(context: Context, newUserProfileData: UserProfileData) {
        val vt = MyDatabase.getDatabaseAccess(context)
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = vt?.userDao()?.getUserById(1)
            if (currentUser == null) {
                val newUser = Users(
                    id = 1,
                    IsMan = newUserProfileData.IsMan,
                    IsLookingForMan = newUserProfileData.IsLookingForMan,
                    FriendsToTalk = newUserProfileData.FriendsToTalk,
                    LongRelationsship = newUserProfileData.LongRelationsship,
                    TouristInTown = newUserProfileData.TouristInTown,
                    JustSexualIntentions = newUserProfileData.JustSexualIntentions,
                    ProfilSliderPosition = newUserProfileData.ProfilSliderPosition,
                    Parameters = newUserProfileData.Parameters,
                    LookingForSliderPosition = newUserProfileData.LookingForSliderPosition
                )
                vt?.userDao()?.insertUser(newUser)
            } else if (hasDataChanged(currentUser, newUserProfileData)) {
                val updatedUser = currentUser.apply {
                    IsMan = newUserProfileData.IsMan
                    IsLookingForMan = newUserProfileData.IsLookingForMan
                    FriendsToTalk = newUserProfileData.FriendsToTalk
                    LongRelationsship = newUserProfileData.LongRelationsship
                    TouristInTown = newUserProfileData.TouristInTown
                    JustSexualIntentions = newUserProfileData.JustSexualIntentions
                    ProfilSliderPosition = newUserProfileData.ProfilSliderPosition
                    Parameters = newUserProfileData.Parameters
                    LookingForSliderPosition = newUserProfileData.LookingForSliderPosition
                }
                vt?.userDao()?.updateUser(updatedUser)
            }
        }
    }


    private fun hasDataChanged(currentUser: Users, newData: UserProfileData): Boolean {
        return currentUser.IsMan != newData.IsMan ||
                currentUser.IsLookingForMan != newData.IsLookingForMan ||
                currentUser.FriendsToTalk != newData.FriendsToTalk ||
                currentUser.LongRelationsship != newData.LongRelationsship ||
                currentUser.TouristInTown != newData.TouristInTown ||
                currentUser.JustSexualIntentions != newData.JustSexualIntentions ||
                currentUser.ProfilSliderPosition != newData.ProfilSliderPosition ||
                currentUser.Parameters != newData.Parameters ||
                currentUser.LookingForSliderPosition != newData.LookingForSliderPosition
    }
    fun saveTags(context: Context, tags: String) {
        val vt = MyDatabase.getDatabaseAccess(context)
        CoroutineScope(Dispatchers.IO).launch {
            val user = vt?.userDao()?.getUserById(1) // Assuming user with ID 1 exists
            user?.let {
                it.Parameters = tags
                vt.userDao().updateUser(it)
            }
        }
    }

    fun saveLookingForSliderPosition(context: Context, position: Int) {
        val vt = MyDatabase.getDatabaseAccess(context)
        CoroutineScope(Dispatchers.IO).launch {
            val user = vt?.userDao()?.getUserById(1) // Assuming user with ID 1 exists
            user?.let {
                it.LookingForSliderPosition = position
                vt.userDao().updateUser(it)
            }
        }
    }

}