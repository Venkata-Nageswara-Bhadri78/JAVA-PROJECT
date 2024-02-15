//	READ README FILE FOR BETTER UNDERSTANDING THIS PROJECT
package CollegeManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/CollegeManagementSystem";
	private static final String username = "root";
	private static final String password = "root";
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		// CREATING DATABASE CONNECTION 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			
			System.out.println("****************************\tWELCOME TO OUR COLLEGE WEBSITE\t******************************");
			int choice = 1;
			while(choice!=0) {
				System.out.println("PLEASE MENTION WHO ARE YOU : (0 for exit)");
				System.out.println("\n1.Student\n2.Teacher\n3.CollegeManagement");
				choice = scan.nextInt();
				switch(choice) {
					case 0 : choice = 0;break;
					case 1 : Student study = new Student(conn,scan);break;
					case 2 : Teacher teach = new Teacher(conn,scan);break;
					case 3 : Administration college = new Administration(conn,scan);break;
					default : System.out.println("INVALID INPUT - ENTER A VALID INPUT");
				}
			}
			conn.close();
		}
		catch(Exception e) {
			System.out.println("Error in entering into College Webpage");
			System.out.println("ERROR : "+e);
		}
		System.out.println("********************************\tSUCESSFULLY EXITED FROM COLLEGE WEBSITE \t\t******************************");
	}
}
