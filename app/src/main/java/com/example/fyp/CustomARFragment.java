package com.example.fyp;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomARFragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {

        //gets rid of promt to tell user to move phone around
        getPlaneDiscoveryController().setInstructionView(null);

        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        //session.configure(config);

        DisplayARCameraActivity arActivity = (DisplayARCameraActivity) getActivity();
        arActivity.AugmentedImgDb(config, session);
        this.getArSceneView().setupSession(session);

        return config;

    }
}