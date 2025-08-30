// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import javax.sound.midi.Patch;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.FlippingUtil;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignToPose extends Command {

  private Pose2d currentPose;
  private CommandSwerveDrivetrain s_Drivetrain;
  private Command PathCommand = Commands.none();


  public AlignToPose(Pose2d currentPose, CommandSwerveDrivetrain s_Drivetrain) {
    PathCommand = Commands.none();
    this.currentPose = currentPose;
    this.s_Drivetrain = s_Drivetrain;

  }

  @Override
  public void initialize() {
    addRequirements(s_Drivetrain);


    if (DriverStation.getAlliance().isPresent()) {
      if (DriverStation.getAlliance().get() == Alliance.Red) {
        PathCommand =AutoBuilder.pathfindToPose(
            FlippingUtil.flipFieldPose(currentPose),
            Constants.PathfindContraints,
            0.00);

        PathCommand.schedule();

        return;
      }
    }

    PathCommand = AutoBuilder.pathfindToPose(
        currentPose,
        Constants.PathfindContraints,
        0.00);
        PathCommand.schedule();


  }

  @Override
  public void execute() {
    // this.PathCommand.schedule();

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
