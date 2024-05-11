package com.rmg.production_monitor.core.service


import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import com.rmg.production_monitor.models.remote.quality.QualityModel
import retrofit2.Response
import retrofit2.http.Body


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {


//    @Deprecated("don't this authenticate_deprecated method", replaceWith = ReplaceWith("authenticate( requestBody: AuthenticationRequest)")
//        , level = DeprecationLevel.ERROR)
    @POST("/api/AnalyticAppAuth/Authenticate")
    suspend fun authenticate(@Body requestBody: AuthenticationRequest): Response<AuthenticateModel>


//    @POST("api/Auth/Authenticate2")
//    suspend fun authenticate(@Body requestBody: AuthenticationRequest): Response<AuthenticateModel>
//
//    //api call example
////    @GET("/repos/cocogitto/cocogitto/commits?per_page=20&sort=author-date")
////    suspend fun getCommitList(@Query("page") pageNumber: Int): Response<CommitModel>
////
////    @GET("/users/{user}")
////    suspend fun getUserProfile(@Path("user") userName:String): Response<UserModel>
//
//    // New select PO QC
//    @GET("api/SewingQc/GetPosForSewingQc")
//    suspend fun getNewSelectPo(): Response<NewSelectPOModel>
//
//    // POs for line history
//    @GET("api/Sewing/GetPosForLineInHistory")
//    suspend fun getPOsForLineHistory(): Response<POsForLineHistoryModel>
//
//    // Line history by user
//    @GET("api/Sewing/GetLineInHistoryByUser")
//    suspend fun getLineHistoryByUser(
//        @Query("poId") poId: Int
//    ): Response<LineHistoryByUserModel>
//
//    // Pos For Qc History By User
//    @GET("api/SewingQc/GetPosForQcHistoryByUser")
//    suspend fun getPosForQcHistoryByUser(): Response<POsForQcHistoryByUserModel>
//
//    // Get Qc History By User
//    @GET("/api/SewingQc/GetQcHistoryByUser")
//    suspend fun getQcHistoryByUser(
//        @Query("poId") poId: Int,
//        @Query("statusId") statusId: Int
//    ): Response<QCHistoryByUserModel>
//
//    // Get PO Color Size for LineIn
//    @GET("api/Sewing/GetPoColorSizeForLineIn")
//    suspend fun getPoColorSizeForLineIn(
//        @Query("sewingLineId") sewingLineId: Int,
//        @Query("poId") poId: Int
//    ) : Response<POColorSizeModel>
//
//    @POST("api/Sewing/PostSewingLineIn")
//    suspend fun createSewingLineIn(
//        @Body postLineIn: PostLineIn
//    ): Response<ResponsePostLineIn>
//
////    @GET("DropDown/GetPlants")
////    suspend fun getPlants(): Response<PlantsModel>
//
//    @GET("api/Sewing/GetUserAssignedSewingLines")
//    suspend fun getSewingLine(): Response<UserAssignedSewingLinesModel>
//
//    @GET("api/Sewing/GetPosForSewingLineIn")
//    suspend fun getPosForSewingLine(
//        @Query("sewingLineId") sewingLineId: Int,
//        @Query("PoNumber") poNumber: String
//    ): Response<PosForSewingLineResponse>
//
//    @GET("api/SewingQc/GetQcStatusForQcUser")
//    suspend fun getQcStatus(@Query("poId")poId:Int): Response<QcStatusModel>
//
//    @POST("api/SewingQc/AddSewingQc")
//    suspend fun addQc(@Body qcRequestModel: QcRequestModel): Response<AuthenticateModel>
//
//    @GET("api/SewingQc/SewingQc/GetSewingQcIssues")
//    suspend fun getSewingQcIssues(): Response<SewingQcIssuesModelResponse>
//
//
//
//
//    @GET("api/SewingQc/GetMarkingImage")
//    suspend fun getMarkingImage( @Query("poItemId") poItemId: Int): Response<MarkingImageResponse>
//
//    // Sewing Qc Operations
//    @GET("api/SewingQc/GetSewingQcOperations")
//    suspend fun getSewingQcOperations( ): Response<QCOperationResponse>
//
//    @GET("api/SewingQc/GetSewingQcIssues")
//    suspend fun getSewingQcIssues(@Query("operationId")  operationId:Int): Response<SewingQcIssuesModelResponse>
//
//    // todo refactor method name
//    @POST("api/SewingQc/SewingQcAddWithOperationAndIssue")
//    suspend fun submitSewingQcAddWithOperationAndIssue(@Body qcRequestBody: SubmitQcRequestBody): Response<ResponsePostLineIn>
//

    // Heat map
    @GET("api/HeeatMap/GetHeatmap")
    suspend fun getHeatmap(): Response<QualityModel>

    @GET("api/AnalyticsDashboard/GetWip")
    suspend fun getDashboardAnalytics(@Query("lineId") lineId: Int): Response<DashboardAnalyticsResponse>
}