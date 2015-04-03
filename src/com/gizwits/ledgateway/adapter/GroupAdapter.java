package  com.gizwits.ledgateway.adapter;   
  
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.framework.config.Configs;
import com.gizwits.framework.entity.GroupDevice;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.ledgateway.R;
import com.gizwits.ledgateway.activity.AddSubDeviceActivity;
import com.gizwits.ledgateway.activity.MainListActivity;
import com.xpg.common.device.DensityUtils;
import com.xtremeprog.xpgconnect.XPGWifiGroup;
import com.xtremeprog.xpgconnect.XPGWifiSubDevice;
  
@SuppressLint("ResourceAsColor")
public class GroupAdapter extends BaseAdapter {   
	private MainListActivity context;
    private Map<String, List<GroupDevice>> mapList;    //信息集合  
    private List<String> list;
    private LayoutInflater listContainer;           //视图容器   
    LinearLayout llview;
    private Dialog delDialog;
    public final class ListItemView{                //自定义控件集合     
        public LinearLayout tv;        
        public TextView groupNameTv;
        public LinearLayout ll_item_bottom;
        public View v_line;
        public ImageView ivDel;
        public LinearLayout item_bg;
	}
    
    public static int height;
       
    public GroupAdapter(MainListActivity context, Map<String, List<GroupDevice>> mapList, List<String> list) { 
    	this.list = list;
    	this.context=context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
        this.mapList = mapList;
    }

	public Object getItem(int arg0) {   
        // TODO Auto-generated method stub   
        return mapList.get(list.get(arg0));   
    }   
  
    public long getItemId(int arg0) {   
        // TODO Auto-generated method stub   
        return 0;   
    }   
    
    @Override
    public boolean isEnabled(int position) {
    	// TODO Auto-generated method stub
    	if (context.ivEdit.getTag().toString().equals("0")) {
			return false;
		}
    	if (position == 0 || position == 1 || position == 2) {
			return false;
		}
    	return true;
    }
           
