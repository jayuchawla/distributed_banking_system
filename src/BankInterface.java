import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface BankInterface extends Remote {
	
	public double deposit(int accountNum, double amount) throws RemoteException;
	public double withdraw(int accountNum, double amount) throws RemoteException;
	public double inquiry(int accountNum) throws RemoteException;
	public java.util.List<Transaction> getStatement(int accountNum, Date from, Date to) throws RemoteException;
	public String get_name_by_acc_no(int acc_no) throws RemoteException;
}
