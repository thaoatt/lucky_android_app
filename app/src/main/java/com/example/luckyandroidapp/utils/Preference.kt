package com.example.luckyandroidapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

private const val IS_LOGIN = "is_login"
private const val IS_REGISTERED = "is_registered"
private const val PREFETCH_IMAGE_ENABLED = "prefetch_images"
private const val DARK_THEME_MODE = "dark_theme"
private const val IS_FIRST_BOOT = "is_first_boot"
private const val HOME_LAST_CACHED_DATE = "home_last_cached_date"
private const val IS_VERTICAL_VIEWER = "is_vertical_viewer"
private const val LAST_VERSION = "last_version"
private const val NIGHT_MODE_VALUE = "NIGHT_MODE_VALUE"
private const val BACKGROUND_COLOR = "BACKGROUND_COLOR"
private const val ALPHA_COLOR = "ALPHA_COLOR"
private const val IS_SETTING_NIGHT_MODE = "IS_SETTING_NIGHT_MODE"

val Context.pref: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)

var SharedPreferences.isLogin: Boolean
    get() = getBoolean(IS_LOGIN, false)
    set(value) = edit().putBoolean(IS_LOGIN, value).apply()

var SharedPreferences.isFirstBoot: Boolean
    get() = getBoolean(IS_FIRST_BOOT, true)
    set(value) = edit().putBoolean(IS_FIRST_BOOT, value).apply()

var SharedPreferences.lastVersion: Int
    get() = getInt(LAST_VERSION, 1)
    set(value) = edit().putInt(LAST_VERSION, value).apply()

var SharedPreferences.darkThemeMode: Int
    get() = getInt(DARK_THEME_MODE, darkThemeMode("default"))
    set(value) = edit().putInt(DARK_THEME_MODE, value).apply()

var SharedPreferences.isPrefetchImageEnabled: Boolean
    get() = getBoolean(PREFETCH_IMAGE_ENABLED, true)
    set(value) = edit().putBoolean(PREFETCH_IMAGE_ENABLED, value).apply()

var SharedPreferences.homeLastCachedDayOfYear: Int
    get() = getInt(HOME_LAST_CACHED_DATE, -1)
    set(value) = edit().putInt(HOME_LAST_CACHED_DATE, value).apply()

var SharedPreferences.isVerticalViewer: Boolean
    get() = getBoolean(IS_VERTICAL_VIEWER, true)
    set(value) = edit().putBoolean(IS_VERTICAL_VIEWER, value).apply()

var SharedPreferences.nightModeValue: Float
    get() = getFloat(NIGHT_MODE_VALUE, 0.5f)
    set(value) = edit().putFloat(NIGHT_MODE_VALUE, value).apply()

var SharedPreferences.alphaValue: Int
    get() = getInt(ALPHA_COLOR, 0)
    set(value) = edit().putInt(ALPHA_COLOR, value).apply()

var SharedPreferences.isSettingNightMode: Boolean
    get() = getBoolean(IS_SETTING_NIGHT_MODE, false)
    set(value) = edit().putBoolean(IS_SETTING_NIGHT_MODE, value).apply()

fun darkThemeMode(value: String?): Int {
    return when (value) {
        "default" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        "on" -> AppCompatDelegate.MODE_NIGHT_YES
        "off" -> AppCompatDelegate.MODE_NIGHT_NO
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}
