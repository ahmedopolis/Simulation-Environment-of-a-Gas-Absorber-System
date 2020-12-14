import java.util.*;
import java.io.*;

public class UserInputs {

	// Beginning of INSTANCE VARIABLES FOR FILE
	// INPUT_________________________________________________________

	// Choice of packing material.
	private double packingMaterialChoice; // 0 for for Rashig Ring or 1 for Berl Saddle or 2 for Pall Ring

	// Mass balance instance variables must be checked via exception handling
	private double l_2; // l_2 > 0.0
	private double v_1; // V_2 > 0.0
	private double y_A1; // Y_A1 > 0.0 && Y_A1 < 1.0
	private double x_A2; // x_A2 >= 0.0 && X_A2 < 1.0
	private double recoveryPercentage; // recoveryPercentage >= 0

	// Binary mixture instance variables
	private double equilibriumPolynomialCoefficient5;// Array of polynomial coefficients, length < 6, every value must
														// be larger than 0
	private double equilibriumPolynomialCoefficient4;
	private double equilibriumPolynomialCoefficient3;
	private double equilibriumPolynomialCoefficient2;
	private double equilibriumPolynomialCoefficient1;
	private double equilibriumPolynomialCoefficient0;

	private double mw_A; // Molecular weight of the solute, mw_A > 0.0
	private double mw_L; // Molecular weight of the liquid, mw_L > 0.0
	private double mw_V; // Molecular weight of the volume, mw_V > 0.0

	private double d_ABV; // Diffusion coefficient of solute through gas, d_ABV > 0.0
	private double u_V; // Viscosity of gas, u_V > 0.0
	private double p_V; // Density of gas, p_V > 0.0

	private double d_ABL; // Diffusion coefficient of solute through liquid, d_ABL > 0.0
	private double u_L; // Viscosity of liquid, u_L > 0.0
	private double p_L; // Density of liquid, p_L > 0.0

	// Value for the lower bound of the root finder
	private double root_Finder_X_L;

	// Value for the upper bound of the root finder
	private double root_Finder_X_U;

	// Tolerance to reach in the root finder algorithm
	private double root_Finder_Tolerance;

	// Maximum iterations for ridder's algorithm
	private double root_Finder_Maximum_Iterations;

	// Number of steps for the numerical integrations
	private double numerical_Integration_Number_Of_Steps;

	// Number of steps needed to compute the optimization section
	private double optimizer_Number_Of_Steps_Liquid_Flow_Rate;

	// Minimum liquid flow rate for the the optimization
	private double optimizer_Minimum_Liquid_Flow_Rate;

	// End of INSTANCE VARIABLES FOR FILE
	// INPUT_______________________________________________________________

	// Beginning of INSTANCE VARIABLES FOR MASS BALANCE, PACKING MATERIAL and
	// GENERAL BINARY MIXTURE__________

	// Object of String to store the file path inputed by the user
	private String filePath;

	// Object of type packing material
	private PackingMaterial myPackingMaterial;

	// Instance variable to store solute-free gas flow rate
	private double v_f;

	// Instance variable to store solute-free liquid flow rate
	private double l_f;

	// Instance variable to store outlet gas flow rate;
	private double v_2;

	// Instance variable to store outlet gas solute composition;
	private double y_A2;

	// Instance variable to store outlet liquid solute composition;
	private double x_A1;

	// Instance variable to store outlet liquid flow rate
	private double l_1;

	// Instance variable to store Schmidt number for gas-side
	private double schmidtGas;

	// Instance variable to store Schmidt number for liquid-side
	private double schmidtLiquid;

	// Instance variable to store slope of operating line.
	private double slopeofOperatingLine;

	// Instance variable to store Y-Intercept of operating line.
	private double YinterceptofOperatingLine;

	// Instance variable to store boolean of test of general binary mixture
	// equilibrium line vs operating line
	private boolean isGeneralBinaryMixtureCorrect;

	private String filepathoutput;

	// End of INSTANCE VARIABLES FOR MASS BALANCE, PACKING MATERIAL and GENERAL
	// BINARY MIXTURE__________

	// Beginning of CONSTTRUCTORS FOR
	// USERINPUTS_________________________________________________________

