import java.text.DecimalFormat;

public class AbsorptionSimulation implements RootFinder, NumericalIntegration {

	// Global variables (Approximation of global variables) for the "AdsorptionColumnSimulator" class.
	private static double g_MW_A;
	private static double g_MW_L;
	private static double g_MW_G;
	private static double g_Y_A2;
	private static double g_Y_A1;
	private static double g_X_A2;
	private static double g_X_A1;
	private static double g_V_f;
	private static double g_L_f;
	private static double g_S;
	private static double g_F_p;
	private static double g_Schmidt_Gas;
	private static double g_Schmidt_Liquid;
	private static double g_ourU_L;

	private static double g_A5_EPC;
	private static double g_A4_EPC;
	private static double g_A3_EPC;
	private static double g_A2_EPC;
	private static double g_A1_EPC;
	private static double g_A0_EPC;

	private static double g_Root_Finder_X_L;
	private static double g_Root_Finder_X_U;
	private static double g_Root_Finder_Tolerance;
	private static double g_Root_Finder_Maximum_Iterations;
	private static double g_Numerical_Integration_Number_Of_Steps;

	private static double g_X_AL;
	private static  double g_Y_AG;
	private static double g_L;
	private static double g_V;
	private double g_weightedMW_V;
	private double g_weightedMW_L;
	private double g_G_V;
	private double g_G_L;
	private double g_kxa;
	private double g_kya;
	private double g_Y_Ai;
	private double g_X_Ai;
	private double g_LogMeanYA;
	private double g_LogMeanXA;

	// Constructor containing global variables
	public AbsorptionSimulation(UserInputs myUserInputs) {
		g_MW_A = myUserInputs.getMw_A();
		g_MW_L = myUserInputs.getMw_L();
		g_MW_G = myUserInputs.getMw_V();
		g_Y_A2 = myUserInputs.getY_A2();
		g_Y_A1 = myUserInputs.getY_A1();
		g_X_A2 = myUserInputs.getX_A2();
		g_X_A1 = myUserInputs.getX_A1();
		g_V_f = myUserInputs.getV_f();
		g_L_f = myUserInputs.getL_f();
		g_S = myUserInputs.getMyPackingMaterial().calculateS();
		g_F_p = myUserInputs.getMyPackingMaterial().getF_p();
		g_Schmidt_Gas = myUserInputs.getSchmidtGas();
		g_Schmidt_Liquid = myUserInputs.getSchmidtLiquid();
		g_ourU_L = myUserInputs.getU_L();

		g_A5_EPC = myUserInputs.getEquilibriumPolynomialCoefficient5();
		g_A4_EPC = myUserInputs.getEquilibriumPolynomialCoefficient4();
		g_A3_EPC = myUserInputs.getEquilibriumPolynomialCoefficient3();
		g_A2_EPC = myUserInputs.getEquilibriumPolynomialCoefficient2();
		g_A1_EPC = myUserInputs.getEquilibriumPolynomialCoefficient1();
		g_A0_EPC = myUserInputs.getEquilibriumPolynomialCoefficient0();

		g_Root_Finder_X_L = myUserInputs.getRoot_Finder_X_L();
		g_Root_Finder_X_U = myUserInputs.getRoot_Finder_X_U();
		g_Root_Finder_Tolerance = myUserInputs.getRoot_Finder_Tolerance();
		g_Root_Finder_Maximum_Iterations = myUserInputs.getRoot_Finder_Maximum_Iterations();
		g_Numerical_Integration_Number_Of_Steps = myUserInputs.getNumerical_Integration_Number_Of_Steps();

		this.g_X_AL = g_X_A2;
		this.g_Y_AG = g_Y_A1;

		this.g_L = 0.0;
		this.g_V = 0.0;
		this.g_weightedMW_V = 0.0;
		this.g_weightedMW_L = 0.0;
		this.g_G_V = 0.0;
		this.g_G_L = 0.0;
		this.g_kxa = 0.0;
		this.g_kya = 0.0;
		this.g_Y_Ai = 0.0;
		this.g_X_Ai = 0.0;
		this.g_LogMeanYA = 0.0;
		this.g_LogMeanXA = 0.0;

		calculateHeightSimpson();
		calculateHeightErrorPercentageSimpson();

		calculateHeightTrapezoid();
		calculateHeightErrorPercentageTrapezoid();

		calculateHeightModifiedSimpson();
		calculateHeightErrorPercentageModifiedSimpson();

		printNonOptimizedResults();

	}

	public double getG_MW_A() {
		return g_MW_A;
	}

	public double getG_MW_L() {
		return g_MW_L;
	}

	public double getG_MW_G() {
		return g_MW_G;
	}

	public double getG_Y_A2() {
		return g_Y_A2;
	}

	public double getG_Y_A1() {
		return g_Y_A1;
	}

	public double getG_X_A2() {
		return g_X_A2;
	}

	public double getG_X_A1() {
		return g_X_A1;
	}

	public double getG_V_f() {
		return g_V_f;
	}

	public double getG_L_f() {
		return g_L_f;
	}

	public double getG_S() {
		return g_S;
	}

	public double getG_F_p() {
		return g_F_p;
	}

	public double getG_Schmidt_Gas() {
		return g_Schmidt_Gas;
	}

	public double getG_Schmidt_Liquid() {
		return g_Schmidt_Liquid;
	}

	public double getG_ourU_L() {
		return g_ourU_L;
	}

	public double getG_A5_EPC() {
		return g_A5_EPC;
	}

	public double getG_A4_EPC() {
		return g_A4_EPC;
	}

	public double getG_A3_EPC() {
		return g_A3_EPC;
	}

	public double getG_A2_EPC() {
		return g_A2_EPC;
	}

	public double getG_A1_EPC() {
		return g_A1_EPC;
	}

	public double getG_A0_EPC() {
		return g_A0_EPC;
	}

	public double getG_X_AL() {
		return g_X_AL;
	}

	public double getG_Y_AG() {
		return g_Y_AG;
	}

	public double getG_L() {
		return g_L;
	}

	public double getG_V() {
		return g_V;
	}

	public double getG_weightedMW_V() {
		return g_weightedMW_V;
	}

	public double getG_weightedMW_L() {
		return g_weightedMW_L;
	}

	public double getG_G_V() {
		return g_G_V;
	}

	public double getG_G_L() {
		return g_G_L;
	}

	public double getG_kxa() {
		return g_kxa;
	}

	public double getG_kya() {
		return g_kya;
	}

	public double getG_Y_Ai() {
		return g_Y_Ai;
	}

	public double getG_X_Ai() {
		return g_X_Ai;
	}
	
	public boolean setG_L_f(double g_L_f) {
		if (g_L_f < 0)
			return false;
		else {
			this.g_L_f = g_L_f;
			return true;
		}
	}

	public boolean setG_X_AL(double g_X_AL) {
		if (g_X_AL < 0.0 || g_X_AL > 1.0)
			return false;
		else {
			this.g_X_AL = g_X_AL;
			return true;
		}

	}

	public boolean setG_Y_AG(double g_Y_AG) {
		if (g_Y_AG < 0.0 || g_Y_AG > 1.0)
			return false;
		else {
			this.g_Y_AG = g_Y_AG;
			return true;
		}

	}

	public boolean setG_X_Ai(double g_X_Ai) {
		if (g_X_Ai < 0.0 || g_X_Ai > 1.0)
			return false;
		else {
			this.g_X_Ai = g_X_Ai;
			return true;
		}
	}

	// Method to calculate the weighted-average molecular weight on the gas side.
	public double calculateMolecularWeightV() {
		this.g_weightedMW_V = (1 - this.g_Y_AG) * this.g_MW_G + this.g_Y_AG * this.g_MW_A;
		return g_weightedMW_V;
	}

	// Method to calculate the weighted-average molecular weight on the liquid side.
	public double calculateMolecularWeightL() {
		this.g_weightedMW_L = (1 - this.g_X_AL) * this.g_MW_L + this.g_X_AL * this.g_MW_A;
		return this.g_weightedMW_L;
	}

	// Method to calculate the gas flow rate.
	public double calculateV() {
		this.g_V = this.g_V_f / (1 - this.g_Y_AG);
		this.g_V = this.g_V / 3600;
		return this.g_V;
	}

	// Method to calculate the liquid flow rate.
	public double calculateL() {
		this.g_L = this.g_L_f / (1 - this.g_X_AL);
		this.g_L = this.g_L / 3600;
		return this.g_L;
	}

	// Method to calculate the gas mass velocity.
	public double calculateG_V() {
		this.g_G_V = this.g_V * this.g_weightedMW_V / this.g_S;
		return this.g_G_V;
	}

	// Method to calculate the liquid mass velocity.
	public double calculateG_L() {
		this.g_G_L = this.g_L * this.g_weightedMW_L / this.g_S;
		return this.g_G_L;
	}

	// Method to calculate k'ya.
	public double calculate_kya() {
		this.g_kya = Math.pow((this.g_S / this.g_V * 0.226 / this.g_F_p * Math.pow(this.g_Schmidt_Gas / 0.66, 0.5)
				* Math.pow(this.g_G_L / 6.782, -0.5) * Math.pow(this.g_G_V / 0.678, 0.35)), -1);
		return this.g_kya;
	}

	// Method to calculate k'xa.
	public double calculate_kxa() {

		this.g_kxa = Math.pow(this.g_S / this.g_L * 0.357 / this.g_F_p * Math.pow(this.g_Schmidt_Liquid / 372, 0.5)
				* Math.pow((this.g_G_L / this.g_ourU_L) / (6.782 / 0.8937e-3), 0.3), -1);
		return this.g_kxa;
	}

	// Method to compute a series of calculations necessary for Ridder's method.
	private void setParemeters() {

		calculateL();
		calculateV();
		calculateMolecularWeightL();
		calculateMolecularWeightV();
		calculateG_L();
		calculateG_V();
		calculate_kxa();
		calculate_kya();

	}

	// Method overridden from the "RootFinder".
	public double findX_AI(double x) {

		double Y_Ai = g_A5_EPC * Math.pow(x, 5) + g_A4_EPC * Math.pow(x, 4) + g_A3_EPC * Math.pow(x, 3)
				+ g_A2_EPC * Math.pow(x, 2) + g_A1_EPC * x + g_A0_EPC;

		double part1 = 1 - Y_Ai;
		double part2 = Math.log(part1 / (1 - this.g_Y_AG));
		double part3 = (part1 - (1 - this.g_Y_AG)) / part2;

		double partA = (1 - this.g_X_AL) - (1 - x);
		double partB = Math.log((1 - this.g_X_AL) / (1 - x));
		double partC = partA / partB;

		double partK = (-g_kxa / g_kya);

		double partX = partK * (part3 / partC);
		double partZ = Y_Ai - (partX * x + 0.5 * (this.g_Y_AG + Y_Ai - partX * (this.g_X_AL + x)));
		return partZ;
	}

	// Method to calculate X_Ai.
	public double calculateX_Ai() {
		setG_X_Ai(RidderSolver.calculateRoot(g_Root_Finder_X_L, g_Root_Finder_X_U, g_Root_Finder_Tolerance,
				g_Root_Finder_Maximum_Iterations, this));
		return this.g_X_Ai;
	}

	// Method to calculate Y_Ai.
	public double calculateY_Ai() {
		this.g_Y_Ai = g_A5_EPC * Math.pow(g_X_Ai, 5) + g_A4_EPC * Math.pow(g_X_Ai, 4) + g_A3_EPC * Math.pow(g_X_Ai, 3)
				+ g_A2_EPC * Math.pow(g_X_Ai, 2) + g_A1_EPC * g_X_Ai + g_A0_EPC;
		return this.g_Y_Ai;
	}

	// Method to calculate the log mean of YA.
	public double logMeanYA() {
		this.g_LogMeanYA = ((1 - this.g_Y_Ai) - (1 - g_Y_AG)) / Math.log((1 - this.g_Y_Ai) / (1 - g_Y_AG));
		return g_LogMeanYA;
	}

	// Method to calculate the log mean of XA.
	public double logMeanXA() {

		this.g_LogMeanXA = ((1 - this.g_X_AL) - (1 - g_X_Ai)) / Math.log((1 - this.g_X_AL) / (1 - g_X_Ai));
		return g_LogMeanXA;
	}

	// Method calculate the differential height in the vapor side.
	public double calculateDZV(double X_AL, double Y_AG) {
		setG_X_AL(X_AL);
		setG_Y_AG(Y_AG);
		setParemeters();
		calculateX_Ai();
		calculateY_Ai();
		logMeanYA();
		logMeanXA();
		double DZV = this.g_V / ((this.g_kya * g_S) / g_LogMeanYA * (1 - g_Y_AG) * (g_Y_AG - this.g_Y_Ai));
		return DZV;
	}

	// Method calculate the differential height in the liquid side.
	public double calculateDZL(double X_AL, double Y_AG) {
		setG_X_AL(X_AL);
		setG_Y_AG(Y_AG);
		setParemeters();
		calculateX_Ai();
		calculateY_Ai();
		logMeanYA();
		logMeanXA();
		double DZL = this.g_L / ((this.g_kxa * g_S) / g_LogMeanXA * (1 - g_X_AL) * (this.g_X_Ai - g_X_AL));
		return DZL;
	}

	// Method that is overridden from "NumericalIntegration" Interface.
	public double[] calculateHeightDZ(double x, double y) {
		double[] arrayofDZ = new double[2];
		arrayofDZ[0] = calculateDZL(x, y);
		arrayofDZ[1] = calculateDZV(x, y);

		return arrayofDZ;
	}

	// Method returning an array of heights using Simpson's.
	public double[] calculateHeightSimpson() {
		return SimpsonSolver.calculateNumericalIntegrationSimpson(2, g_Numerical_Integration_Number_Of_Steps, g_X_A2,
				g_X_A1, g_Y_A2, g_Y_A1, this);
	}

	// Method returning an array of heights using Trapezoid.
	public double[] calculateHeightTrapezoid() {
		return TrapezoidSolver.calculateNumericalIntegrationTrapezoid(2, g_Numerical_Integration_Number_Of_Steps,
				g_X_A2, g_X_A1, g_Y_A2, g_Y_A1, this);
	}

	// Method returning an array of heights using Modified-Simpson's.
	public double[] calculateHeightModifiedSimpson() {
		return ModifiedSimpsonSolver.calculateNumericalIntegrationModifiedSimpson(2,
				g_Numerical_Integration_Number_Of_Steps, g_X_A2, g_X_A1, g_Y_A2, g_Y_A1, this);
	}

	// Method to calculate the error between both liquid and gas heights using
	// Simpson's.
	public double calculateHeightErrorPercentageSimpson() {

		double[] myHeights = new double[2];
		myHeights = calculateHeightSimpson();

		double error;
		double LargerHeight;

		if (myHeights[0] > myHeights[1]) {
			LargerHeight = myHeights[0];
		} else
			LargerHeight = myHeights[1];

		double differenceHeights = Math.abs(myHeights[0] - myHeights[1]);
		error = (differenceHeights * 100.00 / LargerHeight);

		return error;
	}

	// Method to calculate the error between both liquid and gas heights using
	// Trapezoid.
	public double calculateHeightErrorPercentageTrapezoid() {

		double[] myHeights = new double[2];
		myHeights = calculateHeightTrapezoid();

		double error;
		double LargerHeight;

		if (myHeights[0] > myHeights[1]) {
			LargerHeight = myHeights[0];
		} else
			LargerHeight = myHeights[1];

		double differenceHeights = Math.abs(myHeights[0] - myHeights[1]);
		error = (differenceHeights * 100.00 / LargerHeight);

		return error;
	}

	// Method to calculate the error between both liquid and gas heights using
	// Modified-Simpson's.
	public double calculateHeightErrorPercentageModifiedSimpson() {

		double[] myHeights = new double[2];
		myHeights = calculateHeightModifiedSimpson();

		double error;
		double LargerHeight;

		if (myHeights[0] > myHeights[1]) {
			LargerHeight = myHeights[0];
		} else
			LargerHeight = myHeights[1];

		double differenceHeights = Math.abs(myHeights[0] - myHeights[1]);
		error = (differenceHeights * 100.00 / LargerHeight);

		return error;
	}

	public void printNonOptimizedResults() {
		DecimalFormat df = new DecimalFormat("###.###");

		String typeResultStringNon_Optimized = "[Non-Optimized] ";

		System.out.println("\n - - - Part 1: Non-opmitized Validation Section - - - \n");

		String[] arrayAbsorptionColumnSimulationHeightsString = { "Height Liquid [Trapezoid] (m): ",
				"Height Gas [Trapezoid] (m): ", "Height Error Percentage [Trapezoid] (%): ",
				"Height Liquid [Simpson] (m): ", "Height Gas [Simpson] (m): ",
				"Height Error Percentage [Simpson] (%): ", "Height Liquid [Modified-Simpson] (m): ",
				"Height Gas [Modified-Simpson] (m): ", "Height Error Percentage [Modified-Simpson] (%): " };

		double[] arrayAbsorptionColumnSimulationHeightsResults = new double[9];

		arrayAbsorptionColumnSimulationHeightsResults[0] = calculateHeightTrapezoid()[0];
		arrayAbsorptionColumnSimulationHeightsResults[1] = calculateHeightTrapezoid()[1];
		arrayAbsorptionColumnSimulationHeightsResults[2] = calculateHeightErrorPercentageTrapezoid();
		arrayAbsorptionColumnSimulationHeightsResults[3] = calculateHeightSimpson()[0];
		arrayAbsorptionColumnSimulationHeightsResults[4] = calculateHeightSimpson()[1];
		arrayAbsorptionColumnSimulationHeightsResults[5] = calculateHeightErrorPercentageSimpson();
		arrayAbsorptionColumnSimulationHeightsResults[6] = calculateHeightModifiedSimpson()[0];
		arrayAbsorptionColumnSimulationHeightsResults[7] = calculateHeightModifiedSimpson()[1];
		arrayAbsorptionColumnSimulationHeightsResults[8] = calculateHeightErrorPercentageModifiedSimpson();

		for (int i = 0; i < arrayAbsorptionColumnSimulationHeightsResults.length; i++) {
			System.out.println(typeResultStringNon_Optimized + arrayAbsorptionColumnSimulationHeightsString[i]
					+ df.format(arrayAbsorptionColumnSimulationHeightsResults[i]));
		}

		System.out.println("\n- - - Part 1 Complete - - -\n");
	}

}
