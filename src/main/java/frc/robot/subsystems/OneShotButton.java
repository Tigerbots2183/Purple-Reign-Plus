// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.AlignToPose;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class OneShotButton extends SubsystemBase {
  /** Creates a new OneShotButton. */

  // This is for our touchscreen buttonboard that I made, once network table topic
  // is set to true by the
  // touchboard, the subscriber reads it, goes to the pose, and sets it back to
  // false, (in which the touchboard
  // animates the button to show that the action was successfull.)

  final BooleanSubscriber dT;
  final BooleanPublisher dP;
  String buttonName;
  boolean prev = false;
  Command executed;
  PathConstraints constraints = new PathConstraints(

      5,

      3,

      4,

      3

  );

  public OneShotButton(String buttonName, Command executed) {

    // get the default instance of NetworkTables
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // get the subtable called "touchboard"
    NetworkTable datatable = inst.getTable("touchboard");
    // subscribe to the topic in "touchboard" to start command when button pressed
    // and set aback to false
    BooleanTopic blTPC = datatable.getBooleanTopic(buttonName);
    dP = blTPC.publish();
    dT = datatable.getBooleanTopic(buttonName).subscribe(false);

    this.executed=executed;
  }

  public void periodic() {
    // get() can be used with simple change detection to the previous value
    boolean value = dT.get();

    if (value != prev) {
      prev = value; // save previous value

      // if (DriverStation.getAlliance().isPresent()) {
      //   if (DriverStation.getAlliance().get() == Alliance.Red) {
      //     System.out.println("red");
      //     followLeftPath = AutoBuilder.pathfindToPose(
      //       FlippingUtil.flipFieldPose(sentPos),
      //     constraints,
      //     0.00);
      //     followLeftPath.schedule();
      //     return;
      //   }
      // }
      executed.schedule();
      dP.set(false);
    }
  }

  // may not be necessary for robot programs if this class lives for
  // the length of the program
  public void close() {
    dT.close();
  }
}
