package com.example.ejemplo02_2av_pmdm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.ejemplo02_2av_pmdm.adapters.AlbumAdapters;
import com.example.ejemplo02_2av_pmdm.conexiones.ApiConexiones;
import com.example.ejemplo02_2av_pmdm.conexiones.RetrofitObject;
import com.example.ejemplo02_2av_pmdm.databinding.ActivityMainBinding;
import com.example.ejemplo02_2av_pmdm.modelos.Album;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<Album> albums;
    private AlbumAdapters adapters;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        albums = new ArrayList<>();
        adapters = new AlbumAdapters(albums, R.layout.album_view_holder, this);
        layoutManager = new LinearLayoutManager(this);

        binding.contentMain.contenedorAlbums.setAdapter(adapters);
        binding.contentMain.contenedorAlbums.setLayoutManager(layoutManager);

        doGetAlbums();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    //PARTE NUEVA DE RETROFIT
    private void doGetAlbums() {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);

        Call<ArrayList<Album>> getAlbums = api.getAlbums();

        //SE DEBE USAR EL ENQUEUE PARA CONSEGUIR LOS DATOS EN SEGUNDO PLANO
        getAlbums.enqueue(new Callback<ArrayList<Album>>() {

            //LO QUE INTERESA DEL ONRESPONSE ES EL RESPONSE -> RESPONSE<ARRAYLIST>ETC. RESPONSE -> ES LA RESPUESTA DEL SERVIDOR
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                //EL CODE == 200 es que la conexión se ha completado de forma correcta
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    ArrayList<Album> temp = response.body();
                    albums.addAll(temp);
                    adapters.notifyItemRangeInserted(0,temp.size());
                }else{
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No tienes Internet", Toast.LENGTH_SHORT).show();
                Log.e("FAILURE", t.getLocalizedMessage());
            }
        });
    }

}