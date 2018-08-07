package rohit.bman.thelazybaboons.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rohit.bman.thelazybaboons.models.BaseResponse;
import rohit.bman.thelazybaboons.models.DomainResponse;
import rohit.bman.thelazybaboons.models.HolidayResponse;
import rohit.bman.thelazybaboons.models.LoginResponse;
import rohit.bman.thelazybaboons.models.RegisterResponse;

public interface Apis {

    @GET("domains")
    Call<DomainResponse> getDomains();


    @POST("register")
    @FormUrlEncoded
    Call<RegisterResponse> registerUser(@Field("domain") int domain, @Field("email") String email, @Field("name") String name, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> loginUser(@Field("domain") int domain, @Field("email") String email, @Field("password") String password);

    @POST("attendance/mark")
    @FormUrlEncoded
    Call<BaseResponse> markAttendance(@Header("token") String token, @Field("latitude") double latitude, @Field("longitude") double longitude, @Field("key") String qrRawData);


    @GET("holidays")
    Call<HolidayResponse> getHolidays(@Header("token") String token);


}
