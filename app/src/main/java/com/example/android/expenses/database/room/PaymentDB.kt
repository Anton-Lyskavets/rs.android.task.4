package com.example.android.expenses.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.expenses.database.DB_PAYMENTS_NAME
import com.example.android.expenses.database.DB_VERSION
import com.example.android.expenses.model.Payment
import com.example.android.expenses.startPayment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Payment::class], version = DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PaymentDB : RoomDatabase() {
    abstract fun paymentDAO(): PaymentDAO
    companion object {
        @Volatile
        private var INSTANCE: PaymentDB? = null
        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): PaymentDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PaymentDB::class.java,
                        DB_PAYMENTS_NAME
                    )
                        .addCallback(PaymentStartDBCallback(scope))
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
    private class PaymentStartDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val paymentDAO = database.paymentDAO()
                    paymentDAO.clear()
                    val payments = startPayment()
                    payments.forEach { paymentDAO.insert(it) }
                }
            }
        }
    }
}


