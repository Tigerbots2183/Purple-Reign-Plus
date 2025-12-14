// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.List;
import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.jni.SwerveJNI.DriveState;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.POSES;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Touchboard.posePlotterUtil;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveOffLine extends Command {
  /** Creates a new AlignNearestPeg. */

  Boolean isBlue = false;
  CommandSwerveDrivetrain drivetrain;

  public MoveOffLine(CommandSwerveDrivetrain drivetrain) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    if (DriverStation.getAlliance().isPresent()) {
      if (DriverStation.getAlliance().get() == Alliance.Red) {
        isBlue = false;
      } else{
        isBlue = true;
      }
    }


    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Math.atan2(y, x);
    Pose2d CurrentPose = drivetrain.getState().Pose;

    
    

    Pose2d startPose = new Pose2d(0,0, Rotation2d.fromDegrees(90));
    Pose2d endPose;
    GoalEndState endRotation;

    

    if (isBlue) {
      endPose = new Pose2d(1, 0, Rotation2d.fromDegrees(90));
      endRotation = new GoalEndState(0, Rotation2d.fromDegrees(90));

    } else {
      endPose = new Pose2d(-1, 0, Rotation2d.fromDegrees(-90));
      endRotation = new GoalEndState(0, Rotation2d.fromDegrees(270));

    }

    List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(startPose, endPose);

    PathPlannerPath path = new PathPlannerPath(
        waypoints,
        Constants.PathfindContraints,
        null, // The ideal starting state, this is only relevant for pre-planned paths, so can be null for on-the-fly paths.
        endRotation // Goal end state. You can set a holonomic rotation here. If using a differential drivetrain, the rotation will have no effect.
    );  

    path.preventFlipping = true;

    AutoBuilder.followPath(path).schedule();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
