package controller;

import model.Instructor;
import model.Share;
import view.Login;
//controller for the login interface
public class Controller {
    private Login v;
	public Controller(Login v){
		this.v=v;
	}
	//check the password, enter the corresponding interface 
	public void check(Boolean a,Boolean i,Boolean s,String username,String password){
		if(a){
		if(username.equals("administrator")&&password.equals("123")){
			System.out.println("administrator");
			v.changeViewM();
		}
		}
		else if(i){
		if(Share.instructors.getInstructor(username).getPassword().equals(password)){
			System.out.println("instructor");
			//Instructor instructor=new Instructor("200912345");
			v.changeViewI(Share.instructors.getInstructor(username));
		}
		else{
			
		}
		}
		else if(s){
			if(Share.students.getStudent(username).getPassword().equals(password)){
				System.out.println("student");
				v.changeViewS(Share.students.getStudent(username));
			}		
			}
		else{
			
		}
	}
}
