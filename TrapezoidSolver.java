
public class TrapezoidSolver {
 
 public static double[] calculateNumericalIntegrationTrapezoid(double numberofPhases, double numberofSteps, double xLowerBound,
   double xUpperBound, double yLowerBound, double yUpperBound, NumericalIntegration myHeight) {
  
  //Code for Trapezoid.
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

  for (int i = 1; i < (numberofSteps-1); i++) {
   sum[0] = sum[0] + 2.0 * myHeight.calculateHeightDZ(xValue, yValue)[0];
   sum[1] = sum[1] + 2.0 * myHeight.calculateHeightDZ(xValue, yValue)[1];
   
   xValue = xValue + stepSizeX;
   yValue = yValue + stepSizeY;
  }

  Heights[0] = (stepSizeX / 2) * sum[0];
  Heights[1] = (stepSizeY / 2) * sum[1];
  
  return Heights;
 }

}
