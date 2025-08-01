// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Touchboard;

import edu.wpi.first.networktables.*;

/** Add your docs here. */
public class posePlotterUtil {
    StringSubscriber string_Sub;
    NetworkTableInstance inst = NetworkTableInstance.getDefault(); // may cause issues (hasnt so far)
    NetworkTable datatable = inst.getTable("touchboard");

    public String getAutoString() {
        string_Sub = datatable.getStringTopic("posePlotterFinalString").subscribe("NA");

        String currentString = string_Sub.get();

        return currentString;

    }

    public int stringStatus() {
        string_Sub = datatable.getStringTopic("posePlotterFinalString").subscribe("NA");
        String currentString = string_Sub.get();
        if (currentString == "NA") {
            return 404;
        } else if (currentString == "unset") {
            return 204;
        } else {
            return 200;

        }
    }

    public String getAutoStringWithFallback() {

        string_Sub = datatable.getStringTopic("posePlotterFinalString").subscribe("3+-I-4S-0+-LM-T-3+-K-4S-0+-LB-T-3+-L-4S-0");

        String currentString = string_Sub.get();

        return currentString;

    }
}
