package com.example.retrorequest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface service {

    //base
    @GET("/posts")
    // suspend fun getPosts( @Query("{userId}") userId:Int): Response<ResponseBody>
    fun getPosts(): Call<List<User>>

    //test
    @GET("/posts")
    fun groupList(@Query("{userId}") userId: String): Call<List<User>>

    //get specific post details
    @GET("/posts/10")
    fun getPostsDetail(@Query("{id}") id: Int): Call<User>


    @POST("/posts")
    fun createPost(@Body post: User): Call<User>

@FormUrlEncoded
    @PUT("/posts/{id}")
    fun putPost(
        @Path("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Call<User>

    @DELETE("/posts/{id}")
    fun deletePost( @Path("id") id: Int):Call<Unit>

    /*  @PATCH("/posts/{id}")
      fun putPost(@Path("id")id:Int,@Body post: User): Call<User>
      //fun putPost(@Body post: User): Call<User>*/


}