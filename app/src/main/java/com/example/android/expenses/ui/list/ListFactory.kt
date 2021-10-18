package com.example.android.expenses.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.expenses.database.PaymentRepository

class ListFactory (
    private val repository: PaymentRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}