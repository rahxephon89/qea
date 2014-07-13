package benchmark.competition.java.larva.monitoring;

import monitoring.impl.MonitorFactory;
import monitoring.intf.Monitor;
import properties.Property;
import properties.competition.Larva;
import structure.impl.other.Verdict;
import structure.intf.QEA;
import benchmark.competition.java.larva.transactionsystem.Main;
import benchmark.competition.java.larva.transactionsystem.UserAccount;

public aspect LarvaThreeAspect {

	private final Monitor monitor;

	private final int TRANSACTION = 1;

	public LarvaThreeAspect() {
		QEA qea = new Larva().make(Property.LARVA_THREE);
		monitor = MonitorFactory.create(qea);
	}

	pointcut transaction(UserAccount account) :
		(call(void UserAccount.withdraw(double)) ||
		call(void UserAccount.deposit(double))) && target(account);

	after(UserAccount account) : transaction(account) {
		Verdict verdict = monitor.step(TRANSACTION, account.getBalance());
		if (verdict == Verdict.FAILURE) {
			System.err
					.println("Violation in Larva 3. [accountNumber="
							+ account.getAccountNumber()
							+ "] [balance="
							+ account.getBalance()
							+ "]. No account may end up with a negative balance after being accessed.");
			System.exit(0);
		}
	};
	
	pointcut endOfProgram() : execution(void Main.main(String[]));

	after() : endOfProgram() {
		Verdict verdict = monitor.end();
		if (verdict == Verdict.FAILURE || verdict == Verdict.WEAK_FAILURE) {
			System.err
					.println("Violation in Larva 3. No account may end up with a negative balance after being accessed.");
		} else {
			System.err.println("Property Larva 3 satisfied");
		}
	}
}
