// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.Enable5VRailValue;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase implements Reportable{
  private final TalonFX motor;

  private double desiredSpeed = 0.0;
  private boolean enabled = true;
  private TalonFXConfigurator motorConfigurator;
  private VelocityVoltage velocityRequest;
  private final NeutralOut neutralRequest = new NeutralOut();
  public ExampleSubsystem() {
    motor = new TalonFX(0);
    velocityRequest = new VelocityVoltage(0);

   motorConfigurator = motor.getConfigurator();

    setMotorConfigs();

    CommandScheduler.getInstance().registerSubsystem(this);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
    public void setMotorConfigs(){
      TalonFXConfiguration motorConfigs = new TalonFXConfiguration();

    }


  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem stathe, such as a digital sensor.
   */

  @Override
  public void periodic() {
    if (!enabled){
      return;
    }

    velocityRequest.Velocity = desiredSpeed;

    motor.setControl(velocityRequest);
  }
  public void setEnabled(boolean enabled){
    this.enabled = enabled;
    if(!enabled){
      motor.setControl(neutralRequest);
    } 

  }

  public boolean getEnabled() {
    return enabled;
  }

  public void setTargetSpeed(double speed){
    desiredSpeed = speed;
  }

  public double getSpeed(){
    return motor.getVelocity().getValueAsDouble();
  }

  public double getTargetSpeed(){
    return desiredSpeed;
  }

  public boolean atSpeed(){
    return motor.getVelocity().getValueAsDouble() > desiredSpeed;
  }

  public void reportToSmartDashboard(LOG_LEVEL priority){

  }

  @Override
  public void initShuffleboard(LOG_LEVEL priority){
    ShuffleboardTab tab = Shuffleboard.getTab("Motor");
    switch (priority){
      case OFF:
        break;
      case ALL:
      tab.addNumber("Motor Current Position", () -> getSpeed());
      tab.addNumber("Supply Current", () -> motor.getSupplyCurrent().getValueAsDouble());
      tab.addNumber("Feedforward", () -> motor.getClosedLoopFeedForward().getValueAsDouble());
      case MEDIUM:
      tab.addNumber("Motor Target Position", () -> getSpeed());
      tab.addBoolean("enabled", () -> getEnabled());
      case MINIMAL:
        tab.addNumber("Motor Voltage", () -> motor.getMotorVoltage().getValueAsDouble());
        tab.addNumber("Motor Temperature", () -> motor.getDeviceTemp().getValueAsDouble());
    }

  }
}
