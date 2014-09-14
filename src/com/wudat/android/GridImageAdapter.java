package com.wudat.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import  android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class GridImageAdapter extends BaseAdapter {
	private Context mContext;

    
    public GridImageAdapter(Context context) 
    {
        mContext = context;
    }

    public int getCount() {
        return MeetingActivity.mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
   public View getView(int index, View view, ViewGroup viewGroup) 
    {
    	LayoutInflater inflater = (LayoutInflater) mContext
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    		View gridView;
     
    		if (view == null) {
    			gridView = new View(mContext);
    			gridView = inflater.inflate(R.layout.griditem, null);
    			ImageView imageView = (ImageView) gridView
    					.findViewById(R.id.imageViewGridItem);  
    			TextView textView = (TextView) gridView
    					.findViewById(R.id.nameGridItem);
    			
    			if ( index == 0 && MeetingActivity.profileMap != null) {
    					ImageLoader imageLoader;
    					imageLoader = new ImageLoader(mContext);
    					imageLoader.DisplayImage(MeetingActivity.profileMap.getProfileImageURL(), imageView);
        				textView.setText(MeetingActivity.profileMap.getFirstName());					
    			}
    			else {
    				imageView.setImageResource(MeetingActivity.mImageIds[index]);       				
    				textView.setText(MeetingActivity.mfirstNameIds[index]);
    				
    				if ( MeetingActivity.profileMap != null && MeetingActivity.mPPIds[index] == View.VISIBLE )
    					textView.setTextColor(Color.RED);
    			}
    		} else {
    			gridView = (View) view;
    		}
     
    		return gridView;
    }
}
