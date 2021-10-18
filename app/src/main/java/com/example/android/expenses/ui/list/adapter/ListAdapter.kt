package com.example.android.expenses.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.android.expenses.databinding.ListItemBinding
import com.example.android.expenses.model.Payment

class ListAdapter(
    private val listener: ListListener
) : ListAdapter<Payment, ListViewHolder>(ListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }
}


