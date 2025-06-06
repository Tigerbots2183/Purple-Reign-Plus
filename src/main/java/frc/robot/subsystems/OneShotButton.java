// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;
public class OneShotButton extends SubsystemBase {
  /** Creates a new OneShotButton. */
    final BooleanSubscriber dT;
    final BooleanPublisher dP;
    String buttonName;
    boolean prev = false;

    public OneShotButton(String buttonName) {
      // get the default instance of NetworkTables
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      // get the subtable called "datatable"
      NetworkTable datatable = inst.getTable("touchboard");
      // subscribe to the topic in "datatable" called "Y"
      BooleanTopic blTPC = datatable.getBooleanTopic(buttonName);
      dP = blTPC.publish();
      dT = datatable.getBooleanTopic(buttonName).subscribe(false);

    }

    public void periodic() {
      // get() can be used with simple change detection to the previous value
      boolean value = dT.get();
      System.out.println(value);

      if (value != prev) {
        prev = value;  // save previous value
        System.out.println(value);
        dP.set(false);
      }
    }
    // may not be necessary for robot programs if this class lives for
    // the length of the program
    public void close() {
      dT.close();
    }
  }
  