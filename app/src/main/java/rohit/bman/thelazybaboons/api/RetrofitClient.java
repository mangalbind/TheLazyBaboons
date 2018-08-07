package rohit.bman.thelazybaboons.api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static Retrofit INSTANCE = null;

    public static Retrofit getInstance() {

        if (INSTANCE == null) {

            INSTANCE = new Retrofit.Builder()
                    .baseUrl("http://13.127.73.24/api/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }

        return INSTANCE;
    }

    public static Apis getApis() {

        return getInstance().create(Apis.class);

    }


}
