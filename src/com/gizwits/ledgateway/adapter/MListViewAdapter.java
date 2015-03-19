package  com.gizwits.ledgateway.adapter;   
  
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gizwits.ledgateway.R;
import com.gizwits.ledgateway.activity.MainActivity;
  
@SuppressLint("ResourceAsColor")
public class MListViewAdapter extends BaseAdapter {   
	private Context context;
    private Map<String, List<String>> mapList;    //信息集合  
    private List<String> list;
    private LayoutInflater listContainer;           //视图容器   
    LinearLayout llview;
    public final class ListItemView{                //自定义控件集合     
        public LinearLayout tv;         
	}
    
    public static int height;
       
    public MListViewAdapter(MainActivity context, Map<String, List<String>> mapList, List<String> list) { 
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
        for (int i = 0; i < mapList.get(list.get(position)).size(); i++) {
        	if (i % 4 == 0) {
        		llview=new LinearLayout(context);
        		llview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        		llview.setPadding(0, 10, 0, 10);
        		listItemView.tv.addView(llview);
			}
            TextView textview=new TextView(context);
            textview.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            textview.setText(mapList.get(list.get(position)).get(i));
            textview.setGravity(Gravity.CENTER);
            textview.setTextColor(Color.parseColor("#FFFFFF")); 
            Drawable drawable=context.getResources().getDrawable(R.drawable.lampw_framew); 
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textview.setCompoundDrawables(null,drawable,null,null);
            llview.addView(textview); 
		}       
        return convertView;     
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapList.size();
	} 
}  