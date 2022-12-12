package com.example.ejemplo02_2av_pmdm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplo02_2av_pmdm.R;
import com.example.ejemplo02_2av_pmdm.modelos.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoVH> {

    private List<Photo> objects;
    private int resources;
    private Context context;

    public PhotosAdapter(List<Photo> objects, int resources, Context context) {
        this.objects = objects;
        this.resources = resources;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoVH(LayoutInflater.from(context).inflate(resources,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoVH holder, int position) {
        Photo photo = objects.get(position);

        holder.lblTitulo.setText(photo.getTitle());
        Picasso.get()
                .load(photo.getThumbnailUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgFoto);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class PhotoVH extends RecyclerView.ViewHolder {

        ImageView imgFoto;
        TextView lblTitulo;

        public PhotoVH(@NonNull View itemView) {
            super(itemView);

            imgFoto = itemView.findViewById(R.id.imgPhotoCard);
            lblTitulo = itemView.findViewById(R.id.lblTituloPhotoCard);
        }
    }
}
