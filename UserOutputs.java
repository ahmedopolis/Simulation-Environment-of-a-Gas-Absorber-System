import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class UserOutputs {
	
	//Code for User output.

	private String[] resultsNames;

	private double[] resultsValues;
	private String filepathoutput;

	public UserOutputs(UserOutputs source) {
		this.resultsNames = source.resultsNames;
		this.resultsValues = source.resultsValues;
	}

	public UserOutputs(AbsorptionSimulation myAbsorptionSimulation, Optimization myOptimization,
			UserInputs myUserInputs) throws RowsExceededException, WriteException {
		this.filepathoutput = myUserInputs.getfilepathoutput();
		this.resultsNames = getResultsNames();
		this.resultsValues = getResultsValues(myAbsorptionSimulation, myOptimization, myUserInputs);
		this.printPart1();
	}

	public void printPart1() throws RowsExceededException, WriteException {
		WritableWorkbook myFirstWbook = null;
		try {

			myFirstWbook = Workbook.createWorkbook(new File(filepathoutput));
			WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

			// Printing out the variable name of the results
			for (int i = 0; i < this.resultsNames.length; i++) {
				Label label = new Label(i, 0, this.resultsNames[i]);
				excelSheet.addCell(label);
			}
			// Printing out the variables corresponds to the tables

			for (int i = 0; i < this.resultsValues.length; i++) {
				Number number = new Number(i, 1, resultsValues[i]);
				excelSheet.addCell(number);
			}
			myFirstWbook.write();
			System.out.println("---- The excel can be found in the specified folder! ----");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (myFirstWbook != null) {
				try {
					myFirstWbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public String[] getResultsNames() {
		String[] arrayResultsNames = { "[Non-Optimized] Height Liquid [Trapezoid] (m): ",
				"[Non-Optimized] Height Gas [Trapezoid] (m): ",
				"[Non-Optimized] Height Error Percentage [Trapezoid] (%): ",
				"[Non-Optimized] Height Liquid [Simpson] (m): ", "Height Gas [Simpson] (m): ",
				"[Non-Optimized] Height Error Percentage [Simpson] (%): ",
				"[Non-Optimized] Height Liquid [Modified-Simpson] (m): ",
				"[Non-Optimized] Height Gas [Modified-Simpson] (m): ",
				"[Non-Optimized] Height Error Percentage [Modified-Simpson] (%): ",
				"[Optimized] Number of Iterations [Trapezoid]: ", "[Optimized] Height on Liquid Side [Trapezoid] (m): ",
				"[Optimized] Height on Gas Side [Trapezoid] (m): ", "[Optimized] Highest Height [Trapezoid] (m): ",
				"[Optimized] Percentage Error for Liquid and Gas Side [Trapezoid] (%): ",
				"[Optimized] Solute-Free Liquid Flowrate [Trapezoid] (kgmole/h): ",
				"[Optimized] Inlet Liquid Flowrate [Trapezoid] (kgmole/h): ",
				"[Optimized] Number of Iterations [Simpsons]: ", "[Optimized] Height on Liquid Side [Simpson] (m): ",
				"[Optimized] Height on Gas Side [Simpson] (m): ", "[Optimized] Highest Height [Simpson] (m):",
				"[Optimized] Percentage Error for Liquid and Gas Side [Simpson] (%):",
				"[Optimized] Solute-Free Liquid Flowrate [Simpson] (kgmole/h): ",
				"[Optimized] Inlet Liquid Flowrate [Simpson] (kgmole/h): ",
				"[Optimized] Number of Iterations [Modified-Simpson]: ",
				"[Optimized] Height on Liquid Side [Modified-Simpson] (m): ",
				"[Optimized] Height on Gas Side [Modified-Simpson] (m): ",
				"[Optimized] Highest Height [Modified-Simpson] (m): ",
				"[Optimized] Percentage Error for Liquid and Gas Side [Modified-Simpson] (%): ",
				"[Optimized] Solute-Free Liquid Flowrate [Modified-Simpson] (kgmole/h): ",
				"[Optimized] Inlet Liquid Flowrate [Modified-Simpson] (kgmole/h): " };

		return arrayResultsNames;

	}

	public double[] getResultsValues(AbsorptionSimulation myAbsorptionSimulation, Optimization myOptimization,
			UserInputs myUserInputs) {

		double[][] arrayResultsOptimizedValues = myOptimization.summaryofOptimizedResults(
				myOptimization.OptimizedArrayResultsRangeofLiquidFlowrate(myAbsorptionSimulation, myUserInputs));

		double[] arrayResultsValues = new double[30];
		arrayResultsValues[0] = myAbsorptionSimulation.calculateHeightTrapezoid()[0];
		arrayResultsValues[1] = myAbsorptionSimulation.calculateHeightTrapezoid()[1];
		arrayResultsValues[2] = myAbsorptionSimulation.calculateHeightErrorPercentageTrapezoid();
		arrayResultsValues[3] = myAbsorptionSimulation.calculateHeightSimpson()[0];
		arrayResultsValues[4] = myAbsorptionSimulation.calculateHeightSimpson()[1];
		arrayResultsValues[5] = myAbsorptionSimulation.calculateHeightErrorPercentageSimpson();
		arrayResultsValues[6] = myAbsorptionSimulation.calculateHeightModifiedSimpson()[0];
		arrayResultsValues[7] = myAbsorptionSimulation.calculateHeightModifiedSimpson()[1];
		arrayResultsValues[8] = myAbsorptionSimulation.calculateHeightErrorPercentageModifiedSimpson();
		arrayResultsValues[9] = arrayResultsOptimizedValues[0][0];
		arrayResultsValues[10] = arrayResultsOptimizedValues[0][1];
		arrayResultsValues[11] = arrayResultsOptimizedValues[0][2];
		arrayResultsValues[12] = arrayResultsOptimizedValues[0][3];
		arrayResultsValues[13] = arrayResultsOptimizedValues[0][4];
		arrayResultsValues[14] = arrayResultsOptimizedValues[0][5];
		arrayResultsValues[15] = arrayResultsOptimizedValues[0][6];
		arrayResultsValues[16] = arrayResultsOptimizedValues[1][0];
		arrayResultsValues[17] = arrayResultsOptimizedValues[1][1];
		arrayResultsValues[18] = arrayResultsOptimizedValues[1][2];
		arrayResultsValues[19] = arrayResultsOptimizedValues[1][3];
		arrayResultsValues[20] = arrayResultsOptimizedValues[1][4];
		arrayResultsValues[21] = arrayResultsOptimizedValues[1][5];
		arrayResultsValues[22] = arrayResultsOptimizedValues[1][6];
		arrayResultsValues[23] = arrayResultsOptimizedValues[2][0];
		arrayResultsValues[24] = arrayResultsOptimizedValues[2][1];
		arrayResultsValues[25] = arrayResultsOptimizedValues[2][2];
		arrayResultsValues[26] = arrayResultsOptimizedValues[2][3];
		arrayResultsValues[27] = arrayResultsOptimizedValues[2][4];
		arrayResultsValues[28] = arrayResultsOptimizedValues[2][5];
		arrayResultsValues[29] = arrayResultsOptimizedValues[2][6];

		return arrayResultsValues;

	}

}
