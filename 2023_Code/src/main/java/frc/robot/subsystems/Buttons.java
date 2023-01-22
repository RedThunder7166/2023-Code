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

    "resetbutton",
    "stopbutton",
    "wristup",
    "wristdown"
  };

  public Buttons() {
  }
  public boolean isPressed(int id) {
    return m_buttonBoard.getRawButton(id);
  }
  public int getPressedId() {
    for (int id = 0; id < buttons.length; id++){
      // add one to id because arrays start at 0, while button board starts at 1
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