package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import controller.ControllerS;
import model.Instructor;
import model.Student;

//student interface, is not finished yet
public class ViewS {

	private JFrame frame = new JFrame("student");
	private JFrame frame1 = new JFrame("schedule");
	private JFrame frame2 = new JFrame("addcourse");
	private JFrame frame3=new JFrame("Profile");
	private JFrame frame4=new JFrame("Changepassword");
	private Container c = frame.getContentPane();
	private Container c1 = frame1.getContentPane();
	private Container c2 = frame2.getContentPane();
	private Container c3 = frame3.getContentPane();
	private Container c4 = frame4.getContentPane();
	private JComboBox<String> jcb=new JComboBox<String>();
	private JList courseList=new JList();
	private Student student;
	private ControllerS con;
	private int selectedcourseindex;  //index of the course selected in courseList
	public ViewS(Student student){
	    this.student=student;
		frame.setSize(600, 600);
		frame.setBounds(450, 300, 600, 600);
		frame.setVisible(true);
		c.setLayout(null);
		con=new ControllerS(this);
		initFrame();
                  }
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
	public Student getStudent(){
		return student;
	}
	public void initFrame(){
		refreshcourseList();
		frame.setTitle("Student: "+student.getStudentid());
		JLabel a1=new JLabel("course selection:");
		a1.setBounds(20, 15, 120, 20);
		JLabel a2=new JLabel("courses:");
		a2.setBounds(20, 50, 120, 20);
		jcb.setBounds(140, 15, 100, 20);
		c.add(jcb);
		courseList.setBounds(100, 100, 200, 300);
		JScrollPane JSP1= new JScrollPane(courseList);
		JSP1.setBounds(140, 50, 220, 100);
        c.add(JSP1);
        c.add(a1);
        c.add(a2);
        JButton addcourse=new JButton("AddCourse");
        addcourse.setBounds(400, 15, 100, 25);
        JButton dropcourse=new JButton("DropCourse");
        dropcourse.setBounds(400, 50, 100, 25);
		JButton back=new JButton("return");
		back.setBounds(400, 500, 100, 25);
		JButton profile=new JButton("Profile");
		profile.setBounds(20, 500, 100, 25);
		JButton changepassword=new JButton("ChangePassword");
		changepassword.setBounds(150, 500, 100, 25);
        c.add(addcourse);
        c.add(dropcourse);
        c.add(back);
        c.add(changepassword);
        c.add(profile);
        ScheduleFrame();
        con.setcourses(jcb);
        courseList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(courseList.getValueIsAdjusting()){
				System.out.println(((JList)e.getSource()).getSelectedIndex());
				selectedcourseindex=((JList)e.getSource()).getSelectedIndex();
				}
			}});
        addcourse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(con.checkcourseid(jcb.getSelectedItem().toString(), student)){
					JOptionPane.showMessageDialog(null, "the course has been added!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else{
			    con.addCourse(jcb.getSelectedItem().toString(), student);
			    refreshcourseList();
			    ScheduleFrame();
				}
			}
        	
        });
        dropcourse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			    
				con.removecourse(selectedcourseindex,student);
				ScheduleFrame();
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
        back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeView();
			}
        	
        });
	}
	//create profile interface
		public void profileFrame(){
			frame3.setSize(300, 220);
			frame3.setBounds(450, 300, 300, 220);
			frame3.setVisible(true);
			c3.setLayout(new BorderLayout());
			JPanel fieldPanel=new JPanel();
			fieldPanel.setLayout(null);
			JLabel a1=new JLabel("studentid:");
			a1.setBounds(50, 20, 80, 20);
			JLabel a2=new JLabel("name:");
			a2.setBounds(50, 60, 80, 20);
			fieldPanel.add(a1);
			fieldPanel.add(a2);
			JTextField studentid=new JTextField();
			JTextField studentname=new JTextField();
			studentid.setBounds(130, 20, 100, 20);
			studentname.setBounds(130, 60, 100, 20);
			studentid.setEnabled(false);
			studentid.setText(con.getstudentid());
			studentname.setText(con.getstudentname());
			fieldPanel.add(studentid);
			fieldPanel.add(studentname);
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
					con.setstudentname(studentname.getText());
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
	public void ScheduleFrame(){
			JTable schedule=new JTable(9,6);
			schedule.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
			schedule.setEnabled(false);
			Object [][] section=new Object[9][6];
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
			JSP.setBounds(20, 170, 500, 300);
			//c2.add(schedule);
			c.add(JSP);
			con.show(schedule, student);
			c1.revalidate();
		}
	
	public void changeView() {
		// TODO Auto-generated method stub
		frame.setVisible(false);
		new Login();
	}
    public void refreshcourseList(){
    	con.initialCourseList(courseList, student);
    }
}
