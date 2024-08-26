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
        context.getSharedPreferences(
            Config.Storage.APPLICATION_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

    private companion object {

        const val PAGE_FREEZE_FLAG = "page_freeze_flag"
        const val USER_NAME = "user_name"
        const val USER_TOKEN = "user_token"
        const val LINE_ID = "line_id"
        const val UNIT_ID = "unit_id"
        const val PLANT_ID = "plait_id"
        const val NEW_BASE_URL = "base_url"
        const val SELECTED_LINE_NAME = "selected_line_name"

    }

    var isPageFreezeFlag: Boolean
        get() {
            return prefs.getBoolean(PAGE_FREEZE_FLAG, false)
        }
        set(value) {
            storeBoolean(PAGE_FREEZE_FLAG, value)
        }

    var userName: String?
        get() {
            return prefs.getString(USER_NAME, null)
        }
        set(value) {
            storeSting(USER_NAME, value)
        }


    var userToken: String?
        get() {
            return prefs.getString(USER_TOKEN, null)
        }
        set(value) {
            storeSting(USER_TOKEN, value)
        }


    var lineId: Int?
        get() {
            return prefs.getInt(LINE_ID, 0)
        }
        set(value) {
            val s = value ?: -5
            storeInt(LINE_ID, s)
        }

    var unitId: Int?
        get() {
            return prefs.getInt(UNIT_ID, 0)
        }
        set(value) {
            val s = value ?: -5
            storeInt(UNIT_ID, s)
        }


    var plantId: Int?
        get() {
            return prefs.getInt(PLANT_ID, 0)
        }
        set(value) {
            val s = value ?: -5
            storeInt(PLANT_ID, s)
        }


    var baseUrl: String?
        get() {
            return prefs.getString(NEW_BASE_URL, "")
        }
        set(value) {
            val s = value ?: ""
            storeSting(NEW_BASE_URL, s)
        }


    var selectedLineName:String?
        get() {
            return prefs.getString(SELECTED_LINE_NAME,"")
        }

        set(value) {
            val lineName=value?:""
            storeSting(SELECTED_LINE_NAME,lineName)
        }


    private fun getEditor(): Editor {
        return prefs.edit()
    }

    override fun storeSting(key: String, value: String?) {
        getEditor()
            .putString(key, value)
            .apply()

    }


    override fun storeInt(key: String, value: Int) {
        getEditor()
            .putInt(key, value)
            .apply()

    }


    override fun storeBoolean(key: String, value: Boolean) {
        getEditor()
            .putBoolean(key, value)
            .apply()
    }



    override fun remove(key: String) {
        getEditor()
            .remove(key)
            .apply()
    }

    fun clearAll() {
        remove(PAGE_FREEZE_FLAG)
        remove(USER_TOKEN)
        remove(LINE_ID)
        remove(UNIT_ID)
        remove(PLANT_ID)

    }
}