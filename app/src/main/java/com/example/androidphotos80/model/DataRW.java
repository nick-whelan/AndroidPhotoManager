package com.example.androidphotos80.model;

import com.example.androidphotos80.MainActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataRW {

    public static void writeData(ArrayList<Album> albums, String path){
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(albums);
            oos.close();
            fos.close();
            System.out.println("WRITTEN DATA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Album> readData(String path) throws IOException, ClassNotFoundException  {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Album> albums = (ArrayList<Album>) ois.readObject();
        ois.close();
        fis.close();
        System.out.println("READ DATA");
        return albums;
    }



}
