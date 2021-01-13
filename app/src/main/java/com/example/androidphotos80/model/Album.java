package com.example.androidphotos80.model;


import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Album implements Serializable {
    private static final long serialVersionUID = 4106797422494775891L;
    private String name;
    private ArrayList<Photo> photos;

    /**
     * Album constructor
     * @param name Name of album
     */
    public Album(String name) {
        photos = new ArrayList<Photo>();
        this.name = name;
    }

    /**
     * Renames the album
     * @param name New album name
     */
    public void renameAlbum(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the album
     * @return Name string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the albums photo list
     * @return List of photos
     */
    public ArrayList<Photo> getPhotosList(){
        return photos;
    }


    public void setPhotosList(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Adds a photo object to the album
     * @param p Photo to be added
     */
    public void addPhoto(Photo p) {
        photos.add(p);
    }

    /**
     * Deletes the photo at the passed index in the photo list
     * @param index Index of photo to be removed
     */
    public void deletePhoto(int index) {
        photos.remove(index);
    }

    /**
     * Returns the a reference to the photo object in photo list if passed photo has same path
     * @param p Photo object to search for
     * @return Photo object from photo list
     */
    public Photo getPhoto(Photo p) {
        return photos.get(findIndexOfPhoto(p));
    }

    /**
     * Checks if a given path is in the album
     * @param path Path to search for
     * @return True if path is found, false if not
     */
    public boolean isPhotoInAlbum(String path) {
        for(Photo p : photos) {
            if(p.getPath().equalsIgnoreCase(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns index of photo object in photo list if found
     * @param photo Photo to be searched for
     * @return Index of photo if found, -1 if not
     */
    public int findIndexOfPhoto(Photo photo) {
        for (int i = 0 ; i < photos.size(); i++) {
            if(photo.equals(photos.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the next photo object in the photo list, same photo object if we are already at end of list.
     * @param p Photo object to try to get subsequent photo of
     * @return Next photo object
     */
    public Photo getNextPhoto(Photo p) {
        // Get the photo in albumList after passed photo
        int index = findIndexOfPhoto(p);
        if((index) == photos.size()-1) {
            // is last photo, cant go farther
            return p;
        }
        return photos.get(index+1);
    }

    /**
     * Returns the previous photo object in phoo list, same object if passed photo is at the beginning of the list.
     * @param p Photo object to try to get previous photo of
     * @return Previous photo object
     */
    public Photo getPreviousPhoto(Photo p) {
        int index = findIndexOfPhoto(p);
        if(index == 0) {
            return p;
        }
        return photos.get(index-1);
    }

    /**
     * Returns the size of the photo list
     * @return Size integer
     */
    public int getSize() {
        return photos.size();
    }

    /**
     * toString override to print album name
     */
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return name.equals(album.name);
    }
}
