package com.example.android.expenses.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.expenses.database.PaymentRepository

class AddFactory (
    private val repository: PaymentRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}