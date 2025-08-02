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
import frc.robot.commands.Tuneage;
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

import frc.robot.subsystems.Touchboard.AxisKnob;
import frc.robot.subsystems.Touchboard.JukeboxUtil;
import frc.robot.subsystems.Touchboard.OneShotButton;
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
  // private final POVButton leftcoralalign = new POVButton(driver, 270);
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

  private final posePlotterUtil posePlotterValues = new posePlotterUtil();

  private final OneShotButton PAbtn = new OneShotButton("PAbtn", new AlignToPose(POSES.REEF_A, drivetrain));
  private final OneShotButton PBbtn = new OneShotButton("PBbtn", new AlignToPose(POSES.REEF_B, drivetrain));
  private final OneShotButton PCbtn = new OneShotButton("PCbtn", new AlignToPose(POSES.REEF_C, drivetrain));
  private final OneShotButton PDbtn = new OneShotButton("PDbtn", new AlignToPose(POSES.REEF_D, drivetrain));
  private final OneShotButton PEbtn = new OneShotButton("PEbtn", new AlignToPose(POSES.REEF_E, drivetrain));
  private final OneShotButton PFbtn = new OneShotButton("PFbtn", new AlignToPose(POSES.REEF_F, drivetrain));
  private final OneShotButton PGbtn = new OneShotButton("PGbtn", new AlignToPose(POSES.REEF_G, drivetrain));
  private final OneShotButton PHbtn = new OneShotButton("PHbtn", new AlignToPose(POSES.REEF_H, drivetrain));
  private final OneShotButton PIbtn = new OneShotButton("PIbtn", new AlignToPose(POSES.REEF_I, drivetrain));
  private final OneShotButton PJbtn = new OneShotButton("PJbtn", new AlignToPose(POSES.REEF_J, drivetrain));
  private final OneShotButton PKbtn = new OneShotButton("PKbtn", new AlignToPose(POSES.REEF_K, drivetrain));
  private final OneShotButton PLbtn = new OneShotButton("PLbtn", new AlignToPose(POSES.REEF_L, drivetrain));


  private final OneShotButton PLTbtm = new OneShotButton("LTbtn", new AlignToPose(StationPOSES.Left_top_station, drivetrain));
  private final OneShotButton PLMbtn = new OneShotButton("LMbtn", new AlignToPose(StationPOSES.Left_mid_station, drivetrain));
  private final OneShotButton PLBbtn = new OneShotButton("LBbtn", new AlignToPose(StationPOSES.Left_bot_station, drivetrain));
  private final OneShotButton PRTbtn = new OneShotButton("RTbtn", new AlignToPose(StationPOSES.Right_top_station, drivetrain));
  private final OneShotButton PRMbtn = new OneShotButton("RMbtn", new AlignToPose(StationPOSES.Right_mid_station, drivetrain));
  private final OneShotButton PRBbtn = new OneShotButton("RBbtn", new AlignToPose(StationPOSES.Right_bot_station, drivetrain));

  private final limelightalign limelightalign = new limelightalign();

  private final ActionButton shooterButtonAction = new ActionButton("Shoot", new Shoot(-0.07, s_CoralCom));
  private final ActionButton reverseButtonAction = new ActionButton("RevIntake", new Shoot(0.14, s_CoralCom));
  private final ActionButton IntakeButtonAction = new ActionButton("Intake", new Intake(-0.07, s_CoralCom));


  private final DoubleActionButton L4btn = new DoubleActionButton("L4btn", new elevatorCom(3, s_ElevatorCom, false), new elevatorCom(3, s_ElevatorCom, true));
  private final DoubleActionButton L3btn = new DoubleActionButton("L3btn", new elevatorCom(2, s_ElevatorCom, false), new elevatorCom(2, s_ElevatorCom, true));
  private final DoubleActionButton L2btn = new DoubleActionButton("L2btn", new elevatorCom(1, s_ElevatorCom, false), new elevatorCom(1, s_ElevatorCom, true));

  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  private final AxisKnob AlgaeAxis = new AxisKnob("ActuatorAxis");
  
  public RobotContainer() {
    // Note that X is defined as forward according to WPILib convention,
    // and Y is defined as to the left according to WPILib convention.
    
    AlgaeAxis.setCommand(()-> new removalcom(AlgaeAxis.getValue(), s_algaeCom));
    // Configure the button bindings
    configureButtonBindings();


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

    new JukeboxUtil(drivetrain, s_ClimberCom);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // return new exampleAuto(s_Swerve);
    // return new PathPlannerAuto("example");
    // return autoChooser.getSelected();
    HashMap<String, Pose2d> poses = new HashMap<String, Pose2d>();

    poses.put("A", POSES.REEF_A);
    poses.put("B", POSES.REEF_B);
    poses.put("C", POSES.REEF_C);
    poses.put("D", POSES.REEF_D);
    poses.put("E", POSES.REEF_E);
    poses.put("F", POSES.REEF_F);
    poses.put("G", POSES.REEF_G);
    poses.put("H", POSES.REEF_H);
    poses.put("I", POSES.REEF_I);
    poses.put("J", POSES.REEF_J);
    poses.put("K", POSES.REEF_K);
    poses.put("L", POSES.REEF_L);

    poses.put("LT", StationPOSES.Left_top_station);
    poses.put("LM", StationPOSES.Left_mid_station);
    poses.put("LB", StationPOSES.Left_bot_station);
    poses.put("RT", StationPOSES.Right_top_station);
    poses.put("RM", StationPOSES.Right_mid_station);
    poses.put("RB", StationPOSES.Right_bot_station);

    if (DriverStation.getAlliance().isPresent()) {
      if (DriverStation.getAlliance().get() == Alliance.Red) {
        System.out.println("red");
        poses.put("A", FlippingUtil.flipFieldPose(POSES.REEF_A));
        poses.put("B", FlippingUtil.flipFieldPose(POSES.REEF_B));
        poses.put("C", FlippingUtil.flipFieldPose(POSES.REEF_C));
        poses.put("D", FlippingUtil.flipFieldPose(POSES.REEF_D));
        poses.put("E", FlippingUtil.flipFieldPose(POSES.REEF_E));
        poses.put("F", FlippingUtil.flipFieldPose(POSES.REEF_F));
        poses.put("G", FlippingUtil.flipFieldPose(POSES.REEF_G));
        poses.put("H", FlippingUtil.flipFieldPose(POSES.REEF_H));
        poses.put("I", FlippingUtil.flipFieldPose(POSES.REEF_I));
        poses.put("J", FlippingUtil.flipFieldPose(POSES.REEF_J));
        poses.put("K", FlippingUtil.flipFieldPose(POSES.REEF_K));
        poses.put("L", FlippingUtil.flipFieldPose(POSES.REEF_L));

        poses.put("LT", FlippingUtil.flipFieldPose(StationPOSES.Left_top_station));
        poses.put("LM", FlippingUtil.flipFieldPose(StationPOSES.Left_mid_station));
        poses.put("LB", FlippingUtil.flipFieldPose(StationPOSES.Left_bot_station));
        poses.put("RT", FlippingUtil.flipFieldPose(StationPOSES.Right_top_station));
        poses.put("RM", FlippingUtil.flipFieldPose(StationPOSES.Right_mid_station));
        poses.put("RB", FlippingUtil.flipFieldPose(StationPOSES.Right_bot_station));
      }
    }

    PathConstraints constraints = new PathConstraints(
        3,
        2,
        4,
        3);
    // new PathConstraints(null, null, null, null)

    String startString = posePlotterValues.getAutoString();
    // String startString = posePlotterValues.getAutoStringWithFallback(); //ENABLE
    // FOR COMP I SWEAR PLEASE ENABLE FOR COMP YOU WILL FORGET SO
    // ENABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    System.out.println(startString);
    String[] stringArr = startString.split("-");
    Command cmd = Commands.none();
    Command parralelCmd = Commands.none();
    Command nextCommand = Commands.none();
    Boolean currentParralel = false;
    for (String a : stringArr) {

      if (a.contains("4S")) {
        nextCommand = new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false).withTimeout(2);
      } else if (a.contains("4")) {
        nextCommand = new elevatorCom(3, s_ElevatorCom, false);
      } else if (a.contains("T")) {
        // cmd = cmd.andThen(Commands.runOnce(() -> System.out.println(a)));
        nextCommand = new Intake(-.07, s_CoralCom);

      } else if (a.contains("S")) {
        nextCommand = (new Shoot(-.12, s_CoralCom));

      } else if (a.contains("3")) {
        nextCommand = new elevatorCom(2, s_ElevatorCom, false);
      } else if (a.contains("2")) {
        nextCommand = new elevatorCom(1, s_ElevatorCom, false);
      } else if (a.contains("0")) {
        nextCommand = new elevatorCom(1, s_ElevatorCom, true);
      } else if (a.matches("[A-L]")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      } else if (a.contains("LT")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      } else if (a.contains("LM")) {
        System.out.println(a);
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      } else if (a.contains("LB")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      } else if (a.contains("RT")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      } else if (a.contains("RM")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      } else if (a.contains("RB")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
      }

      else if (stringArr.length == 0) {
        System.out.println("No Command!! Consider turning on fallback if at competition!!!!!!!!!!!!!!!");
      } else {
        System.out.println(a + "UNDEFINED COMMAND");
        nextCommand = Commands.none();
      }

      if (a.contains("+")) {
        // cmd = cmd.andThen(Commands.runOnce(()->System.out.println(a +
        // "Simultaneous")));
        parralelCmd = parralelCmd.alongWith(nextCommand);
        currentParralel = true;

        continue;
      } else if (currentParralel) {
        parralelCmd = parralelCmd.alongWith(nextCommand);
        cmd = cmd.andThen(parralelCmd);
        parralelCmd = Commands.none();
        currentParralel = false;
      } else {
        cmd = cmd.andThen(nextCommand);
      }
    }
    ;
    return cmd;
  }
}
