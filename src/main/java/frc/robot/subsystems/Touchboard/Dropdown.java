// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Touchboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Commands;

public class Dropdown extends SubsystemBase {
  /** Creates a new AxisKnob. */
  public String value = "";

  String topic;

  StringSubscriber Ss;
  StringPublisher Sp;
  String prev = "";
  Supplier<Command> passedCommand = () -> Commands.none();

  public Dropdown(String topic) {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // get the subtable called "touchboard"
    NetworkTable datatable = inst.getTable("touchboard");
    // subscribe to the topic in "touchboard" to start command when button pressed
    // and set it back to false.
    StringTopic StrTPC = datatable.getStringTopic(topic);
    Sp = StrTPC.publish();
    Ss = datatable.getStringTopic(topic).subscribe("");
    
    this.topic = topic;
  }

  public void setCommand(Supplier<Command> newCom) {
    passedCommand = newCom;
  }

  public String getValue() {
    return Ss.get();
  }

  @Override
  public void periodic() {
    value = Ss.get();

    if (!value.equals(prev)) {
      prev = value;
      passedCommand.get().schedule();
    }
    // This method will be called once per scheduler run
  }
}
