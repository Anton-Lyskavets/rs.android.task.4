package com.example.android.expenses.ui.list.adapter

import android.text.format.DateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android.expenses.databinding.ListItemBinding
import com.example.android.expenses.model.Payment

class ListViewHolder(
    private val binding: ListItemBinding,
    private val listener: ListListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(payment: Payment?) {
        binding.apply {
            textViewName.text = payment?.name
            textViewCost.text = "${(payment?.cost)?.format(2)}"
            textViewCategory.text = payment?.category?.replaceFirstChar { char -> char.uppercase() }
            textViewDate.text = DateFormat.format("dd.MM.yyyy, HH:mm", payment?.date).toString()
        }
        initButtonsListeners(payment)
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun initButtonsListeners(payment: Payment?) {

        binding.imageViewDel.setOnClickListener {
            payment?.let { it1 -> listener.deleteItem(it1) }

        }

        itemView.setOnLongClickListener(View.OnLongClickListener {
            payment?.let { it1 -> listener.onNodeLongClick(it1.id) }
            true
        })
    }
}