// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.coral;
import frc.robot.commands.elevatorCom;
import frc.robot.subsystems.elevator;
import frc.robot.subsystems.sensorsandleds;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class autocoralCom extends Command {
  double speed;
  double var;
   Joystick copilot;
   coral s_CoralCom;
  Swerve s_Swerve;
  /** Creates a new coralCom. */
  public autocoralCom( double speed,coral s_CoralCom) {
    addRequirements (s_CoralCom);
    this.s_CoralCom = s_CoralCom;
   this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(sensorsandleds.input.get() == false){
      s_CoralCom.Coral(0);
    }else {
      s_CoralCom.Coral(speed);
    }
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_CoralCom.Coral(0);
    new autodrive(false, s_Swerve);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
