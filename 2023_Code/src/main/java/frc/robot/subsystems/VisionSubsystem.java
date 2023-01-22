// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.photonvision.*;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
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

  private PhotonCamera camera;
  private Transform3d robotToCam;
  private AprilTagFieldLayout aprilTagFieldLayout;
  private PhotonPoseEstimator robotPoseEstimator;
  private Buttons buttons;
  private DriveSubsystem m_drive;

  public VisionSubsystem(Buttons bs, DriveSubsystem d) {
    buttons = bs;
    m_drive = d;

    camera = new PhotonCamera("gloworm");
    //Set camera's position relative to the center of the robot
    robotToCam = new Transform3d(new Translation3d(0.305, 0.0889, 0), new Rotation3d(0, 0, 0));


    try {
      // aprilTagFieldLayout = new AprilTagFieldLayout(AprilTagFields.kDefaultField.m_resourceFile);
      aprilTagFieldLayout = new AprilTagFieldLayout(Filesystem.getDeployDirectory() + "/2023AprilTagMap.json");
    } catch (IOException e) {
      String message = "Unable to open April Tag field layout JSON file. Ensure that it is in the deploy folder. Exception: " + e.toString();
      System.out.println(message);
      SmartDashboard.putString("ERROR", message);
    }

    var camList = new ArrayList<Pair<PhotonCamera, Transform3d>>();
    camList.add(new Pair<PhotonCamera, Transform3d>(camera, robotToCam));
    robotPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.AVERAGE_BEST_TARGETS, camera, robotToCam);
  }

  public double getRange() {
    var result = camera.getLatestResult();
    double range = PhotonUtils.calculateDistanceToTargetMeters(
        CAMERA_HEIGHT_METERS,
        TARGET_HEIGHT_METERS,
        CAMERA_PITCH_RADIANS,
        Units.degreesToRadians(result.getBestTarget().getPitch()));
    return range;
  }

  public boolean hasTargets() {
    return camera.getLatestResult().hasTargets();
  }

  public PhotonTrackedTarget getTarget() {
    return camera.getLatestResult().getBestTarget();
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    SmartDashboard.putNumber("BUTTON PRESSING", -1);

    PhotonTrackedTarget target = getTarget();
    int pressed = buttons.getPressedId();

    if (/* target != null && */ pressed != 0) {
      // double range = getRange();
      switch (pressed) {
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
        // SmartDashboard.putNumber("BUTTON PRESSING", -1);
      }

    }
    int ID = -1;
    if (target != null) {
      ID = target.getFiducialId();
    }
    SmartDashboard.putNumber("TARGET ID", (double) ID);
    

    // SmartDashboard.putBoolean("Do I Has Targets?", hasTargets());

  }
  
  public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
    //robotPoseEstimator.setReferencePose(prevEstimatedRobotPose);
    //robotPoseEstimator.setLastPose(prevEstimatedRobotPose);
    return robotPoseEstimator.update();
}

  /*
  public Pair<Pose3d, Double> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
    robotPoseEstimator.setReferencePose(prevEstimatedRobotPose);

    double currentTime = Timer.getFPGATimestamp();
    Optional<Pair<Pose3d, Double>> result = robotPoseEstimator.update();
    if (result.isPresent()) {
        return new Pair<Pose3d, Double>(result.get().getFirst(), currentTime - result.get().getSecond());
    } else {
        return new Pair<Pose3d, Double>(null, 0.0);
    }
  }
    */
}