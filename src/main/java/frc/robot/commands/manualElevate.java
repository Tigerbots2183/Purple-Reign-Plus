// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator;
import edu.wpi.first.wpilibj.Joystick;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class manualElevate extends Command {
  private elevator s_Elevate;
  private Joystick copilot;
  /** Creates a new manualElevate. */
  public manualElevate(elevator s_Elevate, Joystick copilot) {
    addRequirements(s_Elevate);
    this.s_Elevate = s_Elevate;
    this.copilot = copilot;
    // Use addRequirements() here to declare subsystem dependencies.
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(copilot.getRawAxis(1) > 0.2){
      s_Elevate.changeElevate(-5);
    }else if(copilot.getRawAxis(1) < -0.2){
      s_Elevate.changeElevate(5);
    }
    else {
      s_Elevate.changeElevate(0);
    }
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
