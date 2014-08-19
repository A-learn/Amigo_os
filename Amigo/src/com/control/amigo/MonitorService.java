package com.control.amigo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MonitorService extends Service implements SurfaceHolder.Callback {
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView1;
	private ImageView imageView1;
	private Camera camera;
	private Socket socket, _socket;
	private PrintWriter wifiOut = null;
	private WifiManager wifi;
	private List<ScanResult> list;
	private StringBuilder stringBuilder;
	public static int size, wififlag=0, cnt=1, _flag=0;
	private String serverAddr = "120.105.129.101", wifiPort = "861", camPort = "168";
	private boolean wifiRun = false;
	
	public static final String file_name = 
			android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/CamTest/test.jpeg";
	
	public static MonitorStatus mMonitorStatus = MonitorStatus.stopped;
	
	enum MonitorStatus{ stopped, wificonnected, camconnected, bothconnected }
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		
		
//		try {
//			File ipFile = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/ServerInfo/ip.txt");
//			FileReader fileIn = new FileReader(ipFile);
//			BufferedReader bufIn = new BufferedReader(fileIn);
//			serverAddr = bufIn.readLine();
//			wifiPort = bufIn.readLine();
//			camPort = bufIn.readLine();
//			bufIn.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Log.i("wifiPosition", "FileGet!!"+serverAddr+" "+wifiPort+" "+camPort);
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		startWifiPosition();
//		startCam();
		mMonitorStatus = mMonitorStatus.bothconnected;
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void startWifiPosition(){
		wifiRun = true;
		wififlag=0;
		new Wifilevel().start();
	}
	
	public void stopWifiPosition(){
		wififlag = 2;
		unregisterReceiver(wifi_receiver);
	}
	
	private void startCam(){
		new Cam().start();
		mMonitorStatus = MonitorStatus.camconnected;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopWifiPosition();
		mMonitorStatus = MonitorStatus.stopped;
		Log.i("WifiPosition", "MonitorService stop");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	class Wifilevel extends Thread{
		@Override
		public void run(){
			while( wifiRun ){
				if( wififlag == 0 ){
					try{
	    				InetAddress severInetAddr=InetAddress.getByName(serverAddr);
	        			socket=new Socket(severInetAddr, Integer.parseInt(wifiPort));
	        			wifiOut = new PrintWriter(socket.getOutputStream());
	        			wififlag = 1;
	        			mMonitorStatus = MonitorStatus.wificonnected;
	        			Log.i("wifiPosition", "Connect!!");
	        		}catch(Exception e){
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        			Log.e("WifiPosition", "Socket Connect Error!!");
	        			break;
	        		}
				}
				else if( wififlag == 1 ){
					wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					
					IntentFilter intent = new IntentFilter();
					intent.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
					registerReceiver(wifi_receiver, intent);
					
					wifi.startScan();
					wififlag = 3;
				}
				else if( wififlag == 2 ){
					try {
						wifiOut.close();
						wifiOut = null;
						socket.close();
						
						wifiRun = false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.e("WifiPosition", "Socket Close Error!!");
						break;
					}
				}
				else if( wififlag == 3 ){
					
				}
			}	
		}
	}
	
	private BroadcastReceiver wifi_receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if( mMonitorStatus.equals(MonitorStatus.wificonnected) || mMonitorStatus.equals(MonitorStatus.bothconnected) ){
				list = wifi.getScanResults();
				size = list.size();
				stringBuilder = new StringBuilder();
				for (int i = 0; i < list.size(); i++){
//					stringBuilder.append(new Integer(i+1).toString() + ":");
					//將ScanResult信息轉換成一個字符串包  
					//BSSID、SSID、capabilities、frequency、level
					stringBuilder.append("N"+(list.get(i)).toString()); 
				}
				stringBuilder.append("size:"+size);
				
				wifiOut.println(stringBuilder);
    			wifiOut.flush();
    			
    			wififlag = 1;
//    			Log.e("WifiPosition", "sendWifiInfo");
			}
			
		}
	};
	
	class Cam extends Thread{
		@Override
		public void run(){
			if( _flag==0 ) _flag=1;
			else _flag=0;
			while( _flag==1 ){
				camera.autoFocus(cb);
				try{
	    			InetAddress severAddr=InetAddress.getByName(serverAddr);
	    			Log.e("Socket", "Client:Connecting...");
	    			socket=new Socket(severAddr, Integer.parseInt(camPort));
	    			try{
	    				Thread.sleep(3000);
	    				OutputStream outputstream=socket.getOutputStream();
	    				
	    				File myFile=new File(file_name);
	    				if(myFile.exists()){
	    					byte[] mybytearray = new byte[(int) myFile.length()];
	    					FileInputStream fis = new FileInputStream(myFile);

	    					BufferedInputStream bis = new BufferedInputStream(fis, 8*1024);
	    					bis.read(mybytearray, 0, mybytearray.length);
	    				    
	    					outputstream.write(mybytearray, 0, mybytearray.length);  //輸出到電腦
	    					outputstream.flush();
							}else 
								Log.e("Socket", "Doesn't exist!");
						}catch(Exception e){
							Log.e("Socket", "Client: Error!", e);
						}finally{
							socket.close();
						}
	    		}catch(Exception e){
	    			Log.e("Socket", "Final fail!");
	    		}
			}
		}
	}
	
	
	PictureCallback jpeg=new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera){
			Bitmap bmp=BitmapFactory.decodeByteArray(data, 0, data.length);
			//byte轉Bitmap
			imageView1.setImageBitmap(bmp);
			FileOutputStream fop;
			try{
				String path=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/CamTest/test.jpeg";
				fop=new FileOutputStream(path);
				bmp.compress(Bitmap.CompressFormat.JPEG, 20, fop);
				//壓縮bitmap(格式, 輸出質量, 目標路徑);
				fop.close();
				System.out.println("Sucsess!");
			}catch(FileNotFoundException e){
				e.printStackTrace();
				System.out.println("FileNotFoundException");
			}catch(IOException e){
				e.printStackTrace();
				System.out.println("IOException");
			}
			camera.startPreview();//需要手動重新startPreview
		}
	};
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder){
		camera=Camera.open();
		try{
			Camera.Parameters parameters=camera.getParameters();
			parameters.setPictureFormat(ImageFormat.JPEG);
			parameters.setPreviewSize(320, 240);
			camera.setParameters(parameters);//參數
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
			//鏡頭的方向和手機相差90度，所以要轉向
			//攝影頭畫面顯示在Surface上
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		camera.stopPreview();//關閉預覽
		camera.release();
	}
	
	AutoFocusCallback cb=new AutoFocusCallback(){
		@Override
		public void onAutoFocus(boolean suc, Camera camera){
			if(suc){
				camera.takePicture(null, null, jpeg);//對焦後拍照
			}
		}
	};
	
}
