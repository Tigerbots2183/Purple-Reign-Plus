// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.reefstate;
import frc.robot.commands.Alignment.lastpegsave;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class autoshootlfour extends Command {
  double speed;
  elevator s_ElevatorCom;
  double posNum;
  double elevatePos = 0;
  boolean returning;
  coral s_CoralCom;
    /** Creates a new elevatorCom. */
  public autoshootlfour( double speed, double posNum, elevator s_ElevatorCom,  coral s_CoralCom, boolean returning) {
    addRequirements (s_ElevatorCom);
     this.s_ElevatorCom = s_ElevatorCom;
     this.s_CoralCom = s_CoralCom;
     this.speed = speed;
     this.posNum = posNum;
    this.returning = returning;
  }
    // Use addRequirements() here to declare subsystem dependencies.

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
     if (posNum == 3){
      elevatePos = -75;
    }
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(returning){
      
      s_ElevatorCom.setElevator(0);
    } else{
      s_ElevatorCom.setElevator(elevatePos);
      if(elevator.elevatorLeftEncoder.getPosition() < -74.3 ){
        s_CoralCom.Coral(speed);
        reefstate.reefl4[lastpegsave.lastpegsaved] = true;
        
      } 
    }
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_ElevatorCom.Elevator(0);
    s_CoralCom.Coral(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
