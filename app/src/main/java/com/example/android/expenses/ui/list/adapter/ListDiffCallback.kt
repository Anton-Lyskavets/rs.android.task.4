package com.example.android.expenses.ui.list.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.android.expenses.model.Payment

class ListDiffCallback :
    DiffUtil.ItemCallback<Payment>() {

    override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem == newItem
    }

    @Nullable
    @Override
    override fun getChangePayload(oldItem: Payment, newItem: Payment): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}