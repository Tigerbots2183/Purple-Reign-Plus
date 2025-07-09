// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class climber extends SubsystemBase {
  
  TalonFX climberleft= new TalonFX(53);
  TalonFX climberright= new TalonFX(52);
  
  TalonFXConfiguration climberleftConfig = new TalonFXConfiguration();
  TalonFXConfiguration climberrightConfig = new TalonFXConfiguration();

  
  /** Creates a new climber. */
  public climber() {
    climberleftConfig.CurrentLimits.withSupplyCurrentLimit(50);
    climberrightConfig.CurrentLimits.withSupplyCurrentLimit(50);
  }

  public void Climber(double speed) {
    climberleft.set(speed);
    climberright.set(-speed);
        SmartDashboard.putNumber("Climber", speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
