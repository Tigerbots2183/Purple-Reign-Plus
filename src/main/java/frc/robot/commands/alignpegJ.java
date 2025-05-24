package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.Constants.reefstate.Priorities;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class alignpegJ extends Command {
  Swerve s_Swerve;
  boolean isrightscore;
  double speed;
  elevator s_ElevatorCom;
  double posNum;
  double elevatePos = 0;
  boolean returning;
  coral s_CoralCom;

        boolean isFinished = false;


    private Pose2d Targetpose = null;

  public alignpegJ(Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
  }
  //test pr
  // Called when the command is initially scheduled.
  @Override
 public void initialize() {
        
            System.out.println("peg J");
        
            // Blue paths

            Targetpose = POSES.REEF_J;
        
        }
        
          
          @Override
          public void execute() {
        
            s_Swerve.drive(new Translation2d(0,0), 0, false,false);
            PathConstraints constraints= new PathConstraints(
        
                    5,
        
                    3,
        
                    4,
        
                    3
        
            );

        

    if (Targetpose != null) {

        s_Swerve.drive(new Translation2d(0,0), 0, false,false);
        Command pathfindJ = AutoBuilder.pathfindToPose(
            Targetpose,
            constraints,
            0.01
    );
            pathfindJ.schedule();
            

        
    }}

   
    
    




    
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (Priorities.p == 0){
      new autoshootlfour(-.12, 3, s_ElevatorCom,s_CoralCom,false);
    }else if (Priorities.p == 1){
      //autoshootl3
    }else if (Priorities.p == 2){
      //autoshootl2
    }else{
      new autodrive(false, s_Swerve);
    }


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