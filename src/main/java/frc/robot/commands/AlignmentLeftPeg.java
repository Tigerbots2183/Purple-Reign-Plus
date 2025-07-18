package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.POSES;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignmentLeftPeg extends Command {
    Swerve s_Swerve;
    //alignment for left peg, references poses constant
    boolean isFinished = false;

    private Pose2d Targetpose = null;
    public static int lastpeg = 0;

    public AlignmentLeftPeg(Swerve s_Swerve) {
        this.s_Swerve = s_Swerve;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        System.out.println("leftBranchPathfinding method called.");
        double aprilTagID = LimelightHelpers.getFiducialID("limelight");
        // Blue paths

        switch ((int) aprilTagID) {
            case 17: {
                Targetpose = POSES.REEF_C;
                lastpeg = 2;
            }
                break;

            case 18: {
                Targetpose = POSES.REEF_A;
                lastpeg = 0;
            }
                break;

            case 19: {
                Targetpose = POSES.REEF_K;
                lastpeg = 10;
            }
                break;

            case 20: {
                Targetpose = POSES.REEF_I;
                lastpeg = 8;
            }
                break;

            case 21: {
                Targetpose = POSES.REEF_G;
                lastpeg = 6;
            }
                break;

            case 22: {
                Targetpose = POSES.REEF_E;
                lastpeg = 4;
            }
                break;

            // Red Paths
            case 6: {
                Targetpose = POSES.REEF_Kr;
                lastpeg = 10;
            }
                break;

            case 7: {
                Targetpose = POSES.REEF_Ar;
                lastpeg = 0;
            }
                break;

            case 8: {
                Targetpose = POSES.REEF_Cr;
                lastpeg = 2;
            }
                break;

            case 9: {
                Targetpose = POSES.REEF_Er;
                lastpeg = 4;
            }
                break;

            case 10: {
                Targetpose = POSES.REEF_Gr;
                lastpeg = 6;
            }
                break;

            case 11: {
                Targetpose = POSES.REEF_Ir;
                lastpeg = 8;

            }
                break;

            default:

                break;
        }

        return;

    }

    @Override
    public void execute() {
        addRequirements(s_Swerve);

        PathConstraints constraints = new PathConstraints(

        5,

        3,

        4,

        3

        );

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