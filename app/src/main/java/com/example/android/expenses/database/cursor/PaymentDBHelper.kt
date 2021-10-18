package com.example.android.expenses.database.cursor

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.expenses.database.*
import com.example.android.expenses.model.Payment
import java.util.*


class PaymentDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_PAYMENTS_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $DB_PAYMENTS_NAME(" +
                    "$PAYMENT_NAME $TYPE_TEXT, " +
                    "$PAYMENT_COST $TYPE_REAL, " +
                    "$PAYMENT_CATEGORY $TYPE_TEXT, " +
                    "$ID $TYPE_INTEGER PRIMARY KEY, " +
                    "$PAYMENT_DATE $TYPE_INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DB_PAYMENTS_NAME")
        onCreate(db)
    }

    private fun getCursorAllPayments(filter: String?): Cursor {
        return if (filter == null)
            readableDatabase.rawQuery("SELECT * FROM $DB_PAYMENTS_NAME", null)
        else
            readableDatabase.rawQuery(
                "SELECT * FROM $DB_PAYMENTS_NAME WHERE $PAYMENT_CATEGORY = '$filter'",
                null
            )
    }

    fun getPayment(id: Int): Payment? {
        var payment: Payment? = null
        val cursor =
            readableDatabase.rawQuery("SELECT * FROM $DB_PAYMENTS_NAME WHERE $ID = '$id'", null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_NAME))
                    val cost = cursor.getDouble(cursor.getColumnIndexOrThrow(PAYMENT_COST))
                    val category = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_CATEGORY))
                    val _id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                    val date = cursor.getLong(cursor.getColumnIndexOrThrow(PAYMENT_DATE))
                    payment = Payment(name, cost, category, _id, Date(date))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {

        } finally {
            cursor.close()
        }
        return payment
    }

    fun getPaymentsList(filter: String?): List<Payment> {
        val payments = mutableListOf<Payment>()
        val cursor = getCursorAllPayments(filter)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_NAME))
                    val cost = cursor.getDouble(cursor.getColumnIndexOrThrow(PAYMENT_COST))
                    val category = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_CATEGORY))
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                    val date = cursor.getLong(cursor.getColumnIndexOrThrow(PAYMENT_DATE))
                    payments.add(Payment(name, cost, category, id, Date(date)))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {

        } finally {
            cursor.close()
        }
        return payments
    }
}