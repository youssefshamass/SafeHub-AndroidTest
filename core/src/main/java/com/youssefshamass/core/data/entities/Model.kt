/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Model : Parcelable {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null
}
