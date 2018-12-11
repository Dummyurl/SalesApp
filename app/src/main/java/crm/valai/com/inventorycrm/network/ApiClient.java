package crm.valai.com.inventorycrm.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static crm.valai.com.inventorycrm.utils.AppConstants.ROOT;

/**
 * @author by Mohit Arora on 22/9/17.
 */

public class ApiClient {
    private Retrofit retrofit = null;

    public ApiClient() {
    }

    public RestClient getClient() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").setLenient().create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .readTimeout(1000, TimeUnit.SECONDS).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(ROOT)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(RestClient.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}