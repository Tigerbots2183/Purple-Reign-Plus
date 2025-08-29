// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.Constants;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.pathfinding.Pathfinding;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class QueuePath extends Command {

  private Supplier<Pose2d> StartPose;
  private Supplier<Pose2d> TargetPose;

  public QueuePath(Supplier<Pose2d> StartPose, Supplier<Pose2d> TargetPose) {
    this.TargetPose = TargetPose;
    this.StartPose = StartPose;
  }

  @Override
  public void initialize() {

    
      Pathfinding.setStartPosition(StartPose.get().getTranslation());
      // if (DriverStation.getAlliance().isPresent()) {
      //     if (DriverStation.getAlliance().get() == Alliance.Red) {
      //         Pathfinding.setGoalPosition(FlippingUtil.flipFieldPose(TargetPose.get()).getTranslation());
      //     }else{
      //         Pathfinding.setGoalPosition(TargetPose.get().getTranslation());
      //     }
      // }else{
      //     Pathfinding.setGoalPosition(TargetPose.get().getTranslation());
      // }
      Pathfinding.setGoalPosition(TargetPose.get().getTranslation());
      
  }


  @Override
  public void execute() {
                                 
    // if (DriverStation.getAlliance().isPresent()) {
    //     if (DriverStation.getAlliance().get() == Alliance.Red) {
    //         Pathfinding.setGoalPosition(FlippingUtil.flipFieldPose(TargetPose.get()).getTranslation());
    //     }else{
    //         Pathfinding.setGoalPosition(TargetPose.get().getTranslation());
    //     }
    // }else{
    //     Pathfinding.setGoalPosition(TargetPose.get().getTranslation());
    // }
    Pathfinding.setGoalPosition(TargetPose.get().getTranslation());
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
