// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class algeremover extends SubsystemBase {
  SparkMax removal= new SparkMax(58, MotorType.kBrushed);
  
  SparkMaxConfig removalConfig= new SparkMaxConfig();
  
  //Linear actuator move in and out 
  
  public algeremover() {
    removalConfig.smartCurrentLimit(30);
  }
  public void remove(double speed) {
    removal.set(speed);
        SmartDashboard.putNumber("Algae Remover", speed);

  }

  @Override
  public void periodic() {
  }
}
