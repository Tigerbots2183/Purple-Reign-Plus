// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class limelightaligntwo extends SubsystemBase {
  public static NetworkTable limelight;

  public static double xValue;
  public static double yValue;
  public static double areaValue;
  public static double tagValue2;
  public static double redDistance;
  public static  NetworkTableEntry botposEntry;
  public static NetworkTableEntry tx;
  public static NetworkTableEntry ty;
  public static NetworkTableEntry ta;
  public static NetworkTableEntry tid;
  public static NetworkTableEntry distances;
  public static NetworkTableEntry Tx;
  public static NetworkTableEntry Ty;
  public static double[] tagposs;

  public static double tagposx;
  public static double tagposy;
  public static double[] tagpos;
  public static double[] tagplace;

  /** Creates a new limelightalign. */
  public limelightaligntwo() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight-right");

    tx = limelight.getEntry("tx");
    ty = limelight.getEntry("ty");
    ta = limelight.getEntry("ta");
    tid = limelight.getEntry("tid");
    distances = limelight.getEntry("camerapose_targetspace");
    Ty = limelight.getEntry("Camerapose_targetspace");
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tagposs = LimelightHelpers.getCameraPose_TargetSpace("limelight-right");
    double x = tx.getDouble(0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    double ID2 = tid.getDouble(0.0);
    double tagposx = tagposs[0];
    double tagposy = tagposs[2];
    xValue = x;
    yValue = y;
    areaValue = area;
    tagValue2 = ID2;
    // SmartDashboard.putNumber("Limelight Yvalue", limelightalign.yValue);
    // SmartDashboard.putNumber("Limelight value", limelightalign.xValue);
    // SmartDashboard.putNumber("Limelight value tag", limelightalign.tagValue);
    // SmartDashboard.putNumber("Limelight distance to tage", tagposx);
    // Logger.recordOutput("x", tagposx);
    // Logger.recordOutput("y", tagposy);


  }
}
