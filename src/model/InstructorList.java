package model;

import java.util.ArrayList;

public class InstructorList {

	private ArrayList<Instructor> instructors;
	public InstructorList(){
		instructors=new ArrayList<Instructor>();
	}
	public Instructor getInstructor(String id){
	
		for(Instructor instru:instructors){
			if(instru.getInstructorid().equals(id)){
				return instru;
			}
		}
		return null;
	}
	public void addinstructor(Instructor instructor){
		instructors.add(instructor);
	}
}
