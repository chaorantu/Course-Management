package model;

import java.util.ArrayList;

public class Room {

	private String id;
	private int capacity;
	private String address;
	private ArrayList<Slot> slots;
	public Room(String id,String address,int capacity){
		this.id=id;
		this.capacity=capacity;
		this.address=address;
		slots=new ArrayList<Slot>();
	}
	public void addSlot(Slot s){
		slots.add(s);
	}
	public ArrayList<Slot> getslots(){
		return slots;
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
	public Boolean roomAvailable(Slot slot){
		if(slot.getCourse().getCapacity()>capacity){
			return false;
		}
		else{
		for(Slot s:slots){
			if(s.getweek().equals(slot.getweek())&&s.gettime().equals(slot.gettime())){
				return false;
			}
		}
		}
		return true;
	}
	public String getRoomid(){
		return id;
	}
	public int getcapacity(){
		return capacity;
	}
	public String getAddress(){
		return address;
	}
}
