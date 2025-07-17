package com.tuk.tugether.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuk.tugether.databinding.ItemPostBinding
import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel

class PostAdapter(
    private val posts: List<GetAllPostResponseModel>,
    private val onItemClick: (GetAllPostResponseModel) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: GetAllPostResponseModel) {
            binding.tvPostTitle.text = post.title
            binding.tvPostDeadline.text = post.deadlineText
            binding.tvPostPrice.text = "${post.price}원"
            binding.tvPostPersonnel.text = "${post.currentQuantity} / ${post.goalQuantity}"

            // 이미지 로딩
            Glide.with(binding.root.context)
                .load("http://13.125.230.122:8080/${post.imageUrl.replace("\\", "/")}")
                .into(binding.ivPostImage)

            // 클릭 리스너 연결
            binding.root.setOnClickListener {
                onItemClick(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size
}
