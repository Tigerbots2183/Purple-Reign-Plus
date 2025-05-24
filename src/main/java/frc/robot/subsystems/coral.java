// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkFlex;


public class coral extends SubsystemBase {
  SparkFlex coralLeft;
  SparkFlex coralRight;

  SparkMaxConfig coralLeftConfig;
  SparkMaxConfig coralRightConfig;


  /** Creates a new coral. */
  public coral() {
    coralLeft = new SparkFlex(54, MotorType.kBrushless);
    coralRight = new SparkFlex(55, MotorType.kBrushless);


    coralLeftConfig = new SparkMaxConfig();
    coralRightConfig = new SparkMaxConfig();

    coralLeftConfig.smartCurrentLimit(30);
    coralRightConfig.smartCurrentLimit(30);
  }

  public void Coral(double speed) {
    coralLeft.set(speed);
    coralRight.set(speed);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
