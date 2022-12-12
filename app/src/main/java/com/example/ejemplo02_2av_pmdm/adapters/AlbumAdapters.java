package com.example.ejemplo02_2av_pmdm.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplo02_2av_pmdm.PhotosActivity;
import com.example.ejemplo02_2av_pmdm.R;
import com.example.ejemplo02_2av_pmdm.modelos.Album;

import java.util.List;

public class AlbumAdapters extends RecyclerView.Adapter<AlbumAdapters.AlbumVH> {

    private List<Album> objects;
    private int resource;
    private Context context;

    public AlbumAdapters(List<Album> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View albumView = LayoutInflater.from(context).inflate(resource, null);
        albumView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new AlbumVH(albumView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumVH holder, int position) {
        Album a = objects.get(position);
        holder.lblTitulo.setText(a.getTitulo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ALBUM_ID", String.valueOf(a.getId()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class AlbumVH extends RecyclerView.ViewHolder {

        TextView lblTitulo;

        public AlbumVH(@NonNull View itemView) {
            super(itemView);

            lblTitulo = itemView.findViewById(R.id.lblTituloAlbumCard);
        }
    }
}
