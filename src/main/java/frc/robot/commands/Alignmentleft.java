// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.logging.Logger;

//import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;
//import frc.robot.subsystems.limelightalign;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Alignmentleft extends Command {
  Swerve s_Swerve;
  PIDController pidx;
  PIDController pidy;
  PIDController pidrotation;
  double tagID = -1;
  boolean isrightscore;
  public boolean atRSetpoint;
  public double rotationOutput;

  /** Creates a new Alignment. */
  public Alignmentleft(Boolean isrightscore, Swerve s_Swerve) {
    pidx = new PIDController(3, 0.07, 0);
    pidy = new PIDController(3, 0.07, 0);
  pidrotation = new PIDController(0.1, 00, 0);
    addRequirements(s_Swerve);
    this.s_Swerve = s_Swerve;
    this.isrightscore = isrightscore;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidx.setSetpoint(-.20);
    pidx.setTolerance(.01);
    
    pidy.setSetpoint(-.45);
    pidy.setTolerance(.015);

    pidrotation.setSetpoint(-1.3);
    pidrotation.setTolerance(.3);

    if (LimelightHelpers.getTV("limelight-right")){
      tagID = LimelightHelpers.getFiducialID("limelight-right");
    }else{
      s_Swerve.drive(new Translation2d(0,0),0,false,false);
    }

  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(LimelightHelpers.getTV("limelight-right") && LimelightHelpers.getFiducialID("limelight-right") == tagID){
      
    double [] position = LimelightHelpers.getBotPose_TargetSpace("limelight-right");
    //LimelightHelpers.getBotPose_TargetSpace("limelight-right");
    double[] limelight2pos = LimelightHelpers.getBotPose_TargetSpace("");
    //Logger.recordOutput(" rotate",position[4]);
    // double[] positionr = LimelightHelpers.getTargetPose_CameraSpace("limelight-right");



    double xSpeed = -pidx.calculate(position[0]);
    double ySpeed = -pidy.calculate(position[2]);
    // double rotValue = -pidrotation.calculate(position[4]);

    boolean atXSetpoint = Math.abs(-position[0] - .20) <= .01;
    boolean atYSetpoint = Math.abs(-position[2] -.45) <= .015;
    // boolean atRSetpoint = Math.abs(-positionr[4] - 1.3) <= .3;
    
      if (!atXSetpoint && !atYSetpoint){
        s_Swerve.drive(new Translation2d(
        xSpeed,
        ySpeed),
        0,
       false,false);
      // }else if(!atXSetpoint && !atYSetpoint){
      //   s_Swerve.drive(new Translation2d(xSpeed,ySpeed), 0, false,false);
      // }else if(!atXSetpoint && !atRSetpoint){
      //   s_Swerve.drive(new Translation2d(xSpeed,0), rotValue, false,false);
      // }else if(!atYSetpoint && !atRSetpoint){
      //   s_Swerve.drive(new Translation2d(0,ySpeed), rotValue, false,false);
      }else if(!atXSetpoint){
        s_Swerve.drive(new Translation2d(xSpeed,0), 0, false,false);
      }else if(!atYSetpoint){
        s_Swerve.drive(new Translation2d(0,ySpeed), 0, false,false);
      // }else if(!atRSetpoint){
      //   s_Swerve.drive(new Translation2d(0,0), rotValue, false,false);
      }else{
        s_Swerve.drive(new Translation2d(0,0), 0, false,false);}}
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
