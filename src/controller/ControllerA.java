package controller;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Course;
import model.Instructor;
import model.Room;
import model.Section;
import model.Share;
import model.Slot;
import model.Student;
import view.ViewA;
//controller for the administrator interface
public class ControllerA {

	private ViewA v;
	public ControllerA(ViewA v){
		this.v=v;
	}
	//place all the courses in the combobox
	public void setcourses(JComboBox<String> jcb,JTextField coursename){
		jcb.removeAllItems();
		ArrayList<String> courselist=new ArrayList<String>();
		courselist=Share.courses.getCourseList();
		for(String s:courselist){
			jcb.addItem(s);
		}
		if(jcb.getItemCount()!=0){
			setcoursename(jcb,coursename);
			}
	}
	//set the course name according to the selection in combobox
	public void setcoursename(JComboBox<String> jcb,JTextField coursename){
		Course cou=Share.courses.getCourse(jcb.getSelectedItem().toString());
		coursename.setText(cou.getCoursename());
	}
	//initialize the courselist
	public void initialCourseList(JList list){
		DefaultListModel listModel=new DefaultListModel();
		//ArrayList<String> courselist=new ArrayList<String>();
		//courselist=Share.courses.getCourseList();
		for(Course s:Share.courses.getCourses()){
			listModel.addElement(s.getCourseid()+"       "+s.getCoursename()+"       "+s.getCapacity());
		}
		list.setModel(listModel);
		
	}
	//set the section according to the course selected in combobox
	public void setsections(JList list,int index){
		DefaultListModel listModel=new DefaultListModel();
		for(String s:Share.courses.getCourses().get(index).getSectionList()){
			listModel.addElement(s);
		}
		list.setModel(listModel);
	}
	//clear the section list
	public void clearsections(JList list){
		DefaultListModel listModel=new DefaultListModel();
		list.setModel(listModel);
	}
	//initialize the roomlist
	public void initialRoomList(JList list){
		DefaultListModel listModel=new DefaultListModel();
		//ArrayList<String> courselist=new ArrayList<String>();
		//courselist=Share.courses.getCourseList();
		for(Room r:Share.rooms.getrooms()){
			listModel.addElement(r.getRoomid()+"       "+r.getAddress()+"     "+r.getcapacity());
		}
		list.setModel(listModel);
		
	}
	//add a new room
	public void addroom(String roomid,String address,String capacity){
		Room room=new Room(roomid,address,Integer.parseInt(capacity));
		Share.rooms.addRoom(room);
		v.refreshroomList();
	}
	//add a new section
	public void addsection(Course c,String name){
		Section section=new Section(name,c);
		c.addSection(section);
	}
	//add a new course
	public void addcourse(String courseid,String coursename,String capacity){
		Course course=new Course(courseid,coursename,Integer.parseInt(capacity));
		//Course course=new Course("ENGI-1000","Math",30);
		Share.courses.addCourse(course);
		v.refreshcourseList();
	}
	//add a new instructor
	public void addinstructor(String id,String password){
		Instructor instructor=new Instructor(id,password);
		Share.instructors.addinstructor(instructor);
	}
	//add a new student
	public void addstudent(String id,String password){
		Student student=new Student(id,password);
		Share.students.addstudent(student);
	}
	//remove a room
	public void removeroom(int index){
		Room r=Share.rooms.removebyindex(index);
		for(Slot s:r.getslots()){
			//Share.schedule.removeSlot(s.getCourse().getCourseid(), s.getSection().getName());
			s.getSection().setSlot(null);
		}
		v.refreshroomList();
	}
	//remove a section
	public void removesection(int index1,int index2){
		Course c=Share.courses.getCourses().get(index1);
		Section s=c.getSections().get(index2);
		c.removeSection(index2);
		if(s.getSlot()!=null){
		s.getSlot().getRoom().removeSlot(c.getCourseid(), s.getName());}
		//Share.schedule.removeSlot(c.getCourseid(), s.getName());
	}
	//remove a course
	public void removecourse(int index1){
		Course c=Share.courses.getCourses().get(index1);
		Share.courses.removecourse(c.getCourseid());
		for(Section s:c.getSections()){
			if(s.getSlot()!=null){
			s.getSlot().getRoom().removeSlot(c.getCourseid(), s.getName());}
			//Share.schedule.removeSlot(c.getCourseid(), s.getName());
			
		}
		if(c.getInstructor()!=null){
		c.getInstructor().removecourse(c.getCourseid());}
		v.refreshcourseList();
	}
	public void show(JTable table){
		for(Course course:Share.courses.getCourses()){
			for(Section section:course.getSections()){
				if(section.getSlot()!=null){
				int locationy=getTimeindex(section);
				int locationx=getWeekindex(section);
				Object curr=table.getValueAt(locationy, locationx);
				table.setValueAt(curr+"\r\n"+section.getCourse().getCourseid()+"\r\n"+section.getName()+"\r\n"+section.getSlot().getRoom().getRoomid()+"\r\n"+section.getCourse().getInstructor().getInstructorname(), locationy, locationx);
				//table.getPreferredSize();
				}
			}
		}
	}
	public boolean checkcourseid(String courseid){
		if(Share.courses.getCourse(courseid)==null){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkroomid(String roomid){
		if(Share.rooms.getRoom(roomid)==null){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkinstructorid(String instructorid){
		if(Share.instructors.getInstructor(instructorid)==null){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkstudentid(String studentid){
		if(Share.students.getStudent(studentid)==null){
			return true;
		}
		else{
			return false;
		}
	}
	//used only for test,add courses,sections.
	public void testadd(){
		Course course1=new Course("ENGI-1000","Math",50);
		Share.courses.addCourse(course1);
		Course course2=new Course("ENGI-2000","History",30);
		Share.courses.addCourse(course2);
		Course course3=new Course("ENGI-3000","Computer",35);
		Share.courses.addCourse(course3);
		Course course4=new Course("ENGI-4000","Art",20);
		Share.courses.addCourse(course4);
		Section section1=new Section("section-A",course1);
		Section section2=new Section("section-B",course1);
		Section section3=new Section("lab",course1);
		course1.addSection(section1);
		course1.addSection(section2);
		course1.addSection(section3);
		Section section4=new Section("section-A",course2);
		Section section5=new Section("section-B",course2);
		course2.addSection(section4);
		course2.addSection(section5);
		Section section6=new Section("section-A",course3);
		Section section7=new Section("section-B",course3);
		Section section8=new Section("lab",course3);
		course3.addSection(section6);
		course3.addSection(section7);
		course3.addSection(section8);
		Section section9=new Section("section-A",course3);
		course4.addSection(section9);
		Room room1=new Room("EN-1000","1st floor",30);
		Room room2=new Room("EN-1010","1st floor",32);
		Room room3=new Room("EN-2000","2nd floor",35);
		Room room4=new Room("EN-2020","2nd floor",37);
		Room room5=new Room("EN-3000","3rd floor",40);
		Room room6=new Room("EN-3030","3rd floor",45);
		Room room7=new Room("EN-4000","4th floor",50);
		Room room8=new Room("EN-4040","4th floor",60);
		Share.rooms.addRoom(room1);
		Share.rooms.addRoom(room2);
		Share.rooms.addRoom(room3);
		Share.rooms.addRoom(room4);
		Share.rooms.addRoom(room5);
		Share.rooms.addRoom(room6);
		Share.rooms.addRoom(room7);
		Share.rooms.addRoom(room8);
		Instructor instructor=new Instructor("201599293","12345");
		Student student=new Student("201599293","12345");
		Share.instructors.addinstructor(instructor);
		Share.students.addstudent(student);
		v.refreshcourseList();
		v.refreshroomList();
	}
	public int getWeekindex(Section section){
		if(section.getSlot().getweek().equals("Mon")){
			return 1;
		}
		else if(section.getSlot().getweek().equals("Tue")){
			return 2;
		}
		else if(section.getSlot().getweek().equals("Wed")){
			return 3;
		}
		else if(section.getSlot().getweek().equals("Thu")){
			return 4;
		}
		else if(section.getSlot().getweek().equals("Fri")){
			return 5;
		}
		return 0;
	}
    public int getTimeindex(Section section){
    	if(section.getSlot().gettime().equals("8:00-9:00")){
			return 0;
		}
		else if(section.getSlot().gettime().equals("9:00-10:00")){
			return 1;
		}
		else if(section.getSlot().gettime().equals("10:00-11:00")){
			return 2;
		}
		else if(section.getSlot().gettime().equals("11:00-12:00")){
			return 3;
		}
		else if(section.getSlot().gettime().equals("12:00-13:00")){
			return 4;
		}
		else if(section.getSlot().gettime().equals("13:00-14:00")){
			return 5;
		}
		else if(section.getSlot().gettime().equals("14:00-15:00")){
			return 6;
		}
		else if(section.getSlot().gettime().equals("15:00-16:00")){
			return 7;
		}
		else if(section.getSlot().gettime().equals("16:00-17:00")){
			return 8;
		}
		return 0;
	}

}
