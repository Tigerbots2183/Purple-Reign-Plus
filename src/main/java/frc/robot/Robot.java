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


import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.pathfinding.Pathfinding;
import frc.robot.subsystems.Touchboard.posePlotterUtil;  
import edu.wpi.first.math.util.Units;
// import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.*;
import gg.questnav.questnav.QuestNav;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends LoggedRobot {
  // private final orchestraSub s_Orchestra = new orchestraSub();
  public static final CTREConfigs ctreConfigs = new CTREConfigs();

  private Command m_autonomousCommand;

  public RobotContainer m_robotContainer;

  private final boolean kUseLimelight = true;

  public Robot() {
    // Record metadata
    /*
     * Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
     * Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
     * Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
     * Logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
     * Logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
     * switch (BuildConstants.DIRTY) {
     * case 0:
     * Logger.recordMetadata("GitDirty", "All changes committed");
     * break;
     * case 1:
     * Logger.recordMetadata("GitDirty", "Uncomitted changes");
     * break;
     * default:
     * Logger.recordMetadata("GitDirty", "Unknown");
     * break;
     * }
     */

    // Set up data receivers & replay source
    switch (Constants.currentMode) {
      case REAL:
        // Running on a real robot, log to a USB stick ("/U/logs")
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case SIM:
        // Running a physics simulator, log to NT
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case REPLAY:
        // Replaying a log, set up replay source
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
    }

    // Start AdvantageKit logger
    Logger.start();

    m_robotContainer = new RobotContainer();
  }

  Command testCommand;
  private final Field2d m_field = new Field2d();

  public void robotInit() {
    // DO THIS FIRST
    Pathfinding.setPathfinder(new LocalADStarAK());
    PathfindingCommand.warmupCommand().schedule();
    // ... remaining robot initializatio
    SmartDashboard.putData("Field", m_field);
  
  }

  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    /*
     * This example of adding Limelight is very simple and may not be sufficient for on-field use.
     * Users typically need to provide a standard deviation that scales with the distance to target
     * and changes with number of tags available.
     *
     * This example is sufficient to show that vision integration is possible, though exact implementation
     * of how to use vision should be tuned per-robot and to the team's specification.
     */
    // if (kUseLimelight) {
    //   var driveState = m_robotContainer.drivetrain.getState();
      
    //   double headingDeg = driveState.Pose.getRotation().getDegrees();
    //   double omegaRps = Units.radiansToRotations(driveState.Speeds.omegaRadiansPerSecond);
    //   LimelightHelpers.SetRobotOrientation("limelight", headingDeg, 0, 0, 0, 0, 0);
    //   LimelightHelpers.SetRobotOrientation("limelight-right", headingDeg, 0, 0, 0, 0, 0);

    //   var llMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
    //   var lrMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");

    //   if (llMeasurement != null && llMeasurement.tagCount > 0 && Math.abs(omegaRps) < 2.0) {
    //     m_robotContainer.drivetrain.addVisionMeasurement(llMeasurement.pose, llMeasurement.timestampSeconds);
    //   }

    //   if (lrMeasurement != null && lrMeasurement.tagCount > 0 && Math.abs(omegaRps) < 2.0) {
    //     System.out.print("TEST");
    //     m_robotContainer.drivetrain.addVisionMeasurement(lrMeasurement.pose, lrMeasurement.timestampSeconds);

    //   }

    // }
  }


  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {

  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    
    if(m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees() > 1 || m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees() < -1 ){
      m_robotContainer.s_ledCom.led(23);
      SmartDashboard.putBoolean("Heading Check", true);
    } else{
      m_robotContainer.s_ledCom.led(28);
      SmartDashboard.putBoolean("Heading Check", false);
      
    }

    if(m_robotContainer.questNavSubsystem.haveQuest()){

      if(m_robotContainer.questNavSubsystem.questPercent() < 15){
        m_robotContainer.s_ledCom.led(35);
        
      }else{
        m_robotContainer.s_ledCom.led(33);

      }
      SmartDashboard.putBoolean("Quest Check", true);
    } else { 
      m_robotContainer.s_ledCom.led(38);
      SmartDashboard.putBoolean("Quest Check", false);

    }



    if(posePlotterUtil.stringStatus() == 200){
      m_robotContainer.s_ledCom.led(43);
      SmartDashboard.putBoolean("Auto Check", true);
      
    } else{
      m_robotContainer.s_ledCom.led(48);
      SmartDashboard.putBoolean("Auto Check", false);

    }

  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = Commands.none();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    sensorsandleds.leds.updateDutyCycle(.09);

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
