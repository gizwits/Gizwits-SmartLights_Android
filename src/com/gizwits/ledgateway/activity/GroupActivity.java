package com.gizwits.ledgateway.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gizwits.ledgateway.R;
import com.gizwits.ledgateway.adapter.EditGroupAdapter;

public class GroupActivity extends Activity {
	Button button1;
	Map<String, List<String>> mapList=new HashMap<String, List<String>>();
	List<String> ledList = new ArrayList<String>();
	List<String> list = new ArrayList<String>();
	EditGroupAdapter mlistadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		ListView listview=(ListView) findViewById(R.id.listview);
		ledList.add("111");
		ledList.add("222");
		ledList.add("333");
		ledList.add("444");
		ledList.add("555");
		ledList.add("666");
		ledList.add("777");
		ledList.add("888");
		ledList.add("999");
		ledList.add("111");
		ledList.add("222");
		ledList.add("333");
		mapList.put("我的LED", ledList);
		mapList.put("客厅组", new ArrayList<String>());
		mapList.put("主人卧室组", new ArrayList<String>());
		mapList.put("", new ArrayList<String>());
		list.add("我的LED");
		list.add("客厅组");
		list.add("主人卧室组");
		list.add("");
		mlistadapter=new EditGroupAdapter(this, mapList, list);
		listview.setAdapter(mlistadapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(GroupActivity.this, "position "+position, Toast.LENGTH_LONG).show();
			}
		});
	}
}
