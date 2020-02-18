package com.example.fyp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;



public class loadAugmentedImg extends AppCompatActivity  {

    //AssetManager manager = context.getAssets();

    //This function loads a specified image into the above db
    //In this instance here, I am loading the bowl image
    public Bitmap loadBowlImage(){
        try(InputStream inStreamB = getAssets().open("bowlimage.jpeg")){
            Log.i("bowlimg", "The bowl image has loaded********");
            return BitmapFactory.decodeStream(inStreamB);
        }

        catch(IOException e){
            Log.e("ImageLoad", "IO Exception - image did not load properly", e);
        }
        return null;
    }


    /*
    public Bitmap loadPoclamlImage(){
        try(InputStream inStreamP = getAssets().open("proclamation.jpeg")){
            return BitmapFactory.decodeStream(inStreamP);
        }

        catch(IOException e){
            Log.e("ImageLoad", "IO Exception - image did not load properly", e);
        }
        return null;
    }

     */
}




