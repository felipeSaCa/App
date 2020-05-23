package com.comov.myapplication.apiTools;

import com.comov.myapplication.datamodel.Channel;
import com.comov.myapplication.datamodel.ChannelResponse;
import com.comov.myapplication.datamodel.Login;
import com.comov.myapplication.datamodel.Message;
import com.comov.myapplication.datamodel.MessageResponse;
import com.comov.myapplication.datamodel.Token;
import com.comov.myapplication.datamodel.UserResponse;
import com.comov.myapplication.datamodel.Users;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @Headers("Content-Type: application/json")
    @POST("/api/user")
    Call<Users> postRegister (@Body Users myUsers);

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    Call<Token> postLogin (@Body Login data);

    @GET("/api/message")
    Call<Token> getLogin(@Header("authorization") String auth,
                         @Query("username") String username);

    @Headers("Content-Type: application/json")
    @POST("/api/message")
    Call<Message> postMessage(@Header("authorization") String auth,
                              @Body Message myMessage);

    @GET("/api/message")
    Call<MessageResponse> getMessage(@Header("authorization") String auth,
                                     @Query("channel") String channel);

    @GET("/api/message")
    Call<Integer> getNumberMessages(@Header("authorization") String auth,
                                    @Query("channel") String channel);

    @Headers("Content-Type: application/json")
    @POST("/api/channel")
    Call<Channel> postChannel(@Header("authorization") String auth,
                              @Body Channel myChannel);

    @GET("/api/channel")
    Call<ChannelResponse> getChannel(@Header("authorization") String auth,
                                     @Query("username") String username);

    @DELETE("/api/channel")
    Call<ResponseBody> deleteChannel(@Header("authorization") String auth,
                                     @Query("channel") String channel);

    @Headers("Content-Type: application/json")
    @POST("/api/channel/user")
    Call<ResponseBody> postUserChannel(@Header("authorization") String auth,
                                       @Body Login addUser);

    @Headers("Content-Type: application/json")
    @POST("/api/contact")
    Call<Users> postContact(@Header("authorization") String auth,
                            @Body Login contact);

    @GET("/api/user")
    Call<UserResponse> getUser(@Header("authorization") String auth,
                               @Query("username") String username);

    @POST("/api/upload")
    Call<ResponseBody> postPic(@Header("authorization") String auth,
                               @Body Message message);

    @POST("/api/location")
    Call<ResponseBody> postLocation(@Header("authorization") String auth,
                               @Body Message message);

}

