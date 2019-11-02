/////SERVER

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// bank acts as the remote server that the client (ATM) connects to
public class Bank extends UnicastRemoteObject implements BankInterface {
	
	private static final long serialVersionUID = -8317765732411101420L;
	
	// users accounts
	private static List<Account> accounts = new ArrayList<Account>();
	
	public Bank () throws RemoteException{
		super();
	}

	@Override
	public double deposit(int accountNum, double amount) throws RemoteException {
		// the deposit function will be called by the remote client (ATM).
		// increase the balance in the account with account number 'accountNum' by 'amount'.
		for (int i=0; i<accounts.size(); i++){
			Account element = accounts.get(i);
			if (element.getAccountNum() == accountNum){
				element.setBalance(element.getBalance() + amount); 
				element.addTransaction("Deposit", amount);
				return element.getBalance();
			}
		}
		return 0.00;
	}

	@Override
	public double withdraw(int accountNum, double amount) throws RemoteException {
		// reduce the amount in the account with account number 'accountNum' by 'amount'
		for (int i=0; i<accounts.size(); i++){
			Account element = accounts.get(i);
			if (element.getAccountNum() == accountNum){
				element.setBalance(element.getBalance() - amount); 
				element.addTransaction("Withdraw", amount);
				return element.getBalance();
			}
		}
		return 0.00;
	}

	@Override
	public double inquiry(int accountNum) throws RemoteException {
		// returns the balance of the account with account number 'accountNum'
		for (int i=0; i<accounts.size(); i++){
			Account element = accounts.get(i);
			if (element.getAccountNum() == accountNum){
				return element.getBalance(); 
			}
		}
		return 0.00; 
	}

	@Override
	public List<Transaction> getStatement(int accountNum, Date fromDate, Date toDate) throws RemoteException {
		
		List<Transaction> statementList = new ArrayList<Transaction>();
		
		for (int i=0; i<accounts.size(); i++){
			Account element = accounts.get(i);
			if (element.getAccountNum() == accountNum){
				return element.getTransactionsByDate(fromDate, toDate);
			}
		}
		return statementList;
	}
	
	@Override
	public String get_name_by_acc_no(int acc_no) throws RemoteException{
		
		String name = null;
		if(accounts.contains(acc_no)==false)
		{
			return "-1";
		}
		else
		{
			for(int i = 0;i<accounts.size();i++)
			{
				if(accounts.get(i).getAccountNum()==acc_no)
				{
					return accounts.get(i).getAccountName();
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new RMISecurityManager());
//			System.out.println("Security manager set"); 
//		}
		
		@SuppressWarnings("unused")
		Registry registry = LocateRegistry.createRegistry(1099);
		System.setProperty("java.security.policy","file:test.policy");
		
		// Create an instance of the local object 
		Bank bankServer = new Bank(); 
		System.out.println("Instance of Bank created"); 
		
		// Put the server object into the Registry
		Naming.rebind("BankInterface", bankServer); 
		System.out.println("Name rebind completed"); 
		System.out.println("Server ready for requests!"); 
		
		// setup some test accounts with various balances.
		Account account1 = new Account(100, "Jerry Jones", 10000);
		Account account2 = new Account(101, "Mary Maloney", 5000);
		Account account3 = new Account(102, "Ciaran Carey", 540);
		Account account4 = new Account(103, "Francis Farnan", 1000);
		Account account5 = new Account(104, "Ursela Upton", 13400);
		
		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		accounts.add(account4);
		accounts.add(account5);
		
	}

	
}