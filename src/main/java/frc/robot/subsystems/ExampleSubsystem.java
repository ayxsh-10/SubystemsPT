// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ExampleSubsystem extends SubsystemBase implements Reportable{
  private final TalonFX motor;

  public ExampleSubsystem() {
    motor = new TalonFX(1);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
        motor.set(0.5);
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return true if motor is running at above 10% power (i think this code is right)
   */
  public boolean exampleCondition() {
    return Math.abs(motor.get()) > 0.1;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    reportToSmartDashboard(LOG_LEVEL.MINIMAL);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    reportToSmartDashboard(LOG_LEVEL.MEDIUM);
  }

  @Override
  public void reportToSmartDashboard(LOG_LEVEL priority){
    switch (priority){
      case OFF:
        break;
      case ALL:
      SmartDashboard.putNumber("Climb Position", motor.getPosition().getValueAsDouble());
      SmartDashboard.putNumber("Motor Temp", motor.getDeviceTemp().getValueAsDouble());
      SmartDashboard.putNumber("Climb Voltage", motor.getMotorVoltage().getValueAsDouble());
      case MEDIUM:
      SmartDashboard.putNumber("Motor Temp", motor.getDeviceTemp().getValueAsDouble());
      SmartDashboard.putNumber("Climb Voltage", motor.getMotorVoltage().getValueAsDouble());
      case MINIMAL:
      SmartDashboard.putNumber("Climb Position", motor.getPosition().getValueAsDouble());
    }
  }
  
  @Override
  public void initShuffleboard(LOG_LEVEL priority) {
      ShuffleboardTab tab = Shuffleboard.getTab("Example Subsystem");
      tab.addNumber("Climb Position", () -> motor.getPosition());
      tab.addNumber("Motor Temp", () -> motor.getDeviceTemp()).getValue());
      tab.addNumber("Climb Voltage", () -> motor.getTemperature());
  }
}

