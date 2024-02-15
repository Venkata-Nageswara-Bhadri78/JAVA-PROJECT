package CollegeManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Teacher {
	private static Connection conn;
	private static Scanner scan;
	Teacher(Connection conn, Scanner scan){
		this.conn = conn;
		this.scan = scan;
		
		int choice =1;
		while(choice !=0) {
			System.out.println("\n----------------------------------------\tWELCOME TO TEACHERS WEBSITE\t-------------------------------------\n");
			System.out.println("Select the action You want to Perform : (0 for exit)");
			System.out.println("1.Apply For College \t2.LogIn\n");
			choice = scan.nextInt();
			switch(choice) {
				case 0 : choice=0;break;
				case 1 : ApplyForCollege();break;
				case 2 : teacherLogin();break;
				default : System.out.println("Invalid choice - try again");
			}
		}
		System.out.println("\n---------------------------------SUCESSFULLY EXITED FROM TEACHERS WEBSITE\t-------------------------------------\n");
	}
	//-------------------------------------------	LOGIN METHODS	--------------------------------------------------------------
	
	public static void teacherLogin() {
		System.out.println("Enter your collegeMailID or MobileNumber : ");
		scan.nextLine();
		String clgMail = scan.nextLine();
		System.out.println("Enter Your Password : ");
		String password = scan.nextLine();
		try {
			PreparedStatement state = conn.prepareStatement("select * from TeacherDetails where (collegeMail=? || mobileNumber=?) and password=?;");
			state.setString(1, clgMail);
			state.setString(2, clgMail);
			state.setString(3, password);
			ResultSet set = state.executeQuery();
			if(set.next()) {
				System.out.println("SUCCESSFULLY LOGGED INTO YOUR ACCOUNT");
				logInActions(clgMail);
			}
			else {
				System.out.println("INVALID CREDENTIALS - try Again");
			}
			
		}
		catch(Exception e) {
			System.out.println("Error logging into your account");
			System.out.println("ERROR : "+e);
		}
	}
	public static void logInActions(String teacherName) {
		int choice=1;
		while(choice!=0) {
			System.out.println("Select action you want to perform : (0 for exit)");
			System.out.println("\n1.Salary Update\n");
			choice = scan.nextInt();
			switch(choice) {
				case 0 :choice=0;break;
				case 1 : salaryUpdate(teacherName);break;
				default : System.out.println("Invalid input entered");
			}
		}
		System.out.println("SUCCESSFULLY LOGGED OUT");
	}
	//----------------------------------------------------------LogInActions-----------------------------------------------------------------
	public static void salaryUpdate(String teacherName) {
		try {
			PreparedStatement state = conn.prepareStatement("select salary from teacherDetails where mobileNumber=?");
			state.setString(1, teacherName);
			ResultSet set = state.executeQuery();
			if(set.next() && set.getDouble(1)==0) {
				System.out.println("Salary Credited into your account Sucessfully");
			}
			else {
				System.out.println("Salary Not Credited into your Account - wait for 3 business days");
			}
		}
		catch(Exception e) {
			System.out.println("Error in getting salaryUpdate");
			System.out.println("ERROR : "+e);
		}
	}
	
	//-------------------------------------	APPLY FOR COLLEGE	------------------------------------------------------------------
	public static void ApplyForCollege() {
		System.out.println("-----------------------------------------------\tREGISTRATION PROCESS\t--------------------------------------------\n");
		System.out.println("Enter the required Details : ");
		System.out.println("Enter your FirstName : ");
		scan.nextLine();
		String fName = scan.nextLine();

		Student stu = new Student();
		while(!stu.checkCorrectString(fName)) {
			System.out.println("Invalid FirstName - Name must contain only Alphabets");
			System.out.println("Entr FirstName again  ");
			fName = scan.nextLine();
		}
				
		System.out.println("Enter your LastName : ");
		String lName = scan.nextLine();
		
		while(!stu.checkCorrectString(lName)) {
			System.out.println("Invalid LastName - Name must contain only Alphabets");
			System.out.println("Entr LastName again  ");
			lName = scan.nextLine();
		}
		
		
		String fullName = fName + " "+ lName;
		
		System.out.println("Enter mobile Number : ");
		long mobileNumber = scan.nextLong();
		String Mobile = Long.toString(mobileNumber);
		while(Mobile.length()!=10) {
			System.out.println("Mobile Number must contain 10 digits");
			System.out.println("Enter mobile Number : ");
			mobileNumber = scan.nextLong();
			Mobile = Long.toString(mobileNumber);
		}
		
		System.out.println("Enter your personal Mail Id : ");
		scan.nextLine();
		String personalEmail = scan.nextLine();
		while(!personalEmail.contains("@gmail.com")) {
			System.out.println("Enter Valid Email address");
			System.out.println("Enter your personal Mail Id : ");
			personalEmail = scan.nextLine();
		}
		
		System.out.println("Enter your Experience : (in years)");
		double experience = scan.nextDouble();
		
		System.out.println("Enter your Subject of Intrest : ");
		System.out.println("1.Sanscrit\n2.English\n3.Maths\n4.Physics\n5.Chemistry");
		int subjectChoice = scan.nextInt();
		String subjectString = subjectString(subjectChoice);

		double salary = salaryFinder(subjectChoice);
		System.out.println("Enter Password : ");
		scan.nextLine();
		String password = scan.nextLine();
		while(password.length()<8) {
			System.out.println("Password Must contains 8 characters - try Again");
			System.out.print("Enter Password : ");
			password = scan.nextLine();
		}
		
		String collegeMail = createCollegeMail(subjectString.toLowerCase());

		try {
			PreparedStatement state = conn.prepareStatement("insert into TeacherDetails values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			
			state.setString(1, fName);
			state.setString(2, lName);
			state.setString(3, fullName);
			state.setLong(4, mobileNumber);
			state.setString(5, personalEmail);
			state.setString(6, collegeMail);
			state.setDouble(7, experience);
			state.setString(8, subjectString);
			state.setDouble(9, salary);
			state.setString(10, password);
			state.executeUpdate();
			System.out.println("\nSuccessfully Registered Your Data\n");
			System.out.println("\nYour Salary : "+salary);
			System.out.println("\nYour CollegeMailID : "+collegeMail);
		}
		catch(Exception e) {
			System.out.println("Error in Submitting your data");
			System.out.println("ERROR - "+e);
		}
	}
	public static String createCollegeMail(String subject) {
		//format 2020ece.r19@svce.edu.in
		// eg : year+subject+.+r+uniqueNumber/RandomNumber + @svce.edu.in
		String mail = "2024" + subject+".r"+((int)(Math.random()*100))+"clgName.edu.in";
		return mail;
	}
	public static String subjectString(int choice) {
		switch(choice) {
			case 1 : return "SANSKRIT";
			case 2 : return "ENGLISH";
			case 3 : return "MATHS";
			case 4 : return "PHYSICS";
			case 5 : return "CHEMISTRY";
		}
		return "not a teacher";
	}
	public static double salaryFinder(int choice) {
		if(choice==1 ||choice==2) {
			return 40000;
		}
		else if(choice==3) {
			return 75000;
		}
		else {
			return 30000;
		}
	}
}
