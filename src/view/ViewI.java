package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import controller.ControllerI;
import model.Course;
import model.Instructor;
import model.Room;
import model.Section;
import model.Share;
import model.Slot;
import model.Student;
//instructor interface
public class ViewI{

	private Instructor instructor;
	private Course course0;
	private Slot slot;
	private String section0;
	private JFrame frame = new JFrame("Instructor");
	private JFrame frame1=new JFrame("SelectRoom");
	private JFrame frame2=new JFrame("Schedule");
	private JFrame frame3=new JFrame("Profile");
	private JFrame frame4=new JFrame("Changepassword");
	private JFrame frame5=new JFrame("course&student");
	private Container c = frame.getContentPane();
	private Container c1 = frame1.getContentPane();
	private Container c2 = frame2.getContentPane();
	private Container c3 = frame3.getContentPane();
	private Container c4 = frame4.getContentPane();
	private Container c5 = frame5.getContentPane();
	private JComboBox<String> jcb=new JComboBox<String>();//courselist1
	private JComboBox<String> jcb1=new JComboBox<String>();//roomlist
	private JPanel dynamicPanel=new JPanel();
	private JPanel schedulePanel=new JPanel();
	private JTable table;
	private ControllerI con=new ControllerI(this,jcb);
	private Boolean state=false;
	private JTable schedule=new JTable(9,7);
	private JList courseList=new JList();
	private JList studentList=new JList();
	private int selectedcourseindex;
	//customized textArea
	class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer { 
	    public TableCellTextAreaRenderer() { 
	        setLineWrap(true); 
	        setWrapStyleWord(true); 
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value, 
	            boolean isSelected, boolean hasFocus, int row, int column) { 
	        int maxPreferredHeight = 0; 
	        for (int i = 0; i < table.getColumnCount(); i++) { 
	            setText("" + table.getValueAt(row, i)); 
	            setSize(table.getColumnModel().getColumn(column).getWidth(), 0); 
	            maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height); 
	        }

	        if (table.getRowHeight(row) != maxPreferredHeight)  
	            table.setRowHeight(row, maxPreferredHeight);

