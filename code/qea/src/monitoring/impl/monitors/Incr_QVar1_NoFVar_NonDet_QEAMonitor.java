package monitoring.impl.monitors;

import java.util.IdentityHashMap;

import monitoring.impl.configs.NonDetConfig;
import structure.impl.other.Verdict;
import structure.impl.qeas.QVar01_NoFVar_NonDet_QEA;

/**
 * A small-step monitor for a non-deterministic simple QEA
 * 
 * @author Helena Cuenca
 * @author Giles Reger
 */
public class Incr_QVar1_NoFVar_NonDet_QEAMonitor extends
		Abstr_Incr_QVar1_NoFVar_QEAMonitor<QVar01_NoFVar_NonDet_QEA> {

	private IdentityHashMap<Object, NonDetConfig> bindings;

	public Incr_QVar1_NoFVar_NonDet_QEAMonitor(QVar01_NoFVar_NonDet_QEA qea) {
		super(qea);
		bindings = new IdentityHashMap<>();
	}

	@Override
	public Verdict step(int eventName, Object param1) {

		boolean existingBinding = false;
		boolean startConfigFinal = false;
		NonDetConfig config;

		// Determine if the value received corresponds to an existing binding
		if (bindings.containsKey(param1)) { // Existing quantified variable
											// binding

			// Get current configuration for the binding
			config = bindings.get(param1);

			// Assign flags for counters update
			existingBinding = true;
			startConfigFinal = qea.containsFinalState(config);

		} else { // New quantified variable binding

			// Create configuration for the new binding
			config = new NonDetConfig(qea.getInitialState());
		}

		// Compute next configuration
		config = qea.getNextConfig(config, eventName);

		// Update/add configuration for the binding
		bindings.put(param1, config);

		// Determine if there is a final/non-final strong state
		boolean endConfigFinal = checkFinalAndStrongStates(config);

		// Update counters
		updateCounters(existingBinding, startConfigFinal, endConfigFinal);

		return computeVerdict(false);
	}

	@Override
	public Verdict step(int eventName) {
		Verdict finalVerdict = null;
		for (Object binding : bindings.keySet()) {
			// TODO Calling wrong method here
			finalVerdict = step(eventName, binding);
		}
		return finalVerdict;
	}

	@Override
	public String getStatus() {
		String ret = "Map:\n";
		for (IdentityHashMap.Entry<Object, NonDetConfig> entry : bindings
				.entrySet()) {
			ret += entry.getKey() + "\t->\t" + entry.getValue() + "\n";
		}
		return ret;
	}

}
