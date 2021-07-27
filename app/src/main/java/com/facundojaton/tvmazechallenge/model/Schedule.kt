package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val time: String?,
    val days: ArrayList<String>?
) : Parcelable
