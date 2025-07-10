package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathConstraints;

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

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.POSES;
import frc.robot.Constants.reefstate;
import frc.robot.commands.AlignmentLeftPeg;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.AlignmentRightPeg;
import frc.robot.commands.autoshootlfour;
import frc.robot.commands.climberCom;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
import frc.robot.commands.elevatorCom;
import frc.robot.commands.hopperCom;
import frc.robot.commands.manualElevate;
import frc.robot.commands.removalcom;
import frc.robot.subsystems.OneShotButton;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.algeremover;
import frc.robot.subsystems.climber;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.elevator;
import frc.robot.subsystems.hopper;
import frc.robot.subsystems.limelightalign;
import frc.robot.subsystems.sensorsandleds;

import java.security.DrbgParameters.NextBytes;
import java.util.HashMap;

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
  /* Auto Chooser */
  private final SendableChooser<Command> autoChooser;

  /* Controllers */
  private final Joystick driver = new Joystick(0);
  private final Joystick copilot = new Joystick(1);

  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

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
  private final Swerve s_Swerve = new Swerve();
  private final climber s_ClimberCom = new climber();
  private final coral s_CoralCom = new coral();
  private final elevator s_ElevatorCom = new elevator();
  // private final elevator s_ = new elevator();
  private final hopper s_HopperCom = new hopper();
  private final algeremover s_algieCom = new algeremover();
  // private final sensorsandleds s_ledCom = new sensorsandleds();

  private final OneShotButton PAbtn = new OneShotButton("PAbtn", POSES.REEF_A);
  private final OneShotButton PBbtn = new OneShotButton("PBbtn", POSES.REEF_B);
  private final OneShotButton PCbtn = new OneShotButton("PCbtn", POSES.REEF_C);
  private final OneShotButton PDbtn = new OneShotButton("PDbtn", POSES.REEF_D);
  private final OneShotButton PEbtn = new OneShotButton("PEbtn", POSES.REEF_F);
  private final OneShotButton PFbtn = new OneShotButton("PFbtn", POSES.REEF_E);
  private final OneShotButton PGbtn = new OneShotButton("PGbtn", POSES.REEF_G);
  private final OneShotButton PHbtn = new OneShotButton("PHbtn", POSES.REEF_H);
  private final OneShotButton PIbtn = new OneShotButton("PIbtn", POSES.REEF_I);
  private final OneShotButton PJbtn = new OneShotButton("PJbtn", POSES.REEF_J);
  private final OneShotButton PKbtn = new OneShotButton("PKbtn", POSES.REEF_K);
  private final OneShotButton PLbtn = new OneShotButton("PLbtn", POSES.REEF_L);

  private final limelightalign limelightalign = new limelightalign();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    s_Swerve.setDefaultCommand(
        new TeleopSwerve(
            s_Swerve,
            () -> -driver.getRawAxis(translationAxis),
            () -> -driver.getRawAxis(strafeAxis),
            () -> -driver.getRawAxis(rotationAxis),
            () -> robotCentric.getAsBoolean()));

    // Configure the button bindings
    configureButtonBindings();

    NamedCommands.registerCommand("coral", new Intake(-.075, s_CoralCom).withTimeout(.5));
    NamedCommands.registerCommand("coralfast", new Intake(-.075, s_CoralCom).withTimeout(.15));
    NamedCommands.registerCommand("coralplace", new Shoot(-0.12, s_CoralCom).withTimeout(.3));
    NamedCommands.registerCommand("lfour", new elevatorCom(3, s_ElevatorCom, false).withTimeout(2));
    NamedCommands.registerCommand("lfourdown", new elevatorCom(3, s_ElevatorCom, true).withTimeout(2.2));
    NamedCommands.registerCommand("l3", new elevatorCom(2, s_ElevatorCom, true).withTimeout(1));
    NamedCommands.registerCommand("l2", new elevatorCom(1, s_ElevatorCom, true).withTimeout(1));
    NamedCommands.registerCommand("lfourfast", new elevatorCom(3, s_ElevatorCom, false).withTimeout(1.682));
    NamedCommands.registerCommand("lfourcorrect",
        new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false).withTimeout(2));

    // Auto Chooser
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    // SmartDashboard.putBooleanArray("reefl4", reefstate.reefl4);
    SmartDashboard.getBoolean("reefl4", reefstate.reefl4[0]);

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

    /* Driver Buttons */
    zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));

    align.whileTrue(new AlignmentLeftPeg(s_Swerve));
    alignr.whileTrue(new AlignmentRightPeg(s_Swerve));

    climberButton.whileTrue(new climberCom(-0.4, s_ClimberCom));
    rclimberButton.whileTrue(new climberCom(-0.2, s_ClimberCom));

    intakeButton.whileTrue(new Intake(-.07, s_CoralCom));
    reverseCoral.whileTrue(new Shoot(-0.125, s_CoralCom));

    l4Button.whileTrue(new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false));
    l4Button.onFalse(new autoshootlfour(0, s_ElevatorCom, s_CoralCom, true));
    shootButton.whileTrue(new Shoot(0.07, s_CoralCom));

    uphopButton.whileTrue(new hopperCom(0.5, s_HopperCom));
    downhopButton.whileTrue(new hopperCom(-.5, s_HopperCom));

    acuatorin.whileTrue(new removalcom(-1, s_algieCom));
    acuatorout.whileTrue(new removalcom(1, s_algieCom));

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
    // manual.whileTrue(new removalcom(.5,s_algieCom, copilot));
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
    PathConstraints constraints = new PathConstraints(
        5,
        3,
        4,
        3);

    String startString = "I-4-0";
    String[] stringArr = startString.split("-");
    Command cmd = Commands.none();
    Command parralelCmd = Commands.none();
    Command nextCommand = Commands.none();
    Boolean currentParralel = false;
    for (String a : stringArr) {

      if (a.contains("4")) {
        nextCommand = new autoshootlfour(-.12, s_ElevatorCom, s_CoralCom, false).withTimeout(2);
      } else if (a.contains("I")) {
        // cmd = cmd.andThen(Commands.runOnce(() -> System.out.println(a)));
        nextCommand = new Intake(-.07, s_CoralCom);

      } else if (a.contains("S")) {
        nextCommand =(new Shoot(-.12, s_CoralCom));

      } else if (a.contains("3")) {
        nextCommand =new elevatorCom(2, s_ElevatorCom, false);
      } else if (a.contains("2")) {
        nextCommand =new elevatorCom(1, s_ElevatorCom, false);
      } else if (a.contains("0")) {
        nextCommand =new elevatorCom(1, s_ElevatorCom, true);
      } else if (a.matches("[A-L]")) {
        nextCommand = AutoBuilder.pathfindToPose(
            poses.get(a),
            constraints,
            0.00);
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
