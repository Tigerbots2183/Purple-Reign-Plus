// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.AudioConfigs;
// import com.ctre.phoenix6.configs.AudioConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Tuneage extends Command {
  /** Creates a new Tuneage. */

  
  CommandSwerveDrivetrain s_Swerve;
  TalonFX Swerve1;
  TalonFX Swerve2;
  TalonFX Swerve3;
  TalonFX Swerve4;
  TalonFX Swerve5;
  TalonFX Swerve6;
  TalonFX Swerve7;
  TalonFX Swerve8;
  // TalonFX Climb1;
  // TalonFX Climb2;
  
  Orchestra mOrchestra = new Orchestra();
  AudioConfigs configs = new AudioConfigs();//.withAllowMusicDurDisable(true);
  public Tuneage(CommandSwerveDrivetrain s_Swerve) {
    
    this.s_Swerve= s_Swerve;
    // .AllowMusicDurDisable(true);
    configs.AllowMusicDurDisable = true;
    Swerve1 = s_Swerve.getModule(0).getDriveMotor();
    Swerve2 = s_Swerve.getModule(1).getDriveMotor();
    Swerve3 = s_Swerve.getModule(2).getDriveMotor();
    Swerve4 = s_Swerve.getModule(3).getDriveMotor();
    
    Swerve5 = s_Swerve.getModule(0).getSteerMotor();
    Swerve6 = s_Swerve.getModule(1).getSteerMotor();
    Swerve7 = s_Swerve.getModule(2).getSteerMotor();
    Swerve8 = s_Swerve.getModule(3).getSteerMotor();
    
    Swerve1.getConfigurator().apply(configs);
    Swerve2.getConfigurator().apply(configs);
    Swerve3.getConfigurator().apply(configs);
    Swerve4.getConfigurator().apply(configs);
    Swerve5.getConfigurator().apply(configs);
    Swerve6.getConfigurator().apply(configs);
    Swerve7.getConfigurator().apply(configs);
    Swerve8.getConfigurator().apply(configs);
    addRequirements(s_Swerve);

        // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    mOrchestra.addInstrument(Swerve1);
    mOrchestra.addInstrument(Swerve2);
    mOrchestra.addInstrument(Swerve3);
    mOrchestra.addInstrument(Swerve4);
    mOrchestra.addInstrument(Swerve5);
    mOrchestra.addInstrument(Swerve6);
    mOrchestra.addInstrument(Swerve7);
    mOrchestra.addInstrument(Swerve8);

    mOrchestra.loadMusic("spj.chrp");
    mOrchestra.play();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mOrchestra.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
