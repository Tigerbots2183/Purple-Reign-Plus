package frc.robot;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
// import com.pathplanner.lib.auto.NamedCommands;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.POSES;
import frc.robot.Constants.StationPOSES;
import frc.robot.Constants.Swerve;
import frc.robot.Constants.reefstate;
import frc.robot.commands.AlignToPose;
import frc.robot.commands.AlignmentLeftPeg;

import frc.robot.commands.AlignmentRightPeg;
import frc.robot.commands.autoshootlfour;
import frc.robot.commands.climberCom;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
import frc.robot.commands.elevatorCom;
import frc.robot.commands.hopperCom;
import frc.robot.commands.manualElevate;
import frc.robot.commands.removalcom;
import frc.robot.commands.AlignToPose;

import frc.robot.subsystems.algaeremover;
import frc.robot.subsystems.climber;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;
import frc.robot.subsystems.hopper;
import frc.robot.subsystems.limelightalign;
import frc.robot.subsystems.sensorsandleds;
import frc.robot.subsystems.Touchboard.ActionButton;
import frc.robot.subsystems.Touchboard.DoubleActionButton;
import frc.robot.subsystems.Touchboard.Dropdown;
import frc.robot.subsystems.Touchboard.AxisKnob;
import frc.robot.subsystems.Touchboard.JukeboxUtil;
import frc.robot.subsystems.Touchboard.NumberComponent;
import frc.robot.subsystems.Touchboard.OneShotButton;
import frc.robot.subsystems.Touchboard.ToggleButton;
import frc.robot.subsystems.Touchboard.posePlotterUtil;

