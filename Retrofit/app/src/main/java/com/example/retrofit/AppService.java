package com.example.retrofit;

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
    /*
    param:
        pid : String
        photo : String

    response:
        status : String
        response : String
    */
    @Multipart
    @POST("API_MASTER/api_image_upload.php")
    Call<Response> uploadPhoto(@Part MultipartBody.Part photo);
}
