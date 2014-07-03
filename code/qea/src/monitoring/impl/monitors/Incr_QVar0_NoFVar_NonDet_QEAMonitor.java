package monitoring.impl.monitors;

import monitoring.impl.GarbageMode;
import monitoring.impl.IncrementalMonitor;
import monitoring.impl.RestartMode;
import monitoring.impl.configs.NonDetConfig;
import structure.impl.other.Verdict;
import structure.impl.qeas.QVar01_NoFVar_NonDet_QEA;
import exceptions.ShouldNotHappenException;

/**
 * An incremental propositional monitor for a simple non-deterministic QEA with
 * no quantified variables
 * 
 * @author Helena Cuenca
 * @author Giles Reger
 */
public class Incr_QVar0_NoFVar_NonDet_QEAMonitor extends
		IncrementalMonitor<QVar01_NoFVar_NonDet_QEA> {

	/**
	 * Contains the current configuration (set of states) for the monitor
	 */
	private NonDetConfig config;

	public Incr_QVar0_NoFVar_NonDet_QEAMonitor(RestartMode restart,
			GarbageMode garbage, QVar01_NoFVar_NonDet_QEA qea) {
		super(restart, garbage, qea);

		// Set initial state
		config = new NonDetConfig(qea.getInitialState());
	}

	@Override
	public Verdict step(int eventName, Object[] args) {
		throw new ShouldNotHappenException(
				"Never call propositional QEA with arguments");
	}

	@Override
	public Verdict step(int eventName) {

		if (saved != null) {
			if (!restart())
				return saved;
		}

		// Update configuration
		config = qea.getNextConfig(config, eventName);

		// Determine if there is a final/non-final strong state
		checkFinalAndStrongStates(config, null);

		return computeVerdict(false);
	}

	@Override
	public Verdict end() {
		return computeVerdict(true);
	}

	/**
	 * Computes the verdict for this monitor according to the current state of
	 * the binding(s)
	 * 
	 * @param end
	 *            <code>true</code> if all the events have been processed and a
	 *            final verdict is to be computed; <code>false</code> otherwise
	 * 
	 * @return <ul>
	 *         <li>{@link Verdict#SUCCESS} for a strong success
	 *         <li>{@link Verdict#FAILURE} for a strong failure
	 *         <li>{@link Verdict#WEAK_SUCCESS} for a weak success
	 *         <li>{@link Verdict#WEAK_FAILURE} for a weak failure
	 *         </ul>
	 * 
	 *         A strong success or failure means that other events processed
	 *         after this will produce the same verdict, while a weak success or
	 *         failure indicates that the verdict can change
	 */
	private Verdict computeVerdict(boolean end) {

		Verdict result;

		if (qea.containsFinalState(config)) {
			if (end || finalStrongState) {
				saved = Verdict.SUCCESS;
				result = Verdict.SUCCESS;
			} else {
				result = Verdict.WEAK_SUCCESS;
			}
		}
		if (end || nonFinalStrongState) {
			saved = Verdict.FAILURE;
			result = Verdict.FAILURE;
		} else {
			result = Verdict.WEAK_FAILURE;
		}

		if (qea.isNegated()) {
			result = result.negated();
		}
		return result;
	}

	@Override
	public String getStatus() {
		return "Config: " + config;
	}

	@Override
	protected int removeStrongBindings() {
		// Not applicable to this monitor
		return 0;
	}

	@Override
	protected int ignoreStrongBindings() {
		// Not applicable to this monitor
		return 0;
	}

	@Override
	protected int rollbackStrongBindings() {
		config = new NonDetConfig(qea.getInitialState());
		return 1;
	}

}
