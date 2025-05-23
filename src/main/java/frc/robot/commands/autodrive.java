package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
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
    
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (sensorsandleds.input.get() == false){

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
