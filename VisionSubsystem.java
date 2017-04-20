package org.usfirst.frc5505.stripeVision.subsystems;

import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.usfirst.frc5505.stripeVision.GripPipeline;
import org.usfirst.frc5505.stripeVision.Robot;

import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class VisionSubsystem extends Subsystem {

	private static final int camWidth = 160;
	private static final int camHeight = 120;

	private VisionThread visionThread;
	UsbCamera frontCamera;
	private double centerX = 0.0;
	private double centerX1 = 0.0;
	private double centerX2 = 0.0;

	private final Object imgLock = new Object(); // changed to private and
													// called imgLock from
	CvSource source; // Subsystem
	CvSink sink;
	boolean isTargetFound = false; // added boolean to show on dashboard and to
									// end command when target acquired

	public VisionSubsystem() {
		frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
		frontCamera.setResolution(camWidth * 2, camHeight * 2);
		frontCamera.setFPS(16);
		frontCamera.setBrightness(50);
		frontCamera.setExposureManual(20);
		frontCamera.setWhiteBalanceManual(10);
		visionThread = new VisionThread(frontCamera, new GripPipeline(), pipeline -> {
			try {
				if (!pipeline.filterContoursOutput().isEmpty()) {
					System.out.println(pipeline.filterContoursOutput());
					isTargetFound = true;
					Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
					Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
					synchronized (imgLock) {
						centerX1 = r.x + (r.width / 2);
						centerX2 = r1.x + (r1.width / 2);
					}
				}
			} catch (Exception e) {
				if (isTargetFound = false) {
					visionThread.start();
				}
			}

		});
		visionThread.start();

	}

	public void updateStatus() {
		SmartDashboard.putNumber("Center X", this.getCenterX());
	}

	public void initDefaultCommand() {

	}

	public Object getImgLock() {
		return imgLock;
	}

	public static final int getCamWidth() {
		return camWidth;
	}

	public static final int getCamHeight() {
		return camHeight;
	}

	public double getCenterX() {
		synchronized (imgLock) {
			return ((getCenterX1() + getCenterX2()) / 2);
		}
	}

	public double getCenterX1() {
		// synchronized (imgLock) {
		return centerX1;
		// }
	}

	public double getCenterX2() {
		// synchronized (imgLock) {
		return centerX2;
		// }
	}

	public double getTurnCenterX1_X2() {
		return ((centerX1 - (camWidth / 2)) + (centerX2 - (camWidth / 2)) / 2);
	}

	public double getTurnCenterX() {
		return getCenterX() - (camWidth / 2);
	}

	public boolean getIsTargetFound() { // added for command end and to show on
										// dashboard.
		synchronized (imgLock) {
			return isTargetFound;
		}

	}

}
