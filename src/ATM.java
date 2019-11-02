////CLIENT
import java.rmi.Naming;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ATM {

	private static BankInterface bankInterface;
	
	// decimal formatting to 2 decimal places
	private static DecimalFormat precision2 = new DecimalFormat("0.00");

	/**
	 * @param args[0] = operator (e.g. deposit, withdrawal, inquiry, statement)
	 * @param args[1] = accountNum 
	 */
	
	
	public static void main(String[] args) throws Exception{
		
		// connecting to remote server
		bankInterface = (BankInterface) Naming.lookup("rmi://192.168.0.3/BankInterface");
		
	//	int acc_no = bankInterface.get
	//	String acc_name = 
		switch (args[0]) {
		
			/**
			 * args[2] = amount
			 */
		case "deposit":
			double resultDeposit = bankInterface.deposit(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			System.out.println("Current balance after deposit: " + precision2.format(resultDeposit));
			break;
		
			/**
			 * args[2] = amount
			 */	
		case "withdraw":
			double resultWithdrawal = bankInterface.withdraw(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			System.out.println("Current balance after withdrawal: €" + precision2.format(resultWithdrawal));
			break;

		case "inquiry":
			double resultInquiry = bankInterface.inquiry(Integer.parseInt(args[1]));
			System.out.println("Current balance inquiry: €" + precision2.format(resultInquiry));
			break;
			
			/**
			 * args[2] = fromDate (beginning of time period for statement request)
			 * args[3] = toDate (beginning of time period for statement request)
			 */
		case "statement":
			Date fromDate = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(args[2]);
			Date toDate = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(args[3]);
			
			java.util.List<Transaction> statementList = 
					bankInterface.getStatement(Integer.parseInt(args[1]), fromDate, toDate);
			
			for (int i=0; i<statementList.size(); i++) {
				Transaction element = statementList.get(i);
				System.out.println(element.toString());
			}
			break;
		}
	}
}