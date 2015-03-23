package com.gizwits.ledgateway.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.ledgateway.R;
import com.xtremeprog.xpgconnect.XPGWifiCentralControlDevice;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiSDK;
import com.xtremeprog.xpgconnect.XPGWifiSubDevice;

public class TestActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private Button btnAddSubDevice;
	private Button btnGetSubDevice;
	private ListView lvSubDevices;
	private ToggleButton tbtnSwitch;
	private SeekBar sbLightness;
	private LightAdapter lightAdapter;
	private List<XPGWifiSubDevice> subDevices;
	private XPGWifiSubDevice mSubDevice;

	private XPGWifiCentralControlDevice centralControlDevice;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			lightAdapter.updateList(subDevices);
			XPGWifiSDK.sharedInstance().updateDeviceFromServer(
					subDevices.get(0).getSubProductKey());
			lightAdapter.notifyDataSetChanged();
			lvSubDevices.postInvalidate();
		};
	};

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
		subDevices = new ArrayList<XPGWifiSubDevice>();

		lvSubDevices.setOnItemClickListener(this);

		lightAdapter = new LightAdapter(subDevices);

		lvSubDevices.setAdapter(lightAdapter);

		centralControlDevice.setListener(xpgWifiCentralControlDeviceListener);
		tbtnSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mCenter.cSwitchOn(mSubDevice, isChecked);

			}
		});
		sbLightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mCenter.cLightness(mSubDevice, seekBar.getProgress());

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

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
		/** The inflater. */
		private LayoutInflater inflater;

		/** The list. */
		private List<XPGWifiSubDevice> list;

		/**
		 * Instantiates a new wifi list adapter.
		 * 
		 * @param c
		 *            the c
		 * @param list
		 *            the list
		 */
		public LightAdapter(List<XPGWifiSubDevice> list) {
			this.list = list;
			inflater = LayoutInflater.from(TestActivity.this);
		}

		public void updateList(List<XPGWifiSubDevice> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public XPGWifiSubDevice getItem(int position) {
			// TODO Auto-generated method stub
			return (XPGWifiSubDevice) list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			XPGWifiSubDevice subDevice = (XPGWifiSubDevice) list.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.sub_device_item, null);
				holder.tvsubid = (TextView) convertView
						.findViewById(R.id.tvSubdid);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (subDevice.isOnline()) {
				holder.tvsubid.setText(subDevice.getSubDid());
			} else {
				holder.tvsubid.setText(subDevice.getSubDid() + "未连接");
			}

			return convertView;
		}

	}

	/**
	 * 
	 * ClassName: Class ViewHolder. <br/>
	 * <br/>
	 * date: 2015-1-23 12:17:11 <br/>
	 * 
	 * @author Lien
	 */
	private static class ViewHolder {

		/** The tv time. */
		TextView tvsubid;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mSubDevice = (XPGWifiSubDevice) subDevices.get(position);
		mSubDevice.setListener(deviceListener);
	}

	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		super.didReceiveData(device, dataMap, result);
		Log.e("ReceiveData", dataMap.toString());
	}

	@Override
	protected void didSubDiscovered(int error,
			List<XPGWifiSubDevice> subDeviceList) {
		// TODO Auto-generated method stub
		super.didSubDiscovered(error, subDeviceList);
		subDevices = subDeviceList;
		handler.sendEmptyMessage(0);
	}

	@Override
	protected void didSubReceiveData(XPGWifiSubDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		// TODO Auto-generated method stub
		super.didSubReceiveData(device, dataMap, result);
		
	}
	
}
