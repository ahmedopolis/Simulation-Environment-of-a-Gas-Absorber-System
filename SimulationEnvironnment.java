import java.io.IOException;
import jxl.write.*;


public class SimulationEnvironnment {

	public SimulationEnvironnment() throws IOException, WriteException {
		//Simulation code.
		UserInputs myUserInputs = new UserInputs();
		UserInputs myUserInputsCopy = new UserInputs(myUserInputs);
		AbsorptionSimulation myAbsorptionSimulation = new AbsorptionSimulation(myUserInputsCopy);
		Optimization myOptimization = new Optimization(myAbsorptionSimulation, myUserInputs);
		UserOutputs myUserOutputs = new UserOutputs(myAbsorptionSimulation, myOptimization, myUserInputs);

	}
}
