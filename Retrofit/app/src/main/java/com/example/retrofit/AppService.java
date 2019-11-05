package com.example.retrofit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface AppService {
    String BASE_URL = "http://192.168.43.207/AndroidAPI/";
//    String BASE_URL = "http://192.168.1.48/AndroidAPI/";


    @Multipart
    @POST("index.php")
    Call<HashMap<String, String>> convertImage(@Part("image\"; filename=\"myfile.png\" ") RequestBody file,
                                               @Part("text") RequestBody name);
}
