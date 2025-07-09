package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignmentRightPeg extends Command {
  Swerve s_Swerve;
  boolean isrightscore;


        boolean isFinished = false;


    private Pose2d Targetpose = null;

  public AlignmentRightPeg(Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
  }

  // Called when the command is initially scheduled.
  @Override
 public void initialize() {
        
            System.out.println("rightBranchPathfinding method called.");
        
            double aprilTagID = LimelightHelpers.getFiducialID("limelight");
            // Blue paths

            switch ((int) aprilTagID) {
            case 17:
                 {
                    Targetpose = POSES.REEF_D;
                }
                break;

            case 18 :
            {
                Targetpose = POSES.REEF_B;
            }
                break;
            

            case 19:
            {
                 Targetpose = POSES.REEF_L;
            }
                break;

            case 20:
            {
                 Targetpose = POSES.REEF_J;
            }
                break;

            case 21:
            {
                 Targetpose = POSES.REEF_H;
            }
                break;

            case 22:
            {
                 Targetpose = POSES.REEF_F;
            }
                break;

            // Red Paths
            case 6:
            {
                 Targetpose = POSES.REEF_L;
            }
                break;

            case 7:
            {
                 Targetpose = POSES.REEF_B;
            }
                break;

            case 8:
            {
                 Targetpose = POSES.REEF_D;
            }
                break;

            case 9:
            {
                 Targetpose = POSES.REEF_F;
            }
                break;

            case 10:
            {
                 Targetpose = POSES.REEF_H;
            }
                break;

            case 11:
                 {
                 Targetpose = POSES.REEF_J;
               
                }
                break;

            default:

                break;
            }
           
            return;
        
        }
        
          
          @Override
          public void execute() {
        
            s_Swerve.drive(new Translation2d(0,0), 0, false,false);
            PathConstraints constraints= new PathConstraints(
            1,
        
            4,

            9.42478,

            12.5664
        
            );

    //         = new PathConstraints(
        
    //         5,

    //         3,

    //         4,

    //         3

    // );

    if (Targetpose != null) {

        
        Command followLeftPath = AutoBuilder.pathfindToPose(
            Targetpose,
            constraints,
            0.01
    );
            followLeftPath.schedule();
            

        
    }}

   
    
    




    
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