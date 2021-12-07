package me.androidbox.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpriteModel(
    @field:[Expose SerializedName("back_default")]
    val backDefault: String
) : Parcelable