import java.text.DecimalFormat;

public class Optimization {

	// Constructor containing methods.
	public Optimization(AbsorptionSimulation myAbsorptionSimulation, UserInputs myUserInputs) {

		printOptimizationWarnings();
		double[][] myArray = OptimizedArrayResultsRangeofLiquidFlowrate(myAbsorptionSimulation, myUserInputs);
		indexofMinimumErrorPercentage(myArray);
		summaryofOptimizedResults(myArray);
		printSummaryOptimizedResults(myAbsorptionSimulation, myUserInputs);

	}

	// Method to calculate an array of liquid flow rate to simulate.
	public double[] LiquidFlowRateArray(UserInputs myUserInputs) {
		double numberofSteps = myUserInputs.getOptimizer_Number_Of_Steps_Liquid_Flow_Rate();
		double minimumLiquidFlowRate = myUserInputs.getOptimizer_Minimum_Liquid_Flow_Rate();
		double maximumLiquidFlowRate = myUserInputs.getV_f() * 10.00;
		double stepSizeLiquidFlowrate = (maximumLiquidFlowRate - minimumLiquidFlowRate) / numberofSteps;

		double liquidFlowrateValues = minimumLiquidFlowRate;

		double[] arrayLiquidFlowrateArray = new double[(int) numberofSteps];

		for (int i = 0; i < (int) numberofSteps; i++) {
			arrayLiquidFlowrateArray[i] = liquidFlowrateValues;
			liquidFlowrateValues = liquidFlowrateValues + stepSizeLiquidFlowrate;
		}

		return arrayLiquidFlowrateArray;
	}

	// Method to generate the results (Heights and errors) for every liquid flow
	// rate within a set range and step size.
	public double[][] OptimizedArrayResultsRangeofLiquidFlowrate(AbsorptionSimulation myAbsorptionSimulation,
			UserInputs myUserInputs) {

		double[] myLiquidFlowrateArray = LiquidFlowRateArray(myUserInputs);

		double lengthofOptimizedArrayResultsRangeofLiquidFLowrate = myLiquidFlowrateArray.length;
		double[][] myOptimizedArrayResultsRangeofLiquidFlowrate = new double[(int) lengthofOptimizedArrayResultsRangeofLiquidFLowrate][15];
		
		for (int i = 0; i < (int) lengthofOptimizedArrayResultsRangeofLiquidFLowrate; i++) {
			myAbsorptionSimulation.setG_L_f((myLiquidFlowrateArray[i]));
			double myX_A2 = myAbsorptionSimulation.getG_X_A2();

			myOptimizedArrayResultsRangeofLiquidFlowrate[i][0] = i + 1;
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][1] = myLiquidFlowrateArray[i];

			myOptimizedArrayResultsRangeofLiquidFlowrate[i][2] = myAbsorptionSimulation.calculateHeightTrapezoid()[0];
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][3] = myAbsorptionSimulation.calculateHeightTrapezoid()[1];
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][4] = LargerValue(
					myOptimizedArrayResultsRangeofLiquidFlowrate[i][2],
					myOptimizedArrayResultsRangeofLiquidFlowrate[i][3]);
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][5] = myAbsorptionSimulation
					.calculateHeightErrorPercentageTrapezoid();

			myOptimizedArrayResultsRangeofLiquidFlowrate[i][6] = myAbsorptionSimulation.calculateHeightSimpson()[0];
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][7] = myAbsorptionSimulation.calculateHeightSimpson()[1];
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][8] = LargerValue(
					myOptimizedArrayResultsRangeofLiquidFlowrate[i][6],
					myOptimizedArrayResultsRangeofLiquidFlowrate[i][7]);
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][9] = myAbsorptionSimulation
					.calculateHeightErrorPercentageSimpson();

			myOptimizedArrayResultsRangeofLiquidFlowrate[i][10] = myAbsorptionSimulation
					.calculateHeightModifiedSimpson()[0];
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][11] = myAbsorptionSimulation
					.calculateHeightModifiedSimpson()[1];
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][12] = LargerValue(
					myOptimizedArrayResultsRangeofLiquidFlowrate[i][10],
					myOptimizedArrayResultsRangeofLiquidFlowrate[i][11]);
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][13] = myAbsorptionSimulation
					.calculateHeightErrorPercentageModifiedSimpson();
			myOptimizedArrayResultsRangeofLiquidFlowrate[i][14] = myOptimizedArrayResultsRangeofLiquidFlowrate[i][1]
					/ (1 - myX_A2);

		}

		return myOptimizedArrayResultsRangeofLiquidFlowrate;
	}

	// Method to return the larger value.
	public double LargerValue(double Value1, double Value2) {
		double mylargerValue;
		if (Value1 > Value2) {
			mylargerValue = Value1;
		} else
			mylargerValue = Value2;

		return mylargerValue;
	}

	// Method to return a list of indexes corresponding to the number iterations
	// required to reach an optimum liquid flow rate for all three numerical
	// integrations.
	public double[] indexofMinimumErrorPercentage(double[][] OptimizedArrayResultsRangeofLiquidFLowrate) {
		double[][] myOptimizedArrayResultsRangeofLiquidFLowrate = OptimizedArrayResultsRangeofLiquidFLowrate;

		double myMinimumErrorPercentageTrapezoid = myOptimizedArrayResultsRangeofLiquidFLowrate[0][5];
		double myMinimumErrorPercentageSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[0][9];
		double myMinimumErrorPercentageModifiedSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[0][13];

		double indexofSmallerValueTrapezoid = myOptimizedArrayResultsRangeofLiquidFLowrate[0][0];
		double indexofSmallerValueSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[0][0];
		double indexofSmallerValueModifiedSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[0][0];

		for (int i = 0; i < (int) myOptimizedArrayResultsRangeofLiquidFLowrate.length; i++) {

			if (myOptimizedArrayResultsRangeofLiquidFLowrate[i][5] < myMinimumErrorPercentageTrapezoid) {
				myMinimumErrorPercentageTrapezoid = myOptimizedArrayResultsRangeofLiquidFLowrate[i][5];
				indexofSmallerValueTrapezoid = myOptimizedArrayResultsRangeofLiquidFLowrate[i][0];
			}

			else if (myOptimizedArrayResultsRangeofLiquidFLowrate[i][9] < myMinimumErrorPercentageSimpson) {
				myMinimumErrorPercentageSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[i][9];
				indexofSmallerValueSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[i][0];
			}

			else if (myOptimizedArrayResultsRangeofLiquidFLowrate[i][13] < myMinimumErrorPercentageModifiedSimpson) {
				myMinimumErrorPercentageModifiedSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[i][13];
				indexofSmallerValueModifiedSimpson = myOptimizedArrayResultsRangeofLiquidFLowrate[i][0];
			}
		}

		double[] arrayIndexofMinimumErrorPercentage = { indexofSmallerValueTrapezoid, indexofSmallerValueSimpson,
				indexofSmallerValueModifiedSimpson };

		return arrayIndexofMinimumErrorPercentage;
	}

	// Method being a 2D array that stores the liquid side height, gas side height,
	// highest height, error between both the liquid and gas side for all three
	// numerical integrations.
	public double[][] summaryofOptimizedResults(double[][] OptimizedArrayResultsRangeofLiquidFlowrate) {
		double[][] myOptimizedArrayResultsRangeofLiquidFlowrate = OptimizedArrayResultsRangeofLiquidFlowrate;
		double[] myArrayIndexofMinimumErrorPercentage = indexofMinimumErrorPercentage(
				myOptimizedArrayResultsRangeofLiquidFlowrate);

		double[][] mySummaryOptimizedResults = new double[3][7];

		double indexOptimizedTrapezoid = myArrayIndexofMinimumErrorPercentage[0];
		double indexOptimizedSimpson = myArrayIndexofMinimumErrorPercentage[1];
		double indexOptimizedModifiedSimpson = myArrayIndexofMinimumErrorPercentage[2];

		mySummaryOptimizedResults[0][0] = myArrayIndexofMinimumErrorPercentage[0];
		mySummaryOptimizedResults[0][1] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedTrapezoid][2];
		mySummaryOptimizedResults[0][2] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedTrapezoid][3];
		mySummaryOptimizedResults[0][3] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedTrapezoid][4];
		mySummaryOptimizedResults[0][4] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedTrapezoid][5];
		mySummaryOptimizedResults[0][5] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedTrapezoid][1];
		mySummaryOptimizedResults[0][6] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedTrapezoid][14];

		mySummaryOptimizedResults[1][0] = myArrayIndexofMinimumErrorPercentage[1];
		mySummaryOptimizedResults[1][1] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedSimpson][6];
		mySummaryOptimizedResults[1][2] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedSimpson][7];
		mySummaryOptimizedResults[1][3] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedSimpson][8];
		mySummaryOptimizedResults[1][4] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedSimpson][9];
		mySummaryOptimizedResults[1][5] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedSimpson][1];
		mySummaryOptimizedResults[1][6] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedSimpson][14];

		mySummaryOptimizedResults[2][0] = myArrayIndexofMinimumErrorPercentage[2];
		mySummaryOptimizedResults[2][1] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedModifiedSimpson][10];
		mySummaryOptimizedResults[2][2] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedModifiedSimpson][11];
		mySummaryOptimizedResults[2][3] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedModifiedSimpson][12];
		mySummaryOptimizedResults[2][4] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedModifiedSimpson][13];
		mySummaryOptimizedResults[2][5] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedModifiedSimpson][1];
		mySummaryOptimizedResults[2][6] = myOptimizedArrayResultsRangeofLiquidFlowrate[(int) indexOptimizedModifiedSimpson][14];

		return mySummaryOptimizedResults;
	}

	public void printSummaryOptimizedResults(AbsorptionSimulation myAbsorptionSimulation, UserInputs myUserInputs) {
		DecimalFormat df = new DecimalFormat("###.###");

		System.out.println("\n - - - Optimization Validation Section - - - \n");

		String typeResultStringOptimized = "[Optimized] ";

		double[][] arraySummarizedResults = summaryofOptimizedResults(
				OptimizedArrayResultsRangeofLiquidFlowrate(myAbsorptionSimulation, myUserInputs));

		double[] arrayOptimizationResults = { arraySummarizedResults[0][0], arraySummarizedResults[0][1],
				arraySummarizedResults[0][2], arraySummarizedResults[0][3], arraySummarizedResults[0][4],
				arraySummarizedResults[0][5], arraySummarizedResults[0][6], arraySummarizedResults[1][0],
				arraySummarizedResults[1][1], arraySummarizedResults[1][2], arraySummarizedResults[1][3],
				arraySummarizedResults[1][4], arraySummarizedResults[1][5], arraySummarizedResults[1][6],
				arraySummarizedResults[2][0], arraySummarizedResults[2][1], arraySummarizedResults[2][2],
				arraySummarizedResults[2][3], arraySummarizedResults[2][4], arraySummarizedResults[2][5],
				arraySummarizedResults[2][6], };

		String[] arrayOptimizationString = { "Number of Iterations [Trapezoid]: ",
				"Height on Liquid Side [Trapezoid] (m): ", "Height on Gas Side [Trapezoid] (m): ",
				"Highest Height [Trapezoid] (m): ", " Percentage Error for Liquid and Gas Side [Trapezoid] (%): ",
				"Solute-Free Liquid Flowrate [Trapezoid] (kgmole/h): ",
				"Inlet Liquid Flowrate [Trapezoid] (kgmole/h): ", "Number of Iterations [Simpsons]: ",
				"Height on Liquid Side [Simpson] (m): ", "Height on Gas Side [Simpson] (m): ",
				"Highest Height [Simpson] (m):", "Percentage Error for Liquid and Gas Side [Simpson] (%):",
				"Solute-Free Liquid Flowrate [Simpson] (kgmole/h): ", "Inlet Liquid Flowrate [Simpson] (kgmole/h): ",
				"Number of Iterations [Modified-Simpson]: ", "Height on Liquid Side [Modified-Simpson] (m): ",
				"Height on Gas Side [Modified-Simpson] (m): ", "Highest Height [Modified-Simpson] (m): ",
				"Percentage Error for Liquid and Gas Side [Modified-Simpson] (%): ",
				"Solute-Free Liquid Flowrate [Modified-Simpson] (kgmole/h): ",
				"Inlet Liquid Flowrate [Modified-Simpson] (kgmole/h): " };

		for (int i = 0; i < arrayOptimizationResults.length; i++) {
			System.out.println(
					typeResultStringOptimized + arrayOptimizationString[i] + df.format(arrayOptimizationResults[i]));
		}

		System.out.println("\n- - - Part 2: Optimization Complete! - - -\n");

	}

	public void printOptimizationWarnings() {
		System.out.println("Runtime for the optimization may take a few minutes depending on your Computer Specs.");
	}

}
