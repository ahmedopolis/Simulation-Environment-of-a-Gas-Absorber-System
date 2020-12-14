
public class RidderSolver {

	public static double calculateRoot(double xLowerBound, double xUpperBound, double Tolerance, double maxIterations,
			RootFinder myRoot) {
		
		//Setting the error to a large value being 1.00 and initialize the number of iterations to 0.
		double error = 1.00;
		int numberofIterations = 0;
		
        //Initializing XM, XR, newXR.
		double XM = calculateXM(xLowerBound, xUpperBound);
		double XR = calculateXR(xLowerBound, xUpperBound, myRoot);
		double newXR = 0;
        
		//Do-While loop for the computation of the Ridder's algorithm. 
		do {

			if ((XR < XM) && ((myRoot.findX_AI(xLowerBound) * myRoot.findX_AI(XR)) < 0)) {
				xUpperBound = XR;
			} else if ((XR < XM) && (myRoot.findX_AI(XR) * myRoot.findX_AI(XM)) < 0) {
				xLowerBound = XR;
				xUpperBound = XM;
			} else if (XR < XM && (myRoot.findX_AI(XM) * myRoot.findX_AI(xUpperBound)) < 0) {
				xLowerBound = XM;
			}

			else if (XR > XM && (myRoot.findX_AI(xLowerBound) * myRoot.findX_AI(XR)) < 0) {
				xUpperBound = XM;
			} else if (XR > XM && (myRoot.findX_AI(XR) * myRoot.findX_AI(XM)) < 0) {
				xLowerBound = XM;
				xUpperBound = XR;
			} else if (XR > XM && (myRoot.findX_AI(XM) * myRoot.findX_AI(xUpperBound)) < 0) {
				xLowerBound = XR;
			}

			else {
				return xUpperBound;
			}

			newXR = calculateXR(xLowerBound, xUpperBound, myRoot);

			error = Math.abs((newXR - XR) / (newXR));

			numberofIterations++;
			XR = newXR;
			XM = calculateXM(xLowerBound, xUpperBound);
			
			//Do-While Loop flow control to 
		} while (error > Tolerance && numberofIterations < maxIterations);

	    //returning the XR_a
		return XR;
	}
	
	//Method to calculate XR.
	public static double calculateXR(double xLowerBound, double xUpperBound, RootFinder myRoot) {

		double XM = calculateXM(xLowerBound, xUpperBound);
		double numerator = Math.signum((myRoot.findX_AI(xLowerBound) - myRoot.findX_AI(xUpperBound)))
				* myRoot.findX_AI(XM);
		double denominator = Math
				.sqrt(Math.pow(myRoot.findX_AI(XM), 2) - myRoot.findX_AI(xLowerBound) * myRoot.findX_AI(xUpperBound));

		double XR = XM + (XM - xLowerBound) * numerator / denominator;
		return XR;

	}
    
	//Method to calculate XM.
	public static double calculateXM(double xLowerBound, double xUpperBound) {
		return (xLowerBound + xUpperBound) / 2;
	}

	public RidderSolver clone() {
		return new RidderSolver();
	}
}