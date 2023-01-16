// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.*;
import org.photonvision.targeting.PhotonPipelineResult;


public class VisionSubsystem extends SubsystemBase {
  /** Creates a new VisionSubsystem. */
  final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(0);
      final double TARGET_HEIGHT_METERS = Units.feetToMeters(0);
      final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

  private PhotonCamera camera = new PhotonCamera("gloworm");
  private Buttons buttons;

  public VisionSubsystem(Buttons bs) {
    buttons = bs;
  }
  
  public double getRange(){
    var result = camera.getLatestResult();
    double range =
    PhotonUtils.calculateDistanceToTargetMeters(
    CAMERA_HEIGHT_METERS,
    TARGET_HEIGHT_METERS,
    CAMERA_PITCH_RADIANS,
    Units.degreesToRadians(result.getBestTarget().getPitch()));
    return range;
  }
  public boolean hasTargets(){
    return camera.getLatestResult().hasTargets();
  }
  
​
  @Override
  public void periodic() {
  
    // This method will be called once per scheduler run
    int pressed = buttons.getPressedId();
    if (hasTargets() && pressed != 0) {
     double range = getRange();
     switch(pressed) {
      case 1:
        System.out.println("PRESSING ONE");
      case 2:
        System.out.println("PRESSING TWO");
      
     }
     
   }
   
   
​
  //  SmartDashboard.putBoolean("Do I Has Targets?", hasTargets());
  
  }
}
