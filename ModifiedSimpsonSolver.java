
public class ModifiedSimpsonSolver {
	
	//Modified-Simpson class.
	public static double[] calculateNumericalIntegrationModifiedSimpson(double numberofPhases, double numberofSteps, double xLowerBound,
			double xUpperBound, double yLowerBound, double yUpperBound, NumericalIntegration myHeight) {

		double stepSizeX = (xUpperBound - xLowerBound) / numberofSteps;
		double stepSizeY = (yUpperBound - yLowerBound) / numberofSteps;

		double[] sum = new double[(int) numberofPhases];
		double[] Heights = new double[(int) numberofPhases];

		for (int i = 0; i < numberofPhases; i++) {
			sum[i] = myHeight.calculateHeightDZ(xLowerBound, yLowerBound)[i]
					+ myHeight.calculateHeightDZ(xUpperBound, yUpperBound)[i];
		}

		double xValue = xLowerBound;
		double yValue = yLowerBound;

		for (int i = 0; i <= (numberofSteps); i++) {
			if ((i == 0) || (i == numberofSteps)) {
				sum[0] = sum[0] + 17 * myHeight.calculateHeightDZ(xValue, yValue)[0];
			    sum[1] = sum[1] + 17 * myHeight.calculateHeightDZ(xValue, yValue)[1];
			    }
			else if ((i == 1) || (i == ((int) numberofSteps - 1))) {
				sum[0] = sum[0] + 59 * myHeight.calculateHeightDZ(xValue, yValue)[0];
			    sum[1] = sum[1] + 59 * myHeight.calculateHeightDZ(xValue, yValue)[1];
			    }
			else if ((i == 2) || (i == ((int) numberofSteps - 2))) {
				sum[0] = sum[0] + 43 * myHeight.calculateHeightDZ(xValue, yValue)[0];
			    sum[1] = sum[1] + 43 * myHeight.calculateHeightDZ(xValue, yValue)[1];
			    }
			else if ((i == 3) || (i == ((int) numberofSteps - 3))) {
				sum[0] = sum[0] + 43 * myHeight.calculateHeightDZ(xValue, yValue)[0];
				sum[1] = sum[1] + 43 * myHeight.calculateHeightDZ(xValue, yValue)[1];
				}
			else {
				sum[0] = sum[0] + 48 * myHeight.calculateHeightDZ(xValue, yValue)[0];
				sum[1] = sum[1] + 48 * myHeight.calculateHeightDZ(xValue, yValue)[1]; 
				}
			xValue = xValue + stepSizeX;
			yValue = yValue + stepSizeY;
		}

		Heights[0] = (stepSizeX / 48) * sum[0];
		Heights[1] = (stepSizeY / 48) * sum[1];

		return Heights;
	}

}
