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
public class AlignNearestPeg extends Command {
  /** Creates a new AlignNearestPeg. */

  String side;

  CommandSwerveDrivetrain drivetrain;
  Pose2d CenterReef = new Pose2d(4.475, 4.026, Rotation2d.fromDegrees(0));
  Pose2d CurrentPose;
  Pose2d NearestLeftPegPose;
  Pose2d NearestRightPegPose;
  Command PathCommand;
  Rotation2d angleFromReef;
  Boolean ReverseAngle = false;

   /**
     * Aligns to nearest peg based on rotation
     * @param side Reef side, "left" or 'right'.
     * @param drivetrain CTRE swerve reference..
     */
  public AlignNearestPeg(String side, CommandSwerveDrivetrain drivetrain) {
    addRequirements(drivetrain);
    this.side = side;
    this.drivetrain = drivetrain;

    if (DriverStation.getAlliance().isPresent()) {
      if (DriverStation.getAlliance().get() == Alliance.Red) {
        CenterReef = FlippingUtil.flipFieldPose(CenterReef);
      } else{
        ReverseAngle = true;
      }
    }


    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Math.atan2(y, x);
    CurrentPose = drivetrain.getState().Pose;

    
    if(ReverseAngle){
      angleFromReef = Rotation2d
        .fromRadians(Math.atan2((CenterReef.getY() - CurrentPose.getY()) *-1, (CenterReef.getX() - CurrentPose.getX())*-1));

    } else {
      angleFromReef = Rotation2d
        .fromRadians(Math.atan2(CenterReef.getY() - CurrentPose.getY(), CenterReef.getX() - CurrentPose.getX()));

    }

    SmartDashboard.putNumber("Current Angle From Center", angleFromReef.getDegrees());

    

    if (angleFromReef.getDegrees() <= -150) {
      NearestLeftPegPose = POSES.REEF_A;
      NearestRightPegPose = POSES.REEF_B;

    } else if (angleFromReef.getDegrees() <= -90) {
      NearestLeftPegPose = POSES.REEF_C;
      NearestRightPegPose = POSES.REEF_D;

    } else if (angleFromReef.getDegrees() <= -30) {
      NearestLeftPegPose = POSES.REEF_E;
      NearestRightPegPose = POSES.REEF_F;

    } else if (angleFromReef.getDegrees() <= 30) {
      NearestLeftPegPose = POSES.REEF_G;
      NearestRightPegPose = POSES.REEF_H;

    } else if (angleFromReef.getDegrees() <= 90) {
      NearestLeftPegPose = POSES.REEF_I;
      NearestRightPegPose = POSES.REEF_J;

    } else if (angleFromReef.getDegrees() <= 150) {
      NearestLeftPegPose = POSES.REEF_K;
      NearestRightPegPose = POSES.REEF_L;

    } else {
      NearestLeftPegPose = POSES.REEF_A;
      NearestRightPegPose = POSES.REEF_B;

    }

    if (DriverStation.getAlliance().isPresent()) {
      if (DriverStation.getAlliance().get() == Alliance.Red) {
        NearestLeftPegPose = FlippingUtil.flipFieldPose(NearestLeftPegPose);
        NearestRightPegPose = FlippingUtil.flipFieldPose(NearestRightPegPose);
      }
    }

    Pose2d startPose = new Pose2d(drivetrain.getState().Pose.getTranslation(), angleFromReef);
    Pose2d editedPose;
    GoalEndState endRotation;


    

    if (side.equals("left")) {
      editedPose = new Pose2d(NearestLeftPegPose.getTranslation(), angleFromReef);
      endRotation = new GoalEndState(0, NearestLeftPegPose.getRotation());

    } else {
      editedPose = new Pose2d(NearestRightPegPose.getTranslation(), angleFromReef);
      endRotation = new GoalEndState(0, NearestRightPegPose.getRotation());

    }

    List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(startPose, editedPose);

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
