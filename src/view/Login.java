package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.Instructor;
import model.Student;

//login interface
public class Login{
	//public static CourseList courses=new CourseList();
	private JFrame frame = new JFrame("Login");
	private Container c = frame.getContentPane();
	private Controller con;
	public Login(){
		//JFrame frame=new JFrame("Login");
		frame.setSize(350, 250);
		frame.setBounds(450, 300, 350, 250);
		frame.setVisible(true);
		c.setLayout(new BorderLayout());
		con=new Controller(this);
		initFrame();
	}
	//view initialize
	public void initFrame(){
		//top
		JPanel titlePanel=new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.add(new JLabel("login"));
		c.add(titlePanel,"North");
		//middle
		JPanel fieldPanel=new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1=new JLabel("username:");
		a1.setBounds(50, 20, 80, 20);
		JLabel a2=new JLabel("password:");
		a2.setBounds(50, 60, 80, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		JTextField username=new JTextField();
		username.setText("administrator");
		JPasswordField password=new JPasswordField();
		username.setBounds(130, 20, 100, 20);
		password.setBounds(130, 60, 100, 20);
		fieldPanel.add(username);
		fieldPanel.add(password);
        JRadioButton admin=new JRadioButton("administrator");
        JRadioButton instructor=new JRadioButton("instructor");
        JRadioButton student=new JRadioButton("student");
        admin.setBounds(20, 100, 110, 20);
        instructor.setBounds(130, 100, 90, 20);
        student.setBounds(220, 100, 80, 20);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(admin);
        buttonGroup.add(instructor);
        buttonGroup.add(student);
        fieldPanel.add(admin);
        fieldPanel.add(instructor);
        fieldPanel.add(student);
		c.add(fieldPanel, "Center");
		//bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok=new JButton("confirm");
		JButton cancel=new JButton("cancel");
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		c.add(buttonPanel, "South");
		//add Listener
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				char[] pass = password.getPassword();
				String password0 = new String(pass);
				if(username.getText().equals("")||password0.equals("")){
					Object[] ob={"ok"};
					JOptionPane.showMessageDialog(null, "empty input!", "warning",JOptionPane.WARNING_MESSAGE);
				}
				else{
				con.check(admin.isSelected(),instructor.isSelected(),student.isSelected(),username.getText(),password0);
				}
			}
			
		});
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
			new Login() ;
			}
		});
	}
	//jump to administrator interface
	public void changeViewM() {
		// TODO Auto-generated method stub
		ViewA v=new ViewA();
		this.frame.setVisible(false);
	}
	//jump to instructor interface
	public void changeViewI(Instructor instructor) {
		// TODO Auto-generated method stub
		ViewI v=new ViewI(instructor);
		this.frame.setVisible(false);
	}
	//jump to student interface
	public void changeViewS(Student student) {
		// TODO Auto-generated method stub
		ViewS v=new ViewS(student);
		this.frame.setVisible(false);
	}
}
