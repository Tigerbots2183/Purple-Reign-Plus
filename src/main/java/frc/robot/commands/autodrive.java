package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.Constants.StationPOSES;
import frc.robot.Constants.reefstate;
import frc.robot.Constants.reefstate.Priorities;
import frc.robot.commands.Alignment.lastpegsave;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.sensorsandleds;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class autodrive extends Command {
  Swerve s_Swerve;
  boolean isrightscore;
  Pose2d targetpose = null;
  boolean isFinished = false;

  // private Pose2d Targetpose = null;

  public autodrive(Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Every time it executes it was readding the same number, must be reset to zero then counted.
    Priorities.o = 0;
    Priorities.p = 0;
    Priorities.u = 0;
    Priorities.checklvl = 0;

    for (int i = 0; i < 12; i++) {
      if (reefstate.reefl4[i]) {
        Priorities.o++;
      }
    }

    for (int i = 0; i < 12; i++) {
      if (reefstate.reefl3[i]) {
        Priorities.p++;
      }
    }

    for (int i = 0; i < 12; i++) {
      if (reefstate.reefl4[i]) {
        Priorities.u++;
      }
    }

    if (Priorities.rp == true && Priorities.o >= 5 || Priorities.point == true && Priorities.o == 12) {
      Priorities.l4 = true;
      Priorities.i = 0;
      Priorities.checklvl = 1;

    } else if (Priorities.rp == true && Priorities.p >= 5 || Priorities.point == true && Priorities.p == 12) {
      Priorities.l3 = true;
      Priorities.i = 0;
      Priorities.checklvl = 2;
    } else if (Priorities.rp == true && Priorities.u >= 5 || Priorities.point == true && Priorities.u == 12) {
      Priorities.l2 = true;
      Priorities.i = 0;
      Priorities.checklvl = 0;
    }
    if (Priorities.l4 == true && Priorities.l3 == true && Priorities.l2 == true){
      Priorities.rp = false;
    Priorities.point = true;
    Priorities.i = 0;
    Priorities.checklvl = 0;
    }
    if (reefstate.reefl4[0] == false && Priorities.bothstations[Priorities.i] == 0 && Priorities.checklvl == 0
          || reefstate.reefl3[0] == false && Priorities.bothstations[Priorities.i] == 0 && Priorities.checklvl == 1 
          || reefstate.reefl2[0] == false && Priorities.bothstations[Priorities.i] == 0 && Priorities.checklvl == 2) {
        
        Priorities.check = 0;
        
        targetpose = POSES.REEF_A;
      } else if (reefstate.reefl4[1] == false && Priorities.bothstations[Priorities.i] == 1 && Priorities.checklvl == 0
          || reefstate.reefl3[1] == false && Priorities.bothstations[Priorities.i] == 1 && Priorities.checklvl == 1
          || reefstate.reefl2[1] == false && Priorities.bothstations[Priorities.i] == 1 && Priorities.checklvl == 2) {
        
        Priorities.check = 1;
        
        targetpose = POSES.REEF_B;
      } else if (reefstate.reefl4[2] == false && Priorities.bothstations[Priorities.i] == 2 && Priorities.checklvl == 0
          || reefstate.reefl3[2] == false && Priorities.bothstations[Priorities.i] == 2 && Priorities.checklvl == 1
          || reefstate.reefl2[2] == false && Priorities.bothstations[Priorities.i] == 2 && Priorities.checklvl == 2) {
        
        Priorities.check = 2;
        
        targetpose = POSES.REEF_C;
      } else if (reefstate.reefl4[3] == false && Priorities.bothstations[Priorities.i] == 3 && Priorities.checklvl == 0
          || reefstate.reefl3[3] == false && Priorities.bothstations[Priorities.i] == 3 && Priorities.checklvl == 1
          || reefstate.reefl2[3] == false && Priorities.bothstations[Priorities.i] == 3 && Priorities.checklvl == 2) {
        
        Priorities.check = 3;
        
        targetpose = POSES.REEF_D;
      } else if (reefstate.reefl4[4] == false && Priorities.bothstations[Priorities.i] == 4 && Priorities.checklvl == 0
          || reefstate.reefl3[4] == false && Priorities.bothstations[Priorities.i] == 4 && Priorities.checklvl == 1
          || reefstate.reefl2[4] == false && Priorities.bothstations[Priorities.i] == 4 && Priorities.checklvl == 2) {
        
        Priorities.check = 4;
        
        targetpose = POSES.REEF_E;
      } else if (reefstate.reefl4[5] == false && Priorities.bothstations[Priorities.i] == 5 && Priorities.checklvl == 0
          || reefstate.reefl3[5] == false && Priorities.bothstations[Priorities.i] == 5 && Priorities.checklvl == 1
          || reefstate.reefl2[5] == false && Priorities.bothstations[Priorities.i] == 5  && Priorities.checklvl == 2) {
        
        Priorities.check = 5;
        
        targetpose = POSES.REEF_F;
      } else if (reefstate.reefl4[6] == false && Priorities.bothstations[Priorities.i] == 6 && Priorities.checklvl == 0
          || reefstate.reefl3[6] == false && Priorities.bothstations[Priorities.i] == 6 && Priorities.checklvl == 1
          || reefstate.reefl2[6] == false && Priorities.bothstations[Priorities.i] == 6 && Priorities.checklvl == 2) {
        
        Priorities.check = 6;
        
        targetpose = POSES.REEF_G;
      } else if (reefstate.reefl4[7] == false && Priorities.bothstations[Priorities.i] == 7 && Priorities.checklvl == 0
          || reefstate.reefl3[7] == false && Priorities.bothstations[Priorities.i] == 7 && Priorities.checklvl == 1
          || reefstate.reefl2[7] == false && Priorities.bothstations[Priorities.i] == 7 && Priorities.checklvl == 2) {
        
        Priorities.check = 7;
        
        targetpose = POSES.REEF_H;
      } else if (reefstate.reefl4[8] == false && Priorities.bothstations[Priorities.i] == 8 && Priorities.checklvl == 0
          || reefstate.reefl3[8] == false && Priorities.bothstations[Priorities.i] == 8 && Priorities.checklvl == 1
          || reefstate.reefl2[8] == false && Priorities.bothstations[Priorities.i] == 8 && Priorities.checklvl == 2) {
        
        Priorities.check = 8;
        
        targetpose = POSES.REEF_I;
      } else if (reefstate.reefl4[9] == false && Priorities.bothstations[Priorities.i] == 9 && Priorities.checklvl == 0
          || reefstate.reefl3[9] == false && Priorities.bothstations[Priorities.i] == 9 && Priorities.checklvl == 1
          || reefstate.reefl2[9] == false && Priorities.bothstations[Priorities.i] == 9 && Priorities.checklvl == 2) {
        
        Priorities.check = 9;
        
        targetpose = POSES.REEF_J;
      } else if (reefstate.reefl4[10] == false && Priorities.bothstations[Priorities.i] == 10 && Priorities.checklvl == 0
          || reefstate.reefl3[10] == false && Priorities.bothstations[Priorities.i] == 10 && Priorities.checklvl == 1
          || reefstate.reefl2[10] == false && Priorities.bothstations[Priorities.i] == 10 && Priorities.checklvl == 2) {
        
        Priorities.check = 10;
        
        targetpose = POSES.REEF_K;
      } else if (reefstate.reefl4[11] == false && Priorities.bothstations[Priorities.i] == 11 && Priorities.checklvl == 0
          || reefstate.reefl3[11] == false && Priorities.bothstations[Priorities.i] == 11 && Priorities.checklvl == 1
          || reefstate.reefl2[11] == false && Priorities.bothstations[Priorities.i] == 11 && Priorities.checklvl == 2) {
        
        Priorities.check = 11;
        
        targetpose = POSES.REEF_L;
        } else {
          System.out.println("something in alignpeg is messed up");
        }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (sensorsandleds.input.get() == true) {
      if (lastpegsave.lastpegsaved == 0 || lastpegsave.lastpegsaved == 11 || lastpegsave.lastpegsaved == 10
          || lastpegsave.lastpegsaved == 9 || lastpegsave.lastpegsaved == 8 || lastpegsave.lastpegsaved == 7) {
            targetpose = StationPOSES.Left_coral_station;
            cancel();
        cancel();
      } else {
        Priorities.autodrive = false;
        Priorities.rightstation = true;
        cancel();
      }
    } else {
      Priorities.autodrive = false;
      Priorities.align = true;
    }
    s_Swerve.drive(new Translation2d(0, 0), 0, false, false);
    PathConstraints constraints = new PathConstraints(

        5,

        3,

        4,

        3

    );

    if (targetpose != null) {

      s_Swerve.drive(new Translation2d(0, 0), 0, false, false);
      Command pathfind = AutoBuilder.pathfindToPose(
          targetpose,
          constraints,
          0.01);
      pathfind.schedule();

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (Priorities.align = true) {
      Priorities.i++;
      Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
    }
    cancel();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
