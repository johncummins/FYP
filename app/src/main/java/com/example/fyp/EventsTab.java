package com.example.fyp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EventsTab extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_tab_frag, container, false);

        WebView webview;
        Button arButton = view.findViewById(R.id.AR);
        Button mapsButton = view.findViewById(R.id.Maps);
        Button triviaButton = view.findViewById(R.id.Trivia);

        //webview.getSettings().setJavaScriptEnabled(true);
        webview = view.findViewById(R.id.EventsWebView);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.galwaycitymuseum.ie/events/");


        //onPageFinished(webview, "https://www.galwaycitymuseum.ie/events/", arButton);
        /*
        arButton.setVisibility(View.INVISIBLE);
        mapsButton.setVisibility(View.INVISIBLE);
        triviaButton.setVisibility(View.INVISIBLE);


         */
        return view;

    }

    //trying to hide buttons when webpage is loaded, didnt work
    private void onPageFinished(WebView v, String url, Button ar){
        ar.setVisibility(View.INVISIBLE);
    }
}
