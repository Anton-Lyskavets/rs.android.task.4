package com.example.android.expenses.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.expenses.database.*
import java.util.*
@Entity(tableName = "payments")
data class Payment (
    @ColumnInfo(name = PAYMENT_NAME) var name:String?,
    @ColumnInfo(name = PAYMENT_COST) var cost: Double?,
    @ColumnInfo(name = PAYMENT_CATEGORY) var category: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = PAYMENT_DATE) val date: Date = Date()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readInt(),
        TODO("date")
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(cost)
        parcel.writeString(category)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Payment> {
        override fun createFromParcel(parcel: Parcel): Payment {
            return Payment(parcel)
        }

        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }
}
