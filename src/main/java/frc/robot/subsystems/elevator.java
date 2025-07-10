// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class elevator extends SubsystemBase {
  SparkMax elevatorLeft = new SparkMax(50, MotorType.kBrushless);
  SparkMax elevatorRight = new SparkMax(51, MotorType.kBrushless);

  //GOOD LUCK

  public static RelativeEncoder elevatorLeftEncoder;

  private SparkMaxConfig elevatorConfig= new SparkMaxConfig();
  private SparkMaxConfig lelevatorConfig = new SparkMaxConfig();

  private SparkClosedLoopController elevatorLeftPID;

  // private boolean check;

  private DutyCycleEncoder elevate = new DutyCycleEncoder(1);

  /** Creates a new elevator. */
  public elevator() {
    
    elevatorLeftEncoder = elevatorLeft.getEncoder();


    lelevatorConfig.smartCurrentLimit(50);
    elevatorLeftPID = elevatorLeft.getClosedLoopController();

    elevatorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(.01)
        .i(.000000)
        .d(0)
        .outputRange(-1, 1)
        // Set PID values for velocity control in slot 1
        .p(0.01, ClosedLoopSlot.kSlot1)
        .i(0, ClosedLoopSlot.kSlot1)
        .d(0, ClosedLoopSlot.kSlot1)
        .velocityFF(1 / 5767, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1);
    elevatorConfig.smartCurrentLimit(50);
    elevatorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control
        .p(0.004)
        .i(0)
        .d(0.001)
        .outputRange(-.5, .5).maxMotion
        // Set MAXMotion parameters for position control
        .maxVelocity(1000000)
        .maxAcceleration(650000)
        .allowedClosedLoopError(0.25).allowedClosedLoopError(0.3);

    elevatorLeft.configure(
        elevatorConfig,
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);

    elevatorRight.configure(
        elevatorConfig.follow(elevatorLeft, false),
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
  }

  public double DutyCycleEncoder() {
    return elevate.get();
  }

  public double GetCurrentEncoder(){
    return elevatorLeftEncoder.getPosition();
  }

  public boolean setElevator(double pos) {
    elevatorLeftPID.setReference(pos, ControlType.kMAXMotionPositionControl);
    SmartDashboard.putNumber("Elevator Pos", pos);

    if (Math.abs(Math.abs(pos) - Math.abs(elevatorLeftEncoder.getPosition())) < 2) {
      // check = true;
      return true;
    } else {
      // check = false;
      return false;
    }
  }

  public void changeElevate(double change) {
    boolean changeDone = false;

    if (!changeDone) {
      elevatorLeftPID.setReference(elevatorLeftEncoder.getPosition() + change, ControlType.kMAXMotionPositionControl);
      changeDone = true;

    }
  }

  public void Elevator(double speed) {
    elevatorLeft.set(speed);
    elevatorRight.set(speed);

  }

  @Override
  public void periodic() {
    elevate.get();
    elevatorLeftEncoder.getPosition();
    SmartDashboard.putNumber("ElevatorCurPos", elevatorLeftEncoder.getPosition());
    // SmartDashboard.putNumber("elevatePos", elevatorCom.elevatePos);

    // This method will be called once per scheduler run
  }
}
