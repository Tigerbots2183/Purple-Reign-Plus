// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class autoshootlthree extends Command {
  double shooterSpeed;
  elevator s_ElevatorCom;
  double elevatePos = 0;
  boolean returnToZero;
  coral s_CoralCom;

  //Goes to elevator pos and then shoots when it gets there (Currently unused).
  //Elevator is a two stage thriftybot kit elevator (cascading)

  public autoshootlthree(double shooterSpeed, elevator s_ElevatorCom, coral s_CoralCom, boolean returnToZero) {
    addRequirements(s_ElevatorCom);
    this.s_ElevatorCom = s_ElevatorCom;
    this.s_CoralCom = s_CoralCom;
    this.shooterSpeed = shooterSpeed;
    this.returnToZero = returnToZero;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elevatePos = -38.8;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (returnToZero) {

      s_ElevatorCom.setElevator(0);
    } else {
      s_ElevatorCom.setElevator(elevatePos);
      if (elevator.elevatorLeftEncoder.getPosition() < -37.8) {
        s_CoralCom.Coral(shooterSpeed);
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
