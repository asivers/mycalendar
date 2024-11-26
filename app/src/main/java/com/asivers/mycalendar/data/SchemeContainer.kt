package com.asivers.mycalendar.data

import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import com.asivers.mycalendar.data.scheme.TranslationScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme

data class SchemeContainer(
    var countryHoliday: CountryHolidayScheme,
    var translation: TranslationScheme,
    var color: ColorScheme,
    var size: SizeScheme
)