	// All Values are initially set to the Default Case.
	public UserInputs() {

		this.packingMaterialChoice = 0.0;

		this.l_2 = 40.00;
		this.v_1 = 15.00;
		this.y_A1 = 0.12;
		this.x_A2 = 0.00;
		this.recoveryPercentage = 92.00;

		this.equilibriumPolynomialCoefficient5 = 0.0000;
		this.equilibriumPolynomialCoefficient4 = 0.0000;
		this.equilibriumPolynomialCoefficient3 = 0.0000;
		this.equilibriumPolynomialCoefficient2 = 1.9762;
		this.equilibriumPolynomialCoefficient1 = 0.0812;
		this.equilibriumPolynomialCoefficient0 = 0.0000;

		this.mw_A = 44.05;
		this.mw_L = 18.02;
		this.mw_V = 28.05;

		this.d_ABV = 1.35e-05;
		this.u_V = 9.85e-06;
		this.p_V = 1.261;

		this.d_ABL = 1.24e-09;
		this.u_L = 8.90e-04;
		this.p_L = 997.000;

		this.root_Finder_X_L = 0.0;
		this.root_Finder_X_U = 0.118;
		this.root_Finder_Tolerance = 1.00E-06;
		this.root_Finder_Maximum_Iterations = 10000;
		this.numerical_Integration_Number_Of_Steps = 1000;
		this.optimizer_Number_Of_Steps_Liquid_Flow_Rate = 1000;
		this.optimizer_Minimum_Liquid_Flow_Rate = 0;

		this.filePath = this.getFileContent(); // this will get the file from user, and send it over to search for it.
		this.parseFileContents(filePath); // this to parse the content of the file and interpret it.

		this.myPackingMaterial = methodsetPackingMaterials();
		this.v_f = calculateV_f();
		this.l_f = calculateL_f();
		this.v_2 = calculateV_2();
		this.y_A2 = calculateY_A2();
		this.x_A1 = calculateX_A1();
		this.l_1 = calculateL_1();
		this.schmidtGas = calculateSchmidtGas();
		this.schmidtLiquid = calculateSchmidtLiquid();
		this.slopeofOperatingLine = calculateSlopeofOperatingLine();
		this.YinterceptofOperatingLine = calculateYinterceptofOperatingLine();
		this.isGeneralBinaryMixtureCorrect = correctGeneralBinaryMixture();

	}

	public UserInputs(UserInputs source) {
		this.packingMaterialChoice = source.packingMaterialChoice;

		this.l_2 = source.l_2;
		this.v_1 = source.v_1;
		this.y_A1 = source.y_A1;
		this.x_A2 = source.x_A2;
		this.recoveryPercentage = source.recoveryPercentage;

		this.equilibriumPolynomialCoefficient5 = source.equilibriumPolynomialCoefficient5;
		this.equilibriumPolynomialCoefficient4 = source.equilibriumPolynomialCoefficient4;
		this.equilibriumPolynomialCoefficient3 = source.equilibriumPolynomialCoefficient3;
		this.equilibriumPolynomialCoefficient2 = source.equilibriumPolynomialCoefficient2;
		this.equilibriumPolynomialCoefficient1 = source.equilibriumPolynomialCoefficient1;
		this.equilibriumPolynomialCoefficient0 = source.equilibriumPolynomialCoefficient0;

		this.mw_A = source.mw_A;
		this.mw_L = source.mw_L;
		this.mw_V = source.mw_V;

		this.d_ABV = source.d_ABV;
		this.u_V = source.u_V;
		this.p_V = source.p_V;

		this.d_ABL = source.d_ABL;
		this.u_L = source.u_L;
		this.p_L = source.p_L;

		this.root_Finder_X_L = source.root_Finder_X_L;
		this.root_Finder_X_U = source.root_Finder_X_U;
		this.root_Finder_Tolerance = source.root_Finder_Tolerance;
		this.root_Finder_Maximum_Iterations = source.root_Finder_Maximum_Iterations;
		this.numerical_Integration_Number_Of_Steps = source.numerical_Integration_Number_Of_Steps;
		this.optimizer_Number_Of_Steps_Liquid_Flow_Rate = source.optimizer_Number_Of_Steps_Liquid_Flow_Rate;
		this.optimizer_Minimum_Liquid_Flow_Rate = source.optimizer_Minimum_Liquid_Flow_Rate;

		this.myPackingMaterial = source.myPackingMaterial;
		this.v_f = source.v_f;
		this.l_f = source.l_f;
		this.v_2 = source.v_2;
		this.y_A2 = source.y_A2;
		this.x_A1 = source.x_A1;
		this.l_1 = source.l_1;
		this.schmidtGas = source.schmidtGas;
		this.schmidtLiquid = source.schmidtLiquid;
		this.slopeofOperatingLine = source.slopeofOperatingLine;
		this.YinterceptofOperatingLine = source.YinterceptofOperatingLine;
		this.isGeneralBinaryMixtureCorrect = source.isGeneralBinaryMixtureCorrect;
		printCorrectGeneralBinaryMixture();

	}

