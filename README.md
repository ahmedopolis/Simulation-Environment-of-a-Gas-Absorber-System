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
   ![Input Image](C:/Users/ajimp/Documents/GitHub/Simulation-Environment-of-a-Gas-Absorber-System/Images/Input-image.PNG "Input Image")
