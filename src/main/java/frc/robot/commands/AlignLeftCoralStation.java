package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.Constants.StationPOSES;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignLeftCoralStation extends Command {
    Swerve s_Swerve;
    // alignment for right peg, references poses constant
    boolean isFinished = false;

    private Pose2d Targetpose = null;

    public AlignLeftCoralStation(Swerve s_Swerve) {
        this.s_Swerve = s_Swerve;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        System.out.println("rightBranchPathfinding method called.");
      Targetpose = StationPOSES.Left_coral_station;
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

        // = new PathConstraints(

        // 5,

        // 3,

        // 4,

        // 3

        // );

        if (Targetpose != null) {

            Command followLeftPath = AutoBuilder.pathfindToPose(
                    Targetpose,
                    constraints,
                    0.01);
            followLeftPath.schedule();

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

        if (isFinished == true) {
            return true;
        }

        else {
            return false;
        }

    }
}