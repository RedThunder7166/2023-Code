// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Buttons;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CargoPlacementCommand extends CommandBase {
  private final VisionSubsystem m_VisionSubsystem;
  private final Buttons m_Buttons;
  private final String m_buttonPressed;

  /** Creates a new CargoPlacementCommand. */
  public CargoPlacementCommand(VisionSubsystem visionSubsystem, Buttons buttons, String buttonPressed) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_VisionSubsystem = visionSubsystem;
    m_Buttons = buttons;
    m_buttonPressed = buttonPressed;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
