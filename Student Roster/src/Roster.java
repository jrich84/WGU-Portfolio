import java.util.ArrayList;
import java.util.regex.*;

public class Roster {
	
		
		//Create an arrayList of student objects
	static ArrayList<Student> students = new ArrayList<>();
	
	public static void main(String[] args) {
		//Ass Student data
		add("1", "John"  , "Smith"     , "john1989@gmail.com"     , 20, 88, 79, 59);
		add("2", "Suzan" , "Erickson"  , "Erickson_1990@gmailcom", 19, 91, 72, 85);
		add("3", "Jack"  , "Napoli"    , "The_lawyer99yahoo.com"  , 19, 85, 84, 87);
		add("4", "Erin"  , "Black"     , "Erin.black@comcast.net" , 22, 91, 98, 82);
		add("5", "Joseph", "Richardson", "jric176@wguedu"        , 33, 98, 96, 99);
		
		//Print out Student data
		print_all();
		
		//Print invalid emails
		print_invalid_emails();
		
		
		//Print Grade Average
		print_average_grade("4");
		
		//Remove selected student
		remove("3");
		remove("3");
	}
	
	
	// Add method
	public static void add(String studentID, String firstName, String lastName, String emailAddress, int age, double grade1, double grade2, double grade3) {
		
		double[] grades = {grade1, grade2, grade3};
		Student newStudent = new Student(studentID, firstName, lastName, emailAddress, age, grades);  //create new student
		students.add(newStudent);
	}
	
	//Removes student
	public static void remove(String studentID) {	
		for( Student i: students){
			if(i.getStudentId().equals(studentID)){
				students.remove(i);
				return;
			}
		}
		System.out.println("Such a student with this id was not found");
	}

	// Prints ass student data
	public static void print_all() {
		//Loop through ArrayList
		for( int i =0; i < students.size(); i++){
			students.get(i).print();
		}	
	}
	
	// Prints average grade of all students
	public static void print_average_grade(String studentID) {
		for( Student g: students){
			if(g.getStudentId().equals(studentID)){
				double average = (g.getGrades()[0] + g.getGrades()[1] + g.getGrades()[2])/3;
				System.out.println("studentID:\t" + g.getStudentId() + "\tAvg Grades:\t" + average);
				return;
			}
		}
	}
	
	// Finds invalid email by looping through each students email string
    public static void print_invalid_emails() {
    	
    	// Create an arrayList of invalidEmails
    	ArrayList<String> invalidEmails = new ArrayList<String>();
    	
    	// Access student object
    	for (Student e : students) {
    		
    		// Access email objects
    		String email = e.getEmailAddress();
    		
    		// Iterate over every String character
    		for (int i = 0; i < email.length(); i++) {
    			
    			// Iterate searching for "@",".", " "
    			if (!email.contains("@") || !email.contains(".") || email.contains(" ")){
    				invalidEmails.add(email);
    				break; // breaks inner loop and checks next email
    			}
    		}
    	}
    	//Once found print out invalid email
    	System.out.println("The following emails are invalid:\t" + invalidEmails);
	}
    
}
