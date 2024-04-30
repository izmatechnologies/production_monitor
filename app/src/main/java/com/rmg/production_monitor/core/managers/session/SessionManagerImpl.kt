package com.rmg.production_monitor.core.managers.session

import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.managers.preference.AppPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor(
    private val appPreference: AppPreference
) : SessionManager {


    companion object {

        const val AUTH_TOKEN = "auth_token"

        const val LINE_ID = "line_id"
        const val BUTTON_KEY = "button_key"
        const val USER_TYPE = "user_type"
        const val USER_NAME = "user_name"
        const val  UNIT_ID="UNIT"
        const val  PLANT_ID="PLANT"
    }


    override fun saveAuthToken(token: String?) {

        appPreference.storeSting(AUTH_TOKEN, token)

    }


    override fun fetchAuthToken(): String? {
        return appPreference.getSting(AUTH_TOKEN, null)
    }

    override fun clearAuthToken() {
        appPreference.remove(AUTH_TOKEN)
    }

    override fun clearSession() {
        appPreference.remove(AUTH_TOKEN)
        appPreference.remove(LINE_ID)
        appPreference.remove(BUTTON_KEY)
        appPreference.remove(USER_TYPE)
    }

    override fun clearUserType() {
       appPreference.remove(USER_TYPE)
    }

    override fun saveUserType(userType: String?) {
        appPreference.storeSting(USER_TYPE, userType)
    }

    override fun getUserType(): String? {
        return appPreference.getSting(USER_TYPE, Constants.UserType.SWING_LINE_IN_TYPE_USER.value) // By default sewing line in
    }


//    override fun savePlantId(plantId: Int) {
//
//        appPreference.getInt(PLANT_ID, plantId)
//
//    }
//
//    override fun fetchPlantId(): Int {
//        return prefs.getInt(PLANT_ID, 0)
//    }

    override fun saveLineId(lineId: Int) {

        appPreference.getInt(LINE_ID, lineId)

    }

    override fun fetchLineId(): Int {
        return appPreference.getInt(LINE_ID, 0)
    }

    override fun clearLineId() {

        appPreference.remove(LINE_ID)

    }

    override fun saveButtonKey(key: String?) {

        appPreference.storeSting(BUTTON_KEY, key)

    }

    override fun fetchButtonKey(): String? {
        return appPreference.getSting(BUTTON_KEY, null)
    }

    override fun clearButtonKey() {

        appPreference.remove(BUTTON_KEY)

    }

    override fun saveUserName(userName: String?) {
        appPreference.storeSting(USER_NAME, userName)
    }

    override fun getUserName(): String? {
     return   appPreference.getSting(USER_NAME, null)
    }

    override fun saveUnitId(unit: String?) {
        if (unit != null) {
            appPreference.storeSting(UNIT_ID, unit)
        }
    }

    override fun getUnitId(): String? {
        return appPreference.getSting(UNIT_ID, null)
    }

    override fun savePlantId(plant: String?) {
            appPreference.storeSting(PLANT_ID,plant)

    }

    override fun getPlantId(): String? {
        return appPreference.getSting(PLANT_ID, null)
    }
}
