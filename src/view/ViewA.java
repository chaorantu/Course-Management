package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.table.TableModel;

import controller.ControllerA;
import model.Course;
import model.Share;
import view.ViewI.TableCellTextAreaRenderer;
//administrator interface
public class ViewA{

	private JFrame frame = new JFrame("Main");
	private JFrame frame1=new JFrame("AddCourse");
	private JFrame frame2=new JFrame("AddSection");
	private JFrame frame3=new JFrame("AddRoom");
	private JFrame frame4=new JFrame("AddInstructor");
	private JFrame frame5=new JFrame("AddStudent");
	private JFrame frame6=new JFrame("Schedule");
	private JComboBox<String> jcb=new JComboBox<String>();//courselist2
	private JList courseList=new JList();
	private JList sectionList=new JList();
	private JList roomList=new JList();
	private Container c = frame.getContentPane();
	private Container c1 = frame1.getContentPane();
	private Container c2 = frame2.getContentPane();
	private Container c3 = frame3.getContentPane();
	private Container c4 = frame4.getContentPane();
	private Container c5 = frame5.getContentPane();
	private Container c6 = frame6.getContentPane();
	private ControllerA con;
	private int selectedcourseindex;  //index of the course selected in courseList
	private int selectedsectionindex;  //index of the section selected in courseList
	private int selectedroomindex;  //index of the room selected in courseList
	private JPanel schedulePanel=new JPanel();
	private JTable schedule=new JTable(9,7);
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
	public ViewA(){
		//JFrame frame=new JFrame("Login");
		frame.setSize(800, 600);
		frame.setBounds(450, 300, 800, 600);
		frame.setVisible(true);
		c.setLayout(null);
		con=new ControllerA(this);
		initFrame();
	}

