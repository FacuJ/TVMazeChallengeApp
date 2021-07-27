package com.facundojaton.tvmazechallenge.utils

import android.text.Spanned
import androidx.core.text.HtmlCompat


fun htmlTextToString(textToFormat : String): Spanned {
    return HtmlCompat.fromHtml(textToFormat, HtmlCompat.FROM_HTML_MODE_LEGACY)

}
