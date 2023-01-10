// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsytem extends SubsystemBase {
  /** Creates a new VisionSubsytem. */
  public VisionSubsytem() {}
  PhotonCamera camera = new PhotonCamera("gloworm");

  
    
  public boolean hasTargets(){
    var result = camera.getLatestResult();
    if(result.hasTargets() == true){
      return true;
    } else{
      return false;
    }
  }
  
  @Override
  public void periodic() {
   if (hasTargets()) {
     System.out.println("I see the target, honstley it's pretty ugly");
   }
   else{
     System.out.println("Hello target, there is no reason to hide you can come out now");
   }
  }
}
