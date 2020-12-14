public class SimpsonSolver {

	public static double[] calculateNumericalIntegrationSimpson(double numberofPhases, double numberofSteps, double xLowerBound,
			double xUpperBound, double yLowerBound, double yUpperBound, NumericalIntegration myHeight) {
        
		//Step size calculation for the liquid side and gas side.
		double stepSizeX = (xUpperBound - xLowerBound) / numberofSteps;
		double stepSizeY = (yUpperBound - yLowerBound) / numberofSteps;
        
		//Calculation of initial sum and heights.
		double[] sum = new double[(int) numberofPhases];
		
		//An array of heights of size 2, being 1 for the liquid side and gas side.
		double[] Heights = new double[(int) numberofPhases];

		for (int i = 0; i < numberofPhases; i++) {
			sum[i] = myHeight.calculateHeightDZ(xLowerBound, yLowerBound)[i]
					+ myHeight.calculateHeightDZ(xUpperBound, yUpperBound)[i];
		}
        
		//Setting the updated values of x and y to their respective lower bounds.
		double xValue = xLowerBound;
		double yValue = yLowerBound;
        
		//For-loop to compute sums of the Simpson's algorithm.
		for (int i = 1; i < (numberofSteps-1); i++) {
			if (i % 2 == 0) {
				sum[0] = sum[0] + 4.0 * myHeight.calculateHeightDZ(xValue, yValue)[0];
				sum[1] = sum[1] + 4.0 * myHeight.calculateHeightDZ(xValue, yValue)[1];
			}

			else if (i % 2 == 1) {
				sum[0] = sum[0] + 2.0 * myHeight.calculateHeightDZ(xValue, yValue)[0];
				sum[1] = sum[1] + 2.0 * myHeight.calculateHeightDZ(xValue, yValue)[1];
			}
			xValue = xValue + stepSizeX;
			yValue = yValue + stepSizeY;
		}
        //Each height is calculated for with their respective stepsize.
		Heights[0] = (stepSizeX / 3) * sum[0];
		Heights[1] = (stepSizeY / 3) * sum[1];

		return Heights;
	}
}
