package model;

public class Section {

	private String name;
	private Course course;
	private Slot slot;
	public Section(String name,Course course){
		this.name=name;
		this.course=course;
	}
	public Course getCourse(){
		return course;
	}
	public String getName(){
		return name;
	}
	public Slot getSlot(){
		return slot;
	}
	public void setSlot(Slot slot){
		this.slot=slot;
	}
}
