package com.example.fyp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DisplayARCamera extends AppCompatActivity {

    private CustomARFragment arFragment;
    boolean addBowlModel = true;
    boolean addProcAdudio = true;
    private MediaPlayer mediaPlayer;
    FloatingActionButton playButton;
    FloatingActionButton pauseButton;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_arcamera);

        arFragment = (CustomARFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        assert arFragment != null;
        arFragment.getPlaneDiscoveryController().hide(); //hides the hand that moves at the start of AR session
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame); // everytime sceneview is updated the func onupdateframe will be called
        mediaPlayer = MediaPlayer.create(this, R.raw.proclamation_audio);
        playButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);

        //toolbar = findViewById(R.id.toolbarARCamera);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //Context context;
    //This function sets up an augmented image database
    //This function is called in custom ARfragment class
    public boolean setupAugmentedImgDb(Config config, Session session){
        //this.context =ctx;
        AugmentedImageDatabase augmentedImgDB;
        //loadAugmentedImg newImage = new loadAugmentedImg();
        //Bitmap bitmap1 = newImage.loadBowlImage();
        //Bitmap bitmap2 = newImage.loadBowlImage();


        Bitmap bitmap1 = loadBowlImage();
        Bitmap bitmap2 = loadPoclamImage();

        if (bitmap1 == null){
            return false;
        }
        if (bitmap2 == null){
            return false;
        }

        augmentedImgDB = new AugmentedImageDatabase(session);
        augmentedImgDB.addImage("bowl", bitmap1); //adding bowl image(specified in loadAugmentedImg function)to the db
        augmentedImgDB.addImage("proclamation", bitmap2); //adding bowl image(specified in loadAugmentedImg function)to the db


        config.setAugmentedImageDatabase(augmentedImgDB); //setting up db
        return true;


    }



    //This function loads a specified image into the above db
    //In this instance here, I am loading the bowl image
    private Bitmap loadBowlImage(){
        try(InputStream inStream = getAssets().open("bowlimage.jpeg")){
            return BitmapFactory.decodeStream(inStream);
        }

        catch(IOException e){
            Log.e("ImageLoad", "IO Exception - image did not load properly", e);
        }

        return null;
    }


    private Bitmap loadPoclamImage(){
        try(InputStream inStreamP = getAssets().open("proclamation.jpeg")){
            return BitmapFactory.decodeStream(inStreamP);
        }

        catch(IOException e){
            Log.e("ImageLoad", "IO Exception - image did not load properly", e);
        }

        return null;
    }


    /*
            try(InputStream inStreamP = getAssets().open("proclamation1.jpeg")){
        return BitmapFactory.decodeStream(inStreamP);
    }

        catch(IOException e){
        Log.e("ImageLoad", "IO Exception - image did not load properly", e);
    }


     */




    private void onUpdateFrame(FrameTime frameTime){
        Frame frame = arFragment.getArSceneView().getArFrame(); //gets the current frame (basically a screenshot of AR experience)

        //this gets all the augmented images that have been added to the database
        Collection<AugmentedImage> augmentedImages =frame.getUpdatedTrackables(AugmentedImage.class);

        //goes through entire image DB and check if any images have bee detected
        for (AugmentedImage augmentedImage : augmentedImages){ //for each image aug image in augDB
            if (augmentedImage.getTrackingState() == TrackingState.TRACKING){ //if aug img is being tracked
                if (augmentedImage.getName().equals("bowl") && addBowlModel == true){ //if img being tracked has name bowl and bool "addBowlmodel" is true
                    playButton.hide();
                    Log.i("Here", "bowl has been detected, and sets addmodel to true");
                    createModel(arFragment, augmentedImage.createAnchor //then place 3D model ontop of image
                            (augmentedImage.getCenterPose()), //creates anchor in centre of detected image
                            Uri.parse("model_199399840789.sfb")); // model of bowl.sfb)
                    addBowlModel = false; //this ensures model is only added once
                }

                //augmentedImage.getName().equals("proclamation1") // can be added in below if i want two proclamtion pictures

                if (augmentedImage.getName().equals("proclamation") && addProcAdudio == true) { //if img being tracked has name proclamation and bool "addprocaudio" is true
                    Toast.makeText(this, "Audio file detected, press play to listen!", Toast.LENGTH_LONG).show();
                    playButton.show();
                    Log.i("Here", "Proclamtion has been detected, and sets addmodel to true");
                    addProcAdudio = false;

                }
            }
        }

    }

    public void playAudio(View view){
        Log.i("Here: play button pressed", "audio will play now");
        mediaPlayer.start();
        pauseButton.show();
        playButton.hide();
    }

    public void pauseAudio(View view){
        Log.i("Here: pause button pressed", "audio will stop playing now");
        mediaPlayer.pause();
        pauseButton.hide();
        playButton.show();
    }


    private void createModel(ArFragment fragment, Anchor anchor, Uri model){ //builds the model
        ModelRenderable.builder()
                .setSource(fragment.getContext(), model)
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                .exceptionally((throwable -> { AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).setTitle("An Error has occurred - see below for details");
                    AlertDialog dialog = builder.create();
                    dialog.show(); //displays the above error message
                    return null;
                }));

    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable){
        AnchorNode anchorNode = new AnchorNode(anchor); //creates a basic node, in fixed position, cant be moved or interacted with
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem()); //can scale, rotate and move this node
        node.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), 270f)); //rotates the node 270 degrees in y axis (x,y,z), as bowl useed to be upside down
        node.setRenderable(renderable);
        node.setParent(anchorNode); //setting parent of transformable node as anchor
        fragment.getArSceneView().getScene().addChild(anchorNode); // added anchor to scene(adds both transformable and anchor to scene)
        node.select(); //selecting transformable node, so it can be interacted with
    }

}
