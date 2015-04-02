/**
lampW_frameG.png
lampW_frameW.png
lampY_frameG.png
lampY_frameY.png * Project Name:XPGSdkV4AppBase
 * File Name:MainControlActivity.java
 * Package Name:com.gizwits.centercontrolled.activity.control
 * Date:2015-1-27 14:44:17
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gizwits.ledgateway.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.interpolator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.ClipData.Item;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.UserManageActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.activity.device.DeviceManageListActivity;
import com.gizwits.framework.activity.help.AboutActivity;
import com.gizwits.framework.activity.help.HelpActivity;
import com.gizwits.framework.adapter.MenuDeviceAdapter;
import com.gizwits.framework.config.Configs;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.DeviceAlarm;
import com.gizwits.framework.entity.GroupDevice;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.widget.SlidingMenu;
import com.gizwits.ledgateway.R;
import com.gizwits.ledgateway.adapter.GroupAdapter;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiCentralControlDevice;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiGroup;
import com.xtremeprog.xpgconnect.XPGWifiSubDevice;

// TODO: Auto-generated Javadoc
/**
 * Created by Lien on 14/12/21.
 * 
 * 设备主控界面
 * 
 * @author Lien
 */
public class MainListActivity extends BaseActivity implements OnClickListener {

	/** The tag. */
	private final String TAG = "MainControlActivity";
	// private XPGWifiDevice device;

	/** The scl content. */
	private ListView sclContent;

	/** The m view. */
	private SlidingMenu mView;

	/** The rl alarm tips. */
	private RelativeLayout rlAlarmTips;
	
	/** the btn alpha bg */
	private Button alpha_bg;
	
	private LinearLayout llFooter;

	/** The ll bottom. */
	private LinearLayout llBottom;
	
	/** The iv Edit. */
	public ImageView ivEdit;

	/** The iv menu. */
	private ImageView ivMenu;

	/** The tv title. */
	private TextView tvTitle;

	/** The iv back. */
	private ImageView ivBack;

	/** the ListView deviceListview */
	private ListView listview;
	
	/** the TextVIew light_name */
	public TextView light_name;
	
	public ImageView edit_group;
	
	/** the TextView turn on off */
	public TextView btnSwitch;
	
	/** the sb light adjust */
	public SeekBar lightness;

	/** The m adapter. */
	private MenuDeviceAdapter mAdapter;

	/** The lv device. */
	private ListView lvDevice;

	/** The device data map. */
	private ConcurrentHashMap<String, Object> deviceDataMap;

	/** The statu map. */
	private Map<String, Object> statuMap;

	/** The alarm list. */
	private ArrayList<DeviceAlarm> alarmList;

	/** The alarm list has shown. */
	private ArrayList<String> alarmShowList;

	/** The progress dialog. */
	private ProgressDialog progressDialog;

	/** The XPGWifiCentralControlDevice centralControlDevice */
	public XPGWifiCentralControlDevice centralControlDevice;
	
	/** the list device */
	public Map<String, List<GroupDevice>> mapList=new HashMap<String, List<GroupDevice>>();
	public Map<String, List<String>> groupMapList = new HashMap<String, List<String>>();
	public List<GroupDevice> ledList = new ArrayList<GroupDevice>();
	public List<String> list = new ArrayList<String>();
	public List<ImageView> ivDels = new ArrayList<ImageView>();
	
	/** the wifisubdevice status subDevice */
	XPGWifiSubDevice subDevice;
	
	/** the drawable pic white light */
	public Drawable wLight;
	/** the drawable pic yellow light */
	public Drawable yLight;
	/** the drawable pic white light select */
	public Drawable wLightSelect;
	/** the drawable pic yellow light select */
	public Drawable yLightSelect;
	/** the drawable pic add */
	public Drawable add;
	
	/** the groupadapter devicelist */
	public GroupAdapter mGroupAdapter;
	
	/** the tv selecting tv */
	public TextView selecttv;
	
