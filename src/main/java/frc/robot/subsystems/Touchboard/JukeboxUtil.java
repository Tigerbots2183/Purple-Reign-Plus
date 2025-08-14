// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Touchboard;

import java.io.FilenameFilter;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.Tuneage;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.climber;

public class JukeboxUtil extends SubsystemBase {
  /** Creates a new JukeboxUtil. */

  
  final BooleanSubscriber newFileSubscriber;
  final BooleanPublisher newFilePublisher;
  
  final BooleanSubscriber stopSubscriber;
  final BooleanPublisher stopPublisher;

  final BooleanSubscriber pauseSubscriber;
  final BooleanPublisher pausePublisher;

  final BooleanSubscriber playSubscriber;
  final BooleanPublisher playPublisher;

  final BooleanSubscriber restartSubscriber;
  final BooleanPublisher restartPublisher;
  
  final BooleanSubscriber musicIsFinishedSubscriber;
  final BooleanPublisher musicIsFinishedPublisher;

  final BooleanSubscriber nextSongSubscriber;
  final BooleanPublisher nextSongPublisher;

  private Boolean prev = false;
  
  final StringSubscriber currentMusicFileSubscriber;
  CommandSwerveDrivetrain driveMotors;
  climber climberMotors;
  public JukeboxUtil(CommandSwerveDrivetrain driveMotors, climber climbMotors) {
    // get the default instance of NetworkTables
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    this.driveMotors = driveMotors;
    this.climberMotors = climbMotors;
    // get the subtable called "touchboard"
    NetworkTable datatable = inst.getTable("touchboard");

    // subscribe to the topic in "touchboard" to start command when button pressed
    // and set back to false
    currentMusicFileSubscriber = datatable.getStringTopic("currentMusicFile").subscribe("");

    newFilePublisher =  datatable.getBooleanTopic("newFile").publish();
    newFileSubscriber = datatable.getBooleanTopic("newFile").subscribe(false);

    stopPublisher =  datatable.getBooleanTopic("stopMusic").publish();
    stopSubscriber = datatable.getBooleanTopic("stopMusic").subscribe(false);

    pausePublisher =  datatable.getBooleanTopic("pauseMusic").publish();
    pauseSubscriber = datatable.getBooleanTopic("pauseMusic").subscribe(false);

    playPublisher =  datatable.getBooleanTopic("playMusic").publish();
    playSubscriber = datatable.getBooleanTopic("playMusic").subscribe(false);

    restartPublisher =  datatable.getBooleanTopic("restartMusic").publish();
    restartSubscriber = datatable.getBooleanTopic("restartMusic").subscribe(false);
    
    musicIsFinishedPublisher = datatable.getBooleanTopic("musicIsFinished").publish();
    musicIsFinishedSubscriber = datatable.getBooleanTopic("musicIsFinished").subscribe(false);

    nextSongPublisher =  datatable.getBooleanTopic("goToNextSong").publish();
    nextSongSubscriber = datatable.getBooleanTopic("goToNextSong").subscribe(false);

  }

 



  @Override
  public void periodic() {

    if(newFileSubscriber.get()){
      Command musicCom = new Tuneage(currentMusicFileSubscriber.get(), driveMotors, climberMotors);
      musicCom.schedule();
      musicIsFinishedPublisher.set(false);
      
      newFilePublisher.set(false);
      System.out.println("fileLoaded");
      System.out.println(currentMusicFileSubscriber.get());
      prev = false;
    }

    if(stopSubscriber.get()){
      Tuneage.mOrchestra.stop();
      stopPublisher.set(false);
      System.out.println("MusicStopped");
    }

    if(pauseSubscriber.get()){
      Tuneage.mOrchestra.pause();
      pausePublisher.set(false);
      System.out.println("MusicPaused");

    }

    if(playSubscriber.get()){
      Tuneage.mOrchestra.play();
      playPublisher.set(false);
      System.out.println("MusicPlayed");
    }

    if(restartSubscriber.get()){
      Command musicCom = new Tuneage(currentMusicFileSubscriber.get(), driveMotors, climberMotors);
      musicCom.schedule();
      System.out.println("restarted");
      restartPublisher.set(false);


    }
    if(Tuneage.mOrchestra.isPlaying() == false && prev == false && nextSongSubscriber.get()){
      musicIsFinishedPublisher.set(true);
      System.out.println("Music Finished");
      prev = true;
    }
  }
}
