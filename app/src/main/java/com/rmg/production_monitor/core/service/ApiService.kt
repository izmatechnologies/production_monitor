package com.faisal.quc.core.service

import com.faisal.quc.models.remote.authentication.AuthenticateModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.faisal.quc.models.remote.MarkingImageResponse
import com.faisal.quc.models.remote.QCOperationResponse
import com.faisal.quc.models.remote.QcRequestModel
import com.faisal.quc.models.remote.QcStatusModel.QcStatusModel
import com.faisal.quc.models.remote.SubmitQcRequestBody
import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.faisal.quc.models.remote.lineHistoryByUser.LineHistoryByUserModel
import com.faisal.quc.models.remote.newselectpo.NewSelectPOModel
import com.faisal.quc.models.remote.poColorSize.POColorSizeModel
import com.faisal.quc.models.remote.posForLineHistory.POsForLineHistoryModel
import com.faisal.quc.models.remote.posForQcHistoryByUser.POsForQcHistoryByUserModel
import com.faisal.quc.models.remote.posforsewingLine.PosForSewingLineResponse
import com.faisal.quc.models.remote.postsewinglinein.PostLineIn
import com.faisal.quc.models.remote.postsewinglinein.ResponsePostLineIn
import com.faisal.quc.models.remote.qcHistoryByUser.QCHistoryByUserModel
import com.faisal.quc.models.remote.sewingQcIssues.SewingQcIssuesModelResponse
import com.faisal.quc.models.remote.sewingline.UserAssignedSewingLinesModel

import retrofit2.http.GET

import retrofit2.http.Query


interface ApiService {


    @Deprecated("don't this authenticate_deprecated method", replaceWith = ReplaceWith("authenticate( requestBody: AuthenticationRequest)")
        , level = DeprecationLevel.ERROR)
    @POST("api/Auth/Authenticate")
    suspend fun authenticate_deprecated(@Body requestBody: AuthenticationRequest): Response<AuthenticateModel>
    @POST("api/Auth/Authenticate2")
    suspend fun authenticate(@Body requestBody: AuthenticationRequest): Response<AuthenticateModel>

    //api call example
//    @GET("/repos/cocogitto/cocogitto/commits?per_page=20&sort=author-date")
//    suspend fun getCommitList(@Query("page") pageNumber: Int): Response<CommitModel>
//
//    @GET("/users/{user}")
//    suspend fun getUserProfile(@Path("user") userName:String): Response<UserModel>

    // New select PO QC
    @GET("api/SewingQc/GetPosForSewingQc")
    suspend fun getNewSelectPo(): Response<NewSelectPOModel>

    // POs for line history
    @GET("api/Sewing/GetPosForLineInHistory")
    suspend fun getPOsForLineHistory(): Response<POsForLineHistoryModel>

    // Line history by user
    @GET("api/Sewing/GetLineInHistoryByUser")
    suspend fun getLineHistoryByUser(
        @Query("poId") poId: Int
    ): Response<LineHistoryByUserModel>

    // Pos For Qc History By User
    @GET("api/SewingQc/GetPosForQcHistoryByUser")
    suspend fun getPosForQcHistoryByUser(): Response<POsForQcHistoryByUserModel>

    // Get Qc History By User
    @GET("/api/SewingQc/GetQcHistoryByUser")
    suspend fun getQcHistoryByUser(
        @Query("poId") poId: Int,
        @Query("statusId") statusId: Int
    ): Response<QCHistoryByUserModel>

    // Get PO Color Size for LineIn
    @GET("api/Sewing/GetPoColorSizeForLineIn")
    suspend fun getPoColorSizeForLineIn(
        @Query("sewingLineId") sewingLineId: Int,
        @Query("poId") poId: Int
    ) : Response<POColorSizeModel>

    @POST("api/Sewing/PostSewingLineIn")
    suspend fun createSewingLineIn(
        @Body postLineIn: PostLineIn
    ): Response<ResponsePostLineIn>

//    @GET("DropDown/GetPlants")
//    suspend fun getPlants(): Response<PlantsModel>

    @GET("api/Sewing/GetUserAssignedSewingLines")
    suspend fun getSewingLine(): Response<UserAssignedSewingLinesModel>

    @GET("api/Sewing/GetPosForSewingLineIn")
    suspend fun getPosForSewingLine(
        @Query("sewingLineId") sewingLineId: Int,
        @Query("PoNumber") poNumber: String
    ): Response<PosForSewingLineResponse>

    @GET("api/SewingQc/GetQcStatusForQcUser")
    suspend fun getQcStatus(@Query("poId")poId:Int): Response<QcStatusModel>

    @POST("api/SewingQc/AddSewingQc")
    suspend fun addQc(@Body qcRequestModel: QcRequestModel): Response<AuthenticateModel>

    @GET("api/SewingQc/SewingQc/GetSewingQcIssues")
    suspend fun getSewingQcIssues(): Response<SewingQcIssuesModelResponse>




    @GET("api/SewingQc/GetMarkingImage")
    suspend fun getMarkingImage( @Query("poItemId") poItemId: Int): Response<MarkingImageResponse>

    // Sewing Qc Operations
    @GET("api/SewingQc/GetSewingQcOperations")
    suspend fun getSewingQcOperations( ): Response<QCOperationResponse>

    @GET("api/SewingQc/GetSewingQcIssues")
    suspend fun getSewingQcIssues(@Query("operationId")  operationId:Int): Response<SewingQcIssuesModelResponse>

    // todo refactor method name
    @POST("api/SewingQc/SewingQcAddWithOperationAndIssue")
    suspend fun submitSewingQcAddWithOperationAndIssue(@Body qcRequestBody: SubmitQcRequestBody): Response<ResponsePostLineIn>



}