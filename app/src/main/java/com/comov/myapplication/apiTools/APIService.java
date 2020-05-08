package com.comov.myapplication.apiTools;

import com.comov.myapplication.datamodel.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @Headers("Content-Type: application/json")
    @POST("/api/user")
    Call<Post> postRegister (@Body Users myUsers);

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    Call<Token> postLogin (@Body Login data);

    @Headers("Content-Type: application/json")
    @POST("/api/message")
    Call<Post> postMessage(@Body Message myMessage);

    @Headers("Content-Type: application/json")
    @GET("/api/message")
    Call<Message> getMessage(@Body Message messages);

    @Headers("Content-Type: application/json")
    @POST("/api/channel")
    Call<Post> postChannel(@Body Channel myChannel);

    @GET("/api/channel")
    Call<Channels> getChannel(@Query("username") String username);

}