import java.security.DrbgParameters.NextBytes;
import java.util.HashMap;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
  private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max
                                                                                    // angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private final CommandXboxController joystick = new CommandXboxController(0);

  public CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
  /* Auto Chooser */
  // private final SendableChooser<Command> autoChooser;

  /* Controllers */
  private final Joystick driver = new Joystick(0);
  private final Joystick copilot = new Joystick(1);

  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  

  /* Driver Buttons */
  // private final JoystickButton setHeading = new JoystickButton(driver,
  // XboxController.Button.kY.value);
  private final JoystickButton setHeading = new JoystickButton(driver, XboxController.Button.kY.value);

  private final JoystickButton climberButton = new JoystickButton(driver, 2);
  private final JoystickButton rclimberButton = new JoystickButton(driver, 3);

  private final JoystickButton intakeButton = new JoystickButton(copilot, 6);
  private final JoystickButton reverseCoral = new JoystickButton(copilot, 5);
  // private final JoystickButton onecoralButton = new JoystickButton(copilot, 7);
  private final JoystickButton shootButton = new JoystickButton(copilot, 8);

  private final JoystickButton l2Button = new JoystickButton(copilot, 2);
  private final JoystickButton l3Button = new JoystickButton(copilot, 3);
  private final JoystickButton l4Button = new JoystickButton(copilot, 4);

  private final POVButton uphopButton = new POVButton(driver, 0);
  private final POVButton downhopButton = new POVButton(driver, 180);
  // private final POVButton removeoutButton = new POVButton(driver, 90);
  // private final POVButton removeinButton = new POVButton(driver, 270);

  private final JoystickButton align = new JoystickButton(driver, 5);
  private final JoystickButton alignr = new JoystickButton(driver, 6);
  // private final JoystickButton autodrive = new JoystickButton(driver, 3);

  private final JoystickButton manual = new JoystickButton(copilot, 1);
  private final Trigger acuatorin = new Trigger(() -> copilot.getRawAxis(0) > .2);
  private final Trigger acuatorout = new Trigger(() -> copilot.getRawAxis(0) < -.2);

  /* Subsystems */
  // private final Swerve s_Swerve = new Swerve();
  private final climber s_ClimberCom = new climber();
  private final coral s_CoralCom = new coral();
  private final elevator s_ElevatorCom = new elevator();
  // private final elevator s_ = new elevator();
  private final hopper s_HopperCom = new hopper();
  private final algaeremover s_algaeCom = new algaeremover();
  public final sensorsandleds s_ledCom = new sensorsandleds();

  private final OneShotButton PAbtn = new OneShotButton("PAbtn",() -> new AlignToPose(POSES.REEF_A, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PBbtn = new OneShotButton("PBbtn",() -> new AlignToPose(POSES.REEF_B, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PCbtn = new OneShotButton("PCbtn",() -> new AlignToPose(POSES.REEF_C, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PDbtn = new OneShotButton("PDbtn",() -> new AlignToPose(POSES.REEF_D, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PEbtn = new OneShotButton("PEbtn",() -> new AlignToPose(POSES.REEF_E, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PFbtn = new OneShotButton("PFbtn",() -> new AlignToPose(POSES.REEF_F, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PGbtn = new OneShotButton("PGbtn",() -> new AlignToPose(POSES.REEF_G, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PHbtn = new OneShotButton("PHbtn",() -> new AlignToPose(POSES.REEF_H, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PIbtn = new OneShotButton("PIbtn",() -> new AlignToPose(POSES.REEF_I, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PJbtn = new OneShotButton("PJbtn",() -> new AlignToPose(POSES.REEF_J, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PKbtn = new OneShotButton("PKbtn",() -> new AlignToPose(POSES.REEF_K, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  private final OneShotButton PLbtn = new OneShotButton("PLbtn",() -> new AlignToPose(POSES.REEF_L, drivetrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));


  private final OneShotButton PLTbtm = new OneShotButton("LTbtn",() -> new AlignToPose(StationPOSES.Left_top_station, drivetrain));
  private final OneShotButton PLMbtn = new OneShotButton("LMbtn",() -> new AlignToPose(StationPOSES.Left_mid_station, drivetrain));
  private final OneShotButton PLBbtn = new OneShotButton("LBbtn",() -> new AlignToPose(StationPOSES.Left_bot_station, drivetrain));
  private final OneShotButton PRTbtn = new OneShotButton("RTbtn",() -> new AlignToPose(StationPOSES.Right_top_station, drivetrain));
  private final OneShotButton PRMbtn = new OneShotButton("RMbtn",() -> new AlignToPose(StationPOSES.Right_mid_station, drivetrain));
  private final OneShotButton PRBbtn = new OneShotButton("RBbtn",() -> new AlignToPose(StationPOSES.Right_bot_station, drivetrain));

  private final limelightalign limelightalign = new limelightalign();

  private final ActionButton shooterButtonAction = new ActionButton("Shoot", new Shoot(-0.07, s_CoralCom));
  private final ActionButton reverseButtonAction = new ActionButton("RevIntake", new Shoot(0.14, s_CoralCom));
  private final ActionButton IntakeButtonAction = new ActionButton("Intake", new Intake(-0.07, s_CoralCom));


  private final DoubleActionButton L4btn = new DoubleActionButton("L4btn", new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false), new elevatorCom(3, s_ElevatorCom, true));
  private final DoubleActionButton L3btn = new DoubleActionButton("L3btn", new elevatorCom(2, s_ElevatorCom, false), new elevatorCom(2, s_ElevatorCom, true));
  private final DoubleActionButton L2btn = new DoubleActionButton("L2btn", new elevatorCom(1, s_ElevatorCom, false), new elevatorCom(1, s_ElevatorCom, true));

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  private final AxisKnob AlgaeAxis = new AxisKnob("ActuatorAxis");

  public RobotContainer() {
    // Note that X is defined as forward according to WPILib convention,
    // and Y is defined as to the left according to WPILib convention.

    // Configure the button bindings
    configureButtonBindings();

    posePlotterUtil.addCommandPair("A", ()-> new AlignToPose( POSES.REEF_A, drivetrain));
    posePlotterUtil.addCommandPair("B", ()-> new AlignToPose( POSES.REEF_B, drivetrain));
    posePlotterUtil.addCommandPair("C", ()-> new AlignToPose( POSES.REEF_C, drivetrain));
    posePlotterUtil.addCommandPair("D", ()-> new AlignToPose( POSES.REEF_D, drivetrain));
    posePlotterUtil.addCommandPair("E", ()-> new AlignToPose( POSES.REEF_E, drivetrain));
    posePlotterUtil.addCommandPair("F", ()-> new AlignToPose( POSES.REEF_F, drivetrain));
    posePlotterUtil.addCommandPair("G", ()-> new AlignToPose( POSES.REEF_G, drivetrain));
    posePlotterUtil.addCommandPair("H", ()-> new AlignToPose( POSES.REEF_H, drivetrain));
    posePlotterUtil.addCommandPair("I", ()-> new AlignToPose( POSES.REEF_I, drivetrain));
    posePlotterUtil.addCommandPair("J", ()-> new AlignToPose( POSES.REEF_J, drivetrain));
    posePlotterUtil.addCommandPair("K", ()-> new AlignToPose( POSES.REEF_K, drivetrain));
    posePlotterUtil.addCommandPair("L", ()-> new AlignToPose( POSES.REEF_L, drivetrain));
    posePlotterUtil.addCommandPair("LT", ()-> new AlignToPose( StationPOSES.Left_top_station, drivetrain));
    posePlotterUtil.addCommandPair("LM", ()-> new AlignToPose( StationPOSES.Left_mid_station, drivetrain));
    posePlotterUtil.addCommandPair("LB", ()-> new AlignToPose( StationPOSES.Left_bot_station, drivetrain));
    posePlotterUtil.addCommandPair("RT", ()-> new AlignToPose( StationPOSES.Right_top_station, drivetrain));
    posePlotterUtil.addCommandPair("RM", ()-> new AlignToPose( StationPOSES.Right_mid_station, drivetrain));
    posePlotterUtil.addCommandPair("RB", ()-> new AlignToPose( StationPOSES.Right_bot_station, drivetrain));
    posePlotterUtil.addCommandPair("4S", ()-> new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false).withTimeout(2));
    posePlotterUtil.addCommandPair("4", ()-> new elevatorCom(3, s_ElevatorCom, false));
    posePlotterUtil.addCommandPair("3", ()-> new elevatorCom(2, s_ElevatorCom, false));
    posePlotterUtil.addCommandPair("2", ()-> new elevatorCom(1, s_ElevatorCom, false));
    posePlotterUtil.addCommandPair("0", ()-> new elevatorCom(3, s_ElevatorCom, true));
    posePlotterUtil.addCommandPair("T", ()-> new Intake(-.07, s_CoralCom));
    posePlotterUtil.addCommandPair("S", ()-> new Shoot(-.12, s_CoralCom));

    JukeboxUtil jukebox = new JukeboxUtil();
    jukebox.addTalon(drivetrain.getModule(0).getDriveMotor());
    jukebox.addTalon(drivetrain.getModule(1).getDriveMotor());
    jukebox.addTalon(drivetrain.getModule(2).getDriveMotor());
    jukebox.addTalon(drivetrain.getModule(3).getDriveMotor());

    jukebox.addTalon(drivetrain.getModule(0).getSteerMotor());
    jukebox.addTalon(drivetrain.getModule(1).getSteerMotor());
    jukebox.addTalon(drivetrain.getModule(2).getSteerMotor());
    jukebox.addTalon(drivetrain.getModule(3).getSteerMotor());

    jukebox.addTalon(s_ClimberCom.GetLeftKraken());
    jukebox.addTalon(s_ClimberCom.GetRightKraken());

    

    
    // import miracle.java
    // SmartDashboard.putData(reefl4, reefstate.reefl4);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    AlgaeAxis.setCommand(()-> new removalcom(AlgaeAxis.getValue(), s_algaeCom));
    // Note that X is defined as forward according to WPILib convention,
    // and Y is defined as to the left according to WPILib convention.
    drivetrain.setDefaultCommand(
        // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    // Idle while the robot is disabled. This ensures the configured
    // neutral mode is applied to the drive motors while disabled.
    final var idle = new SwerveRequest.Idle();
    RobotModeTriggers.disabled().whileTrue(
        drivetrain.applyRequest(() -> idle).ignoringDisable(true));


    // reset the field-centric heading on left bumper press


    setHeading.onTrue(drivetrain.runOnce(()-> {
      if (DriverStation.getAlliance().isPresent()) {
        if (DriverStation.getAlliance().get() == Alliance.Red) {
          drivetrain.resetRotation(Rotation2d.fromDegrees(90));
        }else{
          drivetrain.resetRotation(Rotation2d.fromDegrees(-90));

        }
      }else{
        drivetrain.resetRotation(Rotation2d.fromDegrees(-90));

      }
    }).ignoringDisable(true));
        
    
    
    //.onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

    drivetrain.registerTelemetry(logger::telemeterize);

    // climberButton.whileTrue(new climberCom(IntakeTestButton.getValue(), s_ClimberCom));
    // climberButton.whileTrue(new Intake(()->IntakeTestButton.value, s_CoralCom));

    intakeButton.whileTrue(new Intake(-.07, s_CoralCom));
    reverseCoral.whileTrue(new Shoot(-0.125, s_CoralCom));

    l4Button.whileTrue(new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false));
    l4Button.onFalse(new autoshootlfour(0, s_ElevatorCom, s_CoralCom, true));
    shootButton.whileTrue(new Shoot(0.07, s_CoralCom));

    uphopButton.whileTrue(new hopperCom(0.5, s_HopperCom));
    downhopButton.whileTrue(new hopperCom(-.5, s_HopperCom));

    acuatorin.whileTrue(new removalcom(-1, s_algaeCom));
    acuatorout.whileTrue(new removalcom(1, s_algaeCom));

    // upButton.whileTrue(new elevatorCom(-0.4, 1, s_Elevator, false));
    l2Button.whileTrue(new elevatorCom(1, s_ElevatorCom, false));
    l2Button.onFalse(new elevatorCom(1, s_ElevatorCom, true));


    align.whileTrue(new AlignmentLeftPeg(drivetrain));
    alignr.whileTrue(new AlignmentRightPeg(drivetrain));
    

    // l3Button.whileTrue(new autoshootlthree(-.09,2, s_ElevatorCom,
    // s_CoralCom,false));
    // l3Button.onFalse(new autoshootlthree(0,2, s_ElevatorCom, s_CoralCom,true));

    l3Button.whileTrue(new elevatorCom(2, s_ElevatorCom, false));
    l3Button.onFalse(new elevatorCom(2, s_ElevatorCom, true));
    // downButton.onFalse(new elevatorCom(.2, 1, s_Elevator, true));

    // uphopButton.whileTrue(new hopperCom(-.5, s_HopperCom));
    // downhopButton.whileTrue(new hopperCom(.5, s_HopperCom));

    manual.whileTrue(new manualElevate(s_ElevatorCom, copilot));
    // manual2.whileTrue(new hopperCom(.5,s_HopperCom, copilot));
    // manual.whileTrue(new removalcom(.5,s_algaeCom, copilot));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    return posePlotterUtil.getAuto();
  }
}

  