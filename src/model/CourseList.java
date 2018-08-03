package model;

import java.util.ArrayList;

public class CourseList {

	private ArrayList<Course> courses;
	private int number;
	public CourseList(){
		courses=new ArrayList<Course>();
	}
    public void addCourse(Course c){
    	courses.add(c);
    }
    public Course getCourse(String courseid){
    	for(Course c:courses){
    		if(c.getCourseid().equals(courseid)){
    			return c;
    		}
    	}
		return null;
    }
    public Course removecourse(String courseid){
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
    public ArrayList<Course> getCourses(){
    	return courses;
    }
    public ArrayList<String> getCourseList(){
    	ArrayList<String> a = new ArrayList<String>();
    	for(Course c:courses){
    		a.add(c.getCourseid());
    	}
    	return a;
    }
}
