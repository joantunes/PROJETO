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


    @FormUrlEncoded
    @POST("/mySlim/api/post")
    fun postIN(@Field("userName") userName: String?, //FUNCAO POST LOGIN
               @Field("pwd") pwd: String? ): Call<OutputPost>
}