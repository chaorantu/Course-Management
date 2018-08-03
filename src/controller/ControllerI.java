package controller;

import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.Course;
import model.Instructor;
import model.Room;
import model.Section;
import model.Share;
import model.Slot;
import model.Student;
import view.ViewI;
import view.ViewI.MyButtonEditor;
import view.ViewI.MyButtonRender;
//controller for the instructor interface
public class ControllerI {

	private ViewI v;
	private JComboBox<String> jcb;
	private Course course;
	public ControllerI(ViewI v,JComboBox<String> jcb){
		this.v=v;
		this.jcb=jcb;
	}
	//check if instructor has set the same time slot
	public Boolean checkdupslot(String week,String time){
		Instructor i=v.getInstructor();
		Boolean state=false;
		for(Course c:i.getCourses()){
			for(Section s:c.getSections()){
				if(s.getSlot()!=null){
				if(s.getSlot().getweek().equals(week)&&s.getSlot().gettime().equals(time)){
					state=true;
				}
				}
			}
		}
		return state;
	}
	//check empty input
	public Boolean checkempty(Object week,Object time,Object number,Object startweek){
		if(week!=null&&time!=null&&number!=null&&startweek!=null){
			if(!week.toString().equals("")&&!time.toString().equals("")&&!number.toString().equals("")&&!startweek.toString().equals("")){
	        return false;
		}
		}
		return true;
	}
	//check if course selected already
	public Boolean checkcourseSeleted(String courseid){
		Course c=Share.courses.getCourse(courseid);
		if(c.getInstructor()==null){
			return false;
		}
		else{
			return true;
		}
	}
	public void initialCourseList(JList list,Instructor instructor){
		DefaultListModel listModel=new DefaultListModel();
		//ArrayList<String> courselist=new ArrayList<String>();
		//courselist=Share.courses.getCourseList();
		for(Course s:instructor.getCourses()){
			listModel.addElement(s.getCourseid()+"       "+s.getCoursename());
		}
		list.setModel(listModel);
		
	}
	//set the section according to the course selected in combobox
	public void setstudents(JList list,int index,Instructor instructor){
		DefaultListModel listModel=new DefaultListModel();
		for(Student s:instructor.getCourses().get(index).getStudents()){
			listModel.addElement(s.getStudentid()+"       "+s.getStudentname());
		}
		list.setModel(listModel);
	}
	public void removecourse(int index,Instructor intructor){
		Course c=intructor.removebyindex(index);
		c.setInstructor(null);
	}
	//check the password change
	public int checkpassword(String old,String n,String c){
		if(v.getInstructor().getPassword().equals(old)){
			if(n.equals(c)){
				v.getInstructor().setPassword(n);
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
	public String getinstructorid(){
		return v.getInstructor().getInstructorid();
	}
	public String getinstructorname(){
		return v.getInstructor().getInstructorname();
	}
	public String getpassword(){
		return v.getInstructor().getPassword();
	}
	public void setinstructorname(String name){
		v.getInstructor().setInstructor(name);
	}
	public void setpassword(String password){
		v.getInstructor().setPassword(password);
	}
	//set all the courses in the combobox
	public void setcourses(JTextField coursename){
		jcb.removeAllItems();
		ArrayList<String> courselist=new ArrayList<String>();
		courselist=Share.courses.getCourseList();
		for(String s:courselist){
			jcb.addItem(s);
		}
		if(jcb.getItemCount()!=0){
			setcoursename(coursename);
			}
	}
	//set the course name
	public void setcoursename(JTextField coursename){
		Course cou=Share.courses.getCourse(jcb.getSelectedItem().toString());
		coursename.setText(cou.getCoursename());
	}
	//initialize the table
	public void initialTable(JTable table){
		Course course=Share.courses.getCourse(jcb.getSelectedItem().toString());
		this.course=course;
		//instructor.addCourse(course);
		//course.setInstructor(instructor);
		int n=course.getSectionList().size();
		Object [][] section=new Object[n][6];
		for(int i=0;i<n;i++){
			System.out.println(course.getSectionList().get(i));
			section[i][0]=course.getSectionList().get(i);
		}
		TableModel model=new DefaultTableModel(
				section, new Object[]{"SECTION","WEEK","TIME","NUMBER","STARTWEEK",""});
		table.setModel(model);
		//table.setTableHeader(tableHeader);
		JComboBox<String> jcboxW = new JComboBox<String>();
		jcboxW.addItem("Mon");
		jcboxW.addItem("Tue");
		jcboxW.addItem("Wed");
		jcboxW.addItem("Thu");
		jcboxW.addItem("Fri"); 
		DefaultCellEditor ceW = new DefaultCellEditor(jcboxW);
		TableColumnModel cm = table.getColumnModel();
		cm.getColumn(1).setCellEditor(ceW);
		
		JComboBox<String> jcboxT = new JComboBox<String>();
		jcboxT.addItem("8:00-9:00");
		jcboxT.addItem("9:00-10:00");
		jcboxT.addItem("10:00-11:00");
		jcboxT.addItem("11:00-12:00");
		jcboxT.addItem("12:00-13:00");
		jcboxT.addItem("13:00-14:00");
		jcboxT.addItem("14:00-15:00");
		jcboxT.addItem("15:00-16:00");
		jcboxT.addItem("16:00-17:00");
		DefaultCellEditor ceT = new DefaultCellEditor(jcboxT);
		cm.getColumn(2).setCellEditor(ceT);
		
		table.getColumnModel().getColumn(5).setCellRenderer(v.new MyButtonRender());
		MyButtonEditor editor=v.new MyButtonEditor();
		cm.getColumn(5).setCellEditor(editor);
	}
	public int getsectionnum(){
		Course course=Share.courses.getCourse(jcb.getSelectedItem().toString());
		return course.getSectionList().size();
	}
	public Course getseletedcourse(){
		return course;
	}
	public void setrooms(JComboBox<String> jcb,Slot slot,JTextField address){
		jcb.removeAllItems();
		for(String s: Share.rooms.getValidRoomList(slot)){
			jcb.addItem(s);
		}
		if(jcb.getItemCount()!=0){
			setaddress(jcb,address);
			}
	}
	public void setaddress(JComboBox<String> jcb,JTextField address){
		Room room=Share.rooms.getRoom(jcb.getSelectedItem().toString());
		address.setText(room.getAddress());
	}
	public Slot createslot(String week,String time,int number,int startweek,String s){
		Section section=course.getSection(s);
		Slot slot=new Slot(week,time,number,startweek,course,section);
		return slot;
	}
	public void show(JTable table,Instructor instructor){
		for(Course course:instructor.getCourses()){
			for(Section section:course.getSections()){
				if(section.getSlot()!=null){
				int locationy=getTimeindex(section);
				int locationx=getWeekindex(section);
				table.setValueAt(section.getCourse().getCourseid()+"\r\n"+section.getName()+"\r\n"+section.getSlot().getRoom().getRoomid(), locationy, locationx);
				//table.getPreferredSize();
				}
			}
		}
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
