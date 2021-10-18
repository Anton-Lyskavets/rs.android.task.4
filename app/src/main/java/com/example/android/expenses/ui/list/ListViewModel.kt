package com.example.android.expenses.ui.list

import androidx.lifecycle.*
import com.example.android.expenses.database.PaymentRepository
import com.example.android.expenses.model.Payment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow as Flow


class ListViewModel(
    private val repository: PaymentRepository,
) : ViewModel() {

    private var _payments: Flow<List<Payment>> = repository.allPayments()
    val payments: Flow<List<Payment>>
        get() = _payments

    private var _cursorPayments = flowOf<List<Payment>>()
    val cursorPayments: Flow<List<Payment>>
        get() = _cursorPayments

    private var _payment = flowOf<Payment?>()
    val payment: Flow<Payment?>
        get() = _payment

    fun getPayment(id: Int) {
        viewModelScope.launch {
            _payment = getPaymentForDB(id)
        }
    }

    fun updateCursorPayments() {
        getCursorPayments()
    }

    private fun getCursorPayments() {
        viewModelScope.launch {
            _cursorPayments = repository.allPayments()
        }
    }

    private fun getPaymentForDB(id: Int): Flow<Payment?> {
        return repository.getPaymentForDB(id)
    }

    fun deletePayment(payment: Payment) = viewModelScope.launch {
        repository.deletePayment(payment)
    }
}
