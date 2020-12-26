package ipvc.estg.projeto1.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call


interface EndPoints {

    @FormUrlEncoded
    @POST("/myslim/api/User")
    fun postLogin(
        @Field("name") name: String?,
        @Field("password") password: String?): Call<OutputPost>

}