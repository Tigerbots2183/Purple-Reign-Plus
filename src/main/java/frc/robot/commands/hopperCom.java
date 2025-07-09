// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.hopper;

public class hopperCom extends Command {
  double speed;
  hopper s_hopperCom;
   
  //Rotates the hopper out of the way to make room for the cage to go into the bot 
  public hopperCom(double speed, hopper s_hopperCom) {
    addRequirements (s_hopperCom);
    this.s_hopperCom = s_hopperCom;
    this.speed = speed;
   
  }

  @Override
  public void initialize() {}
  @Override
  public void execute() {
    
      s_hopperCom.hop(speed);
    
  }
  @Override
  public void end(boolean interrupted) {
    s_hopperCom.hop(0);
  }
  @Override
  public boolean isFinished() {
    return false;
  }
}
