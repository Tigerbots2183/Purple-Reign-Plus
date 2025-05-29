package frc.robot.commands;

// import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
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
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (sensorsandleds.input.get() == true) {
      if (lastpegsave.lastpegsaved == 0 || lastpegsave.lastpegsaved == 11 || lastpegsave.lastpegsaved == 10
          || lastpegsave.lastpegsaved == 9 || lastpegsave.lastpegsaved == 8 || lastpegsave.lastpegsaved == 7) {
            Priorities.autodrive = false;
            Priorities.leftstation = true;
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
