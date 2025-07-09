// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class elevatorCom extends Command {
  double speed;
  elevator s_ElevatorCom;
  double posNum;
  double elevatePos = 0;
  boolean returning;
  // coral s_CoralCom;
    /** Creates a new elevatorCom. */
  public elevatorCom( double posNum, elevator s_ElevatorCom, boolean returning) {
    addRequirements (s_ElevatorCom);
     this.s_ElevatorCom = s_ElevatorCom;
    //  this.s_CoralCom = s_CoralCom;
     this.posNum = posNum;
    this.returning = returning;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
     if(posNum == 1){
      elevatePos = -17;
    } else if (posNum == 2){
      elevatePos = -39;
    }else if (posNum == 3){
      elevatePos = -74.3;
    }
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(returning){
      
      s_ElevatorCom.setElevator(0);
    } else{

      s_ElevatorCom.setElevator(elevatePos);
    }
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_ElevatorCom.Elevator(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