	/** the XPGWifiSubDevice selcetSubDevice */
	public XPGWifiSubDevice selectSubDevice;
	
	public String selectGroup = "";
	
	/** the device select */
	public List<String> showSelectDevices = new ArrayList<String>();

	/** 是否超时标志位 */
	private boolean isTimeOut = false;
	
	private long switchTime = 0;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** 更新UI界面 */
		UPDATE_UI,

		/** 显示警告 */
		ALARM,

		/** 设备断开连接 */
		DISCONNECTED,

		/** 接收到设备的数据 */
		RECEIVED,

		/** 获取设备状态 */
		GET_STATUE,

		/** The login start. */
		LOGIN_START,

		/**
		 * The login success.
		 */
		LOGIN_SUCCESS,

		/**
		 * The login fail.
		 */
		LOGIN_FAIL,

		/**
		 * The login timeout.
		 */
		LOGIN_TIMEOUT,
		
		/**
		 * The device get status
		 */
		DEVICE_GETSTATUS,
	}

	/**
	 * The handler.
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case DEVICE_GETSTATUS:
				XPGWifiSubDevice subObj = (XPGWifiSubDevice) msg.obj;
				mCenter.cGetSubStatus(subObj);
				break;
			case RECEIVED:
				try {
					if (deviceDataMap.get("data") != null) {
						inputDataToMaps(statuMap,
								(String) deviceDataMap.get("data"));

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case UPDATE_UI:
				for (int i = 0; i < ledList.size(); i++) {
					if (ledList.get(i).getSubDevice().getSubDid().equals(subDevice.getSubDid())) {
						ledList.get(i).setOnOff((Boolean) statuMap.get(JsonKeys.ON_OFF));
						ledList.get(i).setLightness(Integer.parseInt(statuMap.get(JsonKeys.LIGHTNESS).toString()));
					}
				}
				if (ivDels.size() > 0) {
					ivDels.clear();
				}
				mGroupAdapter.notifyDataSetChanged();
				break;
			case DISCONNECTED:
				mCenter.cDisconnect(mXpgWifiDevice);
				break;
			case GET_STATUE:
				mCenter.cGetStatus(mXpgWifiDevice);
				break;
			case LOGIN_SUCCESS:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				if (mView.isOpen()) {
					mView.toggle();
				}
				mCenter.cGetStatus(mXpgWifiDevice);
				break;
			case LOGIN_FAIL:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				ToastUtils.showShort(MainListActivity.this, "设备连接失败");
				break;
			case LOGIN_TIMEOUT:
				isTimeOut = true;
				progressDialog.cancel();
				ToastUtils.showShort(MainListActivity.this, "设备连接超时");
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.centercontrolled.activity.BaseActivity#onCreate(android.os
	 * .Bundle )
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_control);
		initViews();
		initEvents();
		initParams();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.centercontrolled.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		initBindList();
		mAdapter.setChoosedPos(-1);
		for (int i = 0; i < bindlist.size(); i++) {
			if (bindlist.get(i).getDid()
					.equalsIgnoreCase(mXpgWifiDevice.getDid()))
				mAdapter.setChoosedPos(i);
		}
		mAdapter.notifyDataSetChanged();

		mCenter.cGetSubDevicesList(centralControlDevice);
		mCenter.cGetGroups(setmanager.getUid(), setmanager.getToken(), Configs.PRODUCT_KEY_Sub);
		
//		handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
	}

	/**
	 * Inits the params.
	 */
	private void initParams() {
		wLight = this.getResources().getDrawable(R.drawable.lampw_framew); 
		wLight.setBounds(0, 0, wLight.getMinimumWidth(), wLight.getMinimumHeight());
		yLight = this.getResources().getDrawable(R.drawable.lampy_framey); 
		yLight.setBounds(0, 0, yLight.getMinimumWidth(), yLight.getMinimumHeight());
		wLightSelect = this.getResources().getDrawable(R.drawable.lampw_frameg); 
		wLightSelect.setBounds(0, 0, wLightSelect.getMinimumWidth(), wLightSelect.getMinimumHeight());
		yLightSelect = this.getResources().getDrawable(R.drawable.lampy_frameg); 
		yLightSelect.setBounds(0, 0, yLightSelect.getMinimumWidth(), yLightSelect.getMinimumHeight());
		add = this.getResources().getDrawable(R.drawable.icon_add); 
		add.setBounds(0, 0, add.getMinimumWidth(), add.getMinimumHeight());
		
		statuMap = new ConcurrentHashMap<String, Object>();
		alarmList = new ArrayList<DeviceAlarm>();
		alarmShowList = new ArrayList<String>();
		mapList.put("light", ledList);
		mapList.put("我的LED", ledList);
		mapList.put("group", ledList);
		mapList.put("addGroup", ledList);
		list.add("light");
		list.add("我的LED");
		list.add("group");
		list.add("addGroup");
		mGroupAdapter = new GroupAdapter(this, mapList, list);
		sclContent.setAdapter(mGroupAdapter);

		centralControlDevice = (XPGWifiCentralControlDevice) mXpgWifiDevice;
		centralControlDevice.setListener(xpgWifiCentralControlDeviceListener);
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		mView = (SlidingMenu) findViewById(R.id.main_layout);
		// rlHeader = (RelativeLayout) findViewById(R.id.rlHeader);
		rlAlarmTips = (RelativeLayout) findViewById(R.id.rlAlarmTips);
		llFooter = (LinearLayout) findViewById(R.id.llFooter);
		alpha_bg = (Button) findViewById(R.id.black_alpha_bg);
		light_name = (TextView) findViewById(R.id.show_led_name);
		edit_group = (ImageView) findViewById(R.id.edit_group);
		llBottom = (LinearLayout) findViewById(R.id.llBottom);
		ivMenu = (ImageView) findViewById(R.id.ivMenu);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ivEdit = (ImageView) findViewById(R.id.ivEdit);
		ivEdit.setTag(1);
//		ivBack = (ImageView) findViewById(R.id.ivBack);
		sclContent = (ListView) findViewById(R.id.sclContent);

		btnSwitch = (TextView) findViewById(R.id.btnSwitch);
		lightness = (SeekBar) findViewById(R.id.sbLightness);

		mAdapter = new MenuDeviceAdapter(this, bindlist);
		lvDevice = (ListView) findViewById(R.id.lvDevice);
		lvDevice.setAdapter(mAdapter);

		progressDialog = new ProgressDialog(MainListActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("设备连接中，请稍候。");
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		ivMenu.setOnClickListener(this);
		rlAlarmTips.setOnClickListener(this);
		tvTitle.setOnClickListener(this);
		llFooter.setOnClickListener(this);
		edit_group.setOnClickListener(this);

		lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mAdapter.getItem(position).isOnline())
					return;

				if (mAdapter.getChoosedPos() == position) {
					mView.toggle();
					return;
				}

				mAdapter.setChoosedPos(position);
				mXpgWifiDevice = bindlist.get(position);
				loginDevice(mXpgWifiDevice);
			}
		});
		lightness.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lightness.getParent().requestDisallowInterceptTouchEvent(true);
					break;
				case MotionEvent.ACTION_CANCEL:
					lightness.getParent().requestDisallowInterceptTouchEvent(false);
					break;
				}
				return false;
			}
		});
		lightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				if (!selectGroup.equals("") && selectGroup != null) {
					for (int i = 0; i < groupMapList.get(selectGroup).size(); i++) {
						for (int j = 0; j < ledList.size(); j++) {
							if (ledList.get(j).getSubDevice().getSubDid().equals(groupMapList.get(selectGroup).get(i))) {
								Log.e("", ""+ledList.get(j).getSubDevice().getSubDid());
								mCenter.cLightness(ledList.get(j).getSubDevice(), seekBar.getProgress());
								break;
							}
						}
					}
				}else {
					mCenter.cLightness(selectSubDevice, seekBar.getProgress());
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});
		alpha_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bottomClose();
			}
		});
		ivEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("showDel", ""+ivDels.size());

				if (ivEdit.getTag().toString().equals("1")) {
					ivEdit.setImageResource(R.drawable.icon_confirm);
					ivEdit.setTag(0);
				}else{
					ivEdit.setImageResource(R.drawable.icon_edit_w);
					ivEdit.setTag(1);
				}
				
				if (ivDels.size() < 1) {
					return;
				}
				
				if (ivEdit.getTag().toString().equals("0")) {
					for (ImageView ivDel : ivDels) {
							ivDel.setVisibility(View.VISIBLE);
					}
				}else{
					for (ImageView ivDel : ivDels) {
						ivDel.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
		sclContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == list.size() - 1) {
					Intent intent = new Intent(MainListActivity.this, EditGroupActivity.class);
					intent.putStringArrayListExtra("ledList", GroupDevice.getAllName(ledList));
					intent.putExtra("did", ""+centralControlDevice.getDid());
					startActivity(intent);
				}
			}
		});
		btnSwitch.setOnClickListener(this);
	}

	/**
	 * 防止循环调用.
	 * 
	 * @author Administrator
	 * @param on
	 *            the new listen null
	 * @return void
	 * @Title: setListenNull
	 * @Description: TODO
	 */
	private void setListenNull(boolean on) {
		// cbWindShake.setOnCheckedChangeListener(on ? null : this);
		// rgWing.setOnCheckedChangeListener(on ? null : this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
		case R.id.ivMenu:
			mView.toggle();
			break;
		case R.id.rlAlarmTips:
		case R.id.tvTitle:
			if (alarmList != null && alarmList.size() > 0) {
				// Intent intent = new Intent(MainListActivity.this,
				// AlarmListActicity.class);
				// intent.putExtra("alarm_list", alarmList);
				// startActivity(intent);
			}
			break;
		case R.id.btnSwitch:
			if (switchTime + 3000 > System.currentTimeMillis()) {
				return;
			}
			switchTime = System.currentTimeMillis();
			if (!selectGroup.equals("") && selectGroup != null) {
				for (int i = 0; i < groupMapList.get(selectGroup).size(); i++) {
					for (int j = 0; j < ledList.size(); j++) {
						if (ledList.get(j).getSubDevice().getSubDid().equals(groupMapList.get(selectGroup).get(i))) {
							Log.e("", ""+ledList.get(j).getSubDevice().getSubDid());
							if (btnSwitch.getText().toString().equals("关灯")){
								mCenter.cSwitchOn(ledList.get(j).getSubDevice(), false);
							}else{
								mCenter.cSwitchOn(ledList.get(j).getSubDevice(), true);
							}
							break;
						}
					}
				}
//				if (btnSwitch.getText().toString().equals("关灯")){
//					btnSwitch.setText("开灯");
//				}else{
//					btnSwitch.setText("关灯");
//				}
			}else {
				if (btnSwitch.getText().toString().equals("关灯")) {
					mCenter.cSwitchOn(selectSubDevice, false);
				}else{
					mCenter.cSwitchOn(selectSubDevice, true);
				}
			}
			break;
		case R.id.edit_group:
			Intent intent = new Intent(MainListActivity.this, EditGroupActivity.class);
			intent.putStringArrayListExtra("ledList", GroupDevice.getAllName(ledList));
			intent.putStringArrayListExtra("groupList", (ArrayList<String>) groupMapList.get(v.getTag().toString()));
			intent.putExtra("groupName", v.getTag().toString());
			intent.putExtra("did", ""+centralControlDevice.getDid());
			startActivity(intent);
			break;
		}
	}
	
	public void bottomShow(){
        llBottom.setVisibility(View.VISIBLE);
        alpha_bg.setVisibility(View.VISIBLE);
	}
	
	private void bottomClose(){
        llBottom.setVisibility(View.GONE);
        alpha_bg.setVisibility(View.GONE);
        if (selecttv != null) {
			if (((GroupDevice)selecttv.getTag()).isOnOff()) {
				selecttv.setCompoundDrawables(null,yLight,null,null);
			}else{
				selecttv.setCompoundDrawables(null,wLight,null,null);
			}
		}
        selectSubDevice = null;
        selectGroup = "";
        selecttv = null;
	}

	public void onClickSlipBar(View view) {
		switch (view.getId()) {
		case R.id.rlDevice:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					DeviceManageListActivity.class);
			break;
		case R.id.rlAbout:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					AboutActivity.class);
			break;
		case R.id.rlAccount:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					UserManageActivity.class);
			break;
		case R.id.rlHelp:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					HelpActivity.class);
			break;
		case R.id.btnDeviceList:
			mCenter.cDisconnect(mXpgWifiDevice);
			DisconnectOtherDevice();
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					DeviceListActivity.class);
			finish();
			break;
		}
	}

	/**
	 * Login device.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	private void loginDevice(XPGWifiDevice xpgWifiDevice) {
		mXpgWifiDevice = xpgWifiDevice;
		mXpgWifiDevice.setListener(deviceListener);
		DisconnectOtherDevice();
		mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
		progressDialog.show();
		isTimeOut = false;
		handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(),
				5000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didLogin(com.xtremeprog.
	 * xpgconnect.XPGWifiDevice, int)
	 */
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		if (isTimeOut)
			return;

		if (result == 0) {
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}

	}

	/**
	 * 检查出了选中device，其他device有没有连接上
	 * 
	 * @param mac
	 *            the mac
	 * @param did
	 *            the did
	 * @return the XPG wifi device
	 */
	private void DisconnectOtherDevice() {
		for (XPGWifiDevice theDevice : bindlist) {
			if (theDevice.isConnected()
					&& !theDevice.getDid().equalsIgnoreCase(
							mXpgWifiDevice.getDid()))
				mCenter.cDisconnect(theDevice);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.centercontrolled.activity.BaseActivity#didReceiveData(com
	 * .xtremeprog .xpgconnect.XPGWifiDevice,
	 * java.util.concurrent.ConcurrentHashMap, int)
	 */
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		subDevice = (XPGWifiSubDevice) device;
		deviceDataMap = dataMap;
		Log.e(TAG, "data " + dataMap.toString());
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
		
	}
	
	@Override
	protected void didSubDiscovered(int error,
			List<XPGWifiSubDevice> subDeviceList) {
		// TODO Auto-generated method stub
		super.didSubDiscovered(error, subDeviceList);
		Log.e(TAG, "getSubDevices size : "+subDeviceList.size());
		list.clear();
		mapList.clear();
		
		mapList.put("light", ledList);
		list.add("light");
		
		ledList = GroupDevice.getGroupDeviceByList(subDeviceList);
		mapList.put("我的LED", ledList);
		list.add("我的LED");
		
		mapList.put("group", ledList);
		list.add("group");
		
		for (int i = 0; i < grouplist.size(); i++) {
			list.add(grouplist.get(i).groupName);
			List<GroupDevice> gDevices = new ArrayList<GroupDevice>();
			for (int j = 0; j < groupMapList.get(grouplist.get(i).groupName).size(); j++) {
				GroupDevice gDevice = new GroupDevice();
				gDevice.setSdid(Integer.parseInt(groupMapList.get(grouplist.get(i).groupName).get(j)));
				gDevices.add(gDevice);
			}
			mapList.put(grouplist.get(i).groupName, gDevices);
		}
		
		mapList.put("addGroup", ledList);
		list.add("addGroup");
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (ivDels.size() > 0) {
					ivDels.clear();
				}
				mGroupAdapter.notifyDataSetChanged();
			}
		});
		for (int i = 0; i < subDeviceList.size(); i++) {
			subDeviceList.get(i).setListener(deviceListener);
			Message msg=new Message();
			msg.what = handler_key.DEVICE_GETSTATUS.ordinal();
			msg.obj = subDeviceList.get(i);
			handler.sendMessageDelayed(msg, 500 * (i+1));
		}
	}
	
	@Override
	protected void didGetGroups(int error, List<XPGWifiGroup> groupList2) {
		// TODO Auto-generated method stub
		super.didGetGroups(error, groupList2);
		grouplist.clear();
		for (int i = 0; i < groupList2.size(); i++) {
			grouplist.add(groupList2.get(i));
			groupList2.get(i).setListener(xpgWifiGroupListener);
			mCenter.cGetGroupDevices(groupList2.get(i));
		}
	}
	
	@Override
	protected void didGetDevices(int error,
			List<ConcurrentHashMap<String, String>> devicesList) {
		// TODO Auto-generated method stub
		super.didGetDevices(error, devicesList);
		if (devicesList == null) {
			Log.e("error", ""+error);
			return;
		}
		Log.e("didGetDevices", ""+devicesList.toString());
		List<String> strings=new ArrayList<String>();
		for (int i = 0; i < devicesList.size(); i++) {
			strings.add(devicesList.get(i).get("sdid"));
		}
		groupMapList.put(grouplist.get(grouplist.size()-1).groupName, strings);
		
		list.clear();
		mapList.clear();
		
		mapList.put("light", ledList);
		list.add("light");
		
		mapList.put("我的LED", ledList);
		list.add("我的LED");
		
		mapList.put("group", ledList);
		list.add("group");
		
		for (int i = 0; i < grouplist.size(); i++) {
			list.add(grouplist.get(i).groupName);
			List<GroupDevice> gDevices = new ArrayList<GroupDevice>();
			for (int j = 0; j < groupMapList.get(grouplist.get(i).groupName).size(); j++) {
				GroupDevice gDevice = new GroupDevice();
				gDevice.setSdid(Integer.parseInt(groupMapList.get(grouplist.get(i).groupName).get(j)));
				gDevices.add(gDevice);
			}
			mapList.put(grouplist.get(i).groupName, gDevices);
		}
		
		mapList.put("addGroup", ledList);
		list.add("addGroup");
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (ivDels.size() > 0) {
					ivDels.clear();
				}
				mGroupAdapter.notifyDataSetChanged();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (mView.isOpen()) {
			mView.toggle();
		} else {
			if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
				mCenter.cDisconnect(mXpgWifiDevice);
				mXpgWifiDevice = null;
			}
			finish();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.centercontrolled.activity.BaseActivity#didDisconnected(com
	 * .xtremeprog .xpgconnect.XPGWifiDevice)
	 */
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		super.didDisconnected(device);
	}
	
	/**
	 * 把状态信息存入表
	 * 
	 * @param map
	 *            the map
	 * @param json
	 *            the json
	 * @throws JSONException
	 *             the JSON exception
	 */
	private void inputDataToMaps(Map<String, Object> map,String json) throws JSONException {
		Log.i("revjson", json);
		JSONObject receive = new JSONObject(json);
		Iterator actions = receive.keys();
		while (actions.hasNext()) {

			String action = actions.next().toString();
			Log.i("revjson", "action=" + action);
			// 忽略特殊部分
			if (action.equals("cmd") || action.equals("qos")
					|| action.equals("seq") || action.equals("version")) {
				continue;
			}
			JSONObject params = receive.getJSONObject(action);
			Log.i("revjson", "params=" + params);
			Iterator it_params = params.keys();
			while (it_params.hasNext()) {
				String param = it_params.next().toString();
				Object value = params.get(param);
				map.put(param, value);
				Log.i(TAG, "Key:" + param + ";value" + value);
			}
		}
		handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
	}
}