	// End of CONSTTRUCTORS FOR
	// USERINPUTS_________________________________________________________________

	// Beginning of METHODS RELATED to FILE
	// INPUTS_________________________________________________________

	// Method to obtain content of file

	private String getFileContent() {
		String fileContents;

		Scanner input = new Scanner(System.in);
		System.out.println("Can you please provide the file path to the input file.");
		this.filePath = input.nextLine();
		System.out.println("Can you enter the file path of the output");
		this.filepathoutput = input.next();

		input.close();
		try {
			input = new Scanner(new File(filePath)).useDelimiter("\\Z"); // goes to the end of the file
			fileContents = input.next();
			input.close();

		} catch (FileNotFoundException e) {
			System.err.println("ERROR: THE FILE IS NOT FOUND ALONG SPECIFIC PATH " + filePath);
			fileContents = this.getFileContent();
		}

		return fileContents;
	}

	/*
	 * This file is called to parse the content of the text file. The white space is
	 * deleted. First The second, the words are seperated by an equal sign that
	 * would separate both of them.
	 */

	private void parseFileContents(String fileContents) {

		fileContents = fileContents.replace(" ", "");

		String[] tokens = fileContents.split("=|\n");

		tokens = this.removeEmptyStrings(tokens);

		for (int i = 0; i + 1 < tokens.length; i = i + 2) {
			this.interpretToken(tokens[i], tokens[i + 1]); // passes name and value to interpretToken
		}

	}

	/*
	 * This private instance variable in the input will remove empty strings. This
	 * empty white string are removed to make the text file more organized.
	 */

	private String[] removeEmptyStrings(String[] originalStringArray) {

		String[] returnArray;
		int returnIndex = 0;
		int numOfEmptySpaces = 0;
		for (int i = 0; i < originalStringArray.length; i++) {
			if (originalStringArray[i].trim().isEmpty()) {
				numOfEmptySpaces = numOfEmptySpaces + 1;
			}
		}
		returnArray = new String[originalStringArray.length - numOfEmptySpaces]; // dimensionalize return array
		for (int i = 0; i < originalStringArray.length; i++) {
			if (!originalStringArray[i].trim().isEmpty()) {
				returnArray[returnIndex] = originalStringArray[i].trim(); // copy over non-empty strings
				returnIndex = returnIndex + 1;
			}
		}

		return returnArray;
	}

	/*
	 * This method is to interrupt the tokens that are passed with the value.
	 */

