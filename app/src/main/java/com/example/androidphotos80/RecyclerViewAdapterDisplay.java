package com.example.androidphotos80;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos80.model.Album;
import com.example.androidphotos80.model.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterDisplay extends RecyclerView.Adapter<RecyclerViewAdapterDisplay.ViewHolder>{

    private Context context;
    private ArrayList<Tag> tagList;
    private OnTagItemClickListener tListener;
    private int selected_position = 0;
    private int position;
    private ArrayList<Album> albumList;

    public RecyclerViewAdapterDisplay(Context context, ArrayList<Tag> tagList,ArrayList<Album> albumList){
        this.context = context;
        this.tagList = tagList;
        this.albumList = albumList;
        //will need to figure out tag passing

    }

    public void updateList(ArrayList<Album> updatedList) throws IOException, ClassNotFoundException {
        albumList = updatedList;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_taglistitem, parent, false);
        RecyclerViewAdapterDisplay.ViewHolder holder = new RecyclerViewAdapterDisplay.ViewHolder(view,tListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.textView.setText(tag.toString());
        holder.itemView.setBackgroundColor(selected_position == position ? context.getResources().getColor(R.color.lightgray) : context.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView, OnTagItemClickListener tListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tagTextView);

            itemView.setOnClickListener(view -> {
                if(tListener != null){
                    notifyItemChanged(position);
                    position = getAdapterPosition();
                    selected_position = position;
                    notifyItemChanged(selected_position);
                    if(position != RecyclerView.NO_POSITION){
                        tListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface OnTagItemClickListener{
        void onItemClick(int position);
    }

    public void setOnTagItemClickListener(OnTagItemClickListener listener){
        tListener = listener;
    }
}
