package com.example.scrollviewslidemenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	private SlideMennu menu;
	private Button btn;
	private ListView listview;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidemenu_test);
		btn = (Button) findViewById(R.id.btn);
		menu = (SlideMennu) findViewById(R.id.view_menu);
		listview = (ListView) findViewById(R.id.listview);
		fillData();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menu.toggle();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	List<String> list = new ArrayList<String>();

	public void fillData() {
		for (int i = 0; i < 10; i++) {
			list.add("第：" + i + " 条数据");
		}
	}
}
