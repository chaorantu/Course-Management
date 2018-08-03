package model;

import java.util.ArrayList;

public class StudentList {
	private ArrayList<Student> students;
	public StudentList(){
		students=new ArrayList<Student>();
	}
	public Student getStudent(String id){
	
		for(Student stu:students){
			if(stu.getStudentid().equals(id)){
				return stu;
			}
		}
		return null;
	}
	public void addstudent(Student student){
		students.add(student);
	}
}
