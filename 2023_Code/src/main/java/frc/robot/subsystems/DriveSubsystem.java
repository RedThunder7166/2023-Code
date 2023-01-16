// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

  private ShuffleboardTab swerveTab = Shuffleboard.getTab("SDS Swerve");
  private ShuffleboardTab moduleTab = Shuffleboard.getTab("Module Info");

  private GenericEntry xSpeedEntry = 
  swerveTab.add("xBox xSpeed", 0)
          .getEntry();

  private GenericEntry ySpeedEntry = 
  swerveTab.add("xBox ySpeed", 0)
          .getEntry();

  private GenericEntry rotEntry = 
  swerveTab.add("xBox rot", 0)
          .getEntry();

  private GenericEntry frontLeftStateEntry =
  swerveTab.add("FL State v", 0)
          .getEntry();
  
  private GenericEntry frontRightStateEntry =
  swerveTab.add("FR State v", 0)
          .getEntry();

  private GenericEntry gyroEntry =
  swerveTab.add("Gyro Heading", 0)
          .getEntry();


  private final SwerveModule m_frontLeft = 
    new SwerveModule(
      DriveConstants.kFrontLeftDriveMotorPort,
      DriveConstants.kFrontLeftTurningMotorPort,
      DriveConstants.kFrontLeftTurningEncoderPorts,
      DriveConstants.kFrontLeftAngleZero,
      moduleTab.getLayout("Front Left Module", BuiltInLayouts.kList)
      .withSize(2, 4)
      .withPosition(0, 0));

  private final SwerveModule m_rearLeft =
    new SwerveModule(
      DriveConstants.kRearLeftDriveMotorPort, 
      DriveConstants.kRearLeftTurningMotorPort, 
      DriveConstants.kRearLeftTurningEncoderPorts,
      DriveConstants.kRearLeftAngleZero,
      moduleTab.getLayout("Rear Left Module", BuiltInLayouts.kList)
      .withSize(2, 4)
      .withPosition(2, 0));

  private final SwerveModule m_frontRight = 
    new SwerveModule(
      DriveConstants.kFrontRightDriveMotorPort, 
      DriveConstants.kFrontRightTurningMotorPort, 
      DriveConstants.kFrontRightTurningEncoderPorts,
      DriveConstants.kFrontRightAngleZero,
      moduleTab.getLayout("Front Right Module", BuiltInLayouts.kList)
      .withSize(2, 4)
      .withPosition(4, 0));

  private final SwerveModule m_rearRight = 
    new SwerveModule(
      DriveConstants.kRearRightDriveMotorPort, 
      DriveConstants.kRearRightTurningMotorPort, 
      DriveConstants.kRearRightTurningEncoderPorts,
      DriveConstants.kRearRightAngleZero,
      moduleTab.getLayout("Rear Right Module", BuiltInLayouts.kList)
      .withSize(2, 4)
      .withPosition(6, 0));

 

  // Initializing the gyro sensor
  private final Gyro m_gyro = new ADXRS450_Gyro();

  // Odometry class for tracking robot pose
  SwerveDriveOdometry m_odometry =
    new SwerveDriveOdometry(DriveConstants.kDriveKinematics, m_gyro.getRotation2d(), 
      new SwerveModulePosition[] {
        new SwerveModulePosition(m_frontLeft.getPosition(), new Rotation2d(m_gyro.getAngle())),
        new SwerveModulePosition(m_frontRight.getPosition(), new Rotation2d(m_gyro.getAngle())),
        new SwerveModulePosition(m_rearLeft.getPosition(), new Rotation2d(m_gyro.getAngle())),
        new SwerveModulePosition(m_rearRight.getPosition(), new Rotation2d(m_gyro.getAngle())),
      }
    );

    /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Update the odometry in the periodic block

    gyroEntry.setDouble(m_gyro.getAngle());
    System.out.println(m_gyro.getAngle());
    SmartDashboard.putNumber("Gyro Entry 2023", m_gyro.getAngle());


    m_odometry.update(
      m_gyro.getRotation2d(),
      new SwerveModulePosition[] {
        new SwerveModulePosition(m_frontLeft.getPosition(), new Rotation2d(m_gyro.getAngle())),
        new SwerveModulePosition(m_frontRight.getPosition(), new Rotation2d(m_gyro.getAngle())),
        new SwerveModulePosition(m_rearLeft.getPosition(), new Rotation2d(m_gyro.getAngle())),
        new SwerveModulePosition(m_rearRight.getPosition(), new Rotation2d(m_gyro.getAngle())),
      }
      );
  }

  // Returns the currently-estimated pose of the robot

  public Pose2d getPose(){
    return m_odometry.getPoseMeters();
  }

  // Resets the odometry to the specified pose

  public void resetOdometry(Pose2d pose){
    m_odometry.resetPosition(m_gyro.getRotation2d(), 
    new SwerveModulePosition[] {
      new SwerveModulePosition(m_frontLeft.getPosition(), new Rotation2d(m_gyro.getAngle())),
      new SwerveModulePosition(m_frontRight.getPosition(), new Rotation2d(m_gyro.getAngle())),
      new SwerveModulePosition(m_rearLeft.getPosition(), new Rotation2d(m_gyro.getAngle())),
      new SwerveModulePosition(m_rearRight.getPosition(), new Rotation2d(m_gyro.getAngle())),
    }, pose); // TODO: CONFIRM THAT WE DIDNT SCREW UP
  }

  /**  Method to drive the robot using joystick info
   * @param xSpeed Speed of the robot in the x direction (forward).
   * @param ySpeed Speed of the robot in the y direction (sideways).
   * @param rot Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
  @SuppressWarnings("ParameterName")
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative){
    
    var swerveModuleStates = 
      DriveConstants.kDriveKinematics.toSwerveModuleStates(
        fieldRelative
          ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d())
          : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.desaturateWheelSpeeds(
      swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);

    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_rearLeft.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]); 

    SmartDashboard.putNumber("FL Mod Heading", m_frontLeft.getModuleHeading());
    SmartDashboard.putNumber("FR Mod Heading", m_frontRight.getModuleHeading());
    SmartDashboard.putNumber("RL Mod Heading", m_rearLeft.getModuleHeading());
    SmartDashboard.putNumber("RR Mod Heading", m_rearRight.getModuleHeading());

    // Telemetry
    xSpeedEntry.setDouble(xSpeed);
    ySpeedEntry.setDouble(ySpeed);
    rotEntry.setDouble(rot);
    frontLeftStateEntry.setDouble(swerveModuleStates[0].speedMetersPerSecond);
    frontRightStateEntry.setDouble(swerveModuleStates[1].speedMetersPerSecond);

  }

    /**
   * Sets the swerve ModuleStates.
   *
   * @param desiredStates The desired SwerveModule states.
   */

  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(
      desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
      m_frontLeft.setDesiredState(desiredStates[0]);
      m_frontRight.setDesiredState(desiredStates[1]);
      m_rearLeft.setDesiredState(desiredStates[2]);
      m_rearRight.setDesiredState(desiredStates[3]);
  }

  // Resets the drive encoders to currently read a position of 0
  public void resetEncoders(){
    m_frontLeft.resetEncoders();
    m_rearLeft.resetEncoders();
    m_frontRight.resetEncoders();
    m_rearRight.resetEncoders();
  }

  // Zeroes the heading of the robot
  public void zeroHeading() {
    m_gyro.reset();
  }

    /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading(){
    return m_gyro.getRotation2d().getDegrees();
  }

    /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate(){
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

}