	private void interpretToken(String name, String value) {
		// System.out.println("I am in the interpretToken");
		// System.out.println("This is the name" + name + "This is the value" + value);
		try {
			if (name.equalsIgnoreCase("packingMaterialChoice") && isPackingMaterial(name, value)) {
				this.setPackingMaterialChoice(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("l_2") && isAboveZeroBoundary(name, value)) {
				this.setL_2(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("v_1") && isAboveZeroBoundary(name, value)) {
				this.setV_1(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("y_A1") && isSmallerOrEqualOne(name, value)) {
				this.setY_A1(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("x_A2") && isSmallerOrEqualOne(name, value)) {
				this.setX_A2(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("recoveryPercentage") && isAboveZeroOrBelowHundred(name, value)) {
				this.setRecoveryPercentage(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("equilibriumPolynomialCoefficient5")) {
				this.setEquilibriumPolynomialCoefficient5(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("equilibriumPolynomialCoefficient4")) {
				this.setEquilibriumPolynomialCoefficient4(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("equilibriumPolynomialCoefficient3")) {
				this.setEquilibriumPolynomialCoefficient3(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("equilibriumPolynomialCoefficient2")) {
				this.setEquilibriumPolynomialCoefficient2(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("equilibriumPolynomialCoefficient1")) {
				this.setEquilibriumPolynomialCoefficient1(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("equilibriumPolynomialCoefficient0")) {
				this.setEquilibriumPolynomialCoefficient0(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("mw_A") && isAboveZeroBoundary(name, value)) {
				this.setMw_A(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("mw_L") && isAboveZeroBoundary(name, value)) {
				this.setMw_L(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("mw_V") && isAboveZeroBoundary(name, value)) {
				this.setMw_V(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("d_ABV") && isAboveZeroBoundary(name, value)) {
				this.setD_ABV(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("u_V") && isAboveZeroBoundary(name, value)) {
				this.setU_V(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("p_V") && isAboveZeroBoundary(name, value)) {
				this.setP_V(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("d_ABL") && isAboveZeroBoundary(name, value)) {
				this.setD_ABL(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("u_L") && isAboveZeroBoundary(name, value)) {
				this.setU_L(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("p_L") && isAboveZeroBoundary(name, value)) {
				this.setP_L(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("root_Finder_X_L") && isEqualorAboveZeroBoundary(name, value)) {
				this.setRoot_Finder_X_L(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("root_Finder_X_U") && isAboveZeroBoundary(name, value)) {
				this.setRoot_Finder_X_U(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("root_Finder_Tolerance") && isAboveZeroBoundary(name, value)) {
				this.setRoot_Finder_Tolerance(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("root_Finder_Maximum_Iterations") && isAboveZeroBoundary(name, value)) {
				this.setRoot_Finder_Maximum_Iterations(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("numerical_Integration_Number_Of_Steps")
					&& isAboveZeroBoundary(name, value)) {
				this.setNumerical_Integration_Number_Of_Steps(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("optimizer_Number_Of_Steps_Liquid_Flow_Rate")
					&& isAboveZeroBoundary(name, value)) {
				this.setOptimizer_Number_Of_Steps_Liquid_Flow_Rate(Double.parseDouble(value));
			}

			else if (name.equalsIgnoreCase("optimizer_Minimum_Liquid_Flow_Rate")
					&& isEqualorAboveZeroBoundary(name, value)) {
				this.setOptimizer_Minimum_Liquid_Flow_Rate(Double.parseDouble(value));
			} else {
				System.err.println("ERROR: Invalid variable name found: \n " + name);
			}

		} catch (NumberFormatException e) {
			System.err.println("FATAL ERROR: Invalid variable type, \"" + value
					+ "\" could not be converted to a number type which was expected for: " + name
					+ "\n code execution cannot continue");
			throw new RuntimeException();
		}

	}// End of interpretToken method

	// End of METHODS RELATED to FILE
	// INPUTS______________________________________________________________________________

	// Beginning of BOOLEAN METHODS TO TEST THE INPUTS FROM THE
	// FILE_____________________________________________________

	// Boolean method checks if the value is above zero boundary (BOOLEAN #1)
	private boolean isAboveZeroBoundary(String name, String value) {
		if (Double.parseDouble(value) >= 0) {
			return true;
		} else {

			System.out.println("Sorry the " + name + " has a value of" + value
					+ " is below zero or equal to zero, can you input an acceptable value in the text file.");
			return false;
		}
	}

	// Boolean method checks if the value is equal or above one boundary (BOOLEAN
	// #2)
	private boolean isSmallerOrEqualOne(String name, String value) {
		double a = Double.parseDouble(value);
		if (a >= 0 && a <= 1) {
			return true;

		} else {
			System.out.println("Sorry the " + name + " that has a value of" + a
					+ " is above One  or below zero. can you input an acceptable value in the text file.");
			// call a function to set the variable to what you want to be.
			return false;
		}
	}

	// Boolean method checks if the value is equal or above zero boundary (BOOLEAN
	// #3)
	private boolean isEqualorAboveZeroBoundary(String name, String value) {
		if (Double.parseDouble(value) >= 0) {
			return true;
		} else {

			System.out.println("Sorry the " + name + " has a value of" + value
					+ " is below zero, can you type it in excel and try again");

			return false;
		}
	}

	// Boolean method to test the choice of packing material is a value among 0, 1,
	// 2 (BOOLEAN #4)
	private boolean isPackingMaterial(String name, String value) {
		double a = Double.parseDouble(value);
		if (a == 0.0 || a == 1.0 || a == 2.0) {
			return true;
		} else {
			System.out.println("Sorry the" + name + " has a value of " + a
					+ " and is not an acceptable value! can you please try again in text file ");
			return false;
		}

	}

	// Boolean method checks if the value is equal or below hundred and equal or
	// larger zero boundary (BOOLEAN #5)
	private boolean isAboveZeroOrBelowHundred(String name, String value) {
		double a = Double.parseDouble(value);
		if (a >= 0 && a <= 100) {
			return true;
		} else {
			System.out.println("Sorry the " + name + " has a value of " + a
					+ " that is is above Hunderd or below Zero, can you type it in excel and try again and restart the program");
			return false;
		}
	}

	// End of BOOLEAN METHODS TO TEST THE INPUTS FROM THE
	// FILE_____________________________________________________

	// Beginning of MUTATORS AND ACCESSORS FOR THE
	// USERINPUTS______________________________________________________

	public double getPackingMaterialChoice() {
		return packingMaterialChoice;
	}

	private void setPackingMaterialChoice(double packingMaterialChoice) {
		this.packingMaterialChoice = packingMaterialChoice;
	}

	public double getL_2() {
		return l_2;
	}

	private void setL_2(double l_2) {
		this.l_2 = l_2;
	}

	public double getV_1() {
		return v_1;
	}

	private void setV_1(double v_1) {
		this.v_1 = v_1;
	}

	public double getY_A1() {
		return y_A1;
	}

	private void setY_A1(double y_A1) {
		this.y_A1 = y_A1;
	}

	public double getX_A2() {
		return x_A2;
	}

	private void setX_A2(double x_A2) {
		this.x_A2 = x_A2;
	}

	public double getRecoveryPercentage() {
		return recoveryPercentage;
	}

	private void setRecoveryPercentage(double recoveryPercentage) {
		this.recoveryPercentage = recoveryPercentage;
	}

	public double getEquilibriumPolynomialCoefficient5() {
		return equilibriumPolynomialCoefficient5;
	}

	private void setEquilibriumPolynomialCoefficient5(double equilibriumPolynomialCoefficient5) {
		this.equilibriumPolynomialCoefficient5 = equilibriumPolynomialCoefficient5;
	}

	public double getEquilibriumPolynomialCoefficient4() {
		return equilibriumPolynomialCoefficient4;
	}

	private void setEquilibriumPolynomialCoefficient4(double equilibriumPolynomialCoefficient4) {
		this.equilibriumPolynomialCoefficient4 = equilibriumPolynomialCoefficient4;
	}

	public double getEquilibriumPolynomialCoefficient3() {
		return equilibriumPolynomialCoefficient3;
	}

	private void setEquilibriumPolynomialCoefficient3(double equilibriumPolynomialCoefficient3) {
		this.equilibriumPolynomialCoefficient3 = equilibriumPolynomialCoefficient3;
	}

	public double getEquilibriumPolynomialCoefficient2() {
		return equilibriumPolynomialCoefficient2;
	}

	private void setEquilibriumPolynomialCoefficient2(double equilibriumPolynomialCoefficient2) {
		this.equilibriumPolynomialCoefficient2 = equilibriumPolynomialCoefficient2;
	}

	public double getEquilibriumPolynomialCoefficient1() {
		return equilibriumPolynomialCoefficient1;
	}

	private void setEquilibriumPolynomialCoefficient1(double equilibriumPolynomialCoefficient1) {
		this.equilibriumPolynomialCoefficient1 = equilibriumPolynomialCoefficient1;
	}

	public double getEquilibriumPolynomialCoefficient0() {
		return equilibriumPolynomialCoefficient0;
	}

	private void setEquilibriumPolynomialCoefficient0(double equilibriumPolynomialCoefficient0) {
		this.equilibriumPolynomialCoefficient0 = equilibriumPolynomialCoefficient0;
	}

	public double getMw_A() {
		return mw_A;
	}

	private void setMw_A(double mw_A) {
		this.mw_A = mw_A;
	}

	public double getMw_L() {
		return mw_L;
	}

	private void setMw_L(double mw_L) {
		this.mw_L = mw_L;
	}

	public double getMw_V() {
		return mw_V;
	}

	private void setMw_V(double mw_V) {
		this.mw_V = mw_V;
	}

	public double getD_ABV() {
		return d_ABV;
	}

	private void setD_ABV(double d_ABV) {
		this.d_ABV = d_ABV;
	}

	public double getU_V() {
		return u_V;
	}

	private void setU_V(double u_V) {
		this.u_V = u_V;
	}

	public double getP_V() {
		return p_V;
	}

	private void setP_V(double p_V) {
		this.p_V = p_V;
	}

	public double getD_ABL() {
		return d_ABL;
	}

	private void setD_ABL(double d_ABL) {
		this.d_ABL = d_ABL;
	}

	public double getU_L() {
		return u_L;
	}

	private void setU_L(double u_L) {
		this.u_L = u_L;
	}

	public double getP_L() {
		return p_L;
	}

	private void setP_L(double p_L) {
		this.p_L = p_L;
	}

	public double getRoot_Finder_X_L() {
		return root_Finder_X_L;
	}

	private void setRoot_Finder_X_L(double root_Finder_X_L) {
		this.root_Finder_X_L = root_Finder_X_L;
	}

	public double getRoot_Finder_X_U() {
		return root_Finder_X_U;
	}

	private void setRoot_Finder_X_U(double root_Finder_X_U) {
		this.root_Finder_X_U = root_Finder_X_U;
	}

	public double getRoot_Finder_Tolerance() {
		return root_Finder_Tolerance;
	}

	private void setRoot_Finder_Tolerance(double root_Finder_Tolerance) {
		this.root_Finder_Tolerance = root_Finder_Tolerance;
	}

	public double getRoot_Finder_Maximum_Iterations() {
		return root_Finder_Maximum_Iterations;
	}

	private void setRoot_Finder_Maximum_Iterations(double root_Finder_Maximum_Iterations) {
		this.root_Finder_Maximum_Iterations = root_Finder_Maximum_Iterations;
	}

	public double getNumerical_Integration_Number_Of_Steps() {
		return numerical_Integration_Number_Of_Steps;
	}

	private void setNumerical_Integration_Number_Of_Steps(double numerical_Integration_Number_Of_Steps) {
		this.numerical_Integration_Number_Of_Steps = numerical_Integration_Number_Of_Steps;
	}

	public double getOptimizer_Number_Of_Steps_Liquid_Flow_Rate() {
		return optimizer_Number_Of_Steps_Liquid_Flow_Rate;
	}

	private void setOptimizer_Number_Of_Steps_Liquid_Flow_Rate(double optimizer_Number_Of_Steps_Liquid_Flow_Rate) {
		this.optimizer_Number_Of_Steps_Liquid_Flow_Rate = optimizer_Number_Of_Steps_Liquid_Flow_Rate;
	}

	public double getOptimizer_Minimum_Liquid_Flow_Rate() {
		return optimizer_Minimum_Liquid_Flow_Rate;
	}

	private void setOptimizer_Minimum_Liquid_Flow_Rate(double optimizer_Minimum_Liquid_Flow_Rate) {
		this.optimizer_Minimum_Liquid_Flow_Rate = optimizer_Minimum_Liquid_Flow_Rate;
	}

	public String getFilePath() {
		return filePath;
	}

	public PackingMaterial getMyPackingMaterial() {
		return myPackingMaterial;
	}

	public double getV_f() {
		return v_f;
	}

	public double getL_f() {
		return l_f;
	}

	public String getfilepathoutput() {
		return this.filepathoutput;
	}

	public double getV_2() {
		return v_2;
	}

	public double getY_A2() {
		return y_A2;
	}

	public double getX_A1() {
		return x_A1;
	}

	public double getL_1() {
		return l_1;
	}

	public double getSchmidtGas() {
		return schmidtGas;
	}

	public double getSchmidtLiquid() {
		return schmidtLiquid;
	}

	public double getSlopeofOperatingLine() {
		return slopeofOperatingLine;
	}

	public double getYinterceptofOperatingLine() {
		return YinterceptofOperatingLine;
	}

	public boolean isGeneralBinaryMixtureCorrect() {
		return isGeneralBinaryMixtureCorrect;
	}

	// End of MUTATORS AND ACCESSORS FOR THE
	// USERINPUTS_____________________________________________________________________________________

	// Beginning of METHODS NEEDED FOR MASS BALANCE, EXTRACTING VALUES FROM PACKING
	// MATERIAL CHOICE and TESTING EQUILIBRIUM LINE____________

	// Method is used to downcast a "PackingMaterial" object to a particular type of
	// "PackingMaterial" Child class like "Pall Rings, Rashig Rings, Berl Saddles".

	private final PackingMaterial methodsetPackingMaterials() {

		double myMaterialChoice = getPackingMaterialChoice();

		PackingMaterial myPackingMaterialObjects = new PackingMaterial();

		if (myMaterialChoice == 0) {
			myPackingMaterialObjects = new RashigRings(0.025, 30.00, 1.246);
		}

		else if (myMaterialChoice == 1) {
			myPackingMaterialObjects = new BerlSaddles(0.025, 15.00, 1.361);
		}

		else if (myMaterialChoice == 2) {
			myPackingMaterialObjects = new PallRings(0.025, 10.00, 0.905);
		} else {
			this.myPackingMaterial = null;
		}

		return myPackingMaterialObjects;
	}

	// Method to calculate the solute-free vapor flow rate going through the
	// absorption tower.
	private final double calculateV_f() {
		double myV_1 = getV_1();
		double myY_A1 = getY_A1();

		double myV_F = myV_1 * (1 - myY_A1);

		return myV_F;
	}

	// Method to calculate the solute-free liquid flow rate going through the
	// absorption tower.
	private final double calculateL_f() {

		double myL_2 = getL_2();
		double myX_A2 = getX_A2();

		double myL_F = myL_2 * (1 - myX_A2);

		return myL_F;
	}

	// Method to calculate the outlet vapor flow rate.
	private final double calculateV_2() {

		double myV_F = calculateV_f();
		double myV_1 = getV_1();
		double myY_A1 = getY_A1();
		double myRecovery_Percentage = getRecoveryPercentage();

		double myV_2 = myV_F + myV_1 * myY_A1 * (1 - (myRecovery_Percentage / 100.00));

		return myV_2;
	}

	// Method to calculate the outlet vapor composition.
	private final double calculateY_A2() {

		double myV_2 = getV_2();
		double myV_1 = getV_1();
		double myY_A1 = getY_A1();
		double myRecovery_Percentage = getRecoveryPercentage();

		double myY_A2 = (myV_1 * myY_A1 * (1 - (myRecovery_Percentage / 100.00))) / myV_2;

		return myY_A2;
	}

	// Method to calculate the inlet vapor composition.
	private final double calculateX_A1() {
		double myV_f = calculateV_f();
		double myL_f = calculateL_f();
		double myY_A2 = calculateY_A2();
		double myY_A1 = getY_A1();
		double myX_A2 = getX_A2();

		double myPart1 = myY_A1 / (1 - myY_A1) - myY_A2 / (1 - myY_A2);
		double myPart2 = myX_A2 / (1 - myX_A2) + myV_f / myL_f * myPart1;
		double myX_A1 = Math.pow(1 + Math.pow(myPart2, -1), -1);

		return myX_A1;
	}

	// Method to calculate the outlet vapor composition.
	private final double calculateL_1() {

		double myL_f = calculateL_f();
		double myX_A1 = calculateX_A1();

		double myL_1 = myL_f / (1 - myX_A1);

		return myL_1;
	}

	// Method to calculate the Schmidt number on the gas side.
	private final double calculateSchmidtGas() {

		double myu_V = getU_V();
		double myp_V = getP_V();
		double myD_ABV = getD_ABV();

		return (myu_V) / (myp_V * myD_ABV);
	}

	// Method to calculate the Schmidt number on the liquid side.
	private final double calculateSchmidtLiquid() {

		double myu_L = getU_L();
		double myp_L = getP_L();
		double myD_ABL = getD_ABL();

		return (myu_L) / (myp_L * myD_ABL);
	}

	// Method to calculate the slope of the operating line.
	private final double calculateSlopeofOperatingLine() {

		double myY_A1 = getY_A1();
		double myY_A2 = calculateY_A2();
		double myX_A1 = calculateX_A1();
		double myX_A2 = getX_A2();

		double mySlopeofOperatingLine = (myY_A2 - myY_A1) / (myX_A2 - myX_A1);

		return mySlopeofOperatingLine;

	}

	// Method to calculate the Y-intercept of the operating line.
	private final double calculateYinterceptofOperatingLine() {

		double myY_A2 = calculateY_A2();
		double myYinterceptofOperatingLine = myY_A2;

		return myYinterceptofOperatingLine;

	}

	// Method to test for any value of liquid composition the calculated gas
	// composition
	// is larger from the equilibrium equation as opposed to the operating line
	// equation
	private final boolean correctGeneralBinaryMixture() {

		double myA5_EPC = getEquilibriumPolynomialCoefficient5();
		double myA4_EPC = getEquilibriumPolynomialCoefficient4();
		double myA3_EPC = getEquilibriumPolynomialCoefficient3();
		double myA2_EPC = getEquilibriumPolynomialCoefficient2();
		double myA1_EPC = getEquilibriumPolynomialCoefficient1();
		double myA0_EPC = getEquilibriumPolynomialCoefficient1();

		double myA1_OL = calculateSlopeofOperatingLine();
		double myA0_OL = calculateYinterceptofOperatingLine();

		double myX_A1 = calculateX_A1();
		double myX_A2 = getX_A2();

		int numberofSteps = 1000;
		double myRangeofX = Math.abs(myX_A1 - myX_A2);
		double myStepSize = myRangeofX / numberofSteps;

		double[] my_X_AI_test = new double[numberofSteps];

		double[] my_Y_A_OL_test = new double[numberofSteps];
		double[] my_Y_AI_EPC_test = new double[numberofSteps];

		boolean[] isCorrectGeneralBinaryMixture = new boolean[numberofSteps];
		boolean booleanCorrectGeneralBinaryMixture = true;

		for (int j = 0; j < numberofSteps; j++) {
			my_X_AI_test[j] = myX_A2 + j * myStepSize;

			my_Y_A_OL_test[j] = myA1_OL * Math.pow(my_X_AI_test[j], 2) + myA0_OL;
			my_Y_AI_EPC_test[j] = myA5_EPC * Math.pow(my_X_AI_test[j], 5) + myA4_EPC * Math.pow(my_X_AI_test[j], 4)
					+ myA3_EPC * Math.pow(my_X_AI_test[j], 3) + myA2_EPC * Math.pow(my_X_AI_test[j], 2)
					+ myA1_EPC * my_X_AI_test[j] + myA0_EPC;

			if (my_Y_AI_EPC_test[j] >= my_Y_A_OL_test[j]) {
				isCorrectGeneralBinaryMixture[j] = true;
				booleanCorrectGeneralBinaryMixture = isCorrectGeneralBinaryMixture[j];
			} else {
				isCorrectGeneralBinaryMixture[j] = false;
				booleanCorrectGeneralBinaryMixture = isCorrectGeneralBinaryMixture[j];
				break;
			}
		}

		return booleanCorrectGeneralBinaryMixture;
	}

	// Method to force exit of program if an unacceptable general binary mixture is
	// inputted.
	private void printCorrectGeneralBinaryMixture() {
		if (correctGeneralBinaryMixture()) {
			System.out.println("\n An acceptable general binary mixture has been inputted.");
		} else {
			System.out.println("Please re-enter new values of equilibirum coefficients inside the text file.\n");
			System.exit(0);
		}
	}

}
