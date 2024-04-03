package com.rmg.production_monitor.core.managers.preference

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.rmg.production_monitor.core.Config
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppPreference {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(Config.Storage.APPLICATION_PREFERENCE_NAME, Context.MODE_PRIVATE)


    private fun getEditor(): Editor {
        return prefs.edit()
    }

    override fun storeSting(key: String, value: String?) {
        getEditor()
            .putString(key, value)
            .apply()

    }

    override fun getSting(key: String, defaultValue: String?): String? {
        return prefs.getString(key, defaultValue)
    }

    override fun storeInt(key: String, value: Int) {
        getEditor()
            .putInt(key, value)
            .apply()

    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }


    override fun remove(key: String) {
        getEditor()
            .remove(key)
            .apply()
    }
}