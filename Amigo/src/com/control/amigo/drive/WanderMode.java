package com.control.amigo.drive;

import android.util.Log;

public class WanderMode implements Runnable {
	private AmigoCommunication Comm;
	
	private boolean active = false;
	private int[] sonar = new int[8];
	
	private int transV=0, rotV=0, motorStopTimes=0, rightRotTimes=0, leftRotTimes=0;
	
	public WanderMode(AmigoCommunication Comm ) {
		// TODO Auto-generated constructor stub
		this.Comm = Comm;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while( active ){
			try {
				sonar = PacketReceiver.mAmigoInfo.getSonars();
				new Thread(new info()).start();
				if ( sonar[0]<50 || sonar[1]<100 || sonar[2]<250 ){
					if( !PacketReceiver.mAmigoInfo.isMotor() ){
						motorStop(40);
					}
//					if ( sonar[2]<300 && sonar[3]<300 || sonar[1]<300 && sonar[2]<300 || sonar[0]<300 && sonar[1]<300 && sonar[2]<300 ){
//						transV=0;System.out.println("1-1");
//						rotV=-40;
//						Comm.setTransVelocity(transV);
//						Comm.setRotVelocity(rotV);
//						Thread.sleep(800);
//						
//						rightRotTimes++;
//					}
//					else if( !receiver.mAmigoInfo.isMotor() ){System.out.println("1-2");
//						motorStop(40);
//					}
//					else{
						transV=0;System.out.println("1-3");
						rotV=-40;
//						Comm.setTransVelocity(transV);
						Comm.setRotVelocity(rotV);
						Thread.sleep(200);
//					}
					
//					if( rightRotTimes > 20 ){
//						rotV=-30;
//						Comm.setRotVelocity(rotV);
//						Comm.setAbsoluteHeading(-90);
//						Thread.sleep(3000);
//						rightRotTimes=0;
//					}
					
				}
				else if( sonar[3]<250 || sonar[4]<100 || sonar[5]<50 ){
					if( !PacketReceiver.mAmigoInfo.isMotor() ){
						motorStop(-40);
					}
//					if ( sonar[2]<300 && sonar[3]<300 || sonar[3]<300 && sonar[4]<300 || sonar[3]<300 && sonar[4]<300 && sonar[5]<300 ){
//						
//						transV=0;System.out.println("2-1");
//						rotV=40;
//						Comm.setTransVelocity(transV);
//						Comm.setRotVelocity(rotV);
//						Thread.sleep(800);
//						
//						leftRotTimes++;
//					}
//					else if( !receiver.mAmigoInfo.isMotor() ){
//						System.out.println("2-2"+" moter:"+receiver.mAmigoInfo.isMotor());
//						motorStop(-40);
//					}
//					else{
						transV=0;System.out.println("2-3");
						rotV=40;
//						Comm.setTransVelocity(transV);
						Comm.setRotVelocity(rotV);
						Thread.sleep(200);
//					}
					
//					if( leftRotTimes>20 ){
//						rotV=30;
//						Comm.setRotVelocity(rotV);
//						Comm.setAbsoluteHeading(90);
//						Thread.sleep(3000);
//						leftRotTimes=0;
//					}
					
				}
				
//				else{
//					if( !PacketReceiver.mAmigoInfo.isMotor() ){System.out.println("3-1");
//						motorStop(randomRotate());
//					}
//					else{
//						transV=200;System.out.println("3-2");
//						rotV=0;
////						Comm.setTransVelocity(transV);
//						Comm.setRotVelocity(rotV);
//						Thread.sleep(100);
//					}
//				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void motorStop( int rotateV ) throws Exception{
		transV=-400;
		rotV=rotateV;
//		Comm.setTransVelocity(transV);
		Comm.setRotVelocity(rotV);
		Thread.sleep(200);
		motorStopTimes++;
		
		if( sonar[6]<300 || sonar[7]<300 ){
			transV=400;System.out.println("3-2"+" moter:"+PacketReceiver.mAmigoInfo.isMotor());
			rotV=rotateV;
//			Comm.setTransVelocity(transV);
			Comm.setRotVelocity(rotV);
			Thread.sleep(200);
			motorStopTimes = 0;
		}
		
		else if( motorStopTimes==1 ){
			rotV=rotateV;
//			Comm.setTransVelocity(transV);
			Comm.setRotVelocity(rotV);
			Thread.sleep(200);
			motorStopTimes = 0;
		}
	}
	
	public int randomRotate(){
		int rand = (int)(Math.random()*2+1);
		//int v = (int)(Math.random()*30+1);
		if( rand==1 ){
			return 40;
		}
		else{
			return -40;
		}
	}
	
	public void endWanderMode() throws Exception{
		active = false;
		Comm.setTransVelocity(0);
		Comm.setRotVelocity(0);
	}
	
	public void startWanderMode(){
		active = true;
		new Thread(this).start();
	}
	 class info implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
			Log.i("findori","x: "+PacketReceiver.mAmigoInfo.getXPos()+" y: "+PacketReceiver.mAmigoInfo.getYPos()+" thpos: "+PacketReceiver.mAmigoInfo.getThetaPos()
					+" control: "+PacketReceiver.mAmigoInfo.getControl()+" compass: "+PacketReceiver.mAmigoInfo.getcompass());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		 
	 }
}
