package com.example.android.expenses.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.expenses.database.PaymentRepository
import com.example.android.expenses.model.Payment
import kotlinx.coroutines.launch

class AddViewModel(
    private val repository: PaymentRepository,
) : ViewModel() {

    var name: String = "no name"
    var cost: Double? = 0.0
    var category: String? = null

    fun addPayment(payment: Payment) = viewModelScope.launch {
        repository.insert(payment)
    }

    fun updatePayment(payment: Payment) = viewModelScope.launch {
        repository.updatePayment(payment)
    }

}