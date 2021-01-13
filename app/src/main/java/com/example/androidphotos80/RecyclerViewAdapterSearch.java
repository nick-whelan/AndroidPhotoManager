package com.example.androidphotos80;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos80.model.Photo;

import java.util.ArrayList;

public class RecyclerViewAdapterSearch extends RecyclerView.Adapter<RecyclerViewAdapterSearch.ViewHolder>{

    private Context context;
    private ArrayList<Photo> resultList;

    public RecyclerViewAdapterSearch(Context context, ArrayList<Photo> resultList){
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_photolistitem, parent , false);
        RecyclerViewAdapterSearch.ViewHolder holder  = new RecyclerViewAdapterSearch.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterSearch.ViewHolder holder, int position) {
        Photo photo = resultList.get(position);
        holder.thumbnail.setImageURI(Uri.parse(photo.getPath()));
        holder.photoName.setText(photo.getCaption());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView photoName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.photoThumbnail);
            photoName = itemView.findViewById(R.id.photoName);
        }
    }
}
