package org.usfirst.frc5505.stripeVision.commands;

import org.usfirst.frc5505.stripeVision.Robot;
import org.usfirst.frc5505.stripeVision.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class Vision_Auton extends Command {
/*
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}*/
	double distance;
	double speed;
	double time;
	boolean targetFound;
	Timer timer;

	static final double p = 0.007;

	public Vision_Auton(double distance, double speed) {
		requires(Robot.visionSubsystem);
		requires(Robot.drive);
		this.distance = distance;
		this.speed = speed;
		this.time = 1000;
	}

	public Vision_Auton(double distance, double speed, double time) {
		requires(Robot.visionSubsystem);
		requires(Robot.drive);
		this.distance = distance;
		this.speed = speed;
		this.time = time;
		timer = new Timer();
	}

	protected void initialize() {
		timer.reset();
		timer.start();
		Robot.drive.resetEncoders();

	}

	protected void execute() {
		double turn = 0.;
		if (Robot.visionSubsystem.getIsTargetFound()) {
			double center = Robot.visionSubsystem.getCenterX();
			turn = p * (VisionSubsystem.getCamWidth() - center);
			System.out.println("Turn value: " + turn);
		}
		Robot.drive.getRobotDrive().arcadeDrive(-speed, turn);
	}

	protected boolean isFinished() {
		if ((Math.abs(Robot.drive.getCanTalonEncoderCounts()) >= Math.abs(distance)) || timer.get() > time) {
			return true;
		} else {
			return false;
		}
	}

	protected void end() {
		Robot.drive.getRobotDrive().arcadeDrive(0, 0);
	}

	protected void interrupted() {
		Robot.drive.getRobotDrive().arcadeDrive(0, 0);
	}
}