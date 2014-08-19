package com.control.amigo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amigo.R;

public class Connect extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String[] arr = new String[]{"RS232藍牙連線","AmigoBot連線","MonitorServer連線","MonitorServer關閉"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(),android.R.layout.simple_list_item_1,arr);
		setListAdapter(adapter);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.connect_view, container, false);
		
		
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch(position){
			case 0:
				if( BluetoothService.mBTState.equals(BluetoothService.BTState.running) ){
					Toast.makeText(getActivity(), "藍牙已連線", Toast.LENGTH_SHORT ).show();
				}
				else if( BluetoothService.mBTState.equals(BluetoothService.BTState.stopped) ){
					Intent btserviceIntent = new Intent(BluetoothService.ACTION_CONNECT);
					getActivity().startService(btserviceIntent);
				
//					Intent intent = new Intent();
//					intent.setClass(getActivity(), BluetoothConnect.class);
//					startActivity(intent);
				}
	            break;
			case 1: 
				BluetoothService.AmigoSwitch();
				
				Intent floatwindowIntent = new Intent();
				floatwindowIntent.setClass(getActivity(), FloatWindowService.class);
				getActivity().startService(floatwindowIntent);
				break;
			case 2:
				Intent monitorIntent = new Intent();
				monitorIntent.setClass(getActivity(), MonitorService.class);
				getActivity().startService(monitorIntent);
				break;
			case 3:
				monitorIntent = new Intent();
				monitorIntent.setClass(getActivity(), MonitorService.class);
				getActivity().stopService(monitorIntent);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}