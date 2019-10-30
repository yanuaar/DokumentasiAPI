package com.example.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    Retrofit retrofit;
    AppService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button  = findViewById(R.id.buttonUpload);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://jabarbasithme.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //ini build retrofit
        service = retrofit.create(AppService.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            Uri selectedImage = data.getData();

            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(getApplicationContext(), selectedImage, proj,null,null,null);
            Cursor cursor = loader.loadInBackground();
            if (cursor != null) {
                //blok ini untuk mendapatkan image file path
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                String imagePath = cursor.getString(columnIndex);
                cursor.close();
                if (imagePath!=null){
                    uploadPhoto(imagePath,selectedImage);
                }
            }

        }
    }

    private void uploadPhoto(String imagePath, Uri selectedImage) {
        //blok ini untuk mendapatkan image file path
        //jika image tidak null
        File file = new File(imagePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("STR_PHOTO", file.getName(), fileReqBody);

        //RequestBody pid = RequestBody.create(MediaType.parse("text/plain"), "01");
        Call<com.example.retrofit.Response> uploadPhoto = service.uploadPhoto(photo);

        uploadPhoto.enqueue(new Callback<com.example.retrofit.Response>() {
            @Override
            public void onResponse(Call<com.example.retrofit.Response> call, Response<com.example.retrofit.Response> response) {
                Log.i("image", response.body().toString());
                Toast.makeText(getApplicationContext(),"SuccessUpload Photo", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<com.example.retrofit.Response> call, Throwable t) {
                Log.i("image", t.getMessage());
                Toast.makeText(getApplicationContext(),"Failed Upload Photo", Toast.LENGTH_SHORT);
            }
        });
    }
}
