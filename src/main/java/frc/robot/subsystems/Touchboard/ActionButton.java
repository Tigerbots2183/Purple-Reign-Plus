// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Touchboard;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ActionButton extends SubsystemBase {
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

  public ActionButton(String buttonName, Command executed) {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // get the subtable called "touchboard"
    NetworkTable datatable = inst.getTable("touchboard");
    // subscribe to the topic in "touchboard" to start command when button pressed and set it back to false.
    BooleanTopic blTPC = datatable.getBooleanTopic(buttonName);
    dP = blTPC.publish();
    dT = datatable.getBooleanTopic(buttonName).subscribe(false);

    this.executed=executed;
  }

  public void periodic() {
    boolean value = dT.get();

    if (value) {
      executed.schedule();
    }else{
      executed.cancel();
    }
  }

  public void close() {
    dT.close();
  }
}
