package com.wudat.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView checkInBtn;
	
	public MainActivity() {		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
        checkInBtn = (ImageView) findViewById(R.id.imageViewCheckIn);
        
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(MainActivity.this, MeetingActivity.class);
                startActivity(intent);  
            }
        });
	}
}
