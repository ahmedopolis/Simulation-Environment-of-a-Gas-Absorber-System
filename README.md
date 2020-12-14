# Description

The Absorption tower simulator was designed to enable users to determine the tower height of
an isothermal and isobaric packed absorption column, knowing a set of parameter. The system is
modelled as a solvent diffusing from a stagnant gas-side to a stagnant liquid-side. It is assumed
that the gas and liquid phase are immiscible. The diffusion coefficient, fluid density and viscosities
of the stream will not vary in the system. This simulator also has an optimization algorithm to
optimize the mas transfer in the gas-side and liquid-side by fluctuating the inlet liquid flow rate.
Simultaneously, an optimal liquid flow rate can obtained based on the criterion of minimizing the
difference the gas height and liquid height. This simulator is programmed Java. The following
features are built-in:

- The inputs for the program are done in a txt-file and datasets can be outputted via Exceloutput.
- The program is designed to utilize a set of numerical methods such as numerical integration (Trapezoidal Rule, Simpson’s Rule, Modified-Simpson’ Rule) and rootfinding like Ridder’s.
- The design of the simulator enables users to set parameters such as: recover percentage, liquid flow rates, gas flow rates, concentration of solute in the liquid and gas side, type of packing material.
- The user can also simulate various binary mixtures modelled by polynomial equations and allows them to manually change the values of the equilibrium relation between the liquid and vapour phase composition.

## Running the Simulator

The following section will describe how to setup the simulator in order for it to output results
based on the values that were inputted. Three Java library must be included for the program to output
data. This manual provides instruction for the DrJava an Eclipse.

1. Open the text file input.txt.
2. Input the values in the text editor and save. Please view Section 4 of this User Manual. The constraints for the inputs will be explained below. There are three choices that can be made by the user in order to choose the packing material of the absorption column. The layout for the text editor should mimic the following image.

![Input Image](/Images/Input-image.PNG "Input Image")

3. Open MainSimulator.java, compile and run.
4. Following step 4, the console should prompt you to input the file path ("Can you please provide the file path ...").
5. To obtain the input.txt file path (this also includes the name of the file). Open the folder containing all the java files.
6. The console should now ask you to find the output file path.
7. To obtain the Output.xls file path (this also includes the name of the file). Open the folder containing all the java files. Similarly, to how the input file path was found, get the output file path.
8. If the program completed properly the following message should be displayed on the console.

![Output Message Image](/Images/Output-message-image.PNG "Output Message Image")

## Input text file

1. In the file input.txt, choose between the three following packing materials:
   - 1 for Pall Ring
   - 2 for Raschig Ring
   - 3 for Berl Saddle
     Any inputs other than the above will be rejected.
2. Only positive values will be accepted as input for the inlet liquid flow rate (l_2) and the inlet gas flow rate (v_1) can be entered respectively.
3. The values of the inlet liquid-side solute composition (x_A2) and the inlet gas-side solute composition y_A1 must be in between 0 and 1, corresponding to the inlet mole fraction of A in the liquid and vapour streams respectively.
4. The values for recovery percentage must be above 0 and below 100, the recovery percentage must be entered without the percent sign following it.
5. In the case that a different binary mixture is used, enter the value for the equilibrium line.
   - equilbriumPolynomialCoefficient5 describes the coefficient of the liquid composition with a degree of 5.
   - equilbriumPolynomialCoefficient4 describes the coefficient of the liquid composition with a degree of 4.
   - equilbriumPolynomialCoefficient3 describes the coefficient of the liquid composition with a degree of 3.
   - equilbriumPolynomialCoefficient2 describes the coefficient of the liquid composition with a degree of 2.
   - equilbriumPolynomialCoefficient1 describes the coefficient of the liquid composition with a degree of 1.
   - equilbriumPolynomialCoefficient describes the coefficient of the liquid composition with a degree of 0.
6. The molecular weights of solute, liquid, and gas can be changed, as long as it remains a positive value. The same concept is applicable to the density of liquid and gas, and the viscosity of the liquid.
7. The file must be saved for the simulator to accept the values entered.
8. The simulation contain a class called “FilePathInput.java” contain a set of exception handling functions to catch the users typos or incorrect inputs.

## Simulation

1. Once the input file has been filled and accepted by the program, the simulator can be ran.
2. The entry point of this program is the following main class: “MainSimulator.java.”
3. Initially, it begins by accessing the class “MyInput.java”, which it contains function to parse, capture the values inputted by the user.
4. Using the parameters inputted in “myInput.java”, the remaining parameters will now be calculated. These parameters are required in order to find the height. This is found in the class called: “MyInputCalculation.java.”
5. To calculate the height differential, it requires the
6. The simulator proceeds to compute the height applying all three different integration methods, trapezoidal rule, Simpson’s rule, and the modified Simpson’s rule and using their corresponding classes: “TrapezoidSolver.java”, “SimpsonsSolver.java”, “ModifiedSimpsonsSolver.java”.
7. All numerical integration methods use the change in height for each iteration of the liquid stream composition through the class IterationProcedure.java.
8. The height differential calculated also needs to find the value of x_Ai, which is the liquid interface composition. This value is evaluated using one of the two root finding methods available: Ridder's method or Bisection method, and their corresponding classes Ridders.java and Bisection.java
9. RiddersSolver.java calls upon the method in order to find the root of the liquid interface composition accordingly.

## Output

There are two ways that this simulator produces an output. This is done by using the external library that
were installed and directly in the console.

1.  The console displays different parameters calculated during runtime.
2.  Once the compiler completes running the code in the simulator, it will output a table of all the parameters of the system in Excel.

## Tools

This project used Java.

## License

This codebase is a public domain, so feel free to use this repo for what you want.
