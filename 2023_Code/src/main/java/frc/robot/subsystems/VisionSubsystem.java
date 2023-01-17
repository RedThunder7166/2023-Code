// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.*;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;


public class VisionSubsystem extends SubsystemBase {
  /** Creates a new VisionSubsystem. */
  final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(0);
      final double TARGET_HEIGHT_METERS = Units.feetToMeters(0);
      final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

  private PhotonCamera camera = new PhotonCamera("gloworm");
  private Buttons buttons;
  private DriveSubsystem m_drive;

  public VisionSubsystem(Buttons bs, DriveSubsystem d) {
    buttons = bs;
    m_drive = d;
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
  public PhotonTrackedTarget getTarget(){
    return camera.getLatestResult().getBestTarget();
  }
  

  @Override
  public void periodic() {
  
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("BUTTON PRESSING", -1);

    PhotonTrackedTarget target = getTarget();
    int pressed = buttons.getPressedId();

    if (/*target != null &&*/ pressed != 0) {
    //  double range = getRange();
     switch(pressed) {
      case 1:
        // SmartDashboard.putBoolean("PRESSING ONE WITH TARGET", true);
        SmartDashboard.putNumber("BUTTON PRESSING", 1);
        PathPlannerTrajectory traj = PathPlanner.loadPath("New Path", new PathConstraints(2, 2));
        m_drive.followTrajectoryCommand(traj, true);
        // System.out.println("PRESSING ONE");
        break;
      case 2:
        // System.out.println("PRESSING TWO");
        // SmartDashboard.putBoolean("PRESSING TWO WITH TARGET", true);
        SmartDashboard.putNumber("BUTTON PRESSING", 2);
        break;
      case 3:
        SmartDashboard.putNumber("BUTTON PRESSING", 3);
        break;
      case 4:
        SmartDashboard.putNumber("BUTTON PRESSING", 4);
        break;
      case 5:
        SmartDashboard.putNumber("BUTTON PRESSING", 5);
        break;
      case 6:
        SmartDashboard.putNumber("BUTTON PRESSING", 6);
        break;
      case 7:
        SmartDashboard.putNumber("BUTTON PRESSING", 7);
        break;
      case 8:
        SmartDashboard.putNumber("BUTTON PRESSING", 8);
        break;
      case 9:
        SmartDashboard.putNumber("BUTTON PRESSING", 9);
        break;
      case 10:
        SmartDashboard.putNumber("BUTTON PRESSING", 10);
        break;
      // default:
      //   SmartDashboard.putNumber("BUTTON PRESSING", -1);
     }
     
   }
   int ID = -1;
   if (target != null) {
    ID = target.getFiducialId();
  }
  SmartDashboard.putNumber("TARGET ID", (double) ID);
   
   

  //  SmartDashboard.putBoolean("Do I Has Targets?", hasTargets());
  
  }
}
