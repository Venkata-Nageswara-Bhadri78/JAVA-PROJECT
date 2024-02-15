package CollegeManagementSystem;
import java.sql.*;
import java.util.Scanner;
public class Student {
	private static Connection conn;
	private static Scanner scan;
	Student(){
		
	}
	Student(Connection conn, Scanner scan){
		this.conn = conn;
		this.scan = scan;
		
		int choice=1;
		while(choice!=0) {
			System.out.println("\n-------------------------------------\tWELCOME TO STUDENTS SITE\t-------------------------------------------\n");
			System.out.println("Select Action you want to Perform : (0 for exit)");
			System.out.println("\n1.Register\t2.Log In");
			choice = scan.nextInt();
			switch(choice) {
				case 0 : choice=0;break;
				case 1 : Register(scan);break;
				case 2 : LogIn(scan);break;
			}
		}
		System.out.println("\n-------------------------------------------\tSUCESSFULLY EXITED FROM STUDENT SITE\t-------------------------------\n");
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	
	public static boolean checkCorrectString(String str) {
		//small letters : 97 - 122
		//Capital Letters : 65 - 90
		int letterAscii;
		for(int i=0;i<str.length();i++) {
			letterAscii = (int)(str.charAt(i));
			if((letterAscii>96 && letterAscii<123) || (letterAscii>64 && letterAscii<91) || str.charAt(i)==' ') {
				
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	//----------------------------------------------	REGISTER	------------------------------------------------------------------
	public static void Register(Scanner scan) {
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>\tREGISTRATION PROCESS\t<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		
		System.out.println("Enter required Details to Register : \n");
		System.out.print("Enter your FirstName : \t\t");
		scan.nextLine();
		String firstName = scan.nextLine();
		while(!checkCorrectString(firstName)) {
			System.out.println("Invalid Name - Name should contain only Alphabet Letter");
			System.out.print("Enter your FirstName Again : ");
			firstName = scan.nextLine();
		}
		System.out.print("Enter Your LastName : \t\t");
		String lastName = scan.nextLine();
		
		while(!checkCorrectString(lastName)) {
			System.out.println("Invalid Name - Name should contain only Alphabet Letter");
			System.out.print("Enter your LasttName Again : ");
			lastName = scan.nextLine();
		}
		
		String fullName = firstName +" "+lastName;
		
		System.out.print("Enter your Personal Email ID : \t\t");
		String email;
		while(true) {
			email = scan.nextLine();
			if(!email.contains("@gmail.com")) {
				System.out.println("Enter Valid Email Address again : ");
				System.out.print("Enter your Personal Email ID : \t\t");
			}
			else {
				break;
			}
		}
		
		System.out.print("Enter Your Date Of Birth(format : dd/mm/yyy) : ");
		String dateOfBirth = scan.nextLine();
		
		System.out.print("Enter Your Father Name : \t");
		String fatherName = scan.nextLine();
		
		
		System.out.print("Enter your course : (String)\n");
		System.out.println("MPC\tBiPC\tMBiPC    : \t");
		String course = scan.nextLine();
		
		double feeAmount = courseFeeDetails(course);
		
		String studentId = createStudentID();
		
		System.out.print("Enter Password : \t\t");		
		String passwordString="";
		while(true) {
			passwordString = scan.nextLine();
			if(passwordString.length()<8) {
				System.out.println("Password must contains 8 Characters - Enter again");
				System.out.print("Enter Password : ");
			}
			else {
				break;
			}
		}
		
		try {
			PreparedStatement state = conn.prepareStatement("insert into StudentDetails values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			state.setString(1, firstName);
			state.setString(2, lastName);
			state.setString(3, fullName);
			state.setString(4, email);			
			state.setString(5, dateOfBirth);
			state.setString(6, fatherName);
			state.setString(7, course);
			state.setDouble(8, feeAmount);
			state.setString(9, studentId);
			state.setString(10, passwordString);
			state.executeUpdate();
			System.out.println("\nYou have Successfully Registered\n");
			System.out.println("\nDetails need to Remember : \n");
			System.out.println("\nYOUR STUDENT ID CODE : "+studentId);
			System.out.println("\nyour courseFee need to pay : "+feeAmount);
			System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>\tREGISTRATION COMPLETED SUCESSFULLY\t<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			
		}
		catch(Exception e) {
			System.out.println("\nError in Registration \n");
			System.out.println("ERROR : "+e);
		}
		
		
	}
	public static double courseFeeDetails(String course) {
		//1 - MPC, 2-BIPC, 3-MBiPC
		int choice=0;
		if(course.equalsIgnoreCase("MPC")) {
			choice = 1;
		}
		else if(course.equalsIgnoreCase("BiPC")) {
			choice = 2;
		}
		else {
			choice = 3;
		}
		switch(choice) {
			case 1 : return 50000;
			case 2 : return 50000;
			case 3 : return 100000;
			default : System.out.println("Invalid Entry");
		}
		return 0;
	}
	public static String createStudentID() {
		String caps = "ABCDEFGHIJKLMNOPQRSTUNVWXY";
		String nums = "0123456789";
		String syms = "@#$%&";
		//PASSWORD Format - Sham@123 	--	Lamb$345	--		Dumb%348;
		String password = "";
		char digit;
		int randomNumber;
		for(int i=1 ; i<=10;i++) {
			if(i<=5) {
				randomNumber = (int)(Math.random()*caps.length());
				digit = caps.charAt(randomNumber);
				if(i!=1) {
					digit = Character.toLowerCase(digit);
				}
				
			}
			else if(i==6) {
				randomNumber = (int)(Math.random()*syms.length());
				digit = syms.charAt(randomNumber);
			}
			else {
				randomNumber = (int)(Math.random()*nums.length());
				digit = nums.charAt(randomNumber);
			}
			password += digit;
		}
		return password;
	}

	//---------------------------------------------		LOGIN METHODS 	   ------------------------------------------------------------------
											        //LOGIN RELATED METHODS

	public static void LogIn(Scanner scan) {
		System.out.print("Enter your Student ID CODE or personal Email : ");
		scan.nextLine();
		String idCode = scan.nextLine();
		System.out.print("Enter Your Password : \t\t\t\t");
		String password = scan.nextLine();
		try {
			PreparedStatement state = conn.prepareStatement("select * from studentDetails where ((studentID=? or email=?) and password =?);");
			state.setString(1, idCode);
			state.setString(2, idCode);
			state.setString(3, password);
			ResultSet set = state.executeQuery();
			if(set.next()) {
				System.out.println("\nLOGIN SUCESSFUL\n");
				System.out.println("\nHAI "+set.getString(3).toUpperCase()+" WELCOME TO OUR COLLEGE WEBSITE\n");
				studentWebActions(set, set.getDouble(8));
			}
			else {
				System.out.println("\nINVALID CREDENTIALS - try Again\n");
			}
		}
		catch(Exception e) {
			System.out.println("Error in Logging into your Account - try again");
			System.out.println("ERROR : "+e);
		}
		
	}
	public static void studentWebActions(ResultSet set, double d) {
		int choice=1;
		while(choice!=0) {
			System.out.println("What Actions You want to Perform : (0 for logOut)");
			System.out.println("1.PayCollegeFee\t\t2.CheckBalanceFee\t3.DisplayProfile\n");
			choice = scan.nextInt();

			switch(choice) {
				case 0 : choice=0;System.out.println("\nSUCESSFULLY LOGGED OUT YOUR ACCOUNT\n");break;
				case 1 : try{PayCollegeFee(checkBalance(set.getString(4)));}catch(Exception e) {};break;
				case 2 : try{System.out.println("\nYOUR DUE AMOUNT : "+checkBalance(set.getString(4)));}catch(Exception e){};break;
				case 3 : DisplayProfile(set);break;
				default : System.out.println("\nINVALID ENTRY ENTERED - TRY AGAIN\n");
			}
		}
	}
	public static double checkBalance(String email) {
		System.out.println("EMAIL : "+email);
		String url="jdbc:mysql://localhost:3306/CollegeManagementSystem";
		String username = "root";
		String password = "root";
		
		double dueAmount=0;
		try{
			Connection conn = DriverManager.getConnection(url,username,password);
			Statement state = conn.createStatement();
			
			ResultSet s = state.executeQuery("select feeAmount from StudentDetails where email='"+email+"';");
			s.next();
			dueAmount=s.getDouble(1);
			conn.close();
		}
		catch(Exception e){
			System.out.println("Error in fetching Balance Due");
			System.out.println("ERROR : "+e);
		}
		return dueAmount;
	}
	/*
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| firstName   | varchar(100) | NO   |     | NULL    |       |
| lastName    | varchar(100) | NO   |     | NULL    |       |
| fullName    | varchar(255) | NO   |     | NULL    |       |
| email       | varchar(255) | NO   | PRI | NULL    |       |
| dateOfBirth | varchar(10)  | NO   |     | NULL    |       |
| fatherName  | varchar(255) | NO   |     | NULL    |       |
| course      | varchar(5)   | NO   |     | NULL    |       |
| feeAmount   | double       | NO   |     | NULL    |       |
| studentId   | varchar(255) | NO   | UNI | NULL    |       |
| password    | varchar(255) | NO   |     | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
	*/
	public static void DisplayProfile(ResultSet set) {
		try {
			System.out.println("\n1. FirstName : \t"+set.getString(1));
			System.out.println("2. LastName  : \t"+set.getString(2));
			System.out.println("3. FullName  : \t"+set.getString(3));
			System.out.println("4. email     : \t"+set.getString(4));
			System.out.println("5. D/Birth   : \t"+set.getString(5));
			System.out.println("6. FatherName: \t"+set.getString(6));
			System.out.println("7. course    : \t"+set.getString(7));
			System.out.println("8. feeBalance: \t"+checkBalance(set.getString(4)));
			System.out.println("9. studentID : \t"+set.getString(9));
			System.out.println("10.Password  : \t"+set.getString(10));
			System.out.println();
		}
		catch(Exception e) {
			System.out.println("Error in showing your details");
			System.out.println("Error : "+e);
		}
	}
	public static void PayCollegeFee(double due) {
		System.out.println("YOU FEE DUE IS "+due);
		System.out.println("Enter Amount You want to Pay : ");
		double amount = scan.nextDouble();
		if(amount<0 || amount>due) {
			System.out.println("Enter Valid Amount");
			PayCollegeFee(due);
		}
		else {
			try {
				PreparedStatement state = conn.prepareStatement("update StudentDetails set feeAmount=? where feeAmount=?");
				state.setDouble(1, due-amount);
				state.setDouble(2, due);
				state.executeUpdate();
				System.out.println("\nPAYMENT SUCESSFUL\n");
				//adding amount to college Amount
				PreparedStatement stat = conn.prepareStatement("update collegeAmount set amount = ?;");
				PreparedStatement s = conn.prepareStatement("select*from collegeAmount;");
				ResultSet se = s.executeQuery();se.next();
				double d = se.getDouble(1);
				stat.setDouble(1, d+amount);
				stat.executeUpdate();
				System.out.println("\nAmount sucessfully credited to CollegeAdministraion\n");
			}
			catch(Exception e) {
				System.out.println("ERROR in Paying your College FEE - try Again");
				System.out.println("ERROR : "+e);
			}
			
		}
	}
	//-------------------------------------------------------------------------------------------------------------------------------
	
}
