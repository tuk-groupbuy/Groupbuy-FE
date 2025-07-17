package com.tuk.tugether.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.tugether.databinding.ItemAlarmBinding

class AlarmAdapter(
    private val alarms: List<Alarm>,
    private val onApprove: (postId: Long, userId: Long) -> Unit,
    private val onReject: (postId: Long, userId: Long) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    inner class AlarmViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm) {
            binding.tvAlarmTitle.text = alarm.title
            binding.tvAlarmPresent.text = "현재"
            binding.tvAlarmPersonnel.text = "${alarm.current}/${alarm.max}"

            val requestAdapter = AlarmRequestAdapter(
                alarm.requests.toMutableList(), // ✅ 여기 중요: MutableList로 변환
                onApprove,
                onReject
            )

            binding.rvAlarm.adapter = requestAdapter
            binding.rvAlarm.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarms[position])
    }

    override fun getItemCount(): Int = alarms.size
}

