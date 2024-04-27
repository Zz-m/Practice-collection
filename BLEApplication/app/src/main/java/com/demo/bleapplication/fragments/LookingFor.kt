    package com.demo.bleapplication.fragments

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import android.widget.Toast
    import androidx.core.content.ContextCompat
    import androidx.fragment.app.viewModels
    import androidx.navigation.NavController
    import androidx.navigation.Navigation
    import com.demo.bleapplication.R
    import com.demo.bleapplication.data.UserProfileData
    import com.demo.bleapplication.databinding.AboutBinding
    import com.demo.bleapplication.databinding.LookingForBinding
    import com.demo.bleapplication.viewModel.GetData
    import com.demo.bleapplication.viewModel.UserDataViewModel
    import com.google.android.material.snackbar.Snackbar


    class LookingFor : Fragment() {
        private lateinit var binding: LookingForBinding
        private lateinit var navController: NavController
        private val profileViewModel: UserDataViewModel by viewModels()
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = LookingForBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            navController = Navigation.findNavController(view)
            binding.backBtn.setOnClickListener {
                navController.popBackStack()
            }

            binding.addTagButton.setOnClickListener {
                val tagText = binding.tagEditText.text.toString()
                if(tagText.isNotEmpty()) {
                    addTagToFlowLayout(tagText)
                    binding.tagEditText.text!!.clear()
                }
            }

            binding.slider.addOnChangeListener { _, value, _ ->
                binding.sliderValue.text = value.toInt().toString()
                GetData.LookingForSliderPosition = value.toInt()
            }

            binding.saveBtn.setOnClickListener {
                val tagsString = getAllTagsAsString()
                GetData.Parameters = tagsString
                profileViewModel.saveTags(requireContext(), tagsString)
                profileViewModel.saveLookingForSliderPosition(requireContext(), GetData.LookingForSliderPosition)
                val currentUserData = UserProfileData(
                    IsMan = GetData.IsMan,
                    IsLookingForMan = GetData.IsLookingForMan,
                    FriendsToTalk = GetData.FriendsToTalk,
                    LongRelationsship = GetData.LongRelationsship,
                    TouristInTown = GetData.TouristInTown,
                    JustSexualIntentions = GetData.JustSexualIntentions,
                    ProfilSliderPosition = GetData.ProfilSliderPosition,
                    Parameters = tagsString,
                    LookingForSliderPosition = GetData.LookingForSliderPosition
                )
                profileViewModel.saveUserProfileData(requireContext(), currentUserData)
                Snackbar.make(view, "Data saved", Snackbar.LENGTH_SHORT).show()
            }
            profileViewModel.fetchProfileData(requireContext())
            loadData()
        }

        private fun addTagToFlowLayout(tagText: String) {
            val tagView = TextView(requireContext()).apply {
                text = tagText
                setBackgroundResource(R.drawable.tag_background)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textSize = 16f
                setPadding(16, 8, 16, 8)
                id = View.generateViewId()

                setOnClickListener {
                    removeTagFromFlowLayout(this)
                }
            }

            binding.constraintLayout.addView(tagView)
            syncFlowLayoutReferencedIds()
        }


        private fun removeTagFromFlowLayout(tagView: TextView) {
            // Remove the tag view from the layout
            binding.constraintLayout.removeView(tagView)
            // Synchronize the FlowLayout's referenced IDs to reflect the removal
            syncFlowLayoutReferencedIds()
            // Update the tags in the database after removal
            val updatedTagsString = getAllTagsAsString()
            GetData.Parameters = updatedTagsString
            profileViewModel.saveTags(requireContext(), updatedTagsString)
            Toast.makeText(requireContext(), "Tag removed", Toast.LENGTH_SHORT).show()
        }


        private fun syncFlowLayoutReferencedIds() {
            val ids = mutableListOf<Int>()
            for (i in 0 until binding.constraintLayout.childCount) {
                val child = binding.constraintLayout.getChildAt(i)
                if (child.id != View.NO_ID && child !is com.google.android.material.slider.Slider) {
                    ids.add(child.id)
                }
            }
            binding.flowLayout.referencedIds = ids.toIntArray()
            binding.flowLayout.requestLayout()
        }

        private fun getAllTagsAsString(): String {
            val tags = mutableListOf<String>()
            for (i in 0 until binding.constraintLayout.childCount) {
                val child = binding.constraintLayout.getChildAt(i)
                if (child is TextView) {
                    tags.add(child.text.toString())
                }
            }
            return tags.joinToString(";")
        }

        private fun loadData() {
            profileViewModel.profileData.observe(viewLifecycleOwner) { userData ->
                clearExistingTags() // Clear existing tags before loading new ones

                val tags = userData.Parameters.split(";")
                tags.forEach { tag ->
                    if (tag.isNotEmpty()) addTagToFlowLayout(tag)
                }
                binding.slider.value = userData.LookingForSliderPosition.toFloat()
                binding.sliderValue.text = userData.LookingForSliderPosition.toString()
            }
        }

        private fun clearExistingTags() {
            // Create a list to hold views to remove to avoid modifying the list while iterating
            val viewsToRemove = mutableListOf<View>()

            // Collect all TextView children (tag views) to be removed
            for (i in 0 until binding.constraintLayout.childCount) {
                val child = binding.constraintLayout.getChildAt(i)
                if (child is TextView && child.id != binding.sliderValue.id) { // Make sure not to remove the sliderValue TextView
                    viewsToRemove.add(child)
                }
            }

            // Remove the collected views from the layout
            viewsToRemove.forEach {
                binding.constraintLayout.removeView(it)
            }

            // Ensure the FlowLayout references are correctly synced after removing views
            syncFlowLayoutReferencedIds()
        }


    }