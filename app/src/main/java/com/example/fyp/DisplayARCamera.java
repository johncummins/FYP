package com.example.fyp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LightingColorFilter;
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
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;


import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DisplayARCamera extends AppCompatActivity {

    private CustomARFragment arFragment;
    boolean addBowlModel = true;
    boolean addProcAdudio = true;
    boolean addPostBox = true;
    boolean addEamon = true;
    private TextView middle_textA, middle_textB, info_text_pop;
    private Button ButtonB;


    private MediaPlayer mediaPlayer;
    FloatingActionButton playButton,pauseButton,infoButtonFloatingB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_arcamera);

        arFragment = (CustomARFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        assert arFragment != null;
        arFragment.getPlaneDiscoveryController().hide(); //hides the hand that moves at the start of AR session
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame); // everytime sceneview is updated the func onupdateframe will be called
        mediaPlayer = MediaPlayer.create(this, R.raw.proclamation_audio_1);
        playButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);
        infoButtonFloatingB  = findViewById(R.id.infoButtonFloatingB);
        middle_textA = findViewById(R.id.middle_text);
        middle_textB = findViewById(R.id.middle_textB);
        ButtonB = findViewById(R.id.ButtonB);
        info_text_pop = findViewById(R.id.info_text_pop);

        playButton.hide();
        pauseButton.hide();
        infoButtonFloatingB.hide();
        middle_textA.setVisibility(View.INVISIBLE);
        middle_textB.setVisibility(View.INVISIBLE);
        ButtonB.setVisibility(View.INVISIBLE);
        info_text_pop.setVisibility(View.INVISIBLE);

        Toolbar myToolbar = findViewById(R.id.toolbarARCamera);
        setSupportActionBar(myToolbar);
    }


    //This function sets up an augmented image database
    //This function is called in custom ARfragment class
    public void setupAugmentedImgDb(Config config, Session session){

        InputStream inS = getResources().openRawResource(R.raw.imagedb);
        try {
            AugmentedImageDatabase augmentedImgDB = AugmentedImageDatabase.deserialize(session, inS);
            config.setAugmentedImageDatabase(augmentedImgDB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void onUpdateFrame(FrameTime frameTime){
        Frame frame = arFragment.getArSceneView().getArFrame(); //gets the current frame (basically a screenshot of AR experience)

        //this gets all the augmented images that have been added to the database
        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);

        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //goes through entire image DB and check if any images have bee detected
        for (AugmentedImage augmentedImage : augmentedImages){ //for each image aug image in augDB
            if (augmentedImage.getTrackingState() == TrackingState.TRACKING){ //if aug img is being tracked, then check condition

        //BOWL
                if (augmentedImage.getName().equals("bowl") && addBowlModel == true){ //if img being tracked has name bowl and bool "addBowlmodel" is true
                    // Vibrate for 400 milliseconds
                    v.vibrate(400);
                    //hide the playButton
                    playButton.hide();
                    middle_textA.setVisibility(View.VISIBLE);
                    ButtonB.setVisibility(View.VISIBLE);
                    middle_textA.setText("This is the North Devon Chafing Fish!");
                    ButtonB.setText("Click here to view 3D model");

                    middle_textA.postDelayed(new Runnable() {
                        public void run() {
                            middle_textA.setVisibility(View.GONE);
                        }
                    }, 7000);

                    Log.i("Here", "bowl has been detected, and sets addmodel to true");

                    //on click listener set for the buttonB, so the user can click and button change colour
                    ButtonB.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            //add sfb model
                            ButtonB.setVisibility(View.GONE);
                            createModel(arFragment, augmentedImage.createAnchor //then place 3D model ontop of image
                                            (augmentedImage.getCenterPose()), //creates anchor in centre of detected image
                                    Uri.parse("model_899146844314.sfb"), 270); // model of bowl.sfb)
                            info_text_pop.setVisibility(View.VISIBLE);
                            info_text_pop.postDelayed(new Runnable() {
                                public void run() {
                                    info_text_pop.setVisibility(View.GONE);
                                }
                            }, 10000);
                            infoButtonFloatingB.show();
                        }
                    });

                    //on click listener set floating info button to the moreInfoBowl method from down below
                    infoButtonFloatingB.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            moreInfoBowl();
                        }
                    });
                    addBowlModel = false; //this ensures model is only added once
                }

        //PROCLAMATION
                if (augmentedImage.getName().equals("proclamation") && addProcAdudio == true) { //if img being tracked has name proclamation and bool "addprocaudio" is true
                    // Vibrate for 400 milliseconds
                    v.vibrate(400);

                    middle_textA.setText("This is a copy of Proclamation of the Irish Republic");
                    middle_textB.setText("Click the play button to hear the proclamation read by relatives of the Easter rising");

                    middle_textA.postDelayed(new Runnable() {
                        public void run() {
                            middle_textA.setVisibility(View.GONE);
                        }
                    }, 10000);

                    infoButtonFloatingB.hide();
                    playButton.show();
                    Log.i("Here", "Proclamtion has been detected, and sets addmodel to true");
                    addProcAdudio = false;
                }

       //POSTBOX
                if (augmentedImage.getName().equals("postbox")&& addPostBox ==true) { //if img being tracked has name proclamation and bool "addprocaudio" is true
                    // Vibrate for 400 milliseconds
                    v.vibrate(400);
                    //showPostBoxInfoBt = true;//if statment for the info button, brings user to more_info_bowl.class
                    playButton.hide();
                    middle_textA.setVisibility(View.VISIBLE);
                    ButtonB.setVisibility(View.VISIBLE);
                    middle_textA.setText("This is the Penfold Postbox");
                    ButtonB.setText("Click here to see how this postbox looked before Ireland got its independence");
                    ButtonB.setTextSize(14);

                    middle_textA.postDelayed(new Runnable() {
                        public void run() {
                            middle_textA.setVisibility(View.GONE);
                        }
                    }, 7000);

                    //on click listener set for the buttonB, so the user can click and button change colour
                    ButtonB.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            ButtonB.setVisibility(View.GONE);
                            createModel(arFragment, augmentedImage.createAnchor //then place 3D model ontop of image
                                            (augmentedImage.getCenterPose()), //creates anchor in centre of detected image
                                    Uri.parse("red_postbox.sfb"), 0); // model of red_postbox.sfb)
                            ButtonB.postDelayed(new Runnable() {
                                public void run() {
                                    info_text_pop.setVisibility(View.VISIBLE);
                                }
                            }, 7000);
                            infoButtonFloatingB.show();
                        }
                    });
                    //on click listener set floating info button to the moreInfoPP method from down below
                    infoButtonFloatingB.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            moreInfoPP();
                        }
                    });
                    addPostBox = false;
                }

      //EAMON DEV
                if (augmentedImage.getName().equals("eamon") && addEamon == true) { //if img being tracked has name proclamation and bool "addprocaudio" is true
                    // Vibrate for 400 milliseconds
                    v.vibrate(400);

                    OnScreenButtonsVisible();
                    middle_textA.setText("This is a statue of Éamon de Valera!");
                    middle_textB.setText("Click the play button below to see videos of Éamon de Valera");

                    middle_textA.postDelayed(new Runnable() {
                        public void run() {
                            OnScreenButtonsGone();
                        }
                    }, 10000);

                    playButton.show();

                    //on click listener set floating info button to the moreInfoPP method from down below
                    playButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            middle_textB.setVisibility(View.GONE);
                            eamonVideo();
                        }
                    });
                    addEamon = false;
                }
            }
        }

    }

    public void playAudio(View view){
        middle_textB.setVisibility(View.GONE);
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


    private void createModel(ArFragment fragment, Anchor anchor, Uri model, int x){ //builds the model
        ModelRenderable.builder()
                .setSource(fragment.getContext(), model)
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable, x))
                .exceptionally((throwable -> { AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).setTitle("An Error has occurred - see below for details");
                    AlertDialog dialog = builder.create();
                    dialog.show(); //displays the above error message
                    return null;
                }));

    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable, int x){
        //change the int to string, then add the f to the string then make it float, in order to rotate bowl on y axis
        String intX = Integer.toString(x);
        String yValue = intX + "f";
        Float yVal =  Float.parseFloat(yValue);

        AnchorNode anchorNode = new AnchorNode(anchor); //creates a basic node, in fixed position, cant be moved or interacted with
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem()); //can scale, rotate and move this node
        node.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), yVal)); //rotates the node 270 degrees in y axis (x,y,z), as bowl useed to be upside down - changed to 0f for when on table
        node.setRenderable(renderable);

        node.setParent(anchorNode); //setting parent of transformable node as anchor
        fragment.getArSceneView().getScene().addChild(anchorNode); // added anchor to scene(adds both transformable and anchor to scene)
        node.select(); //selecting transformable node, so it can be interacted with
    }


    public void moreInfoPP(){
        //infoButtonPP.setVisibility(View.INVISIBLE);
        Log.i("Here", "Inside the startmoreInfopp, postbox one");
        Intent startMoreInfoPP = new Intent(this, postbox_more_info.class);
        startActivity(startMoreInfoPP);

    }

    public void moreInfoBowl() {
        //infoButtonB.setVisibility(View.INVISIBLE);
        Log.i("Here", "Inside the startmoreinfo, bowl one");
        Intent startMoreInfo = new Intent(this, more_info_bowl.class);
        startActivity(startMoreInfo);
    }

    public void eamonVideo() {
        Log.i("Here", "Inside the eamon video");
        Intent startEamonPage = new Intent(this, eamonDev.class);
        startActivity(startEamonPage);
    }

    public void OnScreenButtonsVisible(){
        middle_textA.setVisibility(View.VISIBLE);
        middle_textB.setVisibility(View.VISIBLE);
    }

    public void OnScreenButtonsGone(){
        middle_textA.setVisibility(View.GONE);
        middle_textB.setVisibility(View.GONE);
    }


}
