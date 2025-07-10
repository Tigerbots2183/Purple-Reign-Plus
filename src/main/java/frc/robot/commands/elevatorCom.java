// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator;

public class elevatorCom extends Command {
  elevator s_ElevatorCom;
  double posNum;
  double elevatePos = 0;
  boolean returnToZero;

  //Set elevator to preset poses (contrasted to the auto shoot ones which go up then shoot as one command) 
  //Elevator is a two stage thriftybot kit elevator

  public elevatorCom( double posNum, elevator s_ElevatorCom, boolean returnToZero) {
    addRequirements (s_ElevatorCom);
     this.s_ElevatorCom = s_ElevatorCom;
     this.posNum = posNum;
    this.returnToZero = returnToZero;
  }

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

  @Override
  public void execute() {
    if(returnToZero){
      
      s_ElevatorCom.setElevator(0);
    } else{

      s_ElevatorCom.setElevator(elevatePos);
    }
  }
  
  @Override
  public void end(boolean interrupted) {
    s_ElevatorCom.Elevator(0);
  }

  @Override
  public boolean isFinished() {
    if(returnToZero){
      return elevator.elevatorLeftEncoder.getPosition() >= 0;
    }else{
      return elevator.elevatorLeftEncoder.getPosition() < elevatePos + 1;
    }  

  }
}
