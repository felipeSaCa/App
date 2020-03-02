package com.comov.myapplication.APITools;

import com.comov.myapplication.datamodel.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    // GET Posts collection
    @GET("/posts")
    Call<List<Post>> getPosts();

    // GET Post by ID
    @GET("/posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    // POST Create a new Post resource
    // Body annotation defines a single request body.
    @Headers("Content-Type: application/json")
    @POST("/posts")
    Call<Post> newPost(@Body Post myPost);

    // @FormUrlEncoded
    // @Headers("Content-Type: application/json")
    // @POST("createUser")
    // Call<Post> createUser(
    // @Field("nickname") String nickname,
    // @Field("password") String password,
    // @Field("email") String email,
    // @Field("phone") String phone
    // );
}

