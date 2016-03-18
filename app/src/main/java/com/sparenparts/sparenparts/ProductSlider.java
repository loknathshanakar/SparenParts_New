package com.sparenparts.sparenparts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSlider extends Fragment {

    private static String POSITION="POSITION";
    public ProductSlider() {
        // Required empty public constructor
    }

    static ProductSlider newInstance(int position) {
        ProductSlider swipeFragment = new ProductSlider();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_product_slider, container, false);
        Bundle bundle = getArguments();
        int tabNumber = bundle.getInt(POSITION);
        TextView tv=(TextView)rootView.findViewById(R.id.textView2);
        tv.setText(""+tabNumber);
        return rootView;
    }

}
