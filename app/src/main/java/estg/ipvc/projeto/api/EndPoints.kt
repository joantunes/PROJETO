package estg.ipvc.projeto.api


import estg.ipvc.projeto.problems
import estg.ipvc.projeto.user
import retrofit2.Call
import retrofit2.http.*

interface EndPoints  {
    @GET("/users/")
    fun getUsers(): Call<List<user>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<user>


    @GET("/mySlim/api/logins")
    fun getProblems(): Call<List<problems>>

    @GET("/mySlim/api/delete/{idProblema}")
    fun delete(@Path("idProblema") id: Int): Call<OutputPost>



    @FormUrlEncoded
    @POST("/mySlim/api/marker")
    fun postMarker(@Field("descr") descr: String?, //FUNCAO POST LOGIN
               @Field("lat") lat: String?,
                @Field("lng") lng: String?,
                @Field("userID") userID: Int): Call<OutputPost>


    @FormUrlEncoded
    @POST("/mySlim/api/post")
    fun postIN(@Field("userName") userName: String?, //FUNCAO POST LOGIN
               @Field("pwd") pwd: String? ): Call<OutputPost>

    @FormUrlEncoded
    @POST("/mySlim/api/markercomp")
    fun postON(@Field("id") id: Int ): Call<OutputPost>//FUNCAO POST LOGIN




}