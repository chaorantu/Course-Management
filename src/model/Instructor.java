package model;

import java.util.ArrayList;

public class Instructor {

	private String id;
	private String name;
	private String password;
	private ArrayList<Course> courses;
	public Instructor(String id, String password){
		this.id=id;
		this.password=password;
		courses=new ArrayList<Course>();
	}
	public void setInstructor(String name){
		this.name=name;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public ArrayList<String> getCourseList(){
    	ArrayList<String> a = new ArrayList<String>();
    	for(Course c:courses){
    		a.add(c.getCourseid());
    	}
    	return a;
    }
	public Course removecourse(String courseid){
		if(courses.isEmpty()){return null;}
		return courses.remove(getindex(courseid));
	}
	public Course removebyindex(int index){
		return courses.remove(index);
	}
	
	public int getindex(String courseid){
		int i = 0,n=0;
		for(Course c:courses){
			if(c.getCourseid().equals(courseid)){
			   n=i;
			}
			i++;
		}
		return n;
	}
	public String getInstructorid(){
		return id;
	}
	public String getInstructorname(){
		return name;
	}
	public void addCourse(Course c){
		courses.add(c);
	}
	public ArrayList<Course> getCourses(){
		return courses;
	}
}
