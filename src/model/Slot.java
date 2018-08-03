package model;

public class Slot {

	private String week;
	private String time;
	private int number;
	private int startweek;
	private Course course;
	private Section section;
	private Room room;
	
	public Slot(String week,String time,int number,int startweek,Course course,Section section){
		this.week=week;
		this.time=time;
		this.number=number;
		this.startweek=startweek;
		this.course=course;
		this.section=section;
	}
	public Course getCourse(){
		return course;
	}
	public Section getSection(){
		return section;
	}
	public Room getRoom(){
		return room;
	}
	public String getweek(){
		return week;
	}
	public String gettime(){
		return time;
	}
	public void setRoom(Room room){
		this.room=room;
	}
}
