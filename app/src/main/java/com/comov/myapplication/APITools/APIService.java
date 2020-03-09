package com.comov.myapplication.APITools;

import com.comov.myapplication.datamodel.Login;
import com.comov.myapplication.datamodel.Post;
import com.comov.myapplication.datamodel.Users;

import java.util.List;

import okhttp3.ResponseBody;
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

    @Headers("Content-Type: application/json")
    @POST("/user")
    Call<Post> postRegister (@Body Users myPost);

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<ResponseBody> postLogin (@Body Login data);

}

