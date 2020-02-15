package com.example.fyp;

import android.util.Log;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomARFragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {

        getPlaneDiscoveryController().setInstructionView(null);

        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
        this.getArSceneView().setupSession(session);

        if(((DisplayARCamera)getActivity()).setupAugmentedImgDb(config, session)){
            Log.d("setupAugmentedImgDb", "Successfully setup database");
        }

        else{
            Log.e("setupAugmentedImgDb", "Failed to setup database");
        }


        return config;

    }
}