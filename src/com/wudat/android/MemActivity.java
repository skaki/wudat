package com.wudat.android;

import com.wudat.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MemActivity extends Activity {

	ImageView selectedImage;  
    private Integer[] mImageIds = {
    		   R.drawable.phil_p,
               R.drawable.bruce_p,
               R.drawable.janet_p,
               R.drawable.jenny_p,
               R.drawable.lori_p,
               R.drawable.susan_p,
               R.drawable.valeria_p
    };
    
    private Integer[] mStringIds = {
 		    R.string.phil_p,
            R.string.bruce_p,
            R.string.janet_p,
            R.string.jenny_p,
            R.string.lori_p,
            R.string.susan_p,
            R.string.valeria_p
    };

    private Integer[] mPPIds = {
    		View.GONE,
    		View.VISIBLE,
            View.GONE,
            View.VISIBLE,
            View.GONE,
            View.GONE,
            View.VISIBLE
    };
    
    ImageButton imageBtnHandshake;
    ImageButton imageBtnBusinesscard;
    ImageButton imageBtnPowerPartner;
      
    public MemActivity() {		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.members);
       
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        selectedImage=(ImageView)findViewById(R.id.imageView1);
        gallery.setSpacing(1);
        gallery.setAdapter(new GalleryImageAdapter(this));
		imageBtnHandshake = (ImageButton) findViewById(R.id.imageBtnHandshake);
		imageBtnBusinesscard = (ImageButton) findViewById(R.id.imageBtnBusinesscard);
		imageBtnPowerPartner = (ImageButton) findViewById(R.id.imageBtnPowerPartner);

         // clicklistener for Gallery
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MemActivity.this, mStringIds[position], Toast.LENGTH_SHORT).show();
                selectedImage.setImageResource(mImageIds[position]);
                imageBtnPowerPartner.setVisibility(mPPIds[position]);
            }
        });
        
        addListenerOnHandeShake();
        addListenerOnBusinesscard();
               
	}
	
	@Override
    protected void onResume() {
        super.onResume();
    }
	
	public void addListenerOnHandeShake() {
		imageBtnHandshake.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
			   Toast.makeText(MemActivity.this,
				"Handshake Sent!", Toast.LENGTH_SHORT).show(); 
			} 
		});
	}
	public void addListenerOnBusinesscard() {
		imageBtnBusinesscard.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
			   Toast.makeText(MemActivity.this,
				"Businesscard Sent!", Toast.LENGTH_SHORT).show(); 
			} 
		});
	}
}
