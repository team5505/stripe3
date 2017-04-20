// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5505.stripeVision.commands;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5505.stripeVision.Robot;

import com.ctre.CANTalon;

/**
 *
 */
//public class encoderDrive extends Command {
public class encoderDrive extends Command implements PIDSource, PIDOutput {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private int m_distanceInches;

    private double m_pulses = 0;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    private boolean m_driveGyroStraight = true;
    
    private int m_driveUltrasonicDistance = 0;
    

    private double m_maxSpeed = 0.5;
    private double m_speed = 0;

    private static final double INCHES_PER_REVOLUTION = 8.0 * Math.PI;
	private static final double PULSES_PER_REVOLUTION = 1024 * 4;


	private static final double moveP = 0.01;
	private static final double moveI = 0;
	private static final double moveD = 0;    
    private static final double kF = 0.15;   // minimum power for the motor to move??
    PIDController movePID;

    private double m_initialDirection = 0;
	private static final double turnP = 0.01;
  
	Timer timer;
	double time; // This will be our timeout - default to maybe 1 second or so.
	
	private double m_setUltrasonicInches = 0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public encoderDrive(int distanceInches) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_distanceInches = distanceInches;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        timer = new Timer();
        time = 3; // default to 3 seconds per move?
        
        movePID = new PIDController(moveP, moveI, moveD, kF,
        		this, this);       
        
        movePID.setOutputRange(-m_maxSpeed, m_maxSpeed);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    public encoderDrive(int distanceInches, int ultrasonicDistance, boolean driveGyro) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_distanceInches = distanceInches;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        m_driveGyroStraight = driveGyro; 
        m_driveUltrasonicDistance = ultrasonicDistance;
        
        timer = new Timer();
        time = 3; // default to 3 seconds per move?
        
        movePID = new PIDController(moveP, moveI, moveD, kF,
        		this, this);       
        
        movePID.setOutputRange(-m_maxSpeed, m_maxSpeed);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setFeedbackDevice();

    	Robot.drive.zeroEncoderTalon(); //resetEncoders();
    	if (Robot.drive.getCanTalonEncoderCounts() > 0)
        	Robot.drive.zeroEncoderTalon();  // Sometimes it seems not to get set so Try again!

    	// FIXME convert ppr to inches I think 1024 * 4 ppr == 8" * pi or 0.0245 " per pulse or 163 pulses per inch!!
    	m_pulses = (m_distanceInches * (PULSES_PER_REVOLUTION/INCHES_PER_REVOLUTION)); //163; // + (int)Robot.drive.getWheelPos();
    	SmartDashboard.putNumber("SetPosition", (int)m_pulses);
    	SmartDashboard.putNumber("m_distanceInches", m_distanceInches);
    	SmartDashboard.putNumber("m_driveUltrasonicDistance", m_driveUltrasonicDistance);

    	//    	this.movePID.setContinuous(true);  // This is for the case where rollover is a problem!
    	if (m_pulses >= 0)
    		this.movePID.setInputRange(0, m_pulses); //Robot.drive.getWheelPos(), m_pulses);
    	else
    		this.movePID.setInputRange(m_pulses, 0);
    	
    	this.movePID.setSetpoint(m_pulses);
    	this.movePID.enable();

    	m_initialDirection = Robot.drive.getGyroAngle();
    	
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentPosition = Robot.drive.getCanTalonEncoderCounts();
    	
    	double proportionalDirectionError = (Robot.drive.getGyroAngle() - m_initialDirection) * turnP;
    	
//    	Robot.drive.tankDrive(m_speed, m_speed);
    	
//    	Robot.drive.getRobotDrive().arcadeDrive(m_speed, proportionalDirectionError);
    	Robot.drive.getRobotDrive().arcadeDrive(m_speed, 0);
    	      	
    	SmartDashboard.putNumber("CurrentPosition", currentPosition);
    	SmartDashboard.putNumber("m_speed", m_speed);
    	SmartDashboard.putNumber("proportionalDirectionError", proportionalDirectionError);
//    	SmartDashboard.putNumber("movePID error", (int)movePID.getError());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		return (Math.abs(Robot.drive.getCanTalonEncoderCounts()) >= Math.abs(m_pulses) ); // || 
    				//timer.get() > time); //  ||
//    				(m_driveUltrasonicDistance > 0 ? Robot.drive.getBackUltrasonicDistance() < m_driveUltrasonicDistance : false));
    }

    // Called once after isFinished returns true
    protected void end() {
    	this.movePID.disable();
    	Robot.drive.drivestraight(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    

    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon wheel encoder input and PID Coefficients.    */
    public void pidWrite(double output) {
        m_speed = output;
    }
    
    @Override
    public double pidGet() {
		return Robot.drive.getCanTalonEncoderCounts();		
	}
    	
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
	
	public void setPIDSourceType(PIDSourceType type) {
		//return PIDSourceType.kDisplacement;
	}


}
