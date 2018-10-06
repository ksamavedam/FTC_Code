# Project Title

Omnibot Program - This is our team's template.

## Description
This program controls Flamangos Omnibot. Each project contains many files each with clearly i

#### Hardware Class
**OmnibotHw.java**  - Contains all HW definitions and basic initialization. This class contains public members for each of the HW components of the robot and are initialized with neccessary defaults. 
See this example: 

```
    /* Public OpMode members. */
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
	public DcMotor  bottomDrive = null;
    public DcMotor  topDrive    = null;

    public DcMotor  leftArm     = null;
    public Servo    leftClaw    = null;
    public Servo    rightClaw   = null;
    public BNO055IMU imu        = null;
```

Mapping HW object to RevHub Name is critical. When you configure the RevHub, give names that are appropriate and same or very similar name should be used in the code also. 

See the example in which RevHub DC motor name is "left_drive" and class variable is leftDrive
```
leftDrive  = hwMap.get(DcMotor.class, "left_drive");
```
#### Autonomous Class

**OmnibotAuto.java**  - Contains Autonomous code - Goes forward 12 inches and turns
This line identifies mode and gives it a name. Include your initials in the title so that it clearly identifies who is working on the program. 
```
@Autonomous(name="OmniBot: AutoDrive KS-DP", group="OmniBot")
```
# Project Title

Omnibot Program - This is our team's template.

## Description
This program controls Flamangos Omnibot. Each project contains many files each with clearly i

#### Hardware Class
**OmnibotHw.java**  - Contains all HW definitions and basic initialization. This class contains public members for each of the HW components of the robot and are initialized with neccessary defaults. 
See this example: 

```
    /* Public OpMode members. */
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
	public DcMotor  bottomDrive = null;
    public DcMotor  topDrive    = null;

    public DcMotor  leftArm     = null;
    public Servo    leftClaw    = null;
    public Servo    rightClaw   = null;
    public BNO055IMU imu        = null;
```

Mapping HW object to RevHub Name is critical. When you configure the RevHub, give names that are appropriate and same or very similar name should be used in the code also. 

See the example in which RevHub DC motor name is "left_drive" and class variable is leftDrive
```
leftDrive  = hwMap.get(DcMotor.class, "left_drive");
```
#### Autonomous Class

**OmnibotAuto.java**  - Contains Autonomous code - Goes forward 12 inches and turns
This line identifies mode and gives it a name. Include your initials in the title so that it clearly identifies who is working on the program. 
```
@Autonomous(name="OmniBot: AutoDrive KS-DP", group="OmniBot")
```

**Class Anatomy**

```
    public void runOpMode() {
    ....
    ....
            robot.init(hardwareMap);
    ....    // Other init stuff
    ....
            waitForStart();
    ....    
    ....    // Autonomous logic
    }
```
