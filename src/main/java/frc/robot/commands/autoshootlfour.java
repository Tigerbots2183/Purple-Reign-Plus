// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;
import frc.robot.subsystems.sensorsandleds;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class autoshootlfour extends Command {
  double shooterSpeed;
  elevator s_ElevatorCom;
  double elevatePos = 0;
  boolean returnToZero;
  coral s_CoralCom;
  boolean elevate = false;

  //Goes to elevator pos and then shoots when it gets there. Elevator is a two stage thrifty kit elevator
  public autoshootlfour(double shooterSpeed, elevator s_ElevatorCom, coral s_CoralCom, boolean returnToZero) {
    addRequirements(s_ElevatorCom);
    this.s_ElevatorCom = s_ElevatorCom;
    this.s_CoralCom = s_CoralCom;
    this.returnToZero = returnToZero;
    this.shooterSpeed = shooterSpeed;
    elevate = false;
  }

  @Override
  public void initialize() {
    elevatePos = -75;
  }

  @Override
  public void execute() {
    if (returnToZero) {

      s_ElevatorCom.setElevator(0);
    } else {
      s_ElevatorCom.setElevator(elevatePos);
      if (elevator.elevatorLeftEncoder.getPosition() < -74.3) {
        s_CoralCom.Coral(shooterSpeed);
        if (sensorsandleds.input.get() == true) {
          elevate = true;
        }
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    s_ElevatorCom.Elevator(0);
    s_CoralCom.Coral(0);

  }

  @Override
  public boolean isFinished() {
    return elevate;
  }
}
