package com.example.ejemplo02_2av_pmdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ejemplo02_2av_pmdm.adapters.PhotosAdapter;
import com.example.ejemplo02_2av_pmdm.conexiones.ApiConexiones;
import com.example.ejemplo02_2av_pmdm.conexiones.RetrofitObject;
import com.example.ejemplo02_2av_pmdm.modelos.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Photo> photos;
    private PhotosAdapter photosAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        recyclerView = findViewById(R.id.contenedorPhotos);
        photos = new ArrayList<>();
        photosAdapter = new PhotosAdapter(photos, R.layout.photo_view_holder, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setAdapter(photosAdapter);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent().getExtras().getString("ALBUM_ID") != null){
            doGetPhotos(getIntent().getExtras().getString("ALBUM_ID"));
        }
    }

    private void doGetPhotos(String albumId) {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);
        Call<ArrayList<Photo>> getPhotos = api.getPhotosAlbum(albumId);

        getPhotos.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    photos.addAll(response.body());
                    photosAdapter.notifyItemRangeInserted(0, photos.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {
                Toast.makeText(PhotosActivity.this, "No tienes Internet", Toast.LENGTH_SHORT).show();
                Log.e("FAILURE", t.getLocalizedMessage());
            }
        });
    }


}