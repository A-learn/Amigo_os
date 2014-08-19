package com.control.amigo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.control.amigo.BluetoothConnect.BTstate;
import com.control.amigo.drive.AmigoCommunication;
import com.example.amigo.R;

public class BluetoothService extends Service implements SensorEventListener {
	public static final String ACTION_CONNECT = "com.control.amigo.action.CONNECT";
	public static final String ACTION_STOP = "com.control.amigo.action.STOP";
	public static final String ACTION_WORK = "com.control.amigo.action.WORK";
	public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	public static final UUID uuid = UUID.fromString(SPP_UUID);
	public static final String tag = "Bluetooth";
	public static final String Tag_Position = "ConnectAddressPosition";
	
	public static BTState mBTState = BTState.stopped;
	
	private static ArrayList<String> devices = new ArrayList<String>();
	private static BluetoothAdapter btAdapt;
	private static BluetoothSocket btSocket;
	private static InputStream btIn = null;
	private static OutputStream btOut = null;
	private String devAddr = null;
	private int position;
	private Socket Wifisocket;
	private SensorManager sm;
	enum BTState{ stopped, running };
	
	Notification mNotification = null;
	final int NOTIFICATION_ID = 1;
	
	private static ServiceThread serviceThread;
	public static  float degree;
	public static AmigoCommunication Amigo;
	public static boolean AmigoRun = false;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		btAdapt = BluetoothAdapter.getDefaultAdapter(); //初始化藍牙
		// 用BroadcastReceiver來取得搜尋結果
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(searchDevices, intent); //註冊廣播接收器
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sm.registerListener(BluetoothService.this,  
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),  
                SensorManager.SENSOR_DELAY_FASTEST);  
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.i("Service", "onStartCommand"+action);
		if( action.equals(ACTION_CONNECT) ){
//			position = intent.getIntExtra(Tag_Position, 0);
			startConnect();	
		}
		else if( action.equals(ACTION_STOP) ){
			stopConnect();
		}
		else if( action.equals(ACTION_WORK) ){
			setBluetoothRearch();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void startConnect(){
		mBTState = BTState.running;
		BluetoothConnect.mBTstate = BTstate.connected;
		serviceThread = new ServiceThread();
		new Thread(serviceThread).start();
		
		setUpAsForeground("Wifi連線中...");
	}
	
	private void stopConnect(){
		mBTState = BTState.stopped;
		BluetoothConnect.mBTstate = BTstate.opened;
		unregisterReceiver(searchDevices);
		if( btIn != null ){
			try{
//				btSocket.close();
				Wifisocket.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		stopForeground(true);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ArrayList<String> getdevice(){
		return devices;
	}
	
	public static void resetdeveces(){
		devices = new ArrayList<String>();
		
	}
	
	public static void setBluetoothOpen(){
		btAdapt.enable();
	}
	
	public static boolean BTisEnabled(){
		return btAdapt.isEnabled();
	}
	
	public static void setBluetoothRearch(){
		btAdapt.cancelDiscovery();
		btAdapt.startDiscovery();
	}
	
	public class ServiceThread implements Runnable {
		
		public ServiceThread( ) {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
//				devAddr = devices.get(position).split("\\|")[1];
//				btAdapt.cancelDiscovery();
//				
//				btSocket = btAdapt.getRemoteDevice(devAddr).createRfcommSocketToServiceRecord(uuid);
//				btSocket.connect();Log.e(tag, "connected");
				InetAddress severInetAddr=InetAddress.getByName("120.105.129.108");
				Wifisocket = new Socket(severInetAddr, 8101);
				
				synchronized (this) {
//					btIn = btSocket.getInputStream();
//					btOut = btSocket.getOutputStream();
					btIn = Wifisocket.getInputStream();
					btOut = Wifisocket.getOutputStream();
					Log.e(tag, "connected");
				}
				
				Thread.sleep(100);
				setUpAsForeground("Wifi已連線");
			} catch( IOException | InterruptedException e ){
				e.printStackTrace();
				try{
//					btSocket.close();
//					Wifisocket.close();
					Thread.sleep(1500);
					setUpAsForeground("Wifi未連線");
					Log.i("exiconne", "bluetoohservice bye!;");
				} catch(InterruptedException e1){
					e1.printStackTrace();
					Log.i("exiconne", "bluetoohservice bye!;");
				}
//				btSocket = null;
				Wifisocket = null;
				mBTState = BTState.stopped;
				BluetoothConnect.mBTstate = BTstate.opened;
			}

		}
	}
	
	public static void AmigoSwitch( ){
		if( !AmigoRun ){
			Amigo = new AmigoCommunication(btOut, btIn);
			try {
				Amigo.AmigoStart();
				AmigoRun = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("AmigoConn", "Eerror!!!!!!!!");
				e.printStackTrace();
			}
		}
		else {
			try {
				Amigo.getInfo();
				Amigo.AmigoStop();
				AmigoRun = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	void setUpAsForeground(String text) {
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), FragtabsActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification = new Notification();
        mNotification.tickerText = text;
        mNotification.icon = R.drawable.ic_launcher;
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotification.setLatestEventInfo(getApplicationContext(), "Amigo",
                text, pi);
        startForeground(NOTIFICATION_ID, mNotification);
    }
	
	
	private BroadcastReceiver searchDevices = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Bundle b = intent.getExtras();
			Object[] lstName = b.keySet().toArray();
			
			// 顯示所有收到的資訊及細節
			for( int i=0; i<lstName.length; i++ ){
				String keyName = lstName[i].toString();
				Log.e(keyName, String.valueOf(b.get(keyName)));
			}
			BluetoothDevice device = null;
			// 搜尋設備時，取得設備的MAC位址
			if( BluetoothDevice.ACTION_FOUND.equals(action) ){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String str = device.getName() + "|" + device.getAddress();
				if( devices.indexOf(str) == -1 ) //防止重複增加
					devices.add(str);
				BluetoothConnect.adapterdevices.notifyDataSetChanged();
			}
			else if( BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action) ){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				switch( device.getBondState() ){
					case BluetoothDevice.BOND_BONDING:
						Log.d(tag, "正在配對");
						break;
					case BluetoothDevice.BOND_BONDED:
						Log.d(tag, "完成配對");
						break;
					case BluetoothDevice.BOND_NONE:
						Log.d(tag, "取消配對");
					default:
						break;
				}
			}
		}
	};

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		degree = Math.round(event.values[0]);
	}
	

}
