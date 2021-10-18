package com.example.android.expenses

import com.example.android.expenses.model.Payment


fun categories(): List<String> {
    return listOf("other", "food", "clothes", "hobby", "auto", "repair")
}

fun startPayment() : List<Payment>{
    return (0..50)
        .map { Payment("name$it", (0..100).random().toDouble(),
            categories()[(categories().indices).random()]
        ) }
        .toList()
}