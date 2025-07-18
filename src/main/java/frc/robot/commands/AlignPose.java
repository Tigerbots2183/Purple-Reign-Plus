package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignPose extends Command {
  Swerve s_Swerve;
  // alignment for left peg, references poses constant
  boolean isFinished = false;
  Command followPath;
  private Pose2d Targetpose = null;
  public static int lastpeg = 0;

  public AlignPose(Swerve s_Swerve, Pose2d Targetpose) {
    this.s_Swerve = s_Swerve;
    this.Targetpose = Targetpose;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {




  }

  @Override
  public void execute() {
    addRequirements(s_Swerve);

    PathConstraints constraints = new PathConstraints(

        5,

        3,

        4,

        3

    );

    if (Targetpose != null) {
      followPath = AutoBuilder.pathfindToPose(
          Targetpose,
          constraints,
          0.01);
      followPath.schedule();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    cancel();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    // if (isFinished == true) {
    // return true;
    // }

    // else {
    // return false;
    // }
    return followPath.isFinished();
  }
}