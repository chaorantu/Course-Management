package model;

import java.util.ArrayList;

public class Schedule {

	private ArrayList<Slot> slots;
	public Schedule(){
	
		slots=new ArrayList<Slot>();
	}
	public void addSlot(Slot s){
		slots.add(s);
	}
	public Slot removeSlot(String courseid,String section){
		return slots.remove(getindex(courseid,section));
	}  
	public int getindex(String courseid,String section){
		int i = 0,n=0;
		for(Slot s:slots){
			if(s.getSection().getName().equals(section)&&s.getSection().getCourse().getCourseid().equals(courseid)){
			   n=i;
			}
			i++;
		}
		return n;
	}
}
