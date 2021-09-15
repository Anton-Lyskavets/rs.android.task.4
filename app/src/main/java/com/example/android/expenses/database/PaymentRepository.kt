package com.example.android.expenses.database

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.android.expenses.database.cursor.PaymentDBHelper
import com.example.android.expenses.database.room.PaymentDAO
import com.example.android.expenses.model.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PaymentRepository(
    private val paymentDAO: PaymentDAO,
    private val application: Application,
    private val dbHelper: PaymentDBHelper = PaymentDBHelper(application)
) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val management = preferences.getString("list_management", ROOM).toString().trim()
    private val asSort = preferences.getBoolean("sort", false)
    private val sort = preferences.getString("list_sort", ID).toString().trim()
    private val filter = preferences.getString("category", NONE).toString().trim()
    private val asFilter = preferences.getBoolean("filter", false)

    fun allPayments(): Flow<List<Payment>> {
        return if (management == ROOM) {
            if (asFilter) {
                paymentDAO.getFilter(filter).map { sortListPayments(it) }
            } else
                paymentDAO.getAllElements().map { sortListPayments(it) }
        } else {
            if (asFilter) {
                flowOf(sortListPayments(dbHelper.getPaymentsList(filter)))
            } else
                flowOf(sortListPayments(dbHelper.getPaymentsList(null)))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payment: Payment) {
        if (management == ROOM)
            paymentDAO.insert(payment)
        else {
            val contentValues = ContentValues()
            contentValues.put(PAYMENT_NAME, payment.name)
            contentValues.put(PAYMENT_COST, payment.cost)
            contentValues.put(PAYMENT_CATEGORY, payment.category)
            contentValues.put(PAYMENT_DATE, payment.date.time)
            dbHelper.readableDatabase.insert(DB_PAYMENTS_NAME, null, contentValues)
        }
    }

    fun getPaymentForDB(id: Int): Flow<Payment?> {
        return if (management == ROOM)
            paymentDAO.getDogDistinctUntilChanged(id)
        else flowOf(dbHelper.getPayment(id))
    }

    suspend fun deletePayment(payment: Payment) {
        Log.i("MyLog","deletePayment db.execSQL ${payment.toString()}")

        if (management == ROOM)
            paymentDAO.delete(payment)
        else {
            val db: SQLiteDatabase = application.applicationContext.openOrCreateDatabase(
                DB_PAYMENTS_NAME, Context.MODE_PRIVATE, null
            )
            try {
             val delete =   db.delete(DB_PAYMENTS_NAME,"$ID = ${payment.id}",null)
                Log.i("MyLog","deletePayment db.execSQL ${payment.toString()}")

//                db.execSQL("DELETE FROM $DB_PAYMENTS_NAME WHERE $ID = '${payment.id}'")
            } catch (e: Exception) {

            } finally {
                db.close()
            }
        }
    }

    suspend fun updatePayment(payment: Payment) {
        if (management == ROOM)
            paymentDAO.update(payment)
        else {
            val db: SQLiteDatabase = application.applicationContext.openOrCreateDatabase(
                DB_PAYMENTS_NAME,
                Context.MODE_PRIVATE,
                null
            )
            try {
                db.execSQL(
                    "UPDATE $DB_PAYMENTS_NAME SET " +
                            "$PAYMENT_NAME = '${payment.name}', " +
                            "$PAYMENT_COST = '${payment.cost}', " +
                            "$PAYMENT_CATEGORY = '${payment.category}', " +
                            "$PAYMENT_DATE = '${payment.date.time}' " +
                            "WHERE $ID = '${payment.id}'"
                )
            } catch (e: Exception) {

            } finally {
                db.close()
            }
        }
    }

    private fun sortListPayments(list: List<Payment>): List<Payment> {
        return if (asSort) {
            when (sort) {
                PAYMENT_NAME -> list.sortedByDescending { it.name }
                PAYMENT_COST -> list.sortedByDescending { it.cost }
                PAYMENT_CATEGORY -> list.sortedByDescending { it.category }
                PAYMENT_DATE -> list.sortedByDescending { it.date }
                else -> list.sortedByDescending { it.id }
            }
        } else {
            when (sort) {
                PAYMENT_NAME -> list.sortedBy { it.name }
                PAYMENT_COST -> list.sortedBy { it.cost }
                PAYMENT_CATEGORY -> list.sortedBy { it.category }
                PAYMENT_DATE -> list.sortedBy { it.date }
                else -> list.sortedBy { it.id }
            }
        }
    }
}