// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber;

public class climberCom extends Command {
  double speed;
  climber s_ClimberCom;
  
  //Cimber clamps down two arms onto the cage in a donut climb

  public climberCom(double speed, climber s_ClimberCom) {
    addRequirements (s_ClimberCom);
    this.s_ClimberCom = s_ClimberCom;
    this.speed = speed;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    s_ClimberCom.Climber(speed);
  
  }

  @Override
  public void end(boolean interrupted) {
    s_ClimberCom.Climber(0);
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