	        setText(value == null ? "" : value.toString()); 
	        return this; 
	    } 
	}

	public class MyButtonRender implements TableCellRenderer{

		private JButton button;
		public MyButtonRender(){
			button=new JButton("add");
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return button;
		}
		
	}
	
	public class MyButtonEditor extends DefaultCellEditor{
		private MyButton button;
		public MyButtonEditor(){
			super(new JTextField());
			button=new MyButton("add");
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
				  Object section=table.getValueAt(button.row, 0);
				  Object week=table.getValueAt(button.row, 1);
				  Object time=table.getValueAt(button.row, 2);
				  Object number=table.getValueAt(button.row, 3);
				  Object startweek=table.getValueAt(button.row, 4);
				  if(!con.checkempty(week, time, number, startweek)){
				  if(!con.checkdupslot(week.toString(),time.toString())){
				  int n = JOptionPane.showConfirmDialog(null,"set "+number+section+" at "+week+":"+time+" start from week "+startweek , "",JOptionPane.YES_NO_OPTION);
				  if(n==0){
					 addroomFrame(con.getseletedcourse(),section.toString(),week.toString(),time.toString(),number.toString(),startweek.toString());
				  }
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null, "You have chosen a conflict time", "warning",JOptionPane.WARNING_MESSAGE); 
				  }
				}
				  else
					{
						JOptionPane.showMessageDialog(null, "Form can not be empty!", "warning",JOptionPane.WARNING_MESSAGE); 
						c.revalidate();
					}
				}
				
			});
		}
		@Override
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int row, int column) {
	        button.setRow(row);
	        button.setColumn(column);
	        return button;
	}
	}
	//customized button
	public class MyButton extends JButton{
		private int row;
		private int column;
		public MyButton(){}
		public MyButton(String name){
			super(name);
		}
		public int getRow(){
			return row;
		}
		public int getColumn(){
			return column;
		}
		public void setRow(int row){
			this.row=row;
		}
		public void setColumn(int column){
			this.column=column;
		}
	}
	public ViewI(Instructor instructor){
		this.instructor=instructor;
		frame.setSize(800, 600);
		frame.setBounds(450, 300, 800, 600);
		frame.setVisible(true);
		c.setLayout(new BorderLayout());
		initFrame();
	}
	public Instructor getInstructor(){
		return instructor;
	}
	
	public void initFrame() {
		// TODO Auto-generated method stub
		frame.setTitle("Instructor: "+instructor.getInstructorid());
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(new FlowLayout());
		JLabel a1=new JLabel("courseid:");
		JLabel a2=new JLabel("coursename:");
		JTextField coursename=new JTextField();
		JButton confirm=new JButton("confirm");
		a1.setBounds(10, 20, 80, 20);
		a2.setBounds(170, 20, 80, 20);
		jcb.setBounds(70, 20, 100, 20);
		coursename.setBounds(230, 20, 100, 20);
		confirm.setBounds(330, 20, 80, 20);
		jcb.setLightWeightPopupEnabled(false);
		coursename.setEditable(false);
		con.setcourses(coursename);
		fieldPanel.add(a1);
		fieldPanel.add(jcb);
		fieldPanel.add(a2);
		fieldPanel.add(coursename);
		fieldPanel.add(confirm);
		c.add(fieldPanel, "North");
		
		JPanel returnPanel = new JPanel();
		returnPanel.setLayout(new FlowLayout());
		JButton back=new JButton("return");
		back.setBounds(100, 100, 100, 20);
		returnPanel.add(back);
		c.add(returnPanel, "South");
		
		JPanel showPanel=new JPanel();
		showPanel.setLayout(new BoxLayout(showPanel,BoxLayout.Y_AXIS));
		JButton showcourse=new JButton("ShowSchedule");
		JButton profile=new JButton("Profile");
		JButton changepassword=new JButton("ChangePassword");
		JButton coursestu=new JButton("course&student");
		showPanel.add(showcourse);
		showPanel.add(profile);
		showPanel.add(changepassword);
		showPanel.add(coursestu);
		c.add(showPanel, "East");
		showcourse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showSchedule();
			}
			
		});
		profile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				profileFrame();
			}
			
		});
		changepassword.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				changepasswordFrame();
			}
			
		});
		coursestu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				coursestudentFrame();
			}
			
		});
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!con.checkcourseSeleted(jcb.getSelectedItem().toString())){
			    state=false;
				dynamicTable();
				}
				else{
					JOptionPane.showMessageDialog(null, "course has been selected by another professor", "warning",JOptionPane.WARNING_MESSAGE); 
				}
			}
			
		});
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				changeView();
			}
			
		});
		jcb.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					con.setcoursename(coursename);
				}
			}
			
		});
	}
    //return to login interface
	public void changeView() {
		// TODO Auto-generated method stub
		frame.setVisible(false);
		new Login();
	}
	//show dynamic table
	public void dynamicTable(){
		dynamicPanel.removeAll();
		dynamicPanel.setLayout(new FlowLayout());
		//instructor.addCourse(course);
		//course.setInstructor(instructor);
		int n=con.getsectionnum();
		table=new JTable(n,6);
		con.initialTable(table);
		JScrollPane JSP= new JScrollPane(table);
		JSP.setBounds(100, 100, 200, 200);
		dynamicPanel.add(JSP,"Center");
		c.add(dynamicPanel,"Center");
		c.revalidate();
	}
	//show schedule
	public void showSchedule(){
		schedulePanel.removeAll();
		schedulePanel.setLayout(new FlowLayout());
		frame2.setSize(600, 500);
		frame2.setBounds(300, 300,600, 500);
		frame2.setVisible(true);
		c2.setLayout(new FlowLayout());
		schedule.removeAll();
		JTable schedule=new JTable(9,7);
		schedule.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
		//schedule.setRowHeight(60);
		schedule.setEnabled(false);
		Object [][] section=new Object[9][7];
		section[0][0]="8:00-9:00";
		section[1][0]="9:00-10:00";
		section[2][0]="10:00-11:00";
		section[3][0]="11:00-12:00";
		section[4][0]="12:00-13:00";
		section[5][0]="13:00-14:00";
		section[6][0]="14:00-15:00";
		section[7][0]="15:00-16:00";
		section[8][0]="16:00-17:00";
		TableModel model=new DefaultTableModel(
				section, new Object[]{"","Mon","Tue","Wed","Thu","Fri"});
		schedule.setModel(model);
		JScrollPane JSP= new JScrollPane(schedule);
		JSP.setBounds(100, 100, 300, 300);
		schedulePanel.add(JSP, "Center");
		c2.add(schedulePanel);
		con.show(schedule, instructor);
		c2.revalidate();
	}
	//create profile interface
	public void profileFrame(){
		frame3.setSize(300, 220);
		frame3.setBounds(450, 300, 300, 220);
		frame3.setVisible(true);
		c3.setLayout(new BorderLayout());
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("instructorid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("name:");
		a2.setBounds(50, 60, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		JTextField instructorid=new JTextField();
		JTextField instructorname=new JTextField();
		instructorid.setBounds(130, 20, 100, 20);
		instructorname.setBounds(130, 60, 100, 20);
		instructorid.setEnabled(false);
		instructorid.setText(con.getinstructorid());
		instructorname.setText(con.getinstructorname());
		fieldPanel.add(instructorid);
		fieldPanel.add(instructorname);
		c3.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("update");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c3.add(buttonPanel, "South");
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				con.setinstructorname(instructorname.getText());
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame3.setVisible(false);
			}});
	}
	//create changepassword interface
	public void changepasswordFrame(){
		frame4.setSize(300, 220);
		frame4.setBounds(450, 300, 300, 220);
		frame4.setVisible(true);
		c4.setLayout(new BorderLayout());
		
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("old password:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("new password:");
		a2.setBounds(50, 60, 80, 20);
		JLabel a3=new JLabel("confirm password:");
		a3.setBounds(50, 100, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		fieldPanel.add(a3);
		JPasswordField oldpassword=new JPasswordField();
		JPasswordField newpassword=new JPasswordField();
		JPasswordField conpassword=new JPasswordField();
		oldpassword.setBounds(130, 20, 100, 20);
		newpassword.setBounds(130, 60, 100, 20);
		conpassword.setBounds(130, 100, 100, 20);
		fieldPanel.add(oldpassword);
		fieldPanel.add(newpassword);
		fieldPanel.add(conpassword);
		c4.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("confirm");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c4.add(buttonPanel, "South");
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				char[] pass0 = oldpassword.getPassword();
				String password0 = new String(pass0);
				char[] pass1 = newpassword.getPassword();
				String password1 = new String(pass1);
				char[] pass2 = conpassword.getPassword();
				String password2 = new String(pass2);
				int i=con.checkpassword(password0,password1,password2);
				if(i==0){
				JOptionPane.showMessageDialog(null, "past password is not correct!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else if(i==1){
			    JOptionPane.showMessageDialog(null, "confirm password is not correct!", "warning",JOptionPane.WARNING_MESSAGE);
				}
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame4.setVisible(false);
			}});
	}
	public void coursestudentFrame(){
		c5.removeAll();
		frame5.setSize(400, 300);
		frame5.setBounds(450, 300, 400, 330);
		frame5.setVisible(true);
		c5.setLayout(null);
		courseList.removeAll();
		JLabel a1=new JLabel("courses:");
		a1.setBounds(20, 15, 120, 20);
		JLabel a2=new JLabel("students:");
		a2.setBounds(20, 100, 120, 20);
		JList courseList=new JList();
		courseList.setBounds(100, 100, 200, 300);
		JScrollPane JSP1= new JScrollPane(courseList);
		JSP1.setBounds(90, 15, 220, 80);
		
		JList studentList=new JList();
		studentList.setBounds(100, 100, 200, 300);
		JScrollPane JSP2= new JScrollPane(studentList);
		JSP2.setBounds(90, 100, 220, 100);
		c5.add(a1);
		c5.add(a2);
		c5.add(JSP1);
		c5.add(JSP2);
		JButton remove=new JButton("remove");
		JButton back=new JButton("return");
		remove.setBounds(20, 250, 100, 25);
		back.setBounds(250, 250, 100, 25);
		c5.add(remove);
		c5.add(back);
		refreshcourseList(courseList);
		c5.revalidate();
		courseList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(courseList.getValueIsAdjusting()){
				selectedcourseindex=((JList)e.getSource()).getSelectedIndex();
				System.out.println(selectedcourseindex);
				setstudentList(studentList,selectedcourseindex);
				}
			}});
		 back.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					frame5.setVisible(false);
				}});
		 remove.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
				con.removecourse(selectedcourseindex,instructor);
				clearstudentList(studentList);
				refreshcourseList(courseList);
				}});
	}
	//create addroom interface
	public void addroomFrame(Course course,String section,String week,String time,String number,String startweek){
		c1.removeAll();
		frame1.setSize(300, 220);
		frame1.setBounds(450, 300, 300, 220);
		frame1.setVisible(true);
		c1.setLayout(new BorderLayout());
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		System.out.println(course.getCourseid()+" "+section+" "+week+" "+time+" "+number+" "+startweek);
		JLabel a1=new JLabel("roomid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("address:");
		a2.setBounds(50, 60, 80, 20);
		jcb1.removeAll();
		JTextField address=new JTextField();
		jcb1.setBounds(130, 20, 100, 20);
		jcb1.setLightWeightPopupEnabled(false);
		address.setBounds(130, 60, 100, 20);
		slot=con.createslot(week, time, Integer.parseInt(number), Integer.parseInt(startweek), section);
		con.setrooms(jcb1, slot, address);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		fieldPanel.add(jcb1);
		fieldPanel.add(address);
		c1.add(fieldPanel, "Center");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("add");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c1.add(buttonPanel, "South");
		this.section0=section;
		this.course0=course;
		System.out.println(course.getCourseid()+" "+section+" "+week+" "+time+" "+number+" "+startweek);
		c1.revalidate();
		jcb1.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					con.setaddress(jcb1, address);
				}
			}
			
		});
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int n = JOptionPane.showConfirmDialog(null,"add room "+jcb1.getSelectedItem().toString()+"for "+course0.getCourseid()+": "+section0+" ?", "",JOptionPane.YES_NO_OPTION);
				if(n==0){
				Room room=Share.rooms.getRoom(jcb1.getSelectedItem().toString());
				Section sec=course0.getSection(section0);
				slot.setRoom(room);
				sec.setSlot(slot);
				room.addSlot(slot);
				if(state==false){
				instructor.addCourse(course0);
				state=true;
				}
				course.setInstructor(instructor);
				frame1.setVisible(false);
				}
			}
			
		});
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame1.setVisible(false);
			}
			
		});
	}
	public void refreshcourseList(JList coursList){
    	con.initialCourseList(coursList, instructor);
    }
	public void clearstudentList(JList studentList){
		DefaultListModel listModel=new DefaultListModel();
		studentList.setModel(listModel);
	}
    public void setstudentList(JList studentList,int i){
    	con.setstudents(studentList,i,instructor);
    }
}
