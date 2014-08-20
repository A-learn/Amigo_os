package com.control.amigo.drive;

import java.io.DataInputStream;
import java.io.IOException;

import android.util.Log;

public class PacketReceiver extends Thread {
	
	public static AmigoInfo mAmigoInfo = new AmigoInfo();
	
	private DataInputStream CommIn = null;
	private boolean receiving = false;
	
	private double xPos = 0.0, yPos = 0.0, thetaPos = 0.0;
    private int oldX = 0, oldY = 0;
    private final double WHEEL_COOR_CONV = 1.0;
    private final double WHEEL_ANGLE_CONV = 0.001534;
    private final double SPEED_CONV = 0.6154;
    private final double CONTROL_CONV = 0.001534;
	
    
	public PacketReceiver ( DataInputStream CommIn ){
		this.CommIn = CommIn;
	}
	
	@Override
	public synchronized void run(){
		int newData = 0;
        int counter = 0;
		byte[] dataBuffer = new byte[100];
		while (receiving){
			try {
				newData = CommIn.read();
				
				if( newData==0xFA ){
					dataBuffer[counter] = (byte)(newData);
            		counter++;
            		
            		newData = CommIn.read();
            		if( newData==0xFB ){
            			dataBuffer[counter] = (byte)(newData);
                		counter++;
                		
                		newData = CommIn.read();
                		dataBuffer[counter] = (byte)(newData);
                		counter++;
                		
                		int amount = newData;
                		for( int i=1; i<=amount; ++i ){
                			newData = CommIn.read();
                			dataBuffer[counter] = (byte)(newData);
                    		counter++;
                		}
                		processPacket(dataBuffer);
                		dataBuffer = new byte[100];
                		counter = 0;
            		}
            	}
			} catch (IOException e) {
				Log.e("receive", "Data Error:1");
			}

		}
	}
	
	public void startReceive(){
		receiving = true;
		this.start();
	}
	
	public void endUpdate(){
		receiving = false;
		CommIn = null;
	}
	
	public synchronized void processPacket( byte[] packet ) {
        try {
        if (packet.length > 20) {
                int pointer = 3;
                int status = byteToInt(packet[pointer]);
                if (status == 0x32) {
                    mAmigoInfo.setMotorStatus(false);
                } else if (status == 0x33) {
                    mAmigoInfo.setMotorStatus(true);
                }
                
                pointer++;
                boolean taintedOdometry = false;
                int newX = (byteToInt(packet[pointer++], packet[pointer++]) & 0x7ff);
                int newY = (byteToInt(packet[pointer++], packet[pointer++]) & 0x7ff);
                int theta = byteToInt(packet[pointer++], packet[pointer++]);
                if(xPos != Double.MAX_VALUE) {
                    double change = updatePosition(oldX, newX) * WHEEL_COOR_CONV;
                    oldX = newX;
                    if( change > 10 || change < -10 ) {
                        taintedOdometry = true;
                    }
                    else {
                        xPos += change;
                    }
                } else if(xPos == Double.MAX_VALUE) {
                    xPos = 0;
                    oldX = 0;
                }
                
                if(yPos != Double.MAX_VALUE) {
                    double change = updatePosition(oldY, newY) * WHEEL_COOR_CONV;
                    oldY = newY;
                    if( change > 10 || change < -10 ) {
                        //this is impossible, so let the user know he has to reset the values
                        taintedOdometry = true;
                        
                    }
                    else {
                        yPos += change;
                    }
                } else if(yPos == Double.MAX_VALUE) {
                    yPos = 0;
                    oldY = 0;
                } 
                thetaPos = theta *WHEEL_ANGLE_CONV * (180.0/Math.PI);
                mAmigoInfo.setXPos(xPos);
                mAmigoInfo.setYPos(yPos);
                mAmigoInfo.setThetaPos(thetaPos);
                mAmigoInfo.setTaintedOdometryValues(taintedOdometry);
                // we've finished processing our position, now speed
                double leftVel = SPEED_CONV
                        * byteToInt(packet[pointer++], packet[pointer++]);
                double rightVel = SPEED_CONV
                        * byteToInt(packet[pointer++], packet[pointer++]);
                double velocity = (leftVel + rightVel) / 2.0;
                mAmigoInfo.setLeftVel(leftVel);
                mAmigoInfo.setRightVel(rightVel);
                mAmigoInfo.setVelocity(velocity);

                double battery = byteToInt(packet[pointer++]) / 10;
                mAmigoInfo.setBattery(battery);

                // stall value is not used by player or aria, really have to
                // check, could be handy!
                int stall = byteToInt(packet[pointer++], packet[pointer++]);
                mAmigoInfo.setstall(stall);
                double control = byteToInt(packet[pointer++], packet[pointer++])
                        * CONTROL_CONV;
                mAmigoInfo.setControl(control);
                // PTU describes if motor or sonars are on, could be handy
                byte temp = packet[pointer++];
                byte temp2 = packet[pointer++];
                int PTU = byteToInt(temp, temp2);
                int compass = byteToInt(packet[pointer++]); // always 0, makes
                                                            // sense ^o)
                mAmigoInfo.setcompass(compass);
                int nrSonars = byteToInt(packet[pointer++]);
                int[] sonars = new int[8];
                for (int i = 0; i < nrSonars; i++) {
                    int sonar = byteToInt(packet[pointer++]);
                    sonars[sonar] = byteToInt(
                            packet[pointer++], packet[pointer++]);
                }                
                mAmigoInfo.setSonars(sonars);
//                getInfo();                
        }
        }catch(Exception e) {
            e.printStackTrace();
        }
//        int ll=byteToInt(packet[2]);
//        Log.i("pack",  packet[ll+1]+","+packet[ll+2]+" , "+ll);
//        Log.i("pack", "check : "+ calculateCheckSum(packet,ll-1) );
       
    }
	public void getInfo(){
		System.out.println("Battery:" + mAmigoInfo.getBattery());
		System.out.println("X:" + mAmigoInfo.getXPos());
		System.out.println("Y:" + mAmigoInfo.getYPos());
		System.out.println("Moter:" + mAmigoInfo.isMotor());
//		int[] sonar = mAmigoInfo.getSonars();
//		for( int i=0; i<8; ++i ){
//    		System.out.println( "Sonar["+i+"]:"+sonar[i] );
//    	}
	}
	
	private int updatePosition(int from, int to) {
        int movement = to - from;
//        if(to - from > 1024) {
//            movement = movement - 2048;
//        } else if(from - to > 1024) { 
//            movement = movement + 2048;
//        }
        return movement;
    }
	
	private int byteToInt(byte lsb, byte msb) {
        return (lsb & 0xff) | ((msb & 0xff) << 8);
    }

    private int byteToInt(byte lsb) {
        return lsb & 0xff;
    }
    public static int calculateCheckSum(byte[] packet,int length ) { 
		
		int n = length; int i = 0; int c = 0; while(n > 1) 
		{ c += (((Byte)packet[i] & 0xff)<<8 | (Byte)packet[i+1] & 0xff);
		c = (c & 0xffff); n -= 2; i += 2; } 
		if(n > 0) c = (c ^ ((Byte)packet[i] & 0xff));
		return c; 
	}
	
}
