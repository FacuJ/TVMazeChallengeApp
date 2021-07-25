package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageResponse (
    val medium: String?,
    val original: String?
): Parcelable