package com.control.amigo;

import com.example.amigo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothConnect extends Activity {
	public static ArrayAdapter<String> adapterdevices;
	public static Context mContext;
	public static BTstate mBTstate = BTstate.closed;
	private ListView deviceList;
	
	
	enum BTstate{ opened, closed, connected };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_connect);
		
		deviceList = (ListView) findViewById(R.id.listView1);
		
		adapterdevices = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,BluetoothService.getdevice());
		deviceList.setAdapter(adapterdevices);
		deviceList.setOnItemClickListener(devicelstListener);
		
		mContext = this;
		
		if( BluetoothService.BTisEnabled() ){
			mBTstate = BTstate.opened;
		}
		else {
			mBTstate = BTstate.closed;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();  
        inflater.inflate(R.menu.bluetooth_optionmenu, menu);  
        return true;
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			BluetoothService.resetdeveces();
			onBackPressed();
			break;
		case R.id.open:			
			if( mBTstate.equals(BTstate.opened) ){
				showOpenedMsg();
			}
			else if( mBTstate.equals(BTstate.closed) ){
				OpenDialog();
				mBTstate = BTstate.opened;
			}
			break;
		case R.id.search:
			if( mBTstate.equals(BTstate.opened) ){
				BluetoothService.setBluetoothRearch();
			}
			else if( mBTstate.equals(BTstate.closed) ){
				showUnopenedMsg();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnItemClickListener devicelstListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if( BluetoothService.mBTState.equals(BluetoothService.BTState.running) ){
				showConnectedMsg();
			}
			else if( BluetoothService.mBTState.equals(BluetoothService.BTState.stopped) ){
				Intent intent = new Intent(BluetoothService.ACTION_CONNECT);
				intent.putExtra(BluetoothService.Tag_Position, position);
				startService(intent);
			}
		}
	};
	
	private void showOpenedMsg(){
		Toast.makeText(this, "藍牙已開啟", Toast.LENGTH_SHORT ).show();
	}
	
	private void showConnectedMsg(){
		Toast.makeText(this, "藍牙已連線", Toast.LENGTH_SHORT ).show();
	}
	
	private void showUnopenedMsg(){
		Toast.makeText(this, "藍牙未開啟", Toast.LENGTH_SHORT ).show();
	}
	
	private void OpenDialog(){
		final ProgressDialog progress = ProgressDialog.show(this, "正在開啟藍牙", "請稍候...",false);
		new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                	BluetoothService.setBluetoothOpen();
                    Thread.sleep(3000);
                    BluetoothService.setBluetoothRearch();
                    Thread.sleep(1000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                	progress.dismiss();
                }
            } 
       }).start();
	}

	
	
	
}