	public void initFrame(){

		refreshcourseList();
		courseList.setBounds(200, 100, 200, 300);
		JScrollPane JSP1= new JScrollPane(courseList);
		JSP1.setBounds(220, 15, 220, 100);
        c.add(JSP1);
        
        sectionList.setBounds(200, 100, 200, 300);
		JScrollPane JSP2= new JScrollPane(sectionList);
		JSP2.setBounds(220, 120, 220, 60);
        c.add(JSP2);
        
        refreshroomList();
        roomList.setBounds(200, 100, 200, 300);
		JScrollPane JSP3= new JScrollPane(roomList);
		JSP3.setBounds(220, 263, 220, 100);
        c.add(JSP3);
        
		courseList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(courseList.getValueIsAdjusting()){
				System.out.println(((JList)e.getSource()).getSelectedIndex());
				selectedcourseindex=((JList)e.getSource()).getSelectedIndex();
				setsectionList(selectedcourseindex);
				}
			}});
		sectionList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(sectionList.getValueIsAdjusting()){
					selectedsectionindex=((JList)e.getSource()).getSelectedIndex();
				}
			}});
		roomList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(roomList.getValueIsAdjusting()){
					selectedroomindex=((JList)e.getSource()).getSelectedIndex();
				}
			}});
		//JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
		//buttonPanel.setLayout(null);
		JButton addcourse=new JButton("AddCourse");
		JButton addsection=new JButton("AddSection");
		JButton addroom=new JButton("AddRoom");
		JButton removecourse=new JButton("RemoveCourse");
		JButton removesection=new JButton("RemoveSection");
		JButton removeroom=new JButton("RemoveRoom");
		JButton testadd=new JButton("TestAdd");
		JButton addstudent=new JButton("AddStudent");
		JButton addinstructor=new JButton("AddInstructor");
		JButton Schedule = new JButton("schedule");
		Schedule.setBounds(14, 504, 100, 30);
		addstudent.setBounds(14, 344, 100, 30);
		addinstructor.setBounds(14, 433, 100, 30);
		addcourse.setBounds(490, 15, 100, 30);
		addsection.setBounds(490, 120, 100, 30);
		addroom.setBounds(490, 263, 100, 30);
		removecourse.setBounds(490, 55, 100, 30);
		removesection.setBounds(490, 160, 100, 30);
		removeroom.setBounds(490, 303, 100, 30);
		testadd.setBounds(650, 404, 100, 30);
		c.add(Schedule);
		c.add(addinstructor);
		c.add(addstudent);
		c.add(addcourse);
		c.add(addsection);
		c.add(addroom);
		c.add(testadd);
		c.add(removecourse);
		c.add(removesection);
		c.add(removeroom);
		//c.add(buttonPanel, "West");
		
		//JPanel returnPanel = new JPanel();
		//returnPanel.setLayout(new FlowLayout());
		JButton back=new JButton("return");
		back.setBounds(650, 504, 100, 30);
		c.add(back);
		//c.add(returnPanel, "South");
		
		removecourse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				con.removecourse(selectedcourseindex);
				con.clearsections(sectionList);
			}
			
		});
		
		removesection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			    con.removesection(selectedcourseindex, selectedsectionindex);
				setsectionList(selectedcourseindex);
			}
			
		});
		removeroom.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			    
				con.removeroom(selectedroomindex);
				
			}
			
		});
		
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			    
				changeView();
			}
			
		});
		addsection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addSectionFrame();
			}});
		addcourse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addCourseFrame();
			}
		});
		addroom.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addRoomFrame();
			}
		});
		addinstructor.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addInstructorFrame();
			}
		});
		addstudent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addStudentFrame();
			}
		});
		testadd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				con.testadd();
			}
			
		});
		Schedule.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showSchedule();
			}
			
		});
	}
	//create addcourse interface
	public void addCourseFrame(){
		frame1.setSize(300, 220);
		frame1.setBounds(450, 300, 300, 220);
		frame1.setVisible(true);
		c1.setLayout(new BorderLayout());
		
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("courseid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("coursename:");
		a2.setBounds(50, 60, 80, 20);
		JLabel a3=new JLabel("capacity:");
		a3.setBounds(50, 100, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		fieldPanel.add(a3);
		JTextField courseid=new JTextField();
		JTextField coursename=new JTextField();
		JTextField capacity=new JTextField();
		courseid.setBounds(130, 20, 100, 20);
		coursename.setBounds(130, 60, 100, 20);
		capacity.setBounds(130, 100, 100, 20);
		fieldPanel.add(courseid);
		fieldPanel.add(coursename);
		fieldPanel.add(capacity);
		c1.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("add");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c1.add(buttonPanel, "South");
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(courseid.getText().equals("")||coursename.getText().equals("")||capacity.getText().equals("")){
					JOptionPane.showMessageDialog(null, "empty input!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else if(!con.checkcourseid(courseid.getText())){
					JOptionPane.showMessageDialog(null, "courseid repeated!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else{
				con.addcourse(courseid.getText(),coursename.getText(),capacity.getText());
				refreshTextField(fieldPanel);
				}
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame1.setVisible(false);
			}});
	}
	//create addinstructor interface
	public void addInstructorFrame(){
		frame4.setSize(300, 220);
		frame4.setBounds(450, 300, 300, 220);
		frame4.setVisible(true);
		c4.setLayout(new BorderLayout());
		
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("instructorid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("password:");
		a2.setBounds(50, 60, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		JTextField instructorid=new JTextField();
		JTextField password=new JTextField();
		instructorid.setBounds(130, 20, 100, 20);
		password.setBounds(130, 60, 100, 20);
		password.setText("12345");
		password.setEnabled(false);
		fieldPanel.add(instructorid);
		fieldPanel.add(password);
		c4.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("add");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c4.add(buttonPanel, "South");
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(instructorid.getText().equals("")){
					JOptionPane.showMessageDialog(null, "empty input!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else if(!con.checkinstructorid(instructorid.getText())){
					JOptionPane.showMessageDialog(null, "instructorid repeated!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else{
				con.addinstructor(instructorid.getText(), password.getText());
				instructorid.setText(null);
				}
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame4.setVisible(false);
			}});
	}
	//create addstudent interface
	public void addStudentFrame(){
		frame5.setSize(300, 220);
		frame5.setBounds(450, 300, 300, 220);
		frame5.setVisible(true);
		c5.setLayout(new BorderLayout());
		
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("studentid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("password:");
		a2.setBounds(50, 60, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		JTextField studentid=new JTextField();
		JTextField password=new JTextField();
		studentid.setBounds(130, 20, 100, 20);
		password.setBounds(130, 60, 100, 20);
		password.setText("12345");
		password.setEnabled(false);
		fieldPanel.add(studentid);
		fieldPanel.add(password);
		c5.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("add");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c5.add(buttonPanel, "South");
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(studentid.getText().equals("")){
					JOptionPane.showMessageDialog(null, "empty input!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else if(!con.checkstudentid(studentid.getText())){
					JOptionPane.showMessageDialog(null, "studentid repeated!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else{
				con.addstudent(studentid.getText(), password.getText());
				studentid.setText(null);
				}
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame5.setVisible(false);
			}});
	}
	//create addroom interface
	public void addRoomFrame(){
		frame3.setSize(300, 220);
		frame3.setBounds(450, 300, 300, 220);
		frame3.setVisible(true);
		c3.setLayout(new BorderLayout());
		
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("roomid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("address:");
		a2.setBounds(50, 60, 80, 20);
		JLabel a3=new JLabel("capacity:");
		a3.setBounds(50, 100, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		fieldPanel.add(a3);
		JTextField roomid=new JTextField();
		JTextField address=new JTextField();
		JTextField capacity=new JTextField();
		roomid.setBounds(130, 20, 100, 20);
		address.setBounds(130, 60, 100, 20);
		capacity.setBounds(130, 100, 100, 20);
		fieldPanel.add(roomid);
		fieldPanel.add(address);
		fieldPanel.add(capacity);
		c3.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("add");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c3.add(buttonPanel, "South");
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomid.getText().equals("")||address.getText().equals("")||capacity.getText().equals("")){
					JOptionPane.showMessageDialog(null, "empty input!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else if(!con.checkroomid(roomid.getText())){
					JOptionPane.showMessageDialog(null, "roomid repeated!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else{
				con.addroom(roomid.getText(),address.getText(),capacity.getText());
				refreshTextField(fieldPanel);
				}
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame3.setVisible(false);
			}});
	}
	//create addsection interface
	public void addSectionFrame(){
		c2.removeAll();
		frame2.setSize(300, 220);
		frame2.setBounds(450, 300, 300, 220);
		frame2.setVisible(true);
		c2.setLayout(new BorderLayout());
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("courseid:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("coursename:");
		a2.setBounds(50, 60, 80, 20);
		JLabel a3=new JLabel("sectionname:");
		a3.setBounds(50, 100, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		fieldPanel.add(a3);
		//jcb1.removeAllItems();
		JTextField coursename=new JTextField();
		JTextField sectionname=new JTextField();
		jcb.setBounds(130, 20, 100, 20);
		jcb.setLightWeightPopupEnabled(false);
		coursename.setBounds(130, 60, 100, 20);
		coursename.setEditable(false);
		sectionname.setBounds(130, 100, 100, 20);
		con.setcourses(jcb,coursename);
		fieldPanel.add(jcb);
		fieldPanel.add(coursename);
		fieldPanel.add(sectionname);
		
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("add");
		JButton back=new JButton("return");
		buttonPanel.add(ok);
		buttonPanel.add(back);
		c2.add(fieldPanel, "Center");
		c2.add(buttonPanel, "South");
		c2.revalidate();
		jcb.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					con.setcoursename(jcb, coursename);
				}
			}
			
		});
	    ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(sectionname.getText().equals("")){
					JOptionPane.showMessageDialog(null, "empty input!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				Course c=Share.courses.getCourse(jcb.getSelectedItem().toString());
				con.addsection(c,sectionname.getText());
				refreshTextField(fieldPanel);
				setsectionList(selectedcourseindex);
			}});
	    back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(false);
			}});
	}
	//return to the login interface
	public void changeView() {
		frame1.setVisible(false);
		frame2.setVisible(false);
		frame.setVisible(false);
		new Login();
	}
	//refresh all the TextField in a panel
	public void refreshTextField(Component component){
		 if (component instanceof  JPanel) {
	            for (Component com : ((JPanel) component).getComponents()) {
	            	if (com instanceof JTextField) {
	            		((JTextField) com).setText("");;
		            }
	            }
	        } 
	            
	}
	public void showSchedule(){
		schedulePanel.removeAll();
		schedulePanel.setLayout(new FlowLayout());
		frame6.setSize(600, 500);
		frame6.setBounds(300, 300,600, 500);
		frame6.setVisible(true);
		c6.setLayout(new FlowLayout());
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
		c6.add(schedulePanel);
		con.show(schedule);
		c6.revalidate();
	}
    public void refreshcourseList(){
    	con.initialCourseList(courseList);
    }
    public void setsectionList(int i){
    	con.setsections(sectionList,i);
    }
    public void refreshroomList(){
    	con.initialRoomList(roomList);
    }
}
