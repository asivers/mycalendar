package com.asivers.mycalendar.utils

fun shouldShowWhatsNewDialog(lastSeenVersion: Int, buildVersion: Int): Boolean {
    if (lastSeenVersion == 0) {
        // first launch after initial install
        return false
    }
    if (lastSeenVersion == 17 && buildVersion == 18) {
        // workaround to not show 'new dark theme' popup twice
        return false
    }
    return true
}
