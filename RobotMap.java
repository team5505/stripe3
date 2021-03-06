// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5505.stripeVision;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon driveRightRearTalon2;
    public static CANTalon driveLeftFrontTalon6;
    public static CANTalon driveLeftRearTalon3;
    public static CANTalon driveRigthFrontTalon4;
    public static RobotDrive driveRobotDrive41;
    public static Ultrasonic driveBackUltrasonic;
    public static CANTalon intakeAndWinchCANTalon1;
    public static Servo agitatorServo;
    public static SpeedController agitatormotoragitator;
    public static CANTalon shooterCANTalon5;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveRightRearTalon2 = new CANTalon(2);
        LiveWindow.addActuator("drive", "RightRearTalon2", driveRightRearTalon2);
        
        driveLeftFrontTalon6 = new CANTalon(6);
        LiveWindow.addActuator("drive", "LeftFrontTalon6", driveLeftFrontTalon6);
        
        driveLeftRearTalon3 = new CANTalon(3);
        LiveWindow.addActuator("drive", "LeftRearTalon3", driveLeftRearTalon3);
        
        driveRigthFrontTalon4 = new CANTalon(4);
        LiveWindow.addActuator("drive", "RigthFrontTalon4", driveRigthFrontTalon4);
        
        driveRobotDrive41 = new RobotDrive(driveLeftFrontTalon6, driveLeftRearTalon3,
              driveRigthFrontTalon4, driveRightRearTalon2);
        
        driveRobotDrive41.setSafetyEnabled(false);
        driveRobotDrive41.setExpiration(0.1);
        driveRobotDrive41.setSensitivity(0.5);
        driveRobotDrive41.setMaxOutput(1.0);

        driveBackUltrasonic = new Ultrasonic(4, 5);
        LiveWindow.addSensor("drive", "BackUltrasonic", driveBackUltrasonic);
        
        intakeAndWinchCANTalon1 = new CANTalon(1);
        LiveWindow.addActuator("intakeAndWinch", "CAN Talon 1", intakeAndWinchCANTalon1);
        
        agitatorServo = new Servo(9);
        LiveWindow.addActuator("agitator", "Servo ", agitatorServo);
        
        agitatormotoragitator = new Talon(0);
        LiveWindow.addActuator("agitator", "motoragitator", (Talon) agitatormotoragitator);
        
        shooterCANTalon5 = new CANTalon(5);
        LiveWindow.addActuator("Shooter", "CAN Talon 5", shooterCANTalon5);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        //LiveWindow.addActuator(drive, "DriveSubSys", component);
    }
}
