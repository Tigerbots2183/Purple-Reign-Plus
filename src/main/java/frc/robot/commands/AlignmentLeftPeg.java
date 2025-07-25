package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.POSES;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlignmentLeftPeg extends Command {
    CommandSwerveDrivetrain s_Swerve;
    private SwerveRequest.RobotCentric driveRequest;

    // alignment for right peg, references poses constant
    boolean isFinished = false;

    private Pose2d Targetpose = null;

    public AlignmentLeftPeg(CommandSwerveDrivetrain s_Swerve) {
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
            }
                break;

            case 18: {
                Targetpose = POSES.REEF_A;
            }
                break;

            case 19: {
                Targetpose = POSES.REEF_K;
            }
                break;

            case 20: {
                Targetpose = POSES.REEF_I;
            }
                break;

            case 21: {
                Targetpose = POSES.REEF_G;
            }
                break;

            case 22: {
                Targetpose = POSES.REEF_E;
            }
                break;

            // Red Paths
            case 6: {
                Targetpose = FlippingUtil.flipFieldPose(POSES.REEF_K);
            }
                break;

            case 7: {
                Targetpose = FlippingUtil.flipFieldPose(POSES.REEF_A);
            }
                break;

            case 8: {
                Targetpose = FlippingUtil.flipFieldPose(POSES.REEF_C);
            }
                break;

            case 9: {
                Targetpose = FlippingUtil.flipFieldPose(POSES.REEF_E);
            }
                break;

            case 10: {
                Targetpose = FlippingUtil.flipFieldPose(POSES.REEF_G);
            }
                break;

            case 11: {
                Targetpose = FlippingUtil.flipFieldPose(POSES.REEF_I);

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
                    0.00);
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