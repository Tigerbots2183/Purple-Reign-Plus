package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.Constants.reefstate;
import frc.robot.Constants.reefstate.Priorities;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;


public class alignpeg extends Command {
  Swerve s_Swerve;
  boolean isrightscore;
  double speed;
  elevator s_ElevatorCom;
  double posNum;
  double elevatePos = 0;
  boolean returning;
  coral s_CoralCom;
  boolean isFinished = false;
  Pose2d targetpose = null;

  public alignpeg(Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (reefstate.reefl4[0] == false && Priorities.bothstations[Priorities.i] == 0 && Priorities.checklvl == 0
          || reefstate.reefl3[0] == false && Priorities.bothstations[Priorities.i] == 0 && Priorities.checklvl == 1 
          || reefstate.reefl2[0] == false && Priorities.bothstations[Priorities.i] == 0 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 0;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_A;
      } else if (reefstate.reefl4[1] == false && Priorities.bothstations[Priorities.i] == 1 && Priorities.checklvl == 0
          || reefstate.reefl3[1] == false && Priorities.bothstations[Priorities.i] == 1 && Priorities.checklvl == 1
          || reefstate.reefl2[1] == false && Priorities.bothstations[Priorities.i] == 1 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 1;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_B;
      } else if (reefstate.reefl4[2] == false && Priorities.bothstations[Priorities.i] == 2 && Priorities.checklvl == 0
          || reefstate.reefl3[2] == false && Priorities.bothstations[Priorities.i] == 2 && Priorities.checklvl == 1
          || reefstate.reefl2[2] == false && Priorities.bothstations[Priorities.i] == 2 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 2;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_C;
      } else if (reefstate.reefl4[3] == false && Priorities.bothstations[Priorities.i] == 3 && Priorities.checklvl == 0
          || reefstate.reefl3[3] == false && Priorities.bothstations[Priorities.i] == 3 && Priorities.checklvl == 1
          || reefstate.reefl2[3] == false && Priorities.bothstations[Priorities.i] == 3 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 3;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_D;
      } else if (reefstate.reefl4[4] == false && Priorities.bothstations[Priorities.i] == 4 && Priorities.checklvl == 0
          || reefstate.reefl3[4] == false && Priorities.bothstations[Priorities.i] == 4 && Priorities.checklvl == 1
          || reefstate.reefl2[4] == false && Priorities.bothstations[Priorities.i] == 4 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 4;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_E;
      } else if (reefstate.reefl4[5] == false && Priorities.bothstations[Priorities.i] == 5 && Priorities.checklvl == 0
          || reefstate.reefl3[5] == false && Priorities.bothstations[Priorities.i] == 5 && Priorities.checklvl == 1
          || reefstate.reefl2[5] == false && Priorities.bothstations[Priorities.i] == 5  && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 5;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_F;
      } else if (reefstate.reefl4[6] == false && Priorities.bothstations[Priorities.i] == 6 && Priorities.checklvl == 0
          || reefstate.reefl3[6] == false && Priorities.bothstations[Priorities.i] == 6 && Priorities.checklvl == 1
          || reefstate.reefl2[6] == false && Priorities.bothstations[Priorities.i] == 6 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 6;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_G;
      } else if (reefstate.reefl4[7] == false && Priorities.bothstations[Priorities.i] == 7 && Priorities.checklvl == 0
          || reefstate.reefl3[7] == false && Priorities.bothstations[Priorities.i] == 7 && Priorities.checklvl == 1
          || reefstate.reefl2[7] == false && Priorities.bothstations[Priorities.i] == 7 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 7;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_H;
      } else if (reefstate.reefl4[8] == false && Priorities.bothstations[Priorities.i] == 8 && Priorities.checklvl == 0
          || reefstate.reefl3[8] == false && Priorities.bothstations[Priorities.i] == 8 && Priorities.checklvl == 1
          || reefstate.reefl2[8] == false && Priorities.bothstations[Priorities.i] == 8 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 8;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_I;
      } else if (reefstate.reefl4[9] == false && Priorities.bothstations[Priorities.i] == 9 && Priorities.checklvl == 0
          || reefstate.reefl3[9] == false && Priorities.bothstations[Priorities.i] == 9 && Priorities.checklvl == 1
          || reefstate.reefl2[9] == false && Priorities.bothstations[Priorities.i] == 9 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 9;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_J;
      } else if (reefstate.reefl4[10] == false && Priorities.bothstations[Priorities.i] == 10 && Priorities.checklvl == 0
          || reefstate.reefl3[10] == false && Priorities.bothstations[Priorities.i] == 10 && Priorities.checklvl == 1
          || reefstate.reefl2[10] == false && Priorities.bothstations[Priorities.i] == 10 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 10;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_K;
      } else if (reefstate.reefl4[11] == false && Priorities.bothstations[Priorities.i] == 11 && Priorities.checklvl == 0
          || reefstate.reefl3[11] == false && Priorities.bothstations[Priorities.i] == 11 && Priorities.checklvl == 1
          || reefstate.reefl2[11] == false && Priorities.bothstations[Priorities.i] == 11 && Priorities.checklvl == 2) {
        Priorities.i++;
        Priorities.check = 11;
        Priorities.highlight[Priorities.checklvl][Priorities.check] = true;
        targetpose = POSES.REEF_L;
        } else {
          System.out.println("something in alignpeg is messed up");
        }
  }

  @Override
  public void execute() {

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
    if (reefstate.reefl4[Priorities.check] == false && Priorities.l4 == false) {
      Priorities.shootl4 = true;
    } else if (reefstate.reefl3[Priorities.check] == false && Priorities.l4 == true && Priorities.l3 == false) {
      Priorities.shootl3 = true;
    } else if (reefstate.reefl2[Priorities.check] == false && Priorities.l4 == true && Priorities.l3 == true && Priorities.l2 == false) {
      Priorities.shootl2 = true;
    } else {
      System.out.println("error with shooting");
    }
    Priorities.highlight[Priorities.checklvl][Priorities.check] = false;
    Priorities.align = false;
    cancel();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (isFinished == true) {
      return true;
    }

    else {
      return false;
    }

  }
}