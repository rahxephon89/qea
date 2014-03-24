package monitoring.impl.monitors;

import monitoring.impl.IncrementalMonitor;
import monitoring.impl.configs.DetConfig;
import structure.impl.other.Verdict;
import structure.impl.qeas.QVar01_FVar_Det_QEA;

/**
 * An incremental monitor for a non-simple QEA with no quantified variables
 * 
 * @author Helena Cuenca
 * @author Giles Reger
 */
public class Incr_QVar0_FVar_Det_QEAMonitor extends
		IncrementalMonitor<QVar01_FVar_Det_QEA> {

	/**
	 * Current configuration (state) for the monitor
	 */
	private DetConfig config;

	public Incr_QVar0_FVar_Det_QEAMonitor(QVar01_FVar_Det_QEA qea) {
		super(qea);
		config = new DetConfig(qea.getInitialState(), qea.newBinding());
	}

	@Override
	public Verdict step(int eventName, Object[] args) {

		// Update configuration
		config = qea.getNextConfig(config, eventName, args, null);
		return computeVerdict();
	}

	@Override
	public Verdict step(int eventName) {
		return step(eventName, new Object[] {});
	}

	@Override
	public Verdict end() {
		return computeVerdict();
	}

	private Verdict computeVerdict() {

		if (qea.isStateFinal(config.getState())) {
			if (qea.isStateStrong(config.getState())) {
				return Verdict.SUCCESS;
			}
			return Verdict.WEAK_SUCCESS;
		} else {
			if (qea.isStateStrong(config.getState())) {
				return Verdict.FAILURE;
			}
			return Verdict.WEAK_FAILURE;
		}
	}

	@Override
	public String getStatus() {
		return "Config: " + config;
	}

}
