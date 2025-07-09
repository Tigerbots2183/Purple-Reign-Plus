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

import com.pathplanner.lib.pathfinding.Pathfinding;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.autoshootlfour;
import frc.robot.subsystems.*;
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

  private RobotContainer m_robotContainer;

  private final elevator s_ElevatorCom = new elevator();

  private final coral s_CoralCom = new coral();


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


  public void robotInit() {
    // DO THIS FIRST
    Pathfinding.setPathfinder(new LocalADStarAK());

    // ... remaining robot initialization
    testCommand = new autoshootlfour(0, 3, s_ElevatorCom, s_CoralCom, true);

  }

  /** This function is called periodically during all modes. */

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  

  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    sensorsandleds.leds.updateDutyCycle(.09);
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

    

   
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
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
    if ((limelightaligntwo.tagValue2 == 9 || limelightaligntwo.tagValue2 == 10 || limelightaligntwo.tagValue2 == 11
        || limelightaligntwo.tagValue2 == 6 || limelightaligntwo.tagValue2 == 7 || limelightaligntwo.tagValue2 == 8
        || limelightaligntwo.tagValue2 == 20 || limelightaligntwo.tagValue2 == 21 || limelightaligntwo.tagValue2 == 22
        || limelightaligntwo.tagValue2 == 17 || limelightaligntwo.tagValue2 == 18 || limelightaligntwo.tagValue2 == 19
        || limelightalign.tagValue == 9 || limelightalign.tagValue == 10 || limelightalign.tagValue == 11
        || limelightalign.tagValue == 6 || limelightalign.tagValue == 7 || limelightalign.tagValue == 8
        || limelightalign.tagValue == 20 || limelightalign.tagValue == 21 || limelightalign.tagValue == 22
        || limelightalign.tagValue == 17 || limelightalign.tagValue == 18 || limelightalign.tagValue == 19)
        && sensorsandleds.wall.get() == false) {
      // coral station
      sensorsandleds.leds.updateDutyCycle(.07);
    } else if (limelightaligntwo.tagValue2 == 9 || limelightaligntwo.tagValue2 == 10
        || limelightaligntwo.tagValue2 == 11 || limelightaligntwo.tagValue2 == 6 || limelightaligntwo.tagValue2 == 7
        || limelightaligntwo.tagValue2 == 8 || limelightaligntwo.tagValue2 == 20 || limelightaligntwo.tagValue2 == 21
        || limelightaligntwo.tagValue2 == 22 || limelightaligntwo.tagValue2 == 17 || limelightaligntwo.tagValue2 == 18
        || limelightaligntwo.tagValue2 == 19 || limelightalign.tagValue == 9 || limelightalign.tagValue == 10
        || limelightalign.tagValue == 11 || limelightalign.tagValue == 6 || limelightalign.tagValue == 7
        || limelightalign.tagValue == 8 || limelightalign.tagValue == 20 || limelightalign.tagValue == 21
        || limelightalign.tagValue == 22 || limelightalign.tagValue == 17 || limelightalign.tagValue == 18
        || limelightalign.tagValue == 19) {
      // coral station
      sensorsandleds.leds.updateDutyCycle(.13);
    } else if (sensorsandleds.wall.get() == false) {
      // coral station
      sensorsandleds.leds.updateDutyCycle(.15);
    } else if (sensorsandleds.reef.get() == false) {
      // at reef
      sensorsandleds.leds.updateDutyCycle(.07);
    } else if (sensorsandleds.input.get() == false) {
      // coral aquired
      sensorsandleds.leds.updateDutyCycle(.17);
    } else {
      // peice
      sensorsandleds.leds.updateDutyCycle(.01);
    }
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
