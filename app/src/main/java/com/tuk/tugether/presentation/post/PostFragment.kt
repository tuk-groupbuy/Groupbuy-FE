package com.tuk.tugether.presentation.post

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentPostBinding
import com.tuk.tugether.domain.model.response.post.GetPostDetailResponseModel
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment: BaseFragment<FragmentPostBinding>(R.layout.fragment_post) {

    private val postViewModel: PostViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun initView() {
        bottomNavigationRemove()
        setClickListener()
        handleOnBackPressed()
    }

    override fun initObserver() {
        val userId = getUserIdFromPrefs()
        val postId = arguments?.getLong("postId", -1L) ?: -1L

        Log.d("PostFragment", "userId: $userId, postId: $postId")

        if (userId.isNotEmpty() && postId != -1L) {
            profileViewModel.loadUserProfile(userId)

            val requesterId = userId.toLongOrNull()
            if (requesterId != null) {
                postViewModel.fetchPostDetail(postId, requesterId)
            } else {
                Toast.makeText(requireContext(), "유저 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            postViewModel.postDetail.collect { post ->
                post?.let { bindPostDetail(it) }
            }
        }

        lifecycleScope.launchWhenStarted {
            postViewModel.errorMessage.collect { msg ->
                msg?.let {
                    //Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun getUserIdFromPrefs(): String {
        val prefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        return prefs.getString("user_id", "") ?: ""
    }

    private fun bindPostDetail(post: GetPostDetailResponseModel) = with(binding) {
        tvPostTitle.text = post.title
        tvPostPrice.text = "₩${String.format("%,d", post.price)}"
        tvPostDescription.text = post.content
        tvPostPersonnel.text =
            "현재 ${post.currentQuantity}명    ∙    최소 ${post.minParticipant}명    ∙    최대 ${post.goalQuantity}명"
        tvPostDeadline.text = post.deadline.split("T").first()

        // 이미지 로딩
        Glide.with(requireContext())
            .load("http://13.125.230.122:8080/${post.imageUrl}")
            .placeholder(R.color.gray_100)
            .into(ivPostImage)


        tvPostState.text = if (post.completed) "모집 완료" else "모집 중"
        tvPostState.setBackgroundResource(
            if (post.completed) R.drawable.shape_rect_999_gray200_fill
            else R.drawable.shape_rect_999_blue300_fill
        )

        // 판매자 여부
        clPostSellerBtn.visibility = if (post.isWriter) View.VISIBLE else View.GONE
        tvPostJoinBtn.visibility = if (post.isWriter) View.GONE else View.VISIBLE

        // 참여 상태
        when (post.participationStatus) {
            "APPROVED" -> {
                // 버튼 상태 비활성화
                tvPostJoinBtn.text = "취소하기"
                tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_gray200_fill)
                tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.gray_300))
                tvPostJoinBtn.isEnabled = false
                tvPostJoinBtn.isClickable = false
            }

            "REQUESTED" -> {
                // 버튼 상태 취소로 바꾸기
                tvPostJoinBtn.text = "취소하기"
                tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_blue400_fill)
                tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.white))
            }

            "None"-> {
                // 버튼 상태 참여로 바꾸기
                tvPostJoinBtn.text = "참여하기"
                tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_blue300_fill)
                tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.black_main))
            }
        }
    }

    // BottomNavigationView 숨기기
    private fun bottomNavigationRemove() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    // 뒤로 가기
    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun setClickListener() {
        binding.ivTopbarBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvPostJoinBtn.setOnClickListener {
            val userId = getUserIdFromPrefs().toLongOrNull()
            val postId = arguments?.getLong("postId", -1L) ?: -1L

            if (userId == null || postId == -1L) {
                Toast.makeText(requireContext(), "유효하지 않은 사용자 정보입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isJoining = binding.tvPostJoinBtn.text.toString() == "참여하기"

            if (isJoining) {
                // 참여 요청
                postViewModel.joinPost(postId, userId)

                // 버튼 상태 취소로 바꾸기
                binding.tvPostJoinBtn.text = "취소하기"
                binding.tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_blue400_fill)
                binding.tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.white))
            } else {
                // 취소 요청
                postViewModel.deleteJoinPost(postId, userId)

                // 버튼 상태 참여로 바꾸기
                binding.tvPostJoinBtn.text = "참여하기"
                binding.tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_blue300_fill)
                binding.tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.black_main))
            }
        }

    }

}