package model;

import java.util.ArrayList;

public class Course {

	private String id;
	private String name;
	private String term;
	private int capacity;
	private Instructor instructor;
	private ArrayList<Section> sections;
	private ArrayList<Student> students;
	public Course(String id,String name,int capacity){
		this.id=id;
		this.name=name;
		this.capacity=capacity;
		sections=new ArrayList<Section>();
		students=new ArrayList<Student>();
	}
	public Course(){}
	public void addSection(Section s){
		sections.add(s);
	}
	public Section removeSection(int index){
		
		return sections.remove(index);
	}
	public void addStudent(Student s){
	    students.add(s);
	}
	public void removeStudent(Student stu){
		int n=0;
		int i=0;
		for(Student s:students){
			if(s.getStudentid().equals(stu.getStudentid())){
				n=i;
			}
			i++;
		}
		students.remove(n);
	}
    public void setInstructor(Instructor i){
    	this.instructor=i;
    }
    public String getCourseid(){
    	return id;
    }
    public String getCoursename(){
    	return name;
    }
    public Instructor getInstructor(){
    	return instructor;
    }
    public int getCapacity(){
    	return capacity;
    }
	public ArrayList<String> getSectionList(){
		ArrayList<String> a = new ArrayList<String>();
		for(Section s:sections){
			a.add(s.getName());
		}
		return a;
	}
	public ArrayList<Student> getStudents(){
		return students;
	}
	public ArrayList<Section> getSections(){
		return sections;
	}
	public Section getSection(String section){
		for(Section s:sections){
			if(s.getName().equals(section)){
				return s;
			}
		}
		return null;
	}
}
