package com.asivers.mycalendar.utils

import com.asivers.mycalendar.enums.ExistingLocale

fun getExistingLocaleForLanguage(language: String): ExistingLocale {
    return enumValues<ExistingLocale>()
        .find { language == it.assetName }
        ?: ExistingLocale.EN
}
