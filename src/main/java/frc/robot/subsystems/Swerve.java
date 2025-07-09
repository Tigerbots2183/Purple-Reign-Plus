package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;
// import com.pathplanner.lib.config.ModuleConfig;
// import com.pathplanner.lib.config.PIDConstants;
// import com.pathplanner.lib.config.RobotConfig;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.SwerveModule;
//This is based on dirtbikerxz's swerve library (on github). We were trying to switch to ctre but didnt have enough time. Despite setup with mk4is we use mk4ns.
public class Swerve extends SubsystemBase {
  public SwerveDriveOdometry swerveOdometry;
  public static SwerveModule[] mSwerveMods;
  public static Pigeon2 gyro;
  SwerveModuleState[] desiredStates;
  RobotConfig config;
  public static SwerveDrivePoseEstimator m_poseEstimator;
  public Swerve() {
    gyro = new Pigeon2(Constants.Swerve.pigeonID, "base");
    gyro.getConfigurator().apply(new Pigeon2Configuration());
    gyro.setYaw(0);

    mSwerveMods =
        new SwerveModule[] {
          new SwerveModule(0, Constants.Swerve.Mod0.constants),
          new SwerveModule(1, Constants.Swerve.Mod1.constants),
          new SwerveModule(2, Constants.Swerve.Mod2.constants),
          new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };
 m_poseEstimator =
         new SwerveDrivePoseEstimator(
           Constants.Swerve.swerveKinematics,
           getGyroYaw(),
             getModulePositions(),
             new Pose2d(),
             VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5)),
             VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(30)));
    swerveOdometry =
        new SwerveDriveOdometry(
            Constants.Swerve.swerveKinematics, getGyroYaw(), getModulePositions());

    // RobotConfig config;
    try {
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // Handle exception as needed
      e.printStackTrace();
    }

    // Configure AutoBuilder last
    AutoBuilder.configure(
        this::getPose, // Robot pose supplier
        this::setPose, // Method to reset odometry (will be called if your auto has a starting pose)
        this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
        (speeds, feedforwards) ->
            driveRobotRelative(
                speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds.
        // Also optionally outputs individual module feedforwards
        new PPHolonomicDriveController( // PPHolonomicController is the built in path following
            // controller for holonomic drive trains
            new PIDConstants(3.0, 0.0, 0.0), // Translation PID constants
            new PIDConstants(10.0, 0.0, 0.0) // Rotation PID constants
            ),
        config, // The robot configuration
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red
          // alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this // Reference to this subsystem to set requirements
        );
  }

  public void drive(
      Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
    SwerveModuleState[] swerveModuleStates =
        Constants.Swerve.swerveKinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(
                    translation.getX(), translation.getY(), rotation, getHeading())
                : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
    }
  }
  //  @SuppressWarnings("removal")
  //  public static void updateOdometry() {
  //    m_poseEstimator.update(
  //     getGyroYaw(), getModulePositions());
  //                 boolean useMegaTag2 = true; //set to false to use MegaTag1
  //                 boolean doRejectUpdate = false;
  //                 if(useMegaTag2 == false)
  //                 {
  //                   LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue("");
                    
  //                   if(mt1.tagCount == 1 && mt1.rawFiducials.length == 1)
  //                   {
  //                     if(mt1.rawFiducials[0].ambiguity > .7)
  //                     {
  //                       doRejectUpdate = true;
  //                     }
  //                     if(mt1.rawFiducials[0].distToCamera > 3)
  //                     {
  //                       doRejectUpdate = true;
  //                     }
  //                   }
  //                   if(mt1.tagCount == 0)
  //                   {
  //                     doRejectUpdate = true;
  //                   }
              
  //                   if(!doRejectUpdate)
  //                   {
  //                     m_poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(.5,.5,9999999));
  //                     m_poseEstimator.addVisionMeasurement(
  //                         mt1.pose,
  //                         mt1.timestampSeconds);
  //                   }
  //                 }
  //                 else if (useMegaTag2 == true)
  //                 {
  //                   LimelightHelpers.SetRobotOrientation("", m_poseEstimator.getEstimatedPosition().getRotation().getDegrees(), 0, 0, 0, 0, 0);
  //                   LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
  //                   if(Math.abs(gyro.getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
  //                   {
  //                     doRejectUpdate = true;
  //                   }
  //                   if(mt2.tagCount == 0)
  //                   {
  //                     doRejectUpdate = true;
  //                   }
  //                   if(!doRejectUpdate)
  //                   {
  //                     m_poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
  //                     m_poseEstimator.addVisionMeasurement(
  //                         mt2.pose,
  //                         mt2.timestampSeconds);
  //                   }
  //                 }
  //               }

  /* Used by SwerveControllerCommand in Auto */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
      mod.setDesiredState(desiredStates[mod.moduleNumber], false);
    }
  }

  public SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[4];
    for (SwerveModule mod : mSwerveMods) {
      states[mod.moduleNumber] = mod.getState();
    }
    return states;
  }

  public static SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    for (SwerveModule mod : mSwerveMods) {
      positions[mod.moduleNumber] = mod.getPosition();
    }
    return positions;
  }

  // public Pose2d getPose() {
  //   return swerveOdometry.getPoseMeters();
  // }
  public Pose2d getPose() {
    return m_poseEstimator.getEstimatedPosition();
  }

  public ChassisSpeeds getRobotRelativeSpeeds() {
    return Constants.Swerve.swerveKinematics.toChassisSpeeds(getModuleStates());
  }

  // public void setPose(Pose2d pose) {
  //   swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), pose);
  // }
  public void setPose(Pose2d pose) {
    m_poseEstimator.resetPosition(getGyroYaw(), getModulePositions(), pose);
  }

  public Rotation2d getHeading() {
    return getPose().getRotation();
  }

  // public void setHeading(Rotation2d heading) {
  //   swerveOdometry.resetPosition(
  //       getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), heading));
  // }
  public void setHeading(Rotation2d heading) {
    m_poseEstimator.resetPosition(
        getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), heading));
  }

  // public void zeroHeading() {
  //   swerveOdometry.resetPosition(
  //       getGyroYaw(),
  //       getModulePositions(),
  //       new Pose2d(getPose().getTranslation(), new Rotation2d()));
  // }
  public void zeroHeading() {
    m_poseEstimator.resetPosition(
        getGyroYaw(),
        getModulePositions(),
        new Pose2d(getPose().getTranslation(), new Rotation2d()));
  }

  public static Rotation2d getGyroYaw() {
    return Rotation2d.fromDegrees(gyro.getYaw().getValueAsDouble());
  }

  public void resetModulesToAbsolute() {
    for (SwerveModule mod : mSwerveMods) {
      mod.resetToAbsolute();
    }
  }

  public void driveRobotRelative(ChassisSpeeds speeds) {
    SwerveModuleState[] states = Constants.Swerve.swerveKinematics.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.Swerve.maxSpeed);
    setModuleStates(states);
  }

  @SuppressWarnings("removal")
  @Override
  public void periodic() {
    swerveOdometry.update(getGyroYaw(), getModulePositions());
    
    m_poseEstimator.update(
      getGyroYaw(), getModulePositions());
                  boolean useMegaTag2 = true; //set to false to use MegaTag1
                  boolean doRejectUpdate = false;

                  if (LimelightHelpers.getFiducialID("") > 0){
                  if(useMegaTag2 == false)
                  {
                    LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue("");
                    
                    if(mt1.tagCount == 1 && mt1.rawFiducials.length == 1)
                    {
                      if(mt1.rawFiducials[0].ambiguity > .7)
                      {
                        doRejectUpdate = true;
                      }
                      if(mt1.rawFiducials[0].distToCamera > 3)
                      { 
                        doRejectUpdate = true;
                      }
                    }
                    if(mt1.tagCount == 0)
                    {
                      doRejectUpdate = true;
                    }
              
                    if(!doRejectUpdate)
                    {
                      m_poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(.5,.5,9999999));
                      m_poseEstimator.addVisionMeasurement(
                          mt1.pose,
                          mt1.timestampSeconds);
                    }
                  }
                  else if (useMegaTag2 == true)
                  {
                    LimelightHelpers.SetRobotOrientation("", m_poseEstimator.getEstimatedPosition().getRotation().getDegrees(), 0, 0, 0, 0, 0);
                    LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("");
                    if(Math.abs(gyro.getRate()) > 720) // if our angular  is greater than 720 degrees per second, ignore vision updates
                    {
                      doRejectUpdate = true;
                    }
                    if(mt2 == null )
                    {
                      doRejectUpdate = true;
                    }else if(mt2.tagCount == 0 )
                    {
                      doRejectUpdate = true;
                    }
                    if(!doRejectUpdate)
                    {
                      m_poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
                      m_poseEstimator.addVisionMeasurement(
                          mt2.pose,
                          mt2.timestampSeconds);
                    }
                  }
                }
                if (LimelightHelpers.getFiducialID("limelight-right") > 0){
                  if(useMegaTag2 == false)
                  {
                    LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-right");
                    
                    if(mt1.tagCount == 1 && mt1.rawFiducials.length == 1)
                    {
                      if(mt1.rawFiducials[0].ambiguity > .7)
                      {
                        doRejectUpdate = true;
                      }
                      if(mt1.rawFiducials[0].distToCamera > 3)
                      { 
                        doRejectUpdate = true;
                      }
                    }
                    if(mt1.tagCount == 0)
                    {
                      doRejectUpdate = true;
                    }
              
                    if(!doRejectUpdate)
                    {
                      m_poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(.5,.5,9999999));
                      m_poseEstimator.addVisionMeasurement(
                          mt1.pose,
                          mt1.timestampSeconds);
                    }
                  }
                  else if (useMegaTag2 == true)
                  {
                    LimelightHelpers.SetRobotOrientation("limelight-right", m_poseEstimator.getEstimatedPosition().getRotation().getDegrees(), 0, 0, 0, 0, 0);
                    LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");
                    if(Math.abs(gyro.getRate()) > 720) // if our angular  is greater than 720 degrees per second, ignore vision updates
                    {
                      doRejectUpdate = true;
                    }
                    if(mt2 == null )
                    {
                      doRejectUpdate = true;
                    }else if(mt2.tagCount == 0 )
                    {
                      doRejectUpdate = true;
                    }
                    if(!doRejectUpdate)
                    {
                      m_poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
                      m_poseEstimator.addVisionMeasurement(
                          mt2.pose,
                          mt2.timestampSeconds);
                    }
                  }
                }
    Logger.recordOutput("acual swerve state", getModuleStates());
    Logger.recordOutput("desired swerve state", desiredStates);
    Logger.recordOutput("Odomerty Pose", getPose());
    Logger.recordOutput("turn", getHeading());
    Logger.recordOutput("gyro", getGyroYaw());
    Logger.recordOutput("gyroforalign",getGyroYaw() );

    for (SwerveModule mod : mSwerveMods) {
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " CANcoder", mod.getCANcoder().getDegrees());
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Angle", mod.getPosition().angle.getDegrees());
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " ", mod.getState().speedMetersPerSecond);
    }
  }
}
