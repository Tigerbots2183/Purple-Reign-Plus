// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.util.COTSTalonFXSwerveConstants;
import frc.lib.util.SwerveModuleConstants;
import edu.wpi.first.wpilibj.DriverStation;
/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always
 * "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics
 * sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static final double stickDeadband = 0.1;

  public static final class Swerve {
    public static final int pigeonID = 30;

    public static final COTSTalonFXSwerveConstants chosenModule = // TODO: This must be tuned to specific robot
        COTSTalonFXSwerveConstants.SDS.MK4i.KrakenX60(
            COTSTalonFXSwerveConstants.SDS.MK4i.driveRatios.L2);

    /* Drivetrain Constants */
    public static final double trackWidth = Units.inchesToMeters(22.5); // TODO: This must be tuned to specific robot
    public static final double wheelBase = Units.inchesToMeters(22.5); // TODO: This must be tuned to specific robot
    public static final double wheelCircumference = chosenModule.wheelCircumference;

    /*
     * Swerve Kinematics
     * No need to ever change this unless you are not doing a traditional
     * rectangular/square 4 module swerve
     */
    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
        new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
        new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
        new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
        new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

    /* Module Gear Ratios */
    public static final double driveGearRatio = chosenModule.driveGearRatio;
    public static final double angleGearRatio = chosenModule.angleGearRatio;

    /* Motor Inverts */
    public static final InvertedValue angleMotorInvert = chosenModule.angleMotorInvert;
    public static final InvertedValue driveMotorInvert = chosenModule.driveMotorInvert;

    /* Angle Encoder Invert */
    public static final SensorDirectionValue cancoderInvert = chosenModule.cancoderInvert;

    /* Swerve Current Limiting */
    public static final int angleCurrentLimit = 35;
    public static final int angleCurrentThreshold = 40;
    public static final double angleCurrentThresholdTime = 0.1;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveCurrentLimit = 55;
    public static final int driveCurrentThreshold = 120;
    public static final double driveCurrentThresholdTime = 0.1;
    public static final boolean driveEnableCurrentLimit = true;

    /*
     * These values are used by the drive falcon to ramp in open loop and closed
     * loop driving.
     * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
     */
    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    /* Angle Motor PID Values */
    public static final double angleKP = chosenModule.angleKP;
    public static final double angleKI = chosenModule.angleKI;
    public static final double angleKD = chosenModule.angleKD;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.12; // TODO: This must be tuned to specific robot
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKF = 0.0;

    /* Drive Motor Characterization Values From SYSID */
    public static final double driveKS = 0.32; // TODO: This must be tuned to specific robot
    public static final double driveKV = 1.51;
    public static final double driveKA = 0.27;

    /* Swerve Profiling Values */
    /** Meters per Second */
    public static final double maxSpeed = 4; // TODO: This must be tuned to specific robot
    /** Radians per Second */
    public static final double maxAngularVelocity = 5.0; // TODO: This must be tuned to specific robot

    /* Neutral Modes */
    public static final NeutralModeValue angleNeutralMode = NeutralModeValue.Coast;
    public static final NeutralModeValue driveNeutralMode = NeutralModeValue.Brake;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */
    public static final class Mod0 { // TODO: This must be tuned to specific robot
      public static final int driveMotorID = 10;
      public static final int angleMotorID = 11;
      public static final int canCoderID = 12;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-94.482422);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 { // TODO: This must be tuned to specific robot
      public static final int driveMotorID = 13;
      public static final int angleMotorID = 14;
      public static final int canCoderID = 15;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-155.039063);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }

    /* Back Left Module - Module 2 */
    public static final class Mod2 { // TODO: This must be tuned to specific robot
      public static final int driveMotorID = 16;
      public static final int angleMotorID = 17;
      public static final int canCoderID = 18;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(30.498047);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 { // TODO: This must be tuned to specific robot
      public static final int driveMotorID = 19;
      public static final int angleMotorID = 20;
      public static final int canCoderID = 21;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-15.908203);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }
  }

  public static final class AutoConstants { // TODO: The below constants are used in the example auto, and must be tuned
    // to specific robot
    public static final double kMaxSpeedMetersPerSecond = 5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    /* Constraint for the motion profilied robot angle controller */
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }
  public static class POSES {
    public static final Pose2d RESET_POSE = new Pose2d(3.192, 4.025, new Rotation2d());

    // Blue branch poses
    public static Pose2d REEF_A = new Pose2d(3.40, 4.23, Rotation2d.fromDegrees(90)); // new Pose2d(3.18, 4.22,                                                                           // Rotation2d.fromDegrees(90));
    public static Pose2d REEF_B = new Pose2d(3.21, 3.90, Rotation2d.fromDegrees(90));
    public static Pose2d REEF_C = new Pose2d(3.67, 2.98, Rotation2d.fromDegrees(150));
    public static Pose2d REEF_D = new Pose2d(3.91, 2.89, Rotation2d.fromDegrees(150));
    public static Pose2d REEF_E = new Pose2d(4.95, 2.82, Rotation2d.fromDegrees(-150));
    public static Pose2d REEF_F = new Pose2d(5.24, 2.98, Rotation2d.fromDegrees(-150));
    public static Pose2d REEF_G = new Pose2d(5.76, 3.81, Rotation2d.fromDegrees(-90));
    public static Pose2d REEF_H = new Pose2d(5.77, 4.16, Rotation2d.fromDegrees(-90));
    public static Pose2d REEF_I = new Pose2d(5.36, 4.84, Rotation2d.fromDegrees(-30));
    public static Pose2d REEF_J = new Pose2d(5.03, 5.19, Rotation2d.fromDegrees(-30));
    public static Pose2d REEF_K = new Pose2d(4.01, 5.27, Rotation2d.fromDegrees(30));
    public static Pose2d REEF_L = new Pose2d(3.75, 5.07, Rotation2d.fromDegrees(30));

  }

  public static class StationPOSES {
    public static final Pose2d RESET_POSE = new Pose2d(3.192, 4.025, new Rotation2d());

    public static final Pose2d Left_top_station = new Pose2d(1.55, 7.45, Rotation2d.fromDegrees(30));
    public static final Pose2d Left_mid_station = new Pose2d(1.18, 7.10, Rotation2d.fromDegrees(30));
    public static final Pose2d Left_bot_station = new Pose2d(0.62, 6.73, Rotation2d.fromDegrees(30));
    
    public static final Pose2d Right_top_station = new Pose2d(1.41, 0.73, Rotation2d.fromDegrees(150));
    public static final Pose2d Right_mid_station = new Pose2d(1.08, 1.19, Rotation2d.fromDegrees(150));
    public static final Pose2d Right_bot_station = new Pose2d(0.61, 1.55, Rotation2d.fromDegrees(150));
  }

  public static class reefstate {
    public static boolean[] reefl4 = {
        false, false, false, false, false, false, false, false, false, false, false };

    public static boolean[] reefl3 = {
        false, false, false, false, false, false, false, false, false, false, false };
    public static boolean[] reefl2 = {
        false, false, false, false, false, false, false, false, false, false, false };
  }
}
