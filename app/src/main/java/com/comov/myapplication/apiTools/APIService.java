package com.comov.myapplication.apiTools;

import com.comov.myapplication.datamodel.Channel;
import com.comov.myapplication.datamodel.ChannelResponse;
import com.comov.myapplication.datamodel.Login;
import com.comov.myapplication.datamodel.Message;
import com.comov.myapplication.datamodel.MessageResponse;
import com.comov.myapplication.datamodel.Post;
import com.comov.myapplication.datamodel.Token;
import com.comov.myapplication.datamodel.Users;

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
    Call<Message> postMessage(@Body Message myMessage);

    @GET("/api/message")
    Call<MessageResponse> getMessage(@Query("channel") String channel);

    @Headers("Content-Type: application/json")
    @POST("/api/channel")
    Call<Channel> postChannel(@Body Channel myChannel);

    @GET("/api/channel")
    Call<ChannelResponse> getChannel(@Query("username") String username);

    @Headers("Content-Type: application/json")
    @POST("/api/contact")
    Call<Users> postContact(@Body Login contact);

}

