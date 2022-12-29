public class Student {
	
	/* instance variables */
	private String studentID;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private int age;
	private double[] grades;

	// Constructor
	public Student(String studentID, String firstName, String lastName, String emailAddress, int age, double[] grades) {
		setStudentId(studentID);
		setFirstName(firstName);
		setLastName(lastName);
		setEmailAddress(emailAddress);
		setAge(age);
		setGrades(grades);
	}

	/* accessor ~ getters */
	
	public String getStudentId() {
		return studentID;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public int getAge() {
		return age;
	}
	public double[] getGrades() {
		return grades;
	}
	
    /* mutators ~ setter */
	
	public void setStudentId(String studentID) {
		this.studentID = studentID;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setGrades(double[] grades) {
		this.grades = grades;
	}

	public void print() {
		System.out.println("studentID:\t" + getStudentId() +
						"\tfirstName:\t" + getFirstName() +
						"\tLastName:\t" + getLastName() +
						"\tEmail:\t" + getEmailAddress() +
						"\tAge:\t" + getAge() +
						"\tGrades:\t" + getGrades()[0] + ", " + getGrades()[1] + ", " + getGrades()[2]);
						
	}
}
