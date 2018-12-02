package udemy.bankaccountapp;

import java.util.LinkedList;
import java.util.List;

public class BankAccountApp
{
    public static void main(String[] args)
    {
        String file = "src/udemy/bankaccountapp/NewBankAccounts.csv";

        /*
        Checking chkacc1 = new Checking("Tom Wilson", "321456879", 1500);

        Savings savacc1 = new Savings("Rich Lowe", "456657897", 2500);
        savacc1.compound();
        savacc1.showInfo();
        System.out.println("********************");
        chkacc1.showInfo();
        savacc1.deposit(5000);
        savacc1.withdraw(200);
        savacc1.transfer("Brokerage", 3000);
        */

        List<Account> accounts = new LinkedList<Account>();


        // Read a CSV file then create new accounts based on that data
        List<String[]> newAccountHolders = udemy.utilities.CSV.read(file);
        for(String[] accountHolder : newAccountHolders)
        {
            String name = accountHolder[0];
            String sSN = accountHolder[1];
            String accountType = accountHolder[2];
            double initDeposit = Double.parseDouble(accountHolder[3]);
//            System.out.println(name + " " + sSN + " " + accountType + " $" + initDeposit);
            if(accountType.equals("Savings"))
            {
                accounts.add(new Savings(name, sSN, initDeposit));
            }
            else if(accountType.equals("Checking"))
            {
                accounts.add(new Checking(name, sSN, initDeposit));
            }
            else
            {
                System.out.println("ERROR READING ACCOUNT TYPE");
            }
        }

        for(Account acc : accounts)
        {
            System.out.println("\n*********************");
            acc.showInfo();
        }
    }
}
