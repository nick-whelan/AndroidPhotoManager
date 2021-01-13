package com.example.androidphotos80.model;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Photo implements Serializable {
    private static final long serialVersionUID = 5771854261828268090L;

    /**
     * Tag list for photo
     */
    public ArrayList<Tag> tags;

    /**
     * Caption string
     */
    public String caption = "";


    /**
     * Path string of photo
     */
    public String path;

    /**
     * Photo constructor
     * @param p Path string of photo
     */
    public Photo(String p) {
        this.path = p;
        tags = new ArrayList<Tag>();
    }
    /**
     * Returns path string of photo
     * @return Path string
     */
    public String getPath() {
        return this.path;
    }


    /**
     * Adds tag to tag list
     * @param t Tag object
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     * Returns the tag list
     * @return ArrayList of tags
     */
    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    /**
     * Returns a string of all tags
     * @return Tag string
     */
    public String getStringTags() {
        StringBuilder sb = new StringBuilder();
        for(Tag t : tags) {
            sb.append(t.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Removes a tag from the tag list
     * @param t Tag to be removed
     */
    public void removeTag(Tag t) {
        tags.remove(t);
    }

    /**
     * Checks if a photo has duplicate tags of passed tag
     * @param temp Tag to check if duplicates exist of
     * @return true if found, false if not
     */
    public boolean duplicatesExist(Tag temp) {
        for(Tag t :tags) {
            if(t.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the value of the tag in the tag list at the index passed
     * @param index Index of tag in tag list to be edited.
     * @param name New name for tag
     * @param value New value for tag
     */
    public void editTag(int index, String name, String value) {
        tags.get(index).setName(name);
        tags.get(index).setValue(value);
    }

    /**
     * Returns the photos caption
     * @return Caption string
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the photos caption
     * @param caption Caption string
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean hasTagSearchAND(String searchPerson, String searchLocation){
        // Return true if there is a name tag AND a location tag substring match
        boolean foundPerson = false;
        boolean foundLocation = false;

        // Person search
        for(Tag t : tags){
            if(t.getName().equalsIgnoreCase("person") && t.getValue().startsWith(searchPerson)){
                foundPerson = true;
            }
        }

        // Location search
        for(Tag t : tags){
            if(t.getName().equalsIgnoreCase("location") && t.getValue().startsWith(searchLocation)){
                foundLocation = true;
            }
        }

        return (foundLocation && foundPerson);
    }

    public boolean hasTagSearchOR(String searchPerson, String searchLocation){
        // Return true if there is a name tag OR a location tag
        boolean foundPerson = false;
        boolean foundLocation = false;

        // Person search
        for(Tag t : tags){
            if(t.getName().equalsIgnoreCase("person") && t.getValue().startsWith(searchPerson)){
                foundPerson = true;
            }
        }
        // Location search
        for(Tag t : tags){
            if(t.getName().equalsIgnoreCase("location") && t.getValue().startsWith(searchLocation)){
                foundLocation = true;
            }
        }
        //changed 2nd parameter foundPerson to foundLocation
        return (foundPerson || foundLocation);
    }

    public boolean validSearch(String name, String searchString){
            for(Tag t: tags){
                if(t.getName().equalsIgnoreCase(name) && t.getValue().startsWith(searchString)) {
                    return true;
                }
            }
            return false;
    }

    @Override
    public String toString(){
        return getCaption();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return path.equals(photo.path);
    }

}
