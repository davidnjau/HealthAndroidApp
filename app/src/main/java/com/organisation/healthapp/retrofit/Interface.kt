package com.organisation.healthapp.retrofit

import com.organisation.healthapp.helperclass.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {

    @POST("/api/v1/auth/registration")
    fun registerUser(@Body userRegister: RegisterRequest): Call<RegisterResponse>

    @POST("/api/v1/auth/login")
    fun loginUser(@Body user: UserLogin): Call<SuccessLogin>
    
    
    
//
//    @PUT("/api/v1/user/update_user")
//    fun updateUserDetails(@HeaderMap headers: Map<String, String>, @Body dbUpdateUser: DbUpdateUser): Call<DbUpdateUser>
//
//    @POST("/auth/otp-verify/")
//    fun otpVerify(@Body otpVerify: OtpVerify): Call<SuccessOtpVerify>
//
//    @GET("/easymeal/combos/")
//    fun getChefCombo(@HeaderMap headers: Map<String, String>): Call<SuccessChefCombo>
//
//    @GET("/api/v1/cafeteria/get_my_cafeteria_meals/")
//    fun getCafeteriaMeals(@HeaderMap headers: Map<String, String>): Call<CafeteriaMeals>
//
//    @GET("/easymeal/trending/")
//    fun getTrendingMeals(@HeaderMap headers: Map<String, String>): Call<List<SuccessTrending>>
//
//    @GET("/easymeal/categories/")
//    fun getMenuList(): Call<SuccessCategories>
//
//    @GET("/auth/my-profile/")
//    fun gerProfileDetails(@HeaderMap headers: Map<String, String>): Call<SuccessProfileDetails>
//
//    @POST("/easymeal/orders/create/")
//    fun createOrder(@HeaderMap headers: Map<String, String>, @Body orderDetails: OrderDetails): Call<SuccessOrderDetails>
//
//    @POST("/easymeal/orders/create-from-combo/")
//    fun createComboOrder(@HeaderMap headers: Map<String, String>, @Body orderCombo: OrderCombo): Call<SuccessChefOrderDetails>
//
//    @GET("/easymeal/my-orders/")
//    fun getMyOrders(@HeaderMap headers: Map<String, String>): Call<SuccessMyOrders>
//
//    @GET("/api/v1/cafeteria/get_my_cafeteria_orders/")
//    fun getMyCafeteriaOrders(@HeaderMap headers: Map<String, String>): Call<SuccessCafeteriaOrders>
//
//    @GET("/easymeal/orders/{id}")
//    fun getOrderDetails(@HeaderMap headers: Map<String, String>, @Path("id") id: String): Call<ResponseOrderDetails>
//
//    @GET("/api/v1/cafeteria/get_my_cafeteria_orders/{order_id}")
//    fun getCafeteriaOrderDetails(@Path("order_id") id: String): Call<ResponseCafeteriaOrderDetails>
//
//    @POST("/easymeal/orders/verify/")
//    fun getOrderData(@HeaderMap headers: Map<String, String>, @Body verifyMeal: VerifyMeal): Call<SuccessVerifyOrder>
//
//    @GET("/adm/orders/")
//    fun getOngoingOrders(@HeaderMap headers: Map<String, String>): Call<SuccessOngoingList>
//
//    @POST("/api/v1/cafeteria/verify_order/")
//    fun getDecryptOrderData(@HeaderMap headers: Map<String, String>, @Body verifyMeal: VerifyMeal)
//    : Call<ResponseCafeteriaOrderDetails>
//
//
//    @POST("/api/v1/cafeteria/update_order/")
//    fun updateStatus(@HeaderMap headers: Map<String, String>, @Body updateOrder: UpdateOrder): Call<Any>
//
//    @POST("/api/v1/cafeteria/set_selected_days/")
//    fun setSelectedDays(@HeaderMap headers: Map<String, String>, @Body dayList: List<DbSelectedMeals>): Call<ResponseSelectedDays>
//
//    @GET("/api/v1/cafeteria/get_my_selected_days/")
//    fun getMySelectedDays(@HeaderMap headers: Map<String, String>): Call<ResponseSelectedDays>
//
//
//    @Multipart
//    @POST("/upload/")
//    fun uploadFile(@Part file: MultipartBody.Part): Call<SuccessUpload>
//
//    @PUT("/easymeal/orders/edit/{id}/")
//    fun convertOrderVoucher(@HeaderMap headers: Map<String, String>, @Path("id") id: String): Call<ResponseOrderCancellation>
//
//    @GET("/easymeal/my-vouchers/")
//    fun getMyVouchers(@HeaderMap headers: Map<String, String>): Call<SuccessVoucher>
//
//    @POST("/easymeal/vouchers/create/")
//    fun createVoucher(
//        @HeaderMap headers: Map<String, String>,
//        @Body generateVoucher: GenerateVoucher
//    ): Call<SuccessVouchers>
//
//    @Multipart
//    @POST("/upload/")
//    fun uploadImage(
//
//        @Part image: MultipartBody.Part
//    ): Call<ResponseBody>
//
//    @GET("/cafeteria/items")
//    fun getCafeteriaMeal(@HeaderMap headers: Map<String, String>): Call<ResponseBody>
//
//    @GET("/cafeteria/my/meal-passes/")
//    fun getCafeteriaMealPasses(@HeaderMap headers: Map<String, String>): Call<ResponseBody>
//
//    @POST("/cafeteria/meal-pass/buy/")
//    fun buyMealPass(@HeaderMap headers: Map<String, String>,
//                    @Body buyMealPass: BuyMealPass): Call<SuccessMealPass>
//
//    @POST("/api/v1/cafeteria/create_order")
//    fun createCafeteriaOrder(@HeaderMap headers: Map<String, String>, @Body orderDetails: CreateCafeteriaOrder): Call<CreateCafeteriaOrder>
//
//    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
//    @POST("fcm/send")
//    suspend fun postNotification(
//        @Body notification: PushNotification
//    ): Response<ResponseBody>


}