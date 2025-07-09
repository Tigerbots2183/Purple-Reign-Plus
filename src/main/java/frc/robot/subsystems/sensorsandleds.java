// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class sensorsandleds extends SubsystemBase {
  public static  DigitalOutput leds= new DigitalOutput(6);
 public static DigitalInput input = new DigitalInput(2);
 public static DigitalInput wall = new DigitalInput(4);
 public static DigitalInput reef= new DigitalInput(7);
  /** Creates a new sensorsandleds. */
  public sensorsandleds() {
    leds.setPWMRate(1000);
    leds.enablePWM(0);
  }
  public boolean DigitalInput(){
    return input.get();
  }
  public boolean DigitalInput2(){
    return wall.get();
  }
  public boolean DigitalInput3(){
    return reef.get();
  }
  public void led (double var){
    leds.updateDutyCycle(var);
  }
  
  @Override
  public void periodic() {
    //leds.updateDutyCycle(0);
    input.get();
    wall.get();
    reef.get();
    Logger.recordOutput("coral have", input.get());
    Logger.recordOutput("coral station", wall.get());
    Logger.recordOutput("reef", reef.get());

    SmartDashboard.putBoolean("coral have", input.get());
    SmartDashboard.putBoolean("coral station", wall.get());
    SmartDashboard.putBoolean("reef", reef.get());
    // This method will be called once per scheduler run
  }
}
