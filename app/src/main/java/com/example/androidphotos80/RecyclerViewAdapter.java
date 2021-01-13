package com.example.androidphotos80;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos80.model.Album;
import com.example.androidphotos80.model.DataRW;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<Album> albumList;
    private OnNoteListener mOnNoteListener;
    private Context context;
    private Activity activity;
    private String renameAlbumText;
    private String path;
    ViewGroup parent;


    public RecyclerViewAdapter(ArrayList<Album> albumList, OnNoteListener onNoteListener, Context context) {
        this.albumList = albumList;
        this.mOnNoteListener = onNoteListener;
        this.context = context;
        path = context.getFilesDir() + "/albums.dat";
    }

    public void updateList(ArrayList<Album> updatedList) throws IOException, ClassNotFoundException {
        albumList = updatedList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent , false);
        ViewHolder holder  = new ViewHolder(view, mOnNoteListener);
        activity = (Activity) context;
        this.parent = parent;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.albumName.setText(album.getName());

        // Alert Dialog Stuff
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rename Album");
        builder.setCancelable(true);
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    albumList = DataRW.readData(path);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                renameAlbumText = input.getText().toString();

                // Cant have empty album names
                if(renameAlbumText.isEmpty()){
                    Toast.makeText(parent.getContext(), "Error: Album name cannot be empty" , Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if album with name already exists
                for(Album a : albumList){
                    if(a.getName().equalsIgnoreCase(renameAlbumText)){
                        Toast.makeText(parent.getContext(), "Error: Album name already exists" , Toast.LENGTH_SHORT).show();
                        System.out.println(albumList.toString());
                        return;
                    }
                }

                //Album albumToRename = albumList.get(position);
                //albumToRename.renameAlbum(renameAlbumText);
                albumList.get(position).renameAlbum(renameAlbumText);
                // Save data
                DataRW.writeData(albumList, path);
                notifyDataSetChanged();

                // Bad code for the hail mary save?
                Intent renameIntent  = new Intent(activity, MainActivity.class);
                activity.finish();
                activity.startActivity(renameIntent);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = builder.create();

        // Delete button listener for each album list item.
        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Album albumToDelete = albumList.get(position);

                // Try a reread?
                try {
                    albumList = DataRW.readData(path);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //Pos instead of obj?
                albumList.remove(position);
                //albumList.remove(albumToDelete);
                // Save data
                DataRW.writeData(albumList, path);

                notifyItemRemoved(position);
                notifyDataSetChanged();

                // RELOAD?
                activity.finish();
                Intent intent = activity.getIntent();
                activity.startActivity(intent);


            }
        });

        holder.renameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView albumName;
        Button deleteButton;
        Button renameButton;
        OnNoteListener onNoteListener;


        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            albumName = itemView.findViewById(R.id.textView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            renameButton = itemView.findViewById(R.id.renameButton);

            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}

