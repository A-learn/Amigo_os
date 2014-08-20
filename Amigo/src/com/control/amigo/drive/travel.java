package com.control.amigo.drive;

import com.control.amigo.BluetoothService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;



public class travel implements Runnable {
	private AmigoCommunication Comm;
	private AmigoInfo info;
	public boolean t=false;
	private int[] vi ;
	int[] sonar = new int[8];
		double[][] A=null;
		int[][] medium=null;
		public travel(AmigoCommunication Comm,int[] vi ) {
			// TODO Auto-generated constructor stub
			this.Comm = Comm;
			info=PacketReceiver.mAmigoInfo;
			this.vi=vi;
			
		}
		
		@Override
		public void run(){
			try {
//				go(vi[0],vi[1]);
				
					//Comm.resetPosition
				pathgo(vi);
//				Thread.sleep(3500);
				t=true;
//				countcirle co=new countcirle();
//				co.start();
//				avoi.start();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("exiconne", "travel bye!");
			}
			
		}
		public void thrstop(){
			if(isT()==true){
			try {
				Comm.setTransVelocity(0);
				Comm.setRotVelocity(0);
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
			}
		}
		
	
		
		public void pathgo(int[] vi){
			
//			vec now =null;
//			vec next=null;
			for(int i=0;i<vi.length-1;i++){
//				now=new vec(vi[i]);
//				next=new vec(vi[i+1]);
				int now = vi[i];
				int next=vi[i+1];
				try {
//					if(i==0){
//					while(BluetoothService.degree<170-10||BluetoothService.degree>170+10){
//						Comm.setRelativeHeading(-10);
//						Thread.sleep(1000);
//						}
//					while(BluetoothService.degree!=170){
//						if(BluetoothService.degree<=207-2){
//						Comm.setRelativeHeading(-1);
//						Thread.sleep(1000);}
//						if(BluetoothService.degree>=207+2){
//							Comm.setRelativeHeading(1);
//							Thread.sleep(1000);
//						}
//					}
//					}
					
//					Comm.resetPosition();
					go(now,next);
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("exiconne", "travel bye!-go(now,next);");
				}
//				if(i==vi.length-2){
//					Thread.currentThread().interrupt();
//					t=true;
//				}
			}
		
			
		}
		boolean sourr=true;
		public void go(int sour,int des) throws Exception{
			if(isT()==true){
				Thread.currentThread().isInterrupted();
				return;
				}
			if(sour==0&&des==1){
				
				
					setrotang(0);
					setgodis(360,250);
//				setrotang(90);
//				Thread.sleep(3000);
//				setrotang(0);
//				setrotang(180);
//				Thread.sleep(3000);
//				setrotang(0);
//				setrotang(270);
			}
			
			if(sour==1&&des==0){
				
					setrotang(180);
					setgodis(360,250);
					
			}
			if(sour==1&&des==2){
				setrotang(0);
				setgodis(300,250);
			}
			if(sour==1&&des==4){
				
				setrotang(90);
				setgodis(300,300);
			}
			
			if(sour==2&&des==1){
				setrotang(180);
				setgodis(300,250);
				
			}
			if(sour==2&&des==3){
				setrotang(90);
				setgodis(360,250);
//				setrotang(180);
//				setrotang(359);
			}
			if(sour==2&&des==4){
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=133||PacketReceiver.mAmigoInfo.getThetaPos()>=137)&&t==false){
//					Comm.setRotVelocity(30);
//					Comm.setAbsoluteHeading(135);
//					Thread.sleep(3000);
//					}
				setrotang(135);
				setgodis(423,250);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=178||PacketReceiver.mAmigoInfo.getThetaPos()>=182)&&t==false){
//					Comm.setRotVelocity(30);
//					Comm.setAbsoluteHeading(180);
//					Thread.sleep(1500);
//					}
			}
			if(sour==3&&des==2){
				if(sourr==true){
					setrotang(180);
					sourr=false;
				}
				setrotang(270);
				setgodis(360,200);
				
			}
			if(sour==3&&des==8){//90
				setrotang(90);
				setgodis(360,250);
				
			}
			if(sour==8&&des==3){
				if(sourr==true){
					setrotang(180);
					sourr=false;
				}
				setrotang(270);
				setgodis(360,250);
				
			}
			if(sour==4&&des==2){
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=135||PacketReceiver.mAmigoInfo.getThetaPos()>=135)&&t==false){
//					Comm.setRotVelocity(30);
//					Comm.setAbsoluteHeading(135);
//					Thread.sleep(3000);
//					}
				setrotang(315);
				setgodis(423,250);
				
			}
			
			if(sour==4&&des==1){
				if(sourr==true){
					setrotang(180);
					sourr=false;
				}
				setrotang(270);
				setgodis(300,250);
			}
			if(sour==4&&des==7){
				setrotang(90);
				setgodis(540,250);
			}
			if(sour==7&&des==4){
				if(sourr==true){
					setrotang(180);
					sourr=false;
				}
				setrotang(270);
				setgodis(480,250);
			}
			
			if(sour==0&&des==0x10){
				setrotang(90);
				setgodis(180,250);
//				setrotang(0);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=17||PacketReceiver.mAmigoInfo.getThetaPos()>=21)&&t==false){
//					Comm.setRotVelocity(19);
//					Comm.setAbsoluteHeading(19);
//					Thread.sleep(1000);
//					}
				setrotang(19);
				setgodis(190,250);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()>=3)&&t==false){
//					Comm.setRotVelocity(-19);
//					Comm.setAbsoluteHeading(0);
//					Thread.sleep(1000);
//					}
//				setrotang(0);
			}
			if(sour==0x10&&des==0){
				setrotang(200);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=197||PacketReceiver.mAmigoInfo.getThetaPos()>=201)&&t==false){
//					Comm.setRotVelocity(19);
//					Comm.setAbsoluteHeading(199);
//					Thread.sleep(1000);
//					}
				setgodis(188,250);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=178||PacketReceiver.mAmigoInfo.getThetaPos()>=182)&&t==false){
//					Comm.setRotVelocity(-19);
//					Comm.setAbsoluteHeading(180);
//					Thread.sleep(1000);
//					}
				setrotang(270);
				setgodis(178,250);
				
			}
			if(sour==0x10&&des==0x12){
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=133||PacketReceiver.mAmigoInfo.getThetaPos()>=137)&&t==false){
//					Comm.setRotVelocity(10);
//					Comm.setAbsoluteHeading(135);
//					Thread.sleep(3000);
//					}
//				setrotang(0);
				setrotang(135);
				setgodis(169,250);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=178||PacketReceiver.mAmigoInfo.getThetaPos()>=182)&&t==false){
//					Comm.setRotVelocity(30);
//					Comm.setAbsoluteHeading(165);
//					Thread.sleep(1000);
//					Comm.setRotVelocity(15);
//					Comm.setAbsoluteHeading(180);
//					Thread.sleep(1000);
//					}
				setrotang(180);
			}
			if(sour==0x12&&des==0x10){
//				setrotang(0);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=313||PacketReceiver.mAmigoInfo.getThetaPos()>=317)&&t==false){
//					Comm.setRotVelocity(-30);
//					Comm.setAbsoluteHeading(315);
//					Thread.sleep(1500);
//					}
				setrotang(315);
				setgodis(171,250);
//				while(PacketReceiver.mAmigoInfo.getThetaPos()>=3&&t==false){
//					Comm.setRotVelocity(30);
//					Comm.setAbsoluteHeading(0);
//					Thread.sleep(1500);
//					}
			
				
			}
			if(sour==0&&des==5){
				go(0,0x10);
				go(0x10,0x12);
				
			}
			if(sour==5&&des==0){
				go(0x12,0x10);
				go(0x10,0);
				
			}
			if(sour==5&&des==6){
				setrotang(90);
				setgodis(360,250);
				
			}
			if(sour==6&&des==5){
				if(sourr==true){
					setrotang(180);
					sourr=false;
				}
				setrotang(270);
				setgodis(360,250);
				
			}
			if(sour==8&&des==7){
//				setrotang(180);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=164-2||PacketReceiver.mAmigoInfo.getThetaPos()>=164+2)&&t==false){
//					Comm.setRotVelocity(-16);
//					Comm.setAbsoluteHeading(164);
//					Thread.sleep(1000);
//					}
				setrotang(169);//15
				setgodis(306,200);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=179||PacketReceiver.mAmigoInfo.getThetaPos()>=182)&&t==false){
//					Comm.setRotVelocity(16);
//					Comm.setAbsoluteHeading(180);
//					Thread.sleep(1000);
//					}
				
			}
			if(sour==7&&des==8){
//				setrotang(0);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=344-2||PacketReceiver.mAmigoInfo.getThetaPos()>=344+2)&&t==false){
//					Comm.setRotVelocity(-16);
//					Comm.setAbsoluteHeading(344);
//					Thread.sleep(1000);
//					}
				setrotang(349);
				setgodis(306,200);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()>=3)&&t==false){
//					Comm.setRotVelocity(16);
//					Comm.setAbsoluteHeading(0);
//					Thread.sleep(1000);
//					}
			}
			if(sour==7&&des==6){
//				setrotang(180);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=190||PacketReceiver.mAmigoInfo.getThetaPos()>=194)&&t==false){
//					Comm.setRotVelocity(12);
//					Comm.setAbsoluteHeading(192);
//					Thread.sleep(1000);
//					}
				setrotang(191);//5,11,12
				setgodis(306,250);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=179||PacketReceiver.mAmigoInfo.getThetaPos()>=181)&&t==false){
//					Comm.setRotVelocity(-12);
//					Comm.setAbsoluteHeading(180);
//					Thread.sleep(1000);
//					}
				
			}
			if(sour==6&&des==7){
//				setrotang(0);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()<=8||PacketReceiver.mAmigoInfo.getThetaPos()>=12)&&t==false){
//					Comm.setRotVelocity(10);
//					Comm.setAbsoluteHeading(10);
//					Thread.sleep(1000);
//					}
				setrotang(10);
				setgodis(306,100);
//				while((PacketReceiver.mAmigoInfo.getThetaPos()>=2)&&t==false){
//					Comm.setRotVelocity(-10);
//					Comm.setAbsoluteHeading(0);
//					Thread.sleep(1000);
//					}
				
			}
		}
		
		public void setgodis(int dis,int vel){
			try {
				
				double x=dis/(vel/10);
				double xt=0;
				if(vel>=300){
					x=x-0.01;
				}
				while(xt<=x&&t==false){
					thrstop();
					avoidbum();
					xt=xt+0.1;
				Comm.setTransVelocity(vel);
				Thread.sleep(100);
				}
				Comm.setTransVelocity(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("exiconne", "travel bye!-setgodis;");
			}
			
		}
		int circle=0;
		public void setrotang(int ang){
			boolean a=false;
			double x=0,y=0;
			double x2=ang/10;
			boolean cirflag=false;
			try {
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(ang==0){
				while((PacketReceiver.mAmigoInfo.getThetaPos()>=1)&&t==false){
					
					try {
						if(x>1){
							Comm.setRelativeHeading(-30);
							Thread.sleep(1000);
						}
					if(PacketReceiver.mAmigoInfo.getThetaPos()>=180)
						{
//							Comm.setRotVelocity(10);
						
						int ang0=((circle+1)*360)-(int)((circle*360+360)/45);
						ang0=7;
							Comm.setAbsoluteHeading(ang0);
							Thread.sleep(3000);
							circle=1;
							x++;
							cirflag=true;
							if(PacketReceiver.mAmigoInfo.getThetaPos()<=ang0-0.5||PacketReceiver.mAmigoInfo.getThetaPos()<=ang0+0.5){
								break;
								
							}
						} 	
					else{
						Comm.setAbsoluteHeading(ang);
						Thread.sleep(3000);
					}
					
						
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(cirflag==true){
//					circle++;
					cirflag=false;
				}
				
			}
			else {
				int ang2=ang;
				
				
					ang2=ang-(int)((circle*360+ang)/45);
				
				while((PacketReceiver.mAmigoInfo.getThetaPos()<ang2-0.5||PacketReceiver.mAmigoInfo.getThetaPos()>=ang2+0.5)&&t==false){
					try {//special ang
						thrstop();
						y++;
						Comm.setRotVelocity(5);
						if(PacketReceiver.mAmigoInfo.getThetaPos()>180){
							
//							circle++;
						}
						if(y>1){
							Comm.setRelativeHeading(-30);
							Thread.sleep(1000);
						}
							if(ang>180)
							{
//								Comm.setRotVelocity(-30);
								Comm.setAbsoluteHeading(ang2);
								Thread.sleep(3000);
								Comm.setRotVelocity(0);
							}
							else{
//								Comm.setRotVelocity(30);
								Comm.setAbsoluteHeading(ang2);
								Thread.sleep(3000);
								Comm.setRotVelocity(0);
							}
						
							
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
			}
		
			
		}
		public  boolean isT() {
			return t;
		}
		public  void setT(boolean t) {
			
			Thread.currentThread().interrupt();
			while(Thread.currentThread().isInterrupted()==false){}
			this.t = t;
//			avoi.stopavo(t);
		
		}
		public class vec{
			int x=0;
			int y=0;
			int no=0;
			vec(){}
			vec(int no){
				this.no=no;
				switch(no){
				case 0:
					x=0;
					y=0;
					
				case 1: 
					x=360;
					y=0;
				case 2:
					x=660;
					y=0;
				case 3:
					x=660;
					y=360;
				case 4:
					x=360;
					y=300;
				case 5:
					x=60;
					y=360;
				case 6:
					x=0;
					y=720;
				case 7:
					x=360;
					y=900;
				case 8:
					x=660;
					y=720;
					
				}
			}
			public void set(int x,int y,int no){
				this.x=x;
				this.y=y;
				this.no=no;
			} 
			
		} 
		public void avoidbum() {
			 boolean stop=false;
			 
					sonar = PacketReceiver.mAmigoInfo.getSonars();
					if(sonar[2]<350||sonar[3]<350){
						try {
							if(sonar[2]>sonar[3]){
								Comm.setTransVelocity(0);
								Comm.setRelativeHeading(15);
								Thread.sleep(100);
							
							}else {
								Comm.setTransVelocity(0);
								Comm.setRelativeHeading(-15);
								Thread.sleep(100);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}//if
					else if(sonar[1]<250||sonar[4]<250){
						try {
							if(sonar[1]>sonar[4]){
								Comm.setTransVelocity(0);
								Comm.setRelativeHeading(15);
								Thread.sleep(100);
							
							}else {
								Comm.setTransVelocity(0);
								Comm.setRelativeHeading(-15);
								Thread.sleep(100);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
			}
//			public void stopavo(boolean t){
//					Thread.currentThread().interrupt();
//					while(Thread.currentThread().isInterrupted()==false){}
//					stop=t;
//					
//			}
		
//		}
		public boolean getinterrupt(){
			return t;
		}
	
	
}
