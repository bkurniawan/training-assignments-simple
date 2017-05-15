package eu.sig.training.ch04;

// tag::CheckingAccount[]
public class CheckingAccount {
    private static final float INTEREST_PERCENTAGE = 0.01f;
    private Money balance = new Money();
    private int transferLimit = 100;

    public Transfer makeTransfer(String counterAccount, Money amount)
        throws BusinessException {
        checkWithdrawalLimit(amount);
        // 2. Assuming result is 9-digit bank account number, validate 11-test:
        int sum = 0;
        for (int i = 0; i < counterAccount.length(); i++) {
            char character = counterAccount.charAt(i);
            int characterValue = Character.getNumericValue(character);
            sum = sum + (9 - i) * characterValue;
        }
        if (sum % 11 == 0) {
            return lookUpCounterAccAndTransferObject(counterAccount, amount);
        } else {
            throw new BusinessException("Invalid account number!");
        }
    }




	private Transfer lookUpCounterAccAndTransferObject(String counterAccount, Money amount) {
		CheckingAccount acct = Accounts.findAcctByNumber(counterAccount);
		Transfer result = new Transfer(this, acct, amount);
		return result;
	}




	private void checkWithdrawalLimit(Money amount) throws BusinessException {
		if (amount.greaterThan(this.transferLimit)) {
            throw new BusinessException("Limit exceeded!");
        }
	}
    
    
   

    public void addInterest() {
        Money interest = balance.multiply(INTEREST_PERCENTAGE);
        if (interest.greaterThan(0)) {
            balance.add(interest);
        } else {
            balance.substract(interest);
        }
    }
}
// end::CheckingAccount[]
