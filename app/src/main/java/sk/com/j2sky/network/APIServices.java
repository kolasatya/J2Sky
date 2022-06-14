package sk.com.j2sky.network;


import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sk.com.j2sky.data.model.BaseResponse;

public interface APIServices {

    @GET("https://oxaw5hjbud.execute-api.ap-southeast-1.amazonaws.com/dev/api/v1/profiles/dedup?userId=bala001@gmail.com")
    Call<BaseResponse> getProfile(@Query("userId") String emailAddress);


//    Flowable<BaseResponse> getProfile();
    /*Call<MultipleResource> doGetListResources();

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}