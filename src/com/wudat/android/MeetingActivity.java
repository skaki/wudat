package com.wudat.android;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MeetingActivity extends Activity {
	ImageView image;
	GridView gridView;
    public static Integer[] mImageIds = {
		    R.drawable.user,
 		    R.drawable.phil_p,
            R.drawable.bruce_p,
            R.drawable.janet_p,
            R.drawable.jenny_p,
            R.drawable.lori_p,
            R.drawable.susan_p,
            R.drawable.valeria_p,
            R.drawable.geoff,
            R.drawable.gary,
            R.drawable.sally,
            R.drawable.steve,
            R.drawable.linda
    };

    public static Integer[] mfirstNameIds = {
		 R.string.you_fp,    	
		 R.string.phil_fp,
         R.string.bruce_fp,
         R.string.janet_fp,
         R.string.jenny_fp,
         R.string.lori_fp,
         R.string.susan_fp,
         R.string.valeria_fp,
         R.string.geoff_fp,
         R.string.gary_fp,
         R.string.sally_fp,
         R.string.steve_fp,
         R.string.linda_fp
         
    };
    
    private Integer[] mFullNameIds = {
 		    R.string.you_p,
 		    R.string.phil_p,
            R.string.bruce_p,
            R.string.janet_p,
            R.string.jenny_p,
            R.string.lori_p,
            R.string.susan_p,
            R.string.valeria_p,
            R.string.geoff_p,
            R.string.gary_p,
            R.string.sally_p,
            R.string.steve_p,
            R.string.linda_p
    };

    public static Integer[] mPPIds = {
    	    View.GONE,
    		View.GONE,
    		View.VISIBLE,
            View.GONE,
            View.VISIBLE,
            View.GONE,
            View.GONE,
            View.VISIBLE,
            View.GONE,
            View.GONE,
            View.GONE,
            View.GONE,
            View.GONE
    };
    
    public static Integer[] businessCardType = {
    	R.layout.businesscardt1,
    	R.layout.businesscardt1,
    	R.layout.businesscardt1,
    	R.layout.businesscardt2,
    	R.layout.businesscardt1,
    	R.layout.businesscardt3,
    	R.layout.businesscardt2,
    	R.layout.businesscardt3,
    	R.layout.businesscardt1,
    	R.layout.businesscardt3,
    	R.layout.businesscardt1,
    	R.layout.businesscardt2,
    	R.layout.businesscardt1
    };
    
    private final LayoutInflater mInflater = null;
	private final Context ctx = null;
	private static SocialAuthAdapter adapter;
	private String providerName;
	private ProgressDialog mDialog;
	public static Profile profileMap = null;
	public GridImageAdapter imageAdaptor = null;
	
	public static String meetupToken = null;
	
 	public MeetingActivity() {		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.meetingview);       
        gridView = (GridView) findViewById(R.id.meetingGridView);
        
        imageAdaptor = new GridImageAdapter(this);
        gridView.setAdapter(imageAdaptor);  
        
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	
            	if(position == 0) {
            		if( profileMap == null) {
            			/*
            			Intent intent = new Intent(MeetingActivity.this, MeetupAuthActivity.class);
                        startActivity(intent);  
            			*/
            			
	                    adapter = new SocialAuthAdapter(new ResponseListener());
	            		mDialog = new ProgressDialog(MeetingActivity.this);
	            		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            		mDialog.setMessage("Loading...");	
	            		adapter.authorize(MeetingActivity.this, Provider.LINKEDIN);
	            		        		
            		}
            	}
            	else {
            		final Dialog dialog = new Dialog(MeetingActivity.this);
            		dialog.setContentView(businessCardType[position]);
            		dialog.setTitle(mFullNameIds[position]);
            		ImageView imageBtn = (ImageView) dialog.findViewById(R.id.imageButtonPhoto);
            		imageBtn.setImageResource(mImageIds[position]);                
            		dialog.show();
            	}
            }
        });        
	}
		
	private final class ResponseListener implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			adapter.getUserProfileAsync(new ProfileDataListener());
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("Custom-UI", "Error");
			error.printStackTrace();
		}

		@Override
		public void onCancel() {
			Log.d("Custom-UI", "Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Custom-UI", "Dialog Closed by pressing Back Key");

		}
	}
	
		
		// To receive the profile response after authentication
		private final class ProfileDataListener implements SocialAuthListener<Profile> {

			@Override
			public void onExecute(String provider, Profile t) {
				Log.d("LinkedIN", "Receiving Data");
				mDialog.dismiss();
				profileMap = t;
				MeetingActivity.this.imageAdaptor.notifyDataSetChanged();
				MeetingActivity.this.gridView.setAdapter(MeetingActivity.this.imageAdaptor); 
			}

			@Override
			public void onError(SocialAuthError e) {

			}
		}
		
}
