package com.gizwits.framework.entity;

import java.util.ArrayList;
import java.util.List;

import com.xtremeprog.xpgconnect.XPGWifiSubDevice;

public class GroupDevice {
	public GroupDevice(){
		
	}
	public GroupDevice(XPGWifiSubDevice subDevice, boolean onOff, int lightness){
		this.subDevice = subDevice;
		this.onOff = onOff;
		this.lightness = lightness;
	}
	
	private XPGWifiSubDevice subDevice;
	private boolean onOff;
	private int lightness;
	public XPGWifiSubDevice getSubDevice() {
		return subDevice;
	}
	public void setSubDevice(XPGWifiSubDevice subDevice) {
		this.subDevice = subDevice;
	}
	public boolean isOnOff() {
		return onOff;
	}
	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}
	public int getLightness() {
		return lightness;
	}
	public void setLightness(int lightness) {
		this.lightness = lightness;
	}
	
	public static List<GroupDevice> getGroupDeviceByList(List<XPGWifiSubDevice> subDevices){
		List<GroupDevice> gDevices = new ArrayList<GroupDevice>();
		for (int i = 0; i < subDevices.size(); i++) {
			GroupDevice gDevice = new GroupDevice();
			gDevice.setSubDevice(subDevices.get(i));
			gDevices.add(gDevice);
		}
		return gDevices;
	}
}
