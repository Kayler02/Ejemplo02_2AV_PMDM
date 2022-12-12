package com.example.ejemplo02_2av_pmdm.conexiones;

import com.example.ejemplo02_2av_pmdm.modelos.Album;
import com.example.ejemplo02_2av_pmdm.modelos.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConexiones {

    //En esta parte se genera el endpoint
    @GET("/albums")
    //

    //
    Call<ArrayList<Album>> getAlbums();
    //

    //PARA FOTOS -> /photos?albumId=2
    @GET ("/photos?")
    Call<ArrayList<Photo>> getPhotosAlbum(@Query("albumId") String albumId);
}

