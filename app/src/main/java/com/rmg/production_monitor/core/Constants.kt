package com.rmg.production_monitor.core

object Constants {

     const val INVALID_ID = -1

    object BottomSheetTag{
        const val OPERATION_B_S="ShowOperation"
        const val ISSUE_B_S="ShowIssue"
    }

    enum class NetworkResponseStatus(val statusCode: Int){
        UNAUTHORIZED(401),
        SERVER_ERROR(500)
    }
    enum class NetworkType(val value:String){
        UNKNOWN("UNKNOWN"),
        WIFI("WIFI"),
        CELLULAR("CELLULAR"),
        ETHERNET("ETHERNET")
    }



    enum class UserType(val value:String){
        SWING_LINE_IN_TYPE_USER("UT_03"),
        QC_TYPE_USER("UT_04")
    }
    object FragmentKey{
        const val PO_ITEM_ID="po_item_id"
        const val PO_ID="po_id"
        const val QC_STATUS_ID="qc_status_id"
        const val QC_STATUS_NAME="qc_status_name"
        const val UNIT_LIST="unit"
        const val PLANT_LIST="plant"
        const val LINE_IN_LIST="linein"

    }



}