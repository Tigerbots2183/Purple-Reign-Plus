// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Touchboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.security.PublicKey;

import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Commands;
public class AxisKnob extends SubsystemBase {
  /** Creates a new AxisKnob. */
  public double value = 0;
  String topic;
  DoubleSubscriber Ds;
  DoublePublisher Dp;
  double prev = 0;
  Supplier<Command> passedCommand = ()->Commands.none();
  public AxisKnob(String topic) {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // get the subtable called "touchboard"
    NetworkTable datatable = inst.getTable("touchboard");
    // subscribe to the topic in "touchboard" to start command when button pressed and set it back to false.
    DoubleTopic DblTPC = datatable.getDoubleTopic(topic);
    Dp = DblTPC.publish();
    Ds = datatable.getDoubleTopic(topic).subscribe(0);
    this.topic = topic;
  }

  public void setCommand(Supplier<Command> newCom){
    passedCommand = newCom;
  }

  public double getValue(){
    return Ds.get();
  }

  @Override
  public void periodic() {
    value = Ds.get();

    if(value != prev){
      prev = value;
      passedCommand.get().schedule();

    }
    // This method will be called once per scheduler run
  }
}
