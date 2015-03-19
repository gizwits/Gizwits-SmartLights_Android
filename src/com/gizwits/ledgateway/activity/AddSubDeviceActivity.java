package com.gizwits.ledgateway.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.ledgateway.R;

public class AddSubDeviceActivity extends BaseActivity implements OnClickListener{

	/** The btn confirm. */
	private Button confirm_btn;
	/** The iv back. */
	private ImageView ivBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_sub);
		initViews();
		initEvent();
	}
	
	private void initViews(){
		confirm_btn = (Button) findViewById(R.id.confirm_btn);
		ivBack = (ImageView) findViewById(R.id.ivBack);
	}
	private void initEvent(){
		confirm_btn.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.confirm_btn:
			
			break;
		case R.id.ivBack:
			
			break;
		}
	}
	
}
