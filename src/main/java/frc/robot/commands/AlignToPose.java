// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignToPose extends Command {

  Pose2d currentPose;
  CommandSwerveDrivetrain s_Drivetrain;
  Command PathCommand;

  public AlignToPose(Pose2d currentPose, CommandSwerveDrivetrain s_Drivetrain) {
    addRequirements(s_Drivetrain);

    this.currentPose = currentPose;
    this.s_Drivetrain = s_Drivetrain;

  }

  @Override
  public void initialize() {
    PathConstraints constraints2 = new PathConstraints(

        5,

        3,

        4,

        3

    );

    PathCommand = AutoBuilder.pathfindToPose(
        currentPose,
        constraints2,
        0.00);
    PathCommand.schedule();

  }

  @Override
  public void execute() {

  }

  @Override
  public void end(boolean interrupted) {
    PathCommand.cancel();
  }

  @Override
  public boolean isFinished() {
    return PathCommand.isFinished();
  }
}