    /**  
     * ListView Item设置  
     */  
    public View getView(int position, View convertView, ViewGroup parent) {   
        // TODO Auto-generated method stub  
    	//自定义视图   
        ListItemView  listItemView = null;   
        if (list.get(position).equals("light") || list.get(position).equals("group")) {
        	convertView = listContainer.inflate(R.layout.item_device_tag, null); 
        	TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
        	if (list.get(position).equals("light")) {
            	tv.setText("我的LED");
			}else if(list.get(position).equals("group")){
				tv.setText("我的分组");
			}
        	return convertView;
		}else if(list.get(position).equals("addGroup")){
			convertView = listContainer.inflate(R.layout.item_device_add, null); 
			return convertView;
		}else{
			listItemView = new ListItemView();    
            convertView = listContainer.inflate(R.layout.item_device_list, null); 
		}
        listItemView.item_bg = (LinearLayout) convertView.findViewById(R.id.item_bg);
        listItemView.tv=(LinearLayout) convertView.findViewById(R.id.ll_item);
        listItemView.tv.removeAllViewsInLayout();
        listItemView.groupNameTv = (TextView) convertView.findViewById(R.id.tv_name);
        listItemView.groupNameTv.setText(list.get(position));
        listItemView.ll_item_bottom = (LinearLayout) convertView.findViewById(R.id.ll_item_bottom);
        listItemView.v_line = convertView.findViewById(R.id.v_line);
        listItemView.ivDel = (ImageView) convertView.findViewById(R.id.ivDel);
        List<LinearLayout> llviews=new ArrayList<LinearLayout>();
        if (list.get(position).equals("我的LED")) {
        	listItemView.item_bg.setBackgroundResource(R.drawable.kuang);
        	listItemView.v_line.setVisibility(View.GONE);
        	listItemView.groupNameTv.setVisibility(View.GONE);
        	if (mapList.get(list.get(position)).size() == 0) {
        		TextView add=new TextView(context);
	            add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
	            add.setPadding(0, DensityUtils.dp2px(context, 10f), 0, 0);
	            add.setText("Add");
	            add.setGravity(Gravity.CENTER);
	            add.setTextColor(Color.parseColor("#FFFFFF"));
	            add.setCompoundDrawables(null,context.add,null,null);
	            add.setOnClickListener(addClick);
        		llview=new LinearLayout(context);
        		llview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        		llview.setPadding(0, 0, 0, DensityUtils.dp2px(context, 10f));
        		listItemView.tv.addView(llview);
        		llview.addView(add);
        		add=new TextView(context);
                add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                add.setCompoundDrawables(null,context.add,null,null);
                add.setVisibility(View.INVISIBLE);
        		llview.addView(add);
        		add=new TextView(context);
                add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                add.setCompoundDrawables(null,context.add,null,null);
                add.setVisibility(View.INVISIBLE);
        		llview.addView(add);
        		add=new TextView(context);
                add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                add.setCompoundDrawables(null,context.add,null,null);
                add.setVisibility(View.INVISIBLE);
        		llview.addView(add);
			}
		}else{
			convertView.setTag(list.get(position));
	        convertView.setOnClickListener(groupCtrl);
			listItemView.ivDel.setTag(list.get(position));
			listItemView.ivDel.setOnClickListener(deleteGroupClick);
			if (context.ivEdit.getTag().toString().equals("0")) {
				listItemView.ivDel.setVisibility(View.VISIBLE);
			}
			context.ivDels.add(listItemView.ivDel);
		}
        for (int i = 0; i < mapList.get(list.get(position)).size(); i++) {
        	if (i % 4 == 0) {
        		llview=new LinearLayout(context);
        		llview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        		llview.setPadding(0, 0, 0, DensityUtils.dp2px(context, 10f));
        		if (i >= 4 && !context.showSelectDevices.contains(list.get(position))) {
        			llview.setVisibility(View.GONE);
        			llviews.add(llview);
				}
        		listItemView.tv.addView(llview);
			}
        	FrameLayout flayout=new FrameLayout(context);
        	flayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            TextView textview=new TextView(context);
            android.widget.FrameLayout.LayoutParams params=new android.widget.FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, DensityUtils.dp2px(context, 7f), 0, 0);
            params.gravity = Gravity.CENTER;
            textview.setLayoutParams(params);
            if (list.get(position).equals("我的LED")) {
            	textview.setText("灯"+mapList.get(list.get(position)).get(i).getSubDevice().getSubDid());
            }else{
            	textview.setText("灯"+mapList.get(list.get(position)).get(i).getSdid());
            }
            textview.setGravity(Gravity.CENTER);
            textview.setTextColor(Color.parseColor("#FFFFFF")); 
            if (list.get(position).equals("我的LED")) {
	            if (mapList.get(list.get(position)).get(i).isOnOff()) {
	            	if (context.selecttv != null) {
						if (context.selecttv.getText().equals(textview.getText())) {
							textview.setCompoundDrawables(null,context.yLightSelect,null,null);
							context.btnSwitch.setText("关灯");
							context.lightness.setProgress(mapList.get(list.get(position)).get(i).getLightness());
							context.selecttv=textview;
						}else{
			            	textview.setCompoundDrawables(null,context.yLight,null,null);
						}
					}else{
		            	textview.setCompoundDrawables(null,context.yLight,null,null);
					}
				}else{
					if (context.selecttv != null) {
						if (context.selecttv.getText().equals(textview.getText())) {
				            textview.setCompoundDrawables(null,context.wLightSelect,null,null);
							context.btnSwitch.setText("开灯");
							context.lightness.setProgress(mapList.get(list.get(position)).get(i).getLightness());
							context.selecttv=textview;
						}else{
				            textview.setCompoundDrawables(null,context.wLight,null,null);
						}
					}else{
			            textview.setCompoundDrawables(null,context.wLight,null,null);
					}
				}
            }else{
            	boolean isopenLight = false ;
            	boolean isIntoCheck = false;
            	for (int j = 0; j < mapList.get(list.get(position)).size(); j++) {
					for (int j2 = 0; j2 < context.ledList.size(); j2++) {
						if (context.ledList.get(j2).getSubDevice().getSubDid().equals(""+mapList.get(list.get(position)).get(j).getSdid())) {
							Log.e("context.ledList", ""+ mapList.get(list.get(position)).get(j).getSdid() + "  "+context.ledList.get(j2).getSubDevice().getSubDid());
							if (context.ledList.get(j2).isOnOff()) {
								isopenLight = true;
							}else{
								isopenLight = false;
							}
							isIntoCheck = true;
							break;
						}
					}
					if (isIntoCheck) {
						break;
					}
				}
            	
            	if (isopenLight) {
					textview.setCompoundDrawables(null,context.yLight,null,null);
				}else{
					textview.setCompoundDrawables(null,context.wLight,null,null);
				}
            }
            if (list.get(position).equals("我的LED")) {
                textview.setOnClickListener(ledCtrl);
            }
            textview.setTag(mapList.get(list.get(position)).get(i));
            flayout.addView(textview);
            if (list.get(position).equals("我的LED")) {
                ImageView ivX=new ImageView(context);
                if (context.ivEdit.getTag().toString().equals("0")) {
        			ivX.setVisibility(View.VISIBLE);
    			}else{
    				ivX.setVisibility(View.INVISIBLE);
    			}
    	        ivX.setTag(mapList.get(list.get(position)).get(i).getSubDevice());
                ivX.setImageResource(R.drawable.icon_15);
                android.widget.FrameLayout.LayoutParams param = new android.widget.FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                param.gravity = Gravity.RIGHT;
                ivX.setLayoutParams(param);
                ivX.setOnClickListener(deleteLedClick);
    			context.ivDels.add(ivX);
            	flayout.addView(ivX);
            }
            llview.addView(flayout); 
            if (list.get(position).equals("我的LED")) {
        		TextView add=new TextView(context);
                add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                add.setText("Add");
                add.setGravity(Gravity.CENTER);
                add.setTextColor(Color.parseColor("#FFFFFF"));
                add.setCompoundDrawables(null,context.add,null,null);
                add.setOnClickListener(addClick);
            	if (mapList.get(list.get(position)).size() % 4 != 0 && (i+1) == mapList.get(list.get(position)).size()) {
                    add.setPadding(0, DensityUtils.dp2px(context, 10f), 0, 0);
            		llview.addView(add);
    			}else if(mapList.get(list.get(position)).size() % 4 == 0 && (i+1) == mapList.get(list.get(position)).size()){
    				llview=new LinearLayout(context);
            		llview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            		llview.setPadding(0, 10, 0, 10);
            		if (!context.showSelectDevices.contains("我的LED")) {
                		llview.setVisibility(View.GONE);
					}
            		listItemView.tv.addView(llview);
            		llview.addView(add);
            		add=new TextView(context);
                    add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                    add.setCompoundDrawables(null,context.add,null,null);
                    add.setVisibility(View.INVISIBLE);
            		llview.addView(add);
            		add=new TextView(context);
                    add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                    add.setCompoundDrawables(null,context.add,null,null);
                    add.setVisibility(View.INVISIBLE);
            		llview.addView(add);
            		add=new TextView(context);
                    add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
                    add.setCompoundDrawables(null,context.add,null,null);
                    add.setVisibility(View.INVISIBLE);
            		llview.addView(add);
            		llviews.add(llview);
    			}
			}
            if (mapList.get(list.get(position)).size() % 4 > 0 && i == mapList.get(list.get(position)).size() - 1) {
            	if (list.get(position).equals("我的LED")) {
                	for (int j = 0; j <= (4 - (mapList.get(list.get(position)).size() % 4) - 2); j++) {
    					TextView textviews=new TextView(context);
    		            textviews.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
    		            textviews.setText("null");
    		            textviews.setGravity(Gravity.CENTER);
    		            textviews.setVisibility(View.INVISIBLE);
    		            textviews.setCompoundDrawables(null,context.wLight,null,null);
    		            llview.addView(textviews); 
    				};
				}else{
	            	for (int j = 0; j <= (4 - (mapList.get(list.get(position)).size() % 4) - 1); j++) {
						TextView textviews=new TextView(context);
			            textviews.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
			            textviews.setText("null");
			            textviews.setGravity(Gravity.CENTER);
			            textviews.setVisibility(View.INVISIBLE);
			            textviews.setCompoundDrawables(null,context.wLight,null,null);
			            llview.addView(textviews); 
					};
				}
			}
		}       
        listItemView.ll_item_bottom.setTag(llviews);
        listItemView.ll_item_bottom.setOnClickListener(nameClick);
        return convertView;     
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapList.size();
	} 
	
	OnClickListener nameClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			List<LinearLayout> llviews = (List<LinearLayout>) v.getTag();
			ImageView iv_bottom = (ImageView) v.findViewById(R.id.item_bottom);
			if (llviews.size() == 0) {
				return;
			}
			FrameLayout ll = (FrameLayout) v.getParent();
			if (llviews.get(0).getVisibility() == View.GONE) {
				context.showSelectDevices.add(((TextView)ll.findViewById(R.id.tv_name)).getText().toString());
				for (LinearLayout linearLayout : llviews) {
					linearLayout.setVisibility(View.VISIBLE);
				}
				iv_bottom.setImageResource(R.drawable.icon_close);
			}else{
				context.showSelectDevices.remove(((TextView)ll.findViewById(R.id.tv_name)).getText().toString());
				for (LinearLayout linearLayout : llviews) {
					linearLayout.setVisibility(View.GONE);
				}
				iv_bottom.setImageResource(R.drawable.icon_open);
			}
		}
	};
	
	OnClickListener deleteGroupClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String groupName = v.getTag().toString();
	        delDialog = DialogManager.getDeleteDialog(context, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i = 0; i < context.grouplist.size(); i++) {
						XPGWifiGroup group = context.grouplist.get(i);
						if (group.groupName.equals(groupName)) {
							context.mCenter.cDeleteGroups(context.setmanager.getUid(), 
									context.setmanager.getToken(), group);
							context.mCenter.cGetGroups(context.setmanager.getUid(), context.setmanager.getToken(), Configs.PRODUCT_KEY_Sub);
						}
					}
					DialogManager.dismissDialog(context, delDialog);
				}
			}, groupName);
	        DialogManager.showDialog(context, delDialog);
		}
	};
	
	OnClickListener deleteLedClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final XPGWifiSubDevice led = (XPGWifiSubDevice) v.getTag();
	        delDialog = DialogManager.getDeleteDialog(context,new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					context.mCenter.cDeleteSubDevice(context.centralControlDevice, led);
					context.mCenter.cGetSubDevicesList(context.centralControlDevice);
					DialogManager.dismissDialog(context, delDialog);
				}
			}, "灯"+led.getSubDid());
	        DialogManager.showDialog(context, delDialog);
		}
	};
	
	OnClickListener ledCtrl=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.e("clicknormal", "click");
			if (context.ivEdit.getTag().toString().equals("0")) {
				return;
			}
			
			GroupDevice gSelectDevice = (GroupDevice) v.getTag();
			if (gSelectDevice.isOnOff()) {
				((TextView)v).setCompoundDrawables(null,context.yLightSelect,null,null);
			}else{
	            ((TextView)v).setCompoundDrawables(null,context.wLightSelect,null,null);
			}
			context.selecttv = ((TextView)v);
			context.selectSubDevice = gSelectDevice.getSubDevice();
            if (gSelectDevice.isOnOff()) {
            	context.btnSwitch.setText("关灯");
			}else{
				context.btnSwitch.setText("开灯");
			}
            context.light_name.setText(context.selecttv.getText().toString());
            context.lightness.setProgress(gSelectDevice.getLightness());
            context.bottomShow();
		}
	};
	
	OnClickListener groupCtrl=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String groupName = v.getTag().toString();
			boolean isShowOk = false;
			
			if (context.ivEdit.getTag().toString().equals("0")) {
				return;
			}
			
			for (int j = 0; j < context.groupMapList.get(groupName).size(); j++) {
				for (int i = 0; i < context.ledList.size() ; i++) {
					if (context.groupMapList.get(groupName).get(j).equals(context.ledList.get(i).getSubDevice().getSubDid())) {
						GroupDevice gSelectDevice = (GroupDevice) context.ledList.get(i);
						context.selectSubDevice = gSelectDevice.getSubDevice();
						context.selectGroup = groupName;
			            if (gSelectDevice.isOnOff()) {
			            	context.btnSwitch.setText("关灯");
						}else{
							context.btnSwitch.setText("开灯");
						}
			            context.light_name.setText(groupName);
			            context.edit_group.setVisibility(View.VISIBLE);
			            context.edit_group.setTag(groupName);
			            context.lightness.setProgress(gSelectDevice.getLightness());
						isShowOk = true;
						break;
					}
				}
				if (isShowOk) {
					break;
				}
			}
			if (isShowOk) {
	            context.bottomShow();
			}else{
				Toast.makeText(context, "该组中无可用LED灯", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	OnClickListener addClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, AddSubDeviceActivity.class);
			context.startActivity(intent);
		}
	};
}  