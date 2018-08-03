package controller;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Course;
import model.Instructor;
import model.Section;
import model.Share;
import model.Student;
import view.ViewS;

public class ControllerS {
	private ViewS v;
	public ControllerS(ViewS v){
		this.v=v;
	}
	public void setcourses(JComboBox<String> jcb){
		jcb.removeAllItems();
		ArrayList<String> courselist=new ArrayList<String>();
		courselist=Share.courses.getCourseList();
		for(String s:courselist){
			jcb.addItem(s);
		}
	}
	public boolean checkcourseid(String courseid,Student student){
		for(Course c:student.getCourses()){
			if(c.getCourseid().equals(courseid)){
				return true;
			}
		}
		return false;
	}
	public void addCourse(String courseid,Student student){
		student.addCourse(Share.courses.getCourse(courseid));
		Share.courses.getCourse(courseid).addStudent(student);
	}
	public void initialCourseList(JList list,Student student){
		DefaultListModel listModel=new DefaultListModel();
		//ArrayList<String> courselist=new ArrayList<String>();
		//courselist=Share.courses.getCourseList();
		for(Course s:student.getCourses()){
			listModel.addElement(s.getCourseid()+"       "+s.getCoursename());
		}
		list.setModel(listModel);
		
	}
	public int checkpassword(String old,String n,String c){
		if(v.getStudent().getPassword().equals(old)){
			if(n.equals(c)){
				v.getStudent().setPassword(n);
				return 2;
			}
			else{
				
				return 1;
			}
		}
		else{
			return 0;
		}
	}
	//remove a course
	public void removecourse(int index,Student student){
		Course c=student.removebyindex(index);
		c.removeStudent(student);
		v.refreshcourseList();
	}
	public void show(JTable table,Student student){
		for(Course course:student.getCourses()){
			for(Section section:course.getSections()){
				if(section.getSlot()!=null){
				int locationy=getTimeindex(section);
				int locationx=getWeekindex(section);
				Object curr=table.getValueAt(locationy, locationx);
				table.setValueAt(curr+"\r\n"+section.getCourse().getCourseid()+"\r\n"+section.getName()+"\r\n"+section.getSlot().getRoom().getRoomid(), locationy, locationx);
				//table.getPreferredSize();
				}
			}
		}
	}
	public String getstudentid(){
		return v.getStudent().getStudentid();
	}
	public String getstudentname(){
		return v.getStudent().getStudentname();
	}
	public void setstudentname(String name){
		v.getStudent().setStudent(name);
	}
	public void setpassword(String password){
		v.getStudent().setPassword(password);
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
