package com.example.android.expenses.ui.list.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

open class SwipeCallback(
) :
    ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return  false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        return
    }

}