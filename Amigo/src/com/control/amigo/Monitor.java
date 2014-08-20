package com.control.amigo;




import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import com.control.amigo.drive.AmigoCommunication;
import com.control.amigo.drive.PacketReceiver;
import com.control.amigo.drive.travel;
import com.example.amigo.R;

import android.R.integer;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Monitor extends Fragment implements OnClickListener {
	private Button startMonitor;
	private EditText sour,dest;
	private TextView txt;

	static boolean reach=false;
	static boolean startthr=false;
	 private SensorManager mSensorManager;
	 private Socket infosock=null;
	 static  BufferedReader infoin = null;
	 static PrintWriter infoout = null;
	 private  rev rv=new rev();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View InputFragmentView = inflater.inflate(R.layout.monitor_view, container, false);
		startMonitor = (Button) InputFragmentView.findViewById(R.id.startmonitor);
		startMonitor.setOnClickListener(this);
		sour =(EditText) InputFragmentView.findViewById(R.id.editText1);
		dest=(EditText) InputFragmentView.findViewById(R.id.editText2);
		txt=(TextView)InputFragmentView.findViewById(R.id.textView3);
		
		
		return InputFragmentView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v.getId()==R.id.startmonitor ){
			if( startMonitor.getText().equals("start") ){
				try {
					
					
//					Toast.makeText(getActivity(), pc.getlogin(), Toast.LENGTH_SHORT ).show();
					rv=new rev();
					new Thread(rv).start();
//					Timer timer=new Timer();
//					  timer.schedule(timerTask, 0,1000);
//					int[] t={Integer.parseInt(sour.getText().toString()) ,Integer.parseInt(dest.getText().toString())};
////					
//					BluetoothService.Amigo.starttravel(t);
					startMonitor.setText("stop");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if( startMonitor.getText().equals("stop") ){
				try {
					startMonitor.setText("start");
					BluetoothService.Amigo.stoptravel();
					rv.setstop(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
/****	
	String sourpc="",destpc="";
	int tt=0;
	float degree2;
	 Handler  myHandler = new Handler() {  
        @Override
		public void handleMessage(Message msg) {   
             switch (msg.what) {   
             case 0x10:
            	 Toast.makeText(getActivity(), pc.getlogin(), Toast.LENGTH_SHORT ).show();
            	 break;
             case 0x11:
//            	 Toast.makeText(getActivity(), "�n�Ǯy���o", Toast.LENGTH_SHORT ).show();
            	if(sour.equals("")!=true){
            	 sour.setText(sourpc);
				 dest.setText(destpc);
				
//					
				try {
					 int[] t={Integer.parseInt(sourpc.toString()) ,Integer.parseInt(destpc.toString())};				
					BluetoothService.Amigo.starttravel(t);					
            	}
            	
				 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
            	 break;
            case 0x12:
            	
            	txt.setText(degree2+"度"+tt);
//            	txt.setText("度"+tt);
            	 msg.what=0xff;
            	 break;
            	}
             super.handleMessage(msg);
             }
       
     
	};
	****/
         int[] path=new int [8];
         int thc=0;
         connPC pc=new connPC();
	 class rev implements Runnable{
		 private boolean stop=false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
//				Thread.sleep(1500);
				
//				Message message = new Message();
//				while(true){
//				message.what=0x12;             
//				Monitor.this.myHandler.sendMessage(message);
//				Thread.sleep(2000);
//				tt++;
//				}
//				
//				Message message1 = new Message();
//				sourpc=connPC.pcin.readLine();
//				destpc=connPC.pcin.readLine();
				
			int mode=-1;
			 infoth ifo=new infoth();
			
				while(stop==false){
					
					int premode=mode;
					if(premode!=mode||mode==-1){
						ifo.setstop(true);
						
						 ifo=new infoth();
						ifo.setstop(false);
						new Thread(ifo).start();
					}
					Log.i("path","pc");
//					pc.setfin(true);
					pc=new connPC();
					pc.start();
					Log.i("path","pcstart");
					
					while(pc.getlogin()==false){
						}
					Log.i("path","login"+pc.getlogin()+"1");
					
				int cx=0;
				
				
				 mode=connPC.pcin.readInt();
				 Log.i("path","mode");
				
				if(mode==0||mode==1){
					
				
				int dx=connPC.pcin.readInt();
				Log.i("path","length");
				path[0]=-5;
				path[1]=-5;
				path=new int[dx];
				while (cx<dx) {
			          path[cx] = connPC.pcin.readInt();
			          cx++;
			        }

				if(BluetoothService.Amigo.checktr()==true){//travel unstart or stop
					BluetoothService.Amigo.starttravel(path);
					for(int i=0;i<path.length;i++){
						Log.i("path",""+path[i]);
						}
					}
				
				}//if0.1
				
				if(mode==2){
					
					char comm=connPC.pcin.readChar();
					if(comm=='w'){
						BluetoothService.Amigo.setTransVelocity(250);
						Thread.sleep(1000);
						BluetoothService.Amigo.setTransVelocity(0);
					}
					if(comm=='s'){
						BluetoothService.Amigo.setTransVelocity(-250);
						Thread.sleep(1000);
					}
					if(comm=='a'){
						BluetoothService.Amigo.setRelativeHeading(15);;
						Thread.sleep(1000);
					}
					if(comm=='d'){
						BluetoothService.Amigo.setRelativeHeading(-15);;
						Thread.sleep(1000);
					}
					
					}//mode2
					//pc.setfin(true);
				}//while
	
//				String pp=sour.getText().toString(); 
//				int[] temp=new int[pp.length()];
//				for(int i=0;i<temp.length;i++){
//					temp[i]=  Character.getNumericValue(pp.charAt(i));
//						}
//				BluetoothService.Amigo.starttravel(temp);
				
			}//try
			 catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				 Log.i("path","monitor catch"+pc.getlogin());
				
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 Log.i("path","monitor catch"+pc.getlogin());
				e.printStackTrace();
					}
				
				
			 
			}	//run
		public void setstop(boolean T){
			stop=T;
			Thread.currentThread().interrupt();
			while(Thread.currentThread().interrupted()==false){}
			stop=T;
			
		}
	}
	 class infoth implements Runnable{
		
		private boolean stop=false;
			public void run() {
				InetAddress severInetAddr;
				try {
					severInetAddr = InetAddress.getByName("120.105.129.101");
					infosock = new Socket(severInetAddr,405);
					infoout=new PrintWriter(
							new OutputStreamWriter(infosock.getOutputStream())); 
					
					while(stop==false){
						if(BluetoothService.Amigo.checktr()==true){
							reach=true;
							BluetoothService.Amigo.stoptravel();
						}
						else {
							reach=false;
						}
						infoout.println("Battery: "+PacketReceiver.mAmigoInfo.getBattery()+"Stall: "+PacketReceiver.mAmigoInfo.getstall()
								+"reach: "+reach);
						infoout.flush();
						Thread.sleep(1000);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
			}
			public void setstop(boolean T){
				stop=T;
				Thread.currentThread().interrupt();
				while(Thread.currentThread().isInterrupted()!=true){}
				stop=T;
			}
	 
	 
	 }
	 /****
	 private TimerTask timerTask=new TimerTask() {
			
		  @Override
			public void run() {
					tt++;
					degree2=BluetoothService.degree;
					Message message2 = new Message();
		  			message2.what=0x12;             
		  			Monitor.this.myHandler.sendMessage(message2);
					
				}
			
		};
	    ****/
		
     
	
	
}
