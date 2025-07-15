package com.tuk.tugether.presentation.post

import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentCreatePostBinding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment: BaseFragment<FragmentCreatePostBinding>(R.layout.fragment_create_post) {

    private lateinit var dateBottomSheetBehavior: BottomSheetBehavior<View>

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivCreatePostImage.setImageURI(it)
            binding.clCreatePostImage.visibility = View.GONE
        }
    }

    override fun initView() {
        bottomNavigationRemove()
        setClickListener()
        initBottomSheets()
        initBottomSheetActions()
    }

    override fun initObserver() {}

    private fun bottomNavigationRemove() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun setClickListener() {
        binding.tvCreatePostCompleteBtn.setOnSingleClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.createPostFragment, true)
                .build()
            findNavController().navigate(R.id.goToPost, null, navOptions)
        }

        binding.ivTopbarBack.setOnClickListener { findNavController().popBackStack() }

        binding.ivCreatePostImageBtn.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        binding.tvCreatePostDeadline.setOnClickListener { toggleBottomSheetState(dateBottomSheetBehavior) }
    }

    private fun initBottomSheets() {
        dateBottomSheetBehavior = createBottomSheet(binding.clBottomSheetDate)
    }

    private fun createBottomSheet(sheet: View): BottomSheetBehavior<View> {
        return BottomSheetBehavior.from(sheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 0
            isHideable = true
            skipCollapsed = true
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    handleBackgroundVisibility(newState)
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    private fun handleBackgroundVisibility(newState: Int) {
        binding.viewCreatePostBottomSheetBg.visibility = if (newState == BottomSheetBehavior.STATE_HIDDEN) View.GONE else View.VISIBLE
    }

    private fun toggleBottomSheetState(bottomSheetBehavior: BottomSheetBehavior<View>) {
        bottomSheetBehavior.state = if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initBottomSheetActions() {
        binding.tvCreatePostDateSaveBtn.setOnClickListener {
            val day = binding.pickerDateStart.dayOfMonth
            val month = binding.pickerDateStart.month + 1
            val year = binding.pickerDateStart.year
            val startDate = String.format("%04d-%02d-%02d", year, month, day)
            binding.tvCreatePostDeadline.text = startDate

            dateBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

    }
}
