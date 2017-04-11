package banking.primitive.core;

public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	private int numWithdraws = 0;
	private final float MIN = 0.0f;
	private final float DEPOSITFEE = 0.50f;
	private final float WITHDRAWFEE = 1.0f;
	private final int MAXFREEWITHDRAWS = 3;

	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > MIN) {
			balance = balance + amount - DEPOSITFEE;
			if (balance >= MIN) {
				setState(State.OPEN);
			}
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > MIN) {
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > MAXFREEWITHDRAWS)
				balance = balance - WITHDRAWFEE;
			// KG BVA: should be < 0
			if (balance <= MIN) {
				setState(State.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	public String getType() { return "Checking"; }

	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
