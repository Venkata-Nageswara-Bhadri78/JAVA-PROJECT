package CollegeManagementSystem;

import java.util.Scanner;
import java.sql.*;

public class Administration {
	private static Connection conn;
	private static Scanner scan;
	Administration(Connection conn, Scanner scan){
		this.conn = conn;
		this.scan = scan;
		int choice=1;
		while(choice!=0) {
			System.out.println("\nSelect your Action (0 for exit)\n");
			System.out.println("1.Get Teachers List\t2.GetStudents List\t3.payTeachersSalary\t4.checkCollegeBankBalance\n");
			choice = scan.nextInt();
			switch(choice) {
				case 0 : choice=0;break;
				case 1 : getTeachersList();break;
				case 2 : getStudentsList();break;
				case 3 : payTeachersSalary();break;
				case 4 : checkCollegeBankBalance();break;
				default : System.out.println("invalid entry - try again");
			}
		}
	}
	
	public static void payTeachersSalary() {
		System.out.println("Enter teacher mobileNumber to pay his/her Salary : ");
		long mobNumber = scan.nextLong();
		try {
			PreparedStatement state = conn.prepareStatement("select fullName,salary from TeacherDetails where mobileNumber=?;");
			state.setLong(1, mobNumber);
			ResultSet set = state.executeQuery();
			set.next();
			double ans =set.getDouble(2);
			if(ans==0) {
				System.out.println("SALARY ALREADY PAID TO TEACHER");return;
			}
			PreparedStatement s = conn.prepareStatement("select amount from collegeAmount;");
			ResultSet amount = s.executeQuery();
			amount.next();
			if(ans>amount.getDouble(1)) {
				System.out.println("inSufficient Amount to Pay salry for Teacher - Renew/Credit Amount for paying Salary");
				return;
			}
			
			PreparedStatement stat = conn.prepareStatement("update TeacherDetails set salary=0 where mobileNumber=?");
			stat.setLong(1, mobNumber);
			stat.executeUpdate();
			System.out.println("Payment Successful to "+set.getString(1).toUpperCase()+"'s Account");
			
			state.executeUpdate("update collegeAmount set amount=(amount-"+ans+");");
			System.out.println("AMOUNT SUCESSFULLY DEBITED FROM COLLEGE ACCOUNT");
			
		}catch(Exception e) {
			//System.out.println("Error in Salary Payment try again");
			System.out.println("INVALID MOBILE NUMBER - try Again");
			//System.out.println("ERROR : "+e);
		}
	}
	

	public static void getTeachersList() {
		try {
			PreparedStatement state = conn.prepareStatement("select FullName, mobileNumber, personalMail, collegeMail, subjectName from teacherDetails;");
			ResultSet set = state.executeQuery();
			while(set.next()) {
				System.out.println(set.getString(1)+"\t\t\t"+set.getLong(2)+"\t\t\t"+set.getString(3)+"\t\t\t"+set.getString(4)+"\t\t\t"+set.getString(5));
			}
		}catch(Exception e) {
			System.out.println("Error in getting teachersList - try again");
			System.out.println("ERROR : "+e);
		}
	}

	
	public static void getStudentsList() {
//	| firstName | lastName  | fullName          | email            | dateOfBirth | fatherName          | course | feeAmount | studentId  | password  |

		try {
			PreparedStatement state = conn.prepareStatement("select fullName, email, dateOfBirth, fatherName, course, feeAmount, studentId from studentDetails;");
			ResultSet set = state.executeQuery();
			while(set.next()) {
				System.out.println(set.getString(1)+"\t"+set.getString(2)+"\t"+set.getString(3)+"\t"+set.getString(4)+"\t"+set.getString(5)+"\t"+set.getDouble(6)+"\t"+set.getString(7));
			}
		}
		catch(Exception e) {
			System.out.println("Error in getting Student Details");
			System.out.println("ERROR : "+e);
		}
	}
	
	public static void checkCollegeBankBalance() {
		try {
			PreparedStatement state = conn.prepareStatement("select amount from collegeAmount;");
			ResultSet set = state.executeQuery();
			set.next();
			System.out.println("COLLEGE BALANCE : "+set.getDouble(1));
			
		}
		catch(Exception e){
			System.out.println("Error in fetching college balance");
			System.out.println("ERROR : "+e);
		}
	}
}
