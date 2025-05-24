// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class climber extends SubsystemBase {
  // SparkMax climberLeft;
  // SparkMax climberRight;
  
  TalonFX climberleft;
  TalonFX climberright;
  
  TalonFXConfiguration climberleftConfig;
  TalonFXConfiguration climberrightConfig;

  // SparkMaxConfig climberLeftConfig;
  // SparkMaxConfig climberRightConfig;
  
  /** Creates a new climber. */
  public climber() {
    
    climberleft = new TalonFX(53);
    climberright = new TalonFX(52);

    climberleftConfig = new TalonFXConfiguration();
    climberrightConfig = new TalonFXConfiguration();

    climberleftConfig.CurrentLimits.withSupplyCurrentLimit(50);
    climberrightConfig.CurrentLimits.withSupplyCurrentLimit(50);
  }

  public void Climber(double speed) {
    climberleft.set(speed);
    climberright.set(-speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
