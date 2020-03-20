package com.example.fyp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EventsTabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_tab, container, false);

        WebView webview;
        RelativeLayout bottomButtons = view.findViewById(R.id.BottomButtons);

        webview = view.findViewById(R.id.EventsWebView);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.galwaycitymuseum.ie/events/");


        //onPageFinished(webview, "https://www.galwaycitymuseum.ie/events/", bottomButtons);
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
