// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.sensorsandleds;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Intake extends Command {
  double speed;
  double var;
  coral s_CoralCom;

  //Intakes the piece until a beambreak is tripped
  
  public Intake(double speed, coral s_CoralCom) {
    addRequirements(s_CoralCom);
    this.s_CoralCom = s_CoralCom;
    this.speed = speed;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    if (sensorsandleds.input.get() == false) {
      s_CoralCom.Coral(0);
    } else {
      s_CoralCom.Coral(speed);
    }
  }

  @Override
  public void end(boolean interrupted) {
    s_CoralCom.Coral(0);
  }

  @Override
  public boolean isFinished() {
    if(sensorsandleds.input.get() == false){
      return true;
    } else{
      return false;
    }
  }
}
