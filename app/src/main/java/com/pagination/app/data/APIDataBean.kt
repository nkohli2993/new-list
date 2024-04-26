package com.pagination.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class APIDataBean(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
) : Parcelable