package io.thelimitbreaker

import android.app.Activity

class ThemeUtils {
    companion object ThemeUtils {
        var isLight = true
        var sTheme = R.style.AppTheme_Base_Light
        fun setTheme(activity: Activity) {
            activity.setTheme(sTheme)
        }
    }
}