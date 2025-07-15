package frc.robot.commands;

import java.nio.file.Path;
import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class alignright extends Command {
  Swerve s_Swerve;
  boolean isrightscore;


        boolean isFinished = false;


    private Pose2d Targetpose = null;

  public alignright(Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
  }

  // Called when the command is initially scheduled.
  @Override
 public void initialize() {
        
            double aprilTagID = LimelightHelpers.getFiducialID("limelight");
            // Blue paths

            List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(
              new Pose2d(1.0, 1.0, Rotation2d.fromDegrees(0)), 
            new Pose2d(3.0, 1.0, Rotation2d.fromDegrees(0)),
            new Pose2d(5.0, 3.0, Rotation2d.fromDegrees(0)));
            PathConstraints constraints = PathConstraints.unlimitedConstraints(12.0);

            PathPlannerPath path = new PathPlannerPath(waypoints, constraints, null, null
        
            );

            path.preventFlipping = true;
        }
        
          
          @Override
          public void execute() {
        
        
    //     Command followRightPath = AutoBuilder.pathfindToPose(
    //         ,
    //         constraints,
    //         0.01
    // );
    //         followRightPath.schedule();
            

        
    }




    
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {



    cancel();

  }
    
    

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (isFinished == true){  
        return true ;
    }

    else{
        return false;
    }

}
}