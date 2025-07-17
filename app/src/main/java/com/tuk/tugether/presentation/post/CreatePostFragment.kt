package com.tuk.tugether.presentation.post

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentCreatePostBinding
import com.tuk.tugether.domain.model.request.post.CreatePostRequestModel
import com.tuk.tugether.domain.model.request.post.UpdatePostRequestModel
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class CreatePostFragment: BaseFragment<FragmentCreatePostBinding>(R.layout.fragment_create_post) {

    private lateinit var dateBottomSheetBehavior: BottomSheetBehavior<View>
    private val postViewModel: PostViewModel by viewModels()
    private var imageUri: Uri? = null
    private var isEditMode: Boolean = false
    private var postIdForEdit: Long? = null
    private var imageUrlForEdit: String? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            binding.ivCreatePostImage.setImageURI(it)
            binding.clCreatePostImage.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        isEditMode = arguments?.getBoolean("isEditMode") == true
        postIdForEdit = arguments?.getLong("postId", -1L)?.takeIf { it != -1L }

        if (isEditMode && postIdForEdit != null) {
            val userId = getUserIdFromPrefs().toLongOrNull()
            if (userId != null) {
                postViewModel.fetchPostDetail(postIdForEdit!!, userId)
            }
        }

        bottomNavigationRemove()
        setClickListener()
        initBottomSheets()
        initBottomSheetActions()
        setupPriceFormatting()
        handleOnBackPressed()
        setupTitleLengthLimit()

    }

    override fun initObserver() {
        postViewModel.createPostResult.observe(viewLifecycleOwner) { postId ->
            if (postId != null && postId > 0) {
                val bundle = Bundle().apply {
                    putLong("postId", postId)
                }
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.createPostFragment, true)
                    .build()
                findNavController().navigate(R.id.goToPost, bundle, navOptions)
            } else {
                Toast.makeText(requireContext(), "작성 실패", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            postViewModel.postDetail.collect { post ->
                if (isEditMode && post != null) {
                    binding.etCreatePostTitle.setText(post.title)
                    binding.etCreatePostDescription.setText(post.content)
                    binding.etCreatePostMinPersonnel.setText(post.minParticipant.toString())
                    binding.etCreatePostMaxPersonnel.setText(post.goalQuantity.toString())
                    binding.etCreatePostPrice.setText("₩ ${String.format("%,d", post.price)}")
                    binding.tvCreatePostDeadline.text = post.deadline.split("T").first()

                    imageUrlForEdit = post.imageUrl
                    Glide.with(requireContext())
                        .load("http://13.125.230.122:8080/${post.imageUrl}")
                        .into(binding.ivCreatePostImage)

                    binding.clCreatePostImage.visibility = View.GONE
                }
            }
        }


        postViewModel.updatePostResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "게시글 수정에 실패했습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // 뒤로 가기
    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    // BottomNavigationView 숨기기
    private fun bottomNavigationRemove() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClickListener() {
        binding.tvCreatePostCompleteBtn.setOnSingleClickListener {
            if (!isFormValid()) return@setOnSingleClickListener

            val title = binding.etCreatePostTitle.text.toString().trim()
            val price = binding.etCreatePostPrice.text.toString().replace("[^\\d]".toRegex(), "").toInt()
            val content = binding.etCreatePostDescription.text.toString().trim()
            val goalQuantity = binding.etCreatePostMaxPersonnel.text.toString().trim().toInt()
            val minParticipants = binding.etCreatePostMinPersonnel.text.toString().trim().toInt()
            val writerId = getUserIdFromPrefs().toLong()
            val deadlineText = binding.tvCreatePostDeadline.text.toString().trim()
            val deadline = "${deadlineText}T23:59:00"

            if (isEditMode) {
                val imageUrl = imageUrlForEdit ?: run {
                    Toast.makeText(requireContext(), "수정할 이미지 정보가 없습니다", Toast.LENGTH_SHORT).show()
                    return@setOnSingleClickListener
                }

                val updateModel = UpdatePostRequestModel(
                    title = title,
                    content = content,
                    goalQuantity = goalQuantity,
                    minParticipants = minParticipants,
                    price = price,
                    imageUrl = imageUrl,
                    deadline = deadline
                )

                postIdForEdit?.let { postViewModel.updatePost(it, updateModel) }
                return@setOnSingleClickListener
            }

            val dto = CreatePostRequestModel(
                writerId = writerId,
                title = title,
                content = content,
                goalQuantity = goalQuantity,
                minParticipants = minParticipants,
                price = price,
                deadline = deadline
            )

            val gson = com.google.gson.Gson()
            val json = gson.toJson(dto.toCreatePostRequestDto())
            val dtoRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

            if (imageUri == null) {
                Toast.makeText(requireContext(), "이미지를 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)
            val imageBytes = inputStream?.readBytes()
            val filePart = MultipartBody.Part.createFormData(
                "file",
                "image.jpg",
                imageBytes!!.toRequestBody("image/*".toMediaTypeOrNull())
            )

            postViewModel.createPost(dtoRequestBody, filePart)
        }


        binding.ivTopbarBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cvCreatePostImage.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        binding.tvCreatePostDeadline.setOnClickListener {
            toggleBottomSheetState(dateBottomSheetBehavior)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFormValid(): Boolean {
        val title = binding.etCreatePostTitle.text.toString().trim()
        val priceText = binding.etCreatePostPrice.text.toString().replace("[^\\d]".toRegex(), "")
        val description = binding.etCreatePostDescription.text.toString().trim()
        val minText = binding.etCreatePostMinPersonnel.text.toString().trim()
        val maxText = binding.etCreatePostMaxPersonnel.text.toString().trim()
        val deadlineText = binding.tvCreatePostDeadline.text.toString().trim()
        val hasImage = binding.ivCreatePostImage.drawable != null

        // 필수 항목 검사
        if (title.isEmpty() || priceText.isEmpty() || description.isEmpty()
            || minText.isEmpty() || maxText.isEmpty() || deadlineText.isEmpty() || !hasImage
        ) {
            Toast.makeText(requireContext(), "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        if (description.length > 500) {
            Toast.makeText(requireContext(), "상세설명은 최대 500자까지 입력 가능합니다", Toast.LENGTH_SHORT).show()
            return false
        }

        // 숫자 변환
        val price = priceText.toIntOrNull()
        val min = minText.toIntOrNull()
        val max = maxText.toIntOrNull()

        if (price == null || min == null || max == null) {
            Toast.makeText(requireContext(), "가격과 인원수는 숫자로 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        if (price < 1000 || price > 1_000_000) {
            Toast.makeText(requireContext(), "가격은 1,000원 이상 1,000,000원 이하로 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        if (min < 1 || min > 100 || max < 1 || max > 100) {
            Toast.makeText(requireContext(), "인원수는 1명 이상 100명 이하로 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        if (min > max) {
            Toast.makeText(requireContext(), "최소 인원이 최대 인원보다 많을 수 없습니다", Toast.LENGTH_SHORT).show()
            return false
        }

        // 마감기한 검사
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now()
        val deadline = try {
            LocalDate.parse(deadlineText, formatter)
        } catch (e: Exception) {
            null
        }

        if (deadline == null || deadline.isBefore(today)) {
            Toast.makeText(requireContext(), "마감기한은 오늘 이후로 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
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
        binding.viewCreatePostBottomSheetBg.setOnClickListener {
            dateBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupPriceFormatting() {
        binding.etCreatePostPrice.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return

                isEditing = true

                val digitsOnly = s.toString().replace("[^\\d]".toRegex(), "")

                if (digitsOnly.isNotEmpty()) {
                    try {
                        val parsed = digitsOnly.toLong()
                        val formatted = "₩ " + DecimalFormat("#,###").format(parsed)
                        binding.etCreatePostPrice.setText(formatted)
                        binding.etCreatePostPrice.setSelection(formatted.length)
                    } catch (e: NumberFormatException) {
                        // 무시
                    }
                } else {
                    binding.etCreatePostPrice.setText("")
                }

                isEditing = false
            }
        })
    }

    // 제목 글자수 세기
    private fun setupTitleLengthLimit() {
        val maxLength = 30

        binding.etCreatePostTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                if (text.length > maxLength) {
                    // 초과된 문자 제거
                    val trimmed = text.substring(0, maxLength)
                    binding.etCreatePostTitle.setText(trimmed)
                    binding.etCreatePostTitle.setSelection(trimmed.length) // 커서를 맨 뒤로 이동
                }

                binding.tvCreatePostTitleCount.text = minOf(text.length, maxLength).toString()
            }
        })
    }

    private fun getUserIdFromPrefs(): String {
        val prefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        return prefs.getString("user_id", "") ?: ""
    }
}
