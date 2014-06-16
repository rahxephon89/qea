package benchmark.competition.java.larva.monitoring;

import monitoring.impl.MonitorFactory;
import monitoring.intf.Monitor;
import properties.Property;
import properties.competition.Larva;
import structure.impl.other.Verdict;
import structure.intf.QEA;
import benchmark.competition.java.larva.transactionsystem.UserInfo;

public aspect LarvaNineAspect {

	private final Monitor monitor;

	private final int OPEN_SESSION = 1;
	private int CLOSE_SESSION = 2;

	public LarvaNineAspect() {
		QEA qea = new Larva().make(Property.LARVA_NINE);
		monitor = MonitorFactory.create(qea);
	}

	pointcut openSession(UserInfo user) : call(Integer UserInfo.openSession())
		&& target(user);

	pointcut closeSession(UserInfo user) :
		call(void UserInfo.closeSession(Integer)) && target(user);

	before(UserInfo user) : openSession(user) {
		Verdict verdict = monitor.step(OPEN_SESSION, user.getId());
		if (verdict == Verdict.FAILURE || verdict == Verdict.WEAK_FAILURE) {
			System.err
					.println("Violation in Larva 9. [userId="
							+ user.getId()
							+ "]. A user may not have more than 3 active sessions at once.");
		}
	};

	before(UserInfo user) : closeSession(user) {
		Verdict verdict = monitor.step(CLOSE_SESSION, user.getId());
		if (verdict == Verdict.FAILURE || verdict == Verdict.WEAK_FAILURE) {
			System.err
					.println("Violation in Larva 9. [userId="
							+ user.getId()
							+ "]. A user may not have more than 3 active sessions at once.");
		}
	};

}