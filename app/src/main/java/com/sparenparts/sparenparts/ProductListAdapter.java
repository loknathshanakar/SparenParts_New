package com.sparenparts.sparenparts;

/**
 * Created by Loknath Shankar on 2/28/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class ProductListAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ProductListModel tempValues=null;

    /*************  CustomAdapter Constructor *****************/
    public ProductListAdapter(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return 5;           /** Show only 5 products**/
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView Title;
        public TextView metaText;
        public ImageView image;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.index_product_list_single_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.Title = (TextView) vi.findViewById(R.id.categoryText);
            holder.metaText=(TextView)vi.findViewById(R.id.metaText);
            holder.image=(ImageView)vi.findViewById(R.id.list_image);
            //float _TitleSize=Index.TitleSize+Index.TitleSize*.6f;
            //holder.Title.setTextSize(_TitleSize);
            //holder.metaText.setTextSize(_TitleSize);
            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.Title.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( ProductListModel ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.Title.setText( tempValues.getcategoryText());
            holder.metaText.setText(tempValues.getmetaText());
            holder.image.setImageResource(res.getIdentifier("com.sparenparts.sparenparts:drawable/" + tempValues.getimgIcon(), null, null));

            //float _TitleSize=Index.TitleSize+Index.TitleSize*.6f;
            //holder.Title.setTextSize(_TitleSize);
            //holder.metaText.setTextSize(_TitleSize);
            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            Main sct = (Main)activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
            sct.onItemClick(mPosition);
        }
    }
}