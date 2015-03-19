package com.gizwits.ledgateway.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.ledgateway.R;
import com.xtremeprog.xpgconnect.XPGWifiCentralControlDevice;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class TestActivity extends BaseActivity implements OnClickListener {
	private Button btnAddSubDevice;
	private Button btnGetSubDevice;
	private ListView lvSubDevices;
	private ToggleButton tbtnSwitch;
	private SeekBar sbLightness;

	private XPGWifiCentralControlDevice centralControlDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		btnAddSubDevice = (Button) findViewById(R.id.btnAddSubDevice);
		btnGetSubDevice = (Button) findViewById(R.id.btnGetSubDevice);
		lvSubDevices = (ListView) findViewById(R.id.lvSubDevices);
		tbtnSwitch = (ToggleButton) findViewById(R.id.tbtnSwitch);
		sbLightness = (SeekBar) findViewById(R.id.sbLightness);
		btnAddSubDevice.setOnClickListener(this);
		btnGetSubDevice.setOnClickListener(this);
		centralControlDevice = (XPGWifiCentralControlDevice) mXpgWifiDevice;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddSubDevice:

			break;
		case R.id.btnGetSubDevice:
			mCenter.cGetSubDevicesList(centralControlDevice);
			break;

		default:
			break;
		}

	}

	@Override
	protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
		super.didDiscovered(error, devicesList);
	}

	class LightAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
