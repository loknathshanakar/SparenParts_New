package com.sparenparts.sparenparts;

/**
 * Created by Loknath Shankar on 2/28/2016.
 */
public class ProductListModel {

    private  String categoryText="";
    private  String imgIcon="";
    private  String metaText="";

    /*********** Set Methods ******************/
    public ProductListModel(String categoryText,String metaText,String imgIcon){
        this.categoryText=categoryText;
        this.metaText=metaText;
        this.imgIcon=imgIcon;
    }

    public void setcategoryText(String categoryText)
    {
        this.categoryText = categoryText;
    }

    public void setimgIcon(String imgIcon)
    {
        this.imgIcon = imgIcon;
    }

    public void setmetaText(String metaText)
    {
        this.metaText = metaText;
    }

    /*********** Get Methods ****************/

    public String getcategoryText()
    {
        return this.categoryText;
    }

    public String getimgIcon()
    {
        return this.imgIcon;
    }

    public String getmetaText()
    {
        return this.metaText;
    }
}