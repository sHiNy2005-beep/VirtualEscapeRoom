package com.model;

import java.util.Scanner;
public class EscapeRoomUI {
    private EscapeRoomFacade facade;


   EscapeRoomUI(){
    facade=new EscapeRoomFacade();
   }
    
	public void run() {
		scenario1();
		scenario2();
	}


    public void scenario1(){
        System.out.println();

        if(!facade.login("bob_dev","CharPwd@123")){
            System.out.println("Sorry we couldn't login.");
            return;
        }
        System.out.println("Bob Dev is now logged in");
	}

    public void scenario2() {
		System.out.println();

		if (!facade.login("charlie_x", "DianaStrong*77")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}
		System.out.println("Bobby Smith is now logged in");
    }
    


    public static void main(String[] args) {
		EscapeRoomUI EscaperoomInterface = new EscapeRoomUI();
		EscaperoomInterface.run();

	}
   
}