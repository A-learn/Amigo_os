package com.control.amigo.drive;

public class AmigoInfo {

    protected int PTU,stall;

    protected boolean motor, taintedOdometry;

    protected double battery, xPos, yPos, thetaPos, leftVel, rightVel, velocity, control, lastX, lastY, lastTheta,compass;

    protected int[] sonars;
    
    public AmigoInfo() {
        sonars = new int[8];
    }
    
    /**
     * Are the odometryvalues of this packet tainted. If so, 
     * it is best to reset the values. This usually occurs if
     * the robot has been slipping.
     * @return
     */
    public boolean isOdomodometryTainted() {
        return taintedOdometry;
    }
    
    /**
     * Are the motors moving or not.
     * 
     * @return
     */
    public boolean isMotor() {
        return motor;
    }
    
    public void setMotorStatus(boolean motorStopped) {
        this.motor = motorStopped;
    }

    /**
     * Get volt of battery. Fully charged the battery has 12.7 volt.
     * 
     * @return
     */
    public double getBattery() {
        return battery;
    }
    
    /**
     * Get the total velocity of the robot.
     * @return
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Setpoint of the servers angular position servo in degrees. No idea 
     * what it means do.
     * 
     * @return
     */
    public double getControl() {
        return control;
    }
    public void  setControl(double control) {
       this.control=control;
    }
    /**
     * Get the left wheel velocity in mm/sec.
     * 
     * @return
     */
    public double getLeftVel() {
        return leftVel;
    }
    	
    /**
     * Check if motor is engaged. TODO: This method currently returns dummy
     * data. Parser has to be fixed for this to work!
     * 
     * @return
     */
    public int getPTU() {
        return PTU;
    }

    /**
     * Get the speed of the right motor in mm/sec.
     * 
     * @return
     */
    public double getRightVel() {
        return rightVel;
    }

    /**
     * Get the current angle of the robot in degrees, relative to its initial
     * starting position.
     * 
     * @return
     */
    public double getThetaPos() {
        return thetaPos;
    }

    /**
     * Get the current x-positionof the robot, relative to its starting point in
     * mm.
     * 
     * @return
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Get the current y-position of the robot, relative to its starting point
     * in mm.
     * 
     * @return
     */
    public double getYPos() {
        return yPos;
    }

    public int[] getSonars() {
        return sonars;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public void setLeftVel(double leftVel) {
        this.leftVel = leftVel;
    }

    public void setRightVel(double rightVel) {
        this.rightVel = rightVel;
    }

    public void setSonars(int[] sonars) {
    	for( int i=0; i<8; ++i ){
    		if( sonars[i]!=0 ){
    			this.sonars[i] = sonars[i];
    		}
    	}
    }

    public void setThetaPos(double thetaPos) {
        this.thetaPos = thetaPos;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setXPos(double pos) {
        xPos = pos;
    }

    public void setYPos(double pos) {
        yPos = pos;
    }
    public void setcompass(double com){
    	compass=com;
    }
    public double getcompass(){
    	return compass;
    }
    public void setTaintedOdometryValues(boolean taintedOdometry) {
        this.taintedOdometry = taintedOdometry;
    }
    public void setstall(int x){
    	stall=x;
    }
    public int getstall(){
    	return stall;
    }
}
