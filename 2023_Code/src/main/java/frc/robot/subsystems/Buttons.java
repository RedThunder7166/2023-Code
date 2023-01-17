// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Buttons extends SubsystemBase {
  /** Creates a new Buttons. */
  GenericHID m_buttonBoard = new GenericHID(5);

  int kNodeOneButton = 1;
  int kNodeTwoButton = 2;
  int kNodeThreeButton = 3;
  int kNodeFourButton = 4;
  int kNodeFiveButton = 5;
  int kNodeSixButton = 6;
  int kNodeSevenButton = 7;
  int kNodeEightButton = 8;
  int kNodeNineButton = 9;
  
  int kResetButton = 10;
  int kStopButton = 11;
  int kWristUpButton = 12;
  int kWristDownButton = 13;

  public Buttons() {
  }
  public boolean isPressed(int id) {
    return m_buttonBoard.getRawButton(id);
  }
  public int getPressedId() {
    for (int i = 1; i < 14; i++) {
      if (isPressed(i)) {
        return i;
      }
    }
    return 0;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // if (m_buttonBoard.getRawButton(1)) {
    //   SmartDashboard.putBoolean("Button one?", true);
    // } else {
    //   SmartDashboard.putBoolean("Button one?", false);
    // }

    // if (isPressed(kNodeOneButton)) {
    //   SmartDashboard.putBoolean("Button one?", true);
    // } else {
       
    //   SmartDashboard.putBoolean("Button one?", false);
    // }
    // if (isPressed(kNodeTwoButton)) {
    //   SmartDashboard.putBoolean("Button two?", true);
    // } else {
    //   SmartDashboard.putBoolean("Button two?", false);
    // }
    // if (isPressed(kNodeThreeButton)) {

    // } else {
      
    // }
    // if (isPressed(kNodeFourButton)) {

    // } else {
      
    // }
    // if (isPressed(kNodeFiveButton)) {

    // } else {
      
    // }
    // if (isPressed(kNodeSixButton)) {

    // } else {
      
    // }
    // if (isPressed(kNodeSevenButton)) {

    // } else {
      
    // }
    // if (isPressed(kNodeEightButton)) {

    // } else {
      
    // }
    // if (isPressed(kNodeNineButton)) {

    // } else {
      
    // }
  }
}











