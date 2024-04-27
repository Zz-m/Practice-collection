@file:Suppress("DEPRECATION")

package com.demo.bleapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.demo.bleapplication.R
import com.demo.bleapplication.data.UserProfileData
import com.demo.bleapplication.databinding.ProfileBinding
import com.demo.bleapplication.viewModel.GetData
import com.demo.bleapplication.viewModel.UserDataViewModel
import com.google.android.material.snackbar.Snackbar


class Profile : Fragment() {
    private lateinit var binding: ProfileBinding
    private lateinit var navController: NavController
    private val profileViewModel: UserDataViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        navController = Navigation.findNavController(view)
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        binding.genderCard.setOnClickListener {
            if (binding.genderCard.cardBackgroundColor.defaultColor == resources.getColor(R.color.grey)){
                binding.genderCard.setCardBackgroundColor(resources.getColor(R.color.red_light))
                "Man".also { binding.genderTxt.text = it }
                GetData.IsMan = 1
            }
            else{
                binding.genderCard.setCardBackgroundColor(resources.getColor(R.color.grey))
                "Woman".also { binding.genderTxt.text = it }
                GetData.IsMan = 0
            }
        }

        binding.interestCard.setOnClickListener {
            if (binding.interestCard.cardBackgroundColor.defaultColor == resources.getColor(R.color.grey)){
                binding.interestCard.setCardBackgroundColor(resources.getColor(R.color.red_light))
                "Man".also { binding.interestTxt.text = it }
                GetData.IsLookingForMan = 1
            }
            else{
                binding.interestCard.setCardBackgroundColor(resources.getColor(R.color.grey))
                "Woman".also { binding.interestTxt.text = it }
                GetData.IsLookingForMan = 0
            }
        }

        binding.friendCard.setOnClickListener {
            if (binding.friendCard.cardBackgroundColor.defaultColor == resources.getColor(R.color.grey)){
                binding.friendCard.setCardBackgroundColor(resources.getColor(R.color.red_light))
                "On".also { binding.friendTxt.text = it }
                GetData.FriendsToTalk = 1
            }
            else{
                binding.friendCard.setCardBackgroundColor(resources.getColor(R.color.grey))
                "Off".also { binding.friendTxt.text = it }
                GetData.FriendsToTalk = 0
            }
        }

        binding.durationCard.setOnClickListener {
            if (binding.durationCard.cardBackgroundColor.defaultColor == resources.getColor(R.color.grey)){
                binding.durationCard.setCardBackgroundColor(resources.getColor(R.color.red_light))
                "On".also { binding.durationTxt.text = it }
                GetData.LongRelationsship = 1
            }
            else{
                binding.durationCard.setCardBackgroundColor(resources.getColor(R.color.grey))
                "Off".also { binding.durationTxt.text = it }
                GetData.LongRelationsship = 0
            }
        }

        binding.touristCard.setOnClickListener {
            if (binding.touristCard.cardBackgroundColor.defaultColor == resources.getColor(R.color.grey)){
                binding.touristCard.setCardBackgroundColor(resources.getColor(R.color.red_light))
                "On".also { binding.touristTxt.text = it }
                GetData.TouristInTown = 1
            }
            else{
                binding.touristCard.setCardBackgroundColor(resources.getColor(R.color.grey))
                "Off".also { binding.touristTxt.text = it }
                GetData.TouristInTown = 0
            }
        }

        binding.slider.addOnChangeListener { _, value, _ ->
            binding.sliderValue.text = value.toInt().toString()
            GetData.ProfilSliderPosition = value.toInt()
        }

        binding.saveBtn.setOnClickListener {
            val currentUserProfileData = UserProfileData(
                IsMan = if (binding.genderTxt.text == "Man") 1 else 0,
                IsLookingForMan = if (binding.interestTxt.text == "Man") 1 else 0,
                FriendsToTalk = if (binding.friendTxt.text == "On") 1 else 0,
                LongRelationsship = if (binding.durationTxt.text == "On") 1 else 0,
                TouristInTown = if (binding.touristTxt.text == "On") 1 else 0,
                JustSexualIntentions = GetData.JustSexualIntentions,
                ProfilSliderPosition = binding.slider.value.toInt(),
                Parameters = GetData.Parameters,
                LookingForSliderPosition = GetData.LookingForSliderPosition
            )
            profileViewModel.saveUserProfileData(requireContext(), currentUserProfileData)
            Snackbar.make(view, "Profile saved", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        profileViewModel.fetchProfileData(requireContext())
        profileViewModel.profileData.observe(viewLifecycleOwner) { userData ->
            binding.genderCard.setCardBackgroundColor(if (userData.IsMan == 1) resources.getColor(R.color.red_light) else resources.getColor(R.color.grey))
            binding.genderTxt.text = if (userData.IsMan == 1) "Man" else "Woman"

            binding.interestCard.setCardBackgroundColor(if (userData.IsLookingForMan == 1) resources.getColor(R.color.red_light) else resources.getColor(R.color.grey))
            binding.interestTxt.text = if (userData.IsLookingForMan == 1) "Man" else "Woman"

            binding.friendCard.setCardBackgroundColor(if (userData.FriendsToTalk == 1) resources.getColor(R.color.red_light) else resources.getColor(R.color.grey))
            binding.friendTxt.text = if (userData.FriendsToTalk == 1) "On" else "Off"

            binding.durationCard.setCardBackgroundColor(if (userData.LongRelationsship == 1) resources.getColor(R.color.red_light) else resources.getColor(R.color.grey))
            binding.durationTxt.text = if (userData.LongRelationsship == 1) "On" else "Off"

            binding.touristCard.setCardBackgroundColor(if (userData.TouristInTown == 1) resources.getColor(R.color.red_light) else resources.getColor(R.color.grey))
            binding.touristTxt.text = if (userData.TouristInTown == 1) "On" else "Off"

            binding.slider.value = userData.ProfilSliderPosition.toFloat()
            binding.sliderValue.text = userData.ProfilSliderPosition.toString()
        }
    }
}