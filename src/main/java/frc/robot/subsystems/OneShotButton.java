// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.*;

public class OneShotButton extends SubsystemBase {
  /** Creates a new OneShotButton. */
  final BooleanSubscriber dT;
  final BooleanPublisher dP;
  String buttonName;
  boolean prev = false;
  final Pose2d sentPos;
  PathConstraints constraints= new PathConstraints(
        
                    5,
        
                    3,
        
                    4,
        
                    3
        
            );


  public OneShotButton(String buttonName, Pose2d Pose) {
    // get the default instance of NetworkTables
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // get the subtable called "datatable"
    NetworkTable datatable = inst.getTable("touchboard");
    // subscribe to the topic in "datatable" called "Y"
    BooleanTopic blTPC = datatable.getBooleanTopic(buttonName);
    dP = blTPC.publish();
    dT = datatable.getBooleanTopic(buttonName).subscribe(false);
    sentPos = Pose;
  }

  public void periodic() {
    // get() can be used with simple change detection to the previous value
    boolean value = dT.get();

    if (value != prev) {
      prev = value; // save previous value
      dP.set(false);
      Command followLeftPath = AutoBuilder.pathfindToPose(
          sentPos,
          constraints,
          0.00);
      followLeftPath.schedule();
    }
  }

  // may not be necessary for robot programs if this class lives for
  // the length of the program
  public void close() {
    dT.close();
  }
}
