// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algeremover;

public class removalcom extends Command {
  double speed;
  algeremover s_algieCom;
   

  //This moves a linear actuator in and out, manually
  public removalcom(double speed, algeremover s_algieCom) {
     addRequirements (s_algieCom);
    this.s_algieCom = s_algieCom;
    this.speed = speed;
    
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    
      s_algieCom.remove(speed);
    

  }

  @Override
  public void end(boolean interrupted) {
    s_algieCom.remove(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
