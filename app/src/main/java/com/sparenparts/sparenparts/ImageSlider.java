package com.sparenparts.sparenparts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSlider extends Fragment {

    private static String POSITION="POSITION";
    public ImageSlider() {
        // Required empty public constructor
    }

    static ImageSlider newInstance(int position) {
        ImageSlider swipeFragment = new ImageSlider();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_image_slider, container, false);
        Bundle bundle = getArguments();
        int tabNumber = bundle.getInt(POSITION);
        ImageView banner=(ImageView)rootView.findViewById(R.id.image_slider);
        if(tabNumber==0) {
            banner.setImageResource(R.drawable.side_nav_bar);
        }
        if(tabNumber==1) {
            banner.setImageResource(R.drawable.ic_menu_send);
        }
        if(tabNumber==2) {
            banner.setImageResource(R.drawable.ic_action_arrowforward);
        }
        if(tabNumber==3) {
            banner.setImageResource(R.drawable.ic_action_billing);
        }
        if(tabNumber==4) {
            banner.setImageResource(R.drawable.ic_action_cancel);
        }
        return rootView;
    }
}
