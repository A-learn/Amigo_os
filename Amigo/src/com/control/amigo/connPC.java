package com.control.amigo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

import com.control.amigo.BluetoothConnect.BTstate;
import com.control.amigo.BluetoothService.BTState;
import com.control.amigo.drive.PacketReceiver;

public class connPC extends Thread{
	

		private Socket pcsock=null;
		
//		 static BufferedReader pcin = null;
		 static PrintWriter pcout = null;
		 static   DataInputStream pcin = null;
//				 new DataInputStream(connect.getInputStream());
//		 static 
		private String login="連線失敗";
		private boolean finish=false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{

				InetAddress severInetAddr=InetAddress.getByName("120.105.129.101");
				pcsock = new Socket(severInetAddr,404);
		

					
					pcin = new DataInputStream(pcsock.getInputStream());
					
					pcout = new PrintWriter(
							new OutputStreamWriter(pcsock.getOutputStream()));
					Thread.sleep(100);
					login="連線成功";
//				while(true){
					Thread.sleep(1000);
					boolean reach=true;
					 if(BluetoothService.Amigo.checktr()==true){
						 reach=false;
					 }else {
						 reach=true;
					 }
					
					while(finish==false){
						Thread.sleep(1000);
					}pcsock.close();
					
//				}
				
				
			} catch( IOException | InterruptedException e ){
				e.printStackTrace();
				try{
//					btSocket.close();
//					Wifisocket.close();
					Thread.sleep(1500);
					
				} catch(InterruptedException e1){
					e1.printStackTrace();
				}
//				btSocket = null;
				
				try {
					pcsock.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		
	}
		public void setfin(boolean T){
			Thread.currentThread().interrupt();
			finish=T;
			Thread.currentThread();
			while(Thread.interrupted()==false){
			}
			finish=T;
		}
		public String getlogin(){
			return login;
		}
}
