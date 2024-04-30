package com.rmg.production_monitor.core.managers.session


interface SessionManager {


    fun saveAuthToken(token: String?)
    fun fetchAuthToken(): String?
    fun clearAuthToken()
    fun clearSession()
    fun clearUserType()
    fun saveUserType(userType: String?)
    fun getUserType(): String?

//    fun savePlantId(plantId: Int)
//    fun fetchPlantId() : Int

    fun saveLineId(lineId: Int)
    fun fetchLineId() : Int

    fun clearLineId()

    fun saveButtonKey(key: String?)
    fun fetchButtonKey() : String?

    fun clearButtonKey()

    fun saveUserName(userName: String?)
    fun getUserName(): String?


    fun saveUnitId(unit: String?)
    fun getUnitId() : String?

    fun savePlantId(unit: String?)
    fun getPlantId() : String?

}
