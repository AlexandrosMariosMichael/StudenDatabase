


package ProjVasis;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;


public class DbApp {
	Connection conn;
	boolean exit;
	
	public DbApp() {
		conn = null;
	}
	
	
	public void dbConnect (String ip, int port, String database, String username, String password) {
		try {
			// Check if postgres driver is loaded
     		Class.forName("org.postgresql.Driver");
     		// Establish connection with the database
     		conn = DriverManager.getConnection("jdbc:postgresql://"+ip+":"+port+"/"+database,username,password);
     		System.out.println("Connection Established!");
     		// Disable autocommit.
     		conn.setAutoCommit(false);
     		
		} catch(Exception e) {
            e.printStackTrace();
		}
	}
	
	public void db_commit() {
		try {
			// Commit all changes
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void db_abort() {
		try {
			// Rollback all changes
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForEnter() {
		Scanner scn = new Scanner(System.in);
		System.out.println("Press Enter..");
		scn.nextLine();  
	}
	
	
	private int getNum() {
		Scanner k2 = new Scanner(System.in);
		int choice2 = -1;
		while(choice2 < 0){
			try {
				
				choice2 = Integer.parseInt(k2.nextLine());
			}
			catch (NumberFormatException e ){
				System.out.println("Insert number gtreater than 0!");
			}	
		}
	return choice2;
}
	private String getString() {
		Scanner k2 = new Scanner(System.in);
		String choice2;
				
				choice2 = k2.nextLine();
			
		
	return choice2;
}
	
	public void insertDiploma() {
		try {
			// Create a SQL query template with parameters
			PreparedStatement pst = conn.prepareStatement("SELECT insert_diploma1_4(?,?,?,?,?)");
		
				System.out.print("\nInsert Profesor Amka: ");
				pst.setInt(1,getNum());	// Assign a value in nd parameter (?)
				System.out.print("\nInsert Profesor Amka: ");
				pst.setInt(2,getNum());	// Assign a value in 3rd parameter (?)
				System.out.print("\nInsert Profesor Amka: ");
				pst.setInt(3,getNum()); // Assign a value in 1st parameter (?)
				System.out.print("\nInsert Student AM: ");
				pst.setInt(4,getNum());
				System.out.print("\nInsert Thesis Title: ");
				pst.setString(5,getString());
				
				pst.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void showStudentgrade() {
		try {
			//create temp table  or temp view
			
			PreparedStatement pst = conn.prepareStatement("CREATE TEMP TABLE temp ON COMMIT DROP AS  SELECT final_grade,lab_grade,exam_grade FROM  \"Register\" , (SELECT amka FROM \"Student\" WHERE am=?) as s WHERE course_code=? AND s_amka=s.amka");
				System.out.print("\nInsert Student AM: ");
				pst.setInt(1,getNum());
				System.out.print("\nInsert Course Code: ");
				pst.setString(2,getString());
				pst.execute();
			
			////Show
				// ΤΗΛ 303
				// 2017000003
				
			System.out.print("\n Final Grade| Lab Grade | Exam Grade \n ");
			
			Statement st = conn.createStatement();
			// Execute a simple query
			ResultSet rs = st.executeQuery("SELECT * FROM  \"temp\"");
			while (rs.next()) {
			if(rs.getString(1)!=null)
			System.out.println(rs.getString(1)+"  |  "+rs.getString(2)+"  |  "+rs.getString(3));
			}
			
			//PreparedStatement pst2 = conn.prepareStatement("DROP TEMP TABLE temp");
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void changeStudentgrade(){
		try {
				PreparedStatement pst = conn.prepareStatement("UPDATE  \"Register\" r SET final_grade=? FROM (SELECT amka FROM \"Student\" WHERE am=?) as s2,(SELECT course_code,semester_id FROM \"CourseRun\" WHERE course_code=? and serial_num=?)as s WHERE r.course_code=s.course_code AND r.semester_id=s.semester_id AND r.s_amka=s2.amka ");
			
				System.out.print("\nInsert Student AM: ");
				pst.setInt(2,getNum());
				System.out.print("\nInsert Course Code:  ");
				pst.setString(3,getString());
				System.out.print("\nInsert Course Serial Number: ");
				pst.setInt(4,getNum());
				System.out.print("\nInsert Final Grade:  ");
				pst.setInt(1,getNum());
				
				pst.execute();
			
				//  ΗΡΥ 415
				//  2017000001
				//  181858
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showPerson(){
		try {
			PreparedStatement pst = conn.prepareStatement("CREATE TEMP TABLE temp ON COMMIT DROP AS  SELECT * FROM  show_student_data2_3() WHERE left(surname,?)=? ");
			System.out.println("\nInsert Starting Surname: ");
			String temp1 = getString();
			pst.setInt(1,temp1.length());
			pst.setString(2,temp1);
			
			
			pst.execute();
			
			System.out.print("\n  NAME |  SURNAME |  DEFINITION \n ");
			
			Statement st = conn.createStatement();
			// Execute a simple query
			ResultSet rs = st.executeQuery("SELECT *FROM  \"temp\"");
			while (rs.next()) {
			if(rs.getString(1)!=null)
			System.out.println(rs.getString(1)+"  |  "+rs.getString(2)+"  |  "+rs.getString(3));
			}
			
			//PreparedStatement pst2 = conn.prepareStatement("DROP TEMP TABLE temp");
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showStudentgradeanalysis(){
		try {
			
			PreparedStatement pst = conn.prepareStatement("CREATE TEMP TABLE temp ON COMMIT DROP AS  SELECT * FROM  \"Register\" , (SELECT amka FROM \"Student\" WHERE am=?) as s WHERE s_amka=s.amka");
			System.out.print("\nInsert Student AM: ");
			pst.setInt(1,getNum());
			pst.execute();
		
		////Show
			// ΤΗΛ 303
			// 2017000003
			//181833
			
		System.out.print("\n Lab Grade | Final Grade| Register Status| AMKA | Course Code | Exam Grade | Semester ID \n ");
		
		Statement st = conn.createStatement();
		// Execute a simple query
		ResultSet rs = st.executeQuery("SELECT * FROM  \"temp\"");
		while (rs.next()) {
		if(rs.getString(1)!=null)
		System.out.println(rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getString(4)+" | "+rs.getString(5)+" | "+rs.getString(6)+" | "+rs.getString(7));
		}
		
		//PreparedStatement pst2 = conn.prepareStatement("DROP TEMP TABLE temp");
		conn.commit();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	public void runMenu(){
		printHeader();
		while(!exit){
		printMenu();
		int choice = getInput();
		performAction(choice);

		}
		
	}
	
	
	private void printHeader(){
		System.out.println("+----------------------------+");
		System.out.println("|            PART B          |");	
		System.out.println("|       DATABASE PROJECT     |");
		System.out.println("+----------------------------+");
	}
	private void printMenu(){
		System.out.println(" Choose from Menu below:");
		System.out.println("                        ");
		System.out.println("1: Transaction Begin/Transaction complete ");
		System.out.println("2: Insert Diploma ");
		System.out.println("3: Show Student Grade ");
		System.out.println("4: Change Student Grade ");
		System.out.println("5: Search Person ");
		System.out.println("6: Present Student Grade Analysis ");
		System.out.println("0: Exit");
	}
	private int getInput() {
			Scanner kb = new Scanner(System.in);
			int choice = -1;
			while(choice < 0 ||  choice > 6){
				try {
					System.out.print("\nInsert Choice:");
					choice = Integer.parseInt(kb.nextLine());
				}
				catch (NumberFormatException e ){
					System.out.println("Invalid choice, try again");
				}	
			}
		return choice;
	}
	
	private void performAction(int choice){
		
		switch (choice){
		case 0: 
				exit = true;
				System.out.println("Exiting");
			break;
		case 1: 
			db_commit();
			waitForEnter();
			break;
		case 2: 
			insertDiploma();
			waitForEnter();
			break;
		case 3:
			showStudentgrade();
			waitForEnter();
			break;
		case 4:
			changeStudentgrade();
			waitForEnter();
			break;
		case 5:
			showPerson();
			waitForEnter();
			break;
		case 6:
			showStudentgradeanalysis();
			waitForEnter();
			break;
		default:
				System.out.println("Unkown Error Occured");
			break;
		}
	}

	public static void main(String[] args) {
		
		DbApp db = new DbApp();
		
		db.dbConnect("localhost",5432,"ProjFinal", "postgres", "123456");
		
		db.runMenu();
		

		
	}
}
