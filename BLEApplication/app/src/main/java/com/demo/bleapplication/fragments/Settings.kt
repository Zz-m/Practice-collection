@file:Suppress("DEPRECATION")

package com.demo.bleapplication.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.demo.bleapplication.R
import com.demo.bleapplication.databinding.SettingsBinding

class Settings : Fragment() {
    private lateinit var binding: SettingsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        binding.ProfileScanBtn.setBackgroundColor(sharedPref.getInt("ProfileScanBtnColor", resources.getColor(R.color.red_light)))
        binding.LookingForScanBtn.setBackgroundColor(sharedPref.getInt("LookingForScanBtnColor", resources.getColor(R.color.red_light)))


        binding.ProfileScanBtn.setOnClickListener {
            if (binding.ProfileScanBtn.currentTextColor != resources.getColor(R.color.green_light)) {
                binding.ProfileScanBtn.setBackgroundColor(resources.getColor(R.color.green_light))
                binding.LookingForScanBtn.setBackgroundColor(resources.getColor(R.color.red_light))
                editor.putInt("ProfileScanBtnColor", resources.getColor(R.color.green_light))
                editor.putInt("LookingForScanBtnColor", resources.getColor(R.color.red_light))
                editor.putString("LastGreenButton", "Profile")
                editor.apply()
            }
        }

        binding.LookingForScanBtn.setOnClickListener {
            if (binding.LookingForScanBtn.currentTextColor != resources.getColor(R.color.green_light)) {
                binding.LookingForScanBtn.setBackgroundColor(resources.getColor(R.color.green_light))
                binding.ProfileScanBtn.setBackgroundColor(resources.getColor(R.color.red_light))
                editor.putInt("LookingForScanBtnColor", resources.getColor(R.color.green_light))
                editor.putInt("ProfileScanBtnColor", resources.getColor(R.color.red_light))
                editor.putString("LastGreenButton", "LookingFor")
                editor.apply()
            }
        }
    }



}