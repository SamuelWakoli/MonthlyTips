package com.sam.monthlytips.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Tip(
        @DrawableRes val imageID: Int,
        val dayNumber: Int,
        @StringRes val title: Int,
        @StringRes val description: Int,
)

