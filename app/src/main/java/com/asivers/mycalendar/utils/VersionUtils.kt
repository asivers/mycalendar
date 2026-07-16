package com.asivers.mycalendar.utils

fun shouldShowWhatsNewDialog(lastSeenVersion: Int, buildVersion: Int): Boolean {
    if (lastSeenVersion == 17 && buildVersion == 19) {
        // workaround to not show 'new dark theme' popup twice
        return false
    }
    return true
}
