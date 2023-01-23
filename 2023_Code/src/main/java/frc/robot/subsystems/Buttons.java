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

  String[] buttons = {
    "nodeone",
    "nodetwo",
    "nodethree",
    "nodefour",
    "nodefive",
    "nodesix",
    "nodeseven",
    "nodeeight",
    "nodenine",
    "reset",
    "stop",
    "wristup",
    "wristdown"
  };
  // int kNodeOneButton = 1;
  // int kNodeTwoButton = 2;
  // int kNodeThreeButton = 3;
  // int kNodeFourButton = 4;
  // int kNodeFiveButton = 5;
  // int kNodeSixButton = 6;
  // int kNodeSevenButton = 7;
  // int kNodeEightButton = 8;
  // int kNodeNineButton = 9;
  
  // int kResetButton = 10;
  // int kStopButton = 11;
  // int kWristUpButton = 12;
  // int kWristDownButton = 13;

  public Buttons() {
  }
  public boolean isPressed(int id) {
    return m_buttonBoard.getRawButton(id);
  }

  /**
   * Gets the ID of the button being pressed with the lowest ID.
   * @return <int> button ID
   */
  public int getPressedId() {
    // for (int i = 1; i < 14; i++) {
    //   if (isPressed(i)) {
    //     return i;
    //   }
    // }
    // return 0;
    for (int id = 0; id < buttons.length; id++) {
      // we add one to id because arrays start at 0 while the button board starts at 1
      if (isPressed(id + 1)) {
        return id + 1;
      }
    }
    return 0;
  }
  public String getPressedName(){
    int index = getPressedId() - 1;
    if (index < 0) {
      return "none";
    }
    return buttons[index];
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}