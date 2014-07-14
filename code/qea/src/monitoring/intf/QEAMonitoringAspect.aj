package monitoring.intf;

import monitoring.impl.MonitorFactory;
import structure.impl.other.Verdict;
import structure.intf.QEA;

public abstract aspect QEAMonitoringAspect {

	protected final Monitor monitor;
	protected long startTime;
	protected String validationMsg;
	protected String violationMsg;

	public QEAMonitoringAspect(QEA qea) {
		monitor = MonitorFactory.create(qea);
	}

	pointcut main() : execution(void *.main(String[])) ||
		execution(void *.main(String...));

	before() : main() {
		startTime = System.currentTimeMillis();
	}

	after() : main() {
		Verdict verdict = monitor.end();
		if (verdict == Verdict.WEAK_SUCCESS || verdict == Verdict.SUCCESS) {
			System.err.println(validationMsg);
		} else {
			System.err.println(violationMsg);
		}
		long totalTime = System.currentTimeMillis() - startTime;
		System.err.println(">>Execution time: " + totalTime);
	}

	protected void printTimeAndExit() {
		long totalTime = System.currentTimeMillis() - startTime;
		System.err.println(">>Execution time: " + totalTime);
		System.exit(0);
	}

}