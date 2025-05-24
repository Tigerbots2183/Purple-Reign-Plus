package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.reefstate;
import frc.robot.Constants.reefstate.Priorities;
import frc.robot.commands.Alignment.lastpegsave;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.sensorsandleds;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class autodrive extends Command {
  Swerve s_Swerve;
  boolean isrightscore;


        boolean isFinished = false;


    private Pose2d Targetpose = null;
  public autodrive (Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (reefstate.reefl4[0] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[1] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[2] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[3] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[4] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[5] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[6] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[7] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[8] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[9] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[10] == true){
      Priorities.o ++;
    }else if (reefstate.reefl4[11] == true){
      Priorities.o ++;
    
    
    }else if (reefstate.reefl3[0] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[1] == true){
      Priorities.p++;
    }else if (reefstate.reefl3[2] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[3] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[4] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[5] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[6] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[7] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[8] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[9] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[10] == true){
      Priorities.p ++;
    }else if (reefstate.reefl3[11] == true){
      Priorities.p ++;


    }else if (reefstate.reefl2[0] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[1] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[2] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[3] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[4] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[5] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[6] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[7] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[8] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[9] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[10] == true){
      Priorities.u ++;
    }else if (reefstate.reefl2[11] == true){
      Priorities.u ++;
    }
    if (Priorities.rp == true && Priorities.o >= 5 || Priorities.point == true && Priorities.o == 12){
      Priorities.l4 = true;
      Priorities.i = 0;
    }else if (Priorities.rp == true && Priorities.p >= 5 || Priorities.point == true && Priorities.p == 12){
      Priorities.l3 = true;
      Priorities.i = 0;
    }else if (Priorities.rp == true && Priorities.u >= 5 || Priorities.point == true && Priorities.u == 12){
      Priorities.l2 = true;
      Priorities.i = 0;
    }
    if (Priorities.l4 ==true && Priorities.l3 == true && Priorities.l2 == true)
    Priorities.rp = false;
    Priorities.point = true;
    Priorities.i = 0;
  
  }
    

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (sensorsandleds.input.get() == true){
      if (lastpegsave.lastpegsaved == 0 || lastpegsave.lastpegsaved == 11 ||  lastpegsave.lastpegsaved ==10 || lastpegsave.lastpegsaved == 9 || lastpegsave.lastpegsaved == 8 || lastpegsave.lastpegsaved ==7){
        new alignleftcoralstation(false, s_Swerve);
        }else {
          //alignrightcoral station
        }
    }else{
      if (reefstate.reefl4[0] == false && Priorities.bothstations[Priorities.i] == 0 || reefstate.reefl3[0] == false && Priorities.bothstations[Priorities.i] == 0 || reefstate.reefl2[0] == false && Priorities.bothstations[Priorities.i] == 0){
      Priorities.i ++;  
      new alignpegA(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[1] == false && Priorities.bothstations[Priorities.i] == 1 || reefstate.reefl3[1] == false && Priorities.bothstations[Priorities.i] == 1 || reefstate.reefl2[1] == false && Priorities.bothstations[Priorities.i] == 1){
      Priorities.i ++;  
      new alignpegB(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[2] == false && Priorities.bothstations[Priorities.i] == 2 || reefstate.reefl3[2] == false && Priorities.bothstations[Priorities.i] == 2 || reefstate.reefl2[2] == false && Priorities.bothstations[Priorities.i] == 2){
      Priorities.i ++;  
      new alignpegC(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[3] == false && Priorities.bothstations[Priorities.i] == 3 || reefstate.reefl3[3] == false && Priorities.bothstations[Priorities.i] == 3 || reefstate.reefl2[3] == false && Priorities.bothstations[Priorities.i] == 3){
      Priorities.i ++;  
      new alignpegD(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[4] == false && Priorities.bothstations[Priorities.i] == 4 || reefstate.reefl3[4] == false && Priorities.bothstations[Priorities.i] == 4 || reefstate.reefl2[4] == false && Priorities.bothstations[Priorities.i] == 4){
      Priorities.i ++;  
      new alignpegE(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[5] == false && Priorities.bothstations[Priorities.i] == 5 || reefstate.reefl3[5] == false && Priorities.bothstations[Priorities.i] == 5 || reefstate.reefl2[5] == false && Priorities.bothstations[Priorities.i] == 1){
      Priorities.i ++;  
      new alignpegF(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[6] == false && Priorities.bothstations[Priorities.i] == 6 || reefstate.reefl3[6] == false && Priorities.bothstations[Priorities.i] == 6 || reefstate.reefl2[6] == false && Priorities.bothstations[Priorities.i] == 6){
      Priorities.i ++;  
      new alignpegG(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[7] == false && Priorities.bothstations[Priorities.i] == 7 || reefstate.reefl3[7] == false && Priorities.bothstations[Priorities.i] == 7 || reefstate.reefl2[7] == false && Priorities.bothstations[Priorities.i] == 7){
      Priorities.i ++;  
      new alignpegH(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[8] == false && Priorities.bothstations[Priorities.i] == 8 || reefstate.reefl3[8] == false && Priorities.bothstations[Priorities.i] == 8 || reefstate.reefl2[8] == false && Priorities.bothstations[Priorities.i] == 8){
      Priorities.i ++;  
      new alignpegI(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[9] == false && Priorities.bothstations[Priorities.i] == 9 || reefstate.reefl3[9] == false && Priorities.bothstations[Priorities.i] == 9 || reefstate.reefl2[9] == false && Priorities.bothstations[Priorities.i] == 9){
      Priorities.i ++;  
      new alignpegJ(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[10] == false && Priorities.bothstations[Priorities.i] == 10 || reefstate.reefl3[10] == false && Priorities.bothstations[Priorities.i] == 10 || reefstate.reefl2[10] == false && Priorities.bothstations[Priorities.i] == 10){
      Priorities.i ++;  
      new alignpegK(false, s_Swerve);
      cancel();
      }else if (reefstate.reefl4[11] == false && Priorities.bothstations[Priorities.i] == 11 || reefstate.reefl3[11] == false && Priorities.bothstations[Priorities.i] == 11 || reefstate.reefl2[11] == false && Priorities.bothstations[Priorities.i] == 11){
      Priorities.i ++;
      new alignpegL(false, s_Swerve);
      cancel();
      }else {
        cancel();
      }}
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cancel();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
