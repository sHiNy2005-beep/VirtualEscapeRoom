package com.model;

import java.util.ArrayList;
import java.util.Scanner;

public class EscapeRoomUI {

  private EscapeRoomFacade facade;

   EscapeRoomUI(){
    facade =new EscapeRoomFacade();
   }
    
	public void run() {
    System.out.println("Welcome to the Virtual Escape Room!");
		scenario1(); //login, list rooms, select room, start game, solve puzzle, end game
		scenario2();//sign up,select different room, start game, use hint, solve puzzle, end game
	}


    public void scenario1(){
        System.out.println("Scenario 1: ");

        if(!facade.login("bob_dev","BobPass#88")){
            System.out.println("Sorry we couldn't login.");
            return;
        }
        System.out.println("Logged in as " +facade.getCurrentUser().getUserName());

        ArrayList<Room> rooms = facade.getRoomList();
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getTitle() + " (" + r.getDifficulty() + ")");
        }




	}

    public void scenario2() {
		System.out.println("Scenario 2: ");
        

		
    }
    


    public static void main(String[] args) {
		EscapeRoomUI EscaperoomInterface = new EscapeRoomUI();
		EscaperoomInterface.run();

	}
   
}