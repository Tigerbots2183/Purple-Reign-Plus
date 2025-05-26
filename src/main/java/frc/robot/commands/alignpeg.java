package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
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

  Pose2d Targetpose;

  public alignpeg(Pose2d Targetpose, Boolean isrightscore, Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
    this.Targetpose = Targetpose;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

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

    if (Targetpose != null) {

      s_Swerve.drive(new Translation2d(0, 0), 0, false, false);
      Command pathfind = AutoBuilder.pathfindToPose(
          Targetpose,
          constraints,
          0.01);
      pathfind.schedule();

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (reefstate.reefl4[Priorities.check] == false && Priorities.l4 == false) {
      new autol4(-.12, 3, s_ElevatorCom, s_CoralCom, false);
    } else if (reefstate.reefl3[Priorities.check] == false && Priorities.l4 == true && Priorities.l3 == false) {
      // autol3
    } else if (reefstate.reefl2[Priorities.check] == false && Priorities.l4 == true && Priorities.l3 == true && Priorities.l2 == false) {
      // autol2
    } else {
      new autodrive(false, s_Swerve);
    }
    Priorities.highlight[Priorities.checklvl][Priorities.check] = false;
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