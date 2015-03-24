package  com.gizwits.ledgateway.adapter;   
  
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gizwits.framework.entity.GroupDevice;
import com.gizwits.ledgateway.R;
import com.gizwits.ledgateway.activity.MainActivity;
import com.gizwits.ledgateway.activity.MainListActivity;
import com.xtremeprog.xpgconnect.XPGWifiSubDevice;
  
@SuppressLint("ResourceAsColor")
public class GroupAdapter extends BaseAdapter {   
	private MainListActivity context;
    private Map<String, List<GroupDevice>> mapList;    //信息集合  
    private List<String> list;
    private LayoutInflater listContainer;           //视图容器   
    LinearLayout llview;
    public final class ListItemView{                //自定义控件集合     
        public LinearLayout tv;        
        public TextView groupNameTv;
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
           
    /**  
     * ListView Item设置  
     */  
    public View getView(int position, View convertView, ViewGroup parent) {   
        // TODO Auto-generated method stub  
    	//自定义视图   
        ListItemView  listItemView = null;   
        if (convertView == null) {   
            listItemView = new ListItemView();    
            //获取list_item布局文件的视图   
            convertView = listContainer.inflate(R.layout.item_device_list, null); 
            //获取控件对象   
            convertView.setTag(listItemView);   
        }else {   
            listItemView = (ListItemView)convertView.getTag();   
        }
        listItemView.tv=(LinearLayout) convertView.findViewById(R.id.ll_item);
        listItemView.tv.removeAllViewsInLayout();
        listItemView.groupNameTv = (TextView) convertView.findViewById(R.id.tv_name);
        listItemView.groupNameTv.setText(list.get(position));
        for (int i = 0; i < mapList.get(list.get(position)).size(); i++) {
        	if (!MainActivity.showString.equals(list.get(position)) && i == 4) {
				break;
			}
        	if (i % 4 == 0) {
        		llview=new LinearLayout(context);
        		llview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        		llview.setPadding(0, 10, 0, 10);
        		listItemView.tv.addView(llview);
			}
            TextView textview=new TextView(context);
            textview.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            textview.setText("灯"+mapList.get(list.get(position)).get(i).getSubDevice().getSubDid());
            textview.setGravity(Gravity.CENTER);
            textview.setTextColor(Color.parseColor("#FFFFFF")); 
            textview.setCompoundDrawables(null,context.wLight,null,null);
            textview.setOnClickListener(context);
            textview.setTag(mapList.get(list.get(position)).get(i));
            llview.addView(textview); 
            if ((i+1) % 4 > 0 && i == mapList.get(list.get(position)).size()-1) {
				for (int j = 0; j < (i+1) % 4; j++) {
					TextView textviews=new TextView(context);
		            textviews.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		            textviews.setText("123");
		            textviews.setGravity(Gravity.CENTER);
		            textviews.setVisibility(View.INVISIBLE);
		            textviews.setCompoundDrawables(null,context.wLight,null,null);
		            llview.addView(textviews); 
				};
			}
		}       
        return convertView;     
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapList.size();
	} 
}  