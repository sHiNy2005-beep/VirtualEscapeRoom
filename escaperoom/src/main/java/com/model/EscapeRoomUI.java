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
        scenario3();// 
	}


    public void scenario1(){
        System.out.println("Scenario 1: ");

        if(!facade.login("bob_dev","BobPass#88")){
            System.out.println("Sorry we couldn't login.");
            return;
        }
        System.out.println("Logged in as " +facade.getCurrentUser().getUserName());

        ArrayList<Room> rooms = facade.getAllRooms();
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getTitle() + " (" + r.getDifficulty() + ")");
        }

        System.out.println("\nStarting room: Bedroom...");
        Room selectedRoom = null;
        for (Room r : rooms) {
            if ("Bedroom".equals(r.getTitle())) {
                selectedRoom = r;
                break;
            }
        }
        if (selectedRoom != null) {
            GameSession session = facade.startGame(selectedRoom);
            if (session != null) {
                System.out.println("Entered the Bedroom.");
            } else {
                System.out.println("Could not start game.");
            }
        } else {
            System.out.println("Could not start game.");
        }


      



    }
    public void scenario2() {
		System.out.println("Scenario 2: ");

        if(!facade.login("charlie_x","DianaStrong*77")){
            System.out.println("Sorry we couldn't login.");
            return;
        }
        System.out.println("Logged in as " +facade.getCurrentUser().getUserName());


        System.out.println("\nSelected room: Library...");

        
    
    public void scenario3(){
        
    }




    public static void main(String[] args) {
		EscapeRoomUI EscaperoomInterface = new EscapeRoomUI();
		EscaperoomInterface.run();

	}
   
}
