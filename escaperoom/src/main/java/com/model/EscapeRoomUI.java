package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.speech.Speek;

public class EscapeRoomUI {

  private EscapeRoomFacade facade;


   EscapeRoomUI(){
    facade =new EscapeRoomFacade();
   }
    
	public void run() {
    System.out.println("\n****Welcome to the Virtual Escape Room!****");
    System.out.println("Goal: ");
    System.out.println("You must gather evidence of the real culprit by solving puzzles in each room.");
	System.out.println("Each correctly solved puzzle rewards you with a clue, which points to more than one person.");
    System.out.println("Gather all the clues, and they will point to one person. Good luck!");
    scenario1(); 
	scenario2();//sign up,select different room, start game, use hint, solve puzzle, end game
    scenario3();//login as different user, list rooms, select room, start game, solve puzzle, end game
    scenario4(); //login, search for an easy room, and check the leaderboard for aformentioned room
	}


    public void scenario1(){
        System.out.println("\nCreate Account - Duplicate User: ");

        System.out.println("Attempting to create account for Bob Dev...");
      if (!facade.createAccount("bob_dev", "bob.dev@example.com", "BobPass#88")) {
        System.out.println("Account creation failed! Duplicate username or email found.");
      } else {
        System.out.println("Account successfully created!");
      }

      System.out.println("\nCreate Account - Sucess: ");
    if (!facade.createAccount("leni_rivers", "leni.r@example.com", "LeniPass#123")) {
      System.out.println("Account successfully created for Leni Rivers!");

    
    if (facade.login("leni_rivers", "LeniPass#123")) {
        System.out.println("Login successful for Leni Rivers!");
    } else {
        System.out.println("Login failed for Leni Rivers!");
    }
    }
        
    }



    public void scenario2() {
        System.out.println("\nScenario 2: ");

        // Sign up AND login
        if (UserList.getInstance().signUp("diana_k", "DianaStrong*77")) {
            System.out.println("User already exists or could not sign up.");
            return;
        }

        if (!facade.login("diana_k", "DianaStrong*77")) {
            System.out.println("Sorry we couldn't login.");
            return;
        }
        System.out.println("Logged in as " + facade.getCurrentUser().getUserName());

        ArrayList<Room> rooms = facade.getAllRooms();
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getTitle() + " (" + r.getDifficulty() + ")");
        }

        System.out.println("\nSelected room: Library...");
        Room selectedRoom = null;
        for (Room r : rooms) {
            if ("Library".equalsIgnoreCase(r.getTitle())) {
                selectedRoom = r;
                break;
            }
        }
        if (selectedRoom != null) {
            GameSession session = facade.startGame(selectedRoom);
            if (session != null) {
                System.out.println("Entered the Library.");

                Puzzle puzzle = selectedRoom.getPuzzles().get(0);
                System.out.println("Puzzle: " + puzzle.getTitle());
                System.out.println("Description: " + puzzle.getDescription());

                System.out.println("Using a hint...");
                String hint = facade.useHint(puzzle.getTitle());  
                System.out.println("Hint: " + hint);

                System.out.println("Submitting answer 'knowledgeispower'...");
                boolean solved = facade.submitAnswer(puzzle.getTitle(), "knowledgeispower");
                if (solved) {
                    System.out.println("Puzzle solved successfully!");
                } else {
                    System.out.println("Incorrect solution. Try again!");
                }

                System.out.println("Ending game...");
                facade.endGame();
                System.out.println("Game ended. Progress saved.");
            } else {
                System.out.println("Could not start game.");
            }
        } else {
            System.out.println("Could not start game.");
        }
    }


            
    


    public void scenario3()
    {
        System.out.println("\nScenario 3: ");
        if(!facade.login("charlie_x", "CharPwd@123"))
            System.out.println("Sorry we couldn't login.");

        System.out.println("Logged in as " +facade.getCurrentUser().getUserName());

        ArrayList<Room> rooms = facade.getAllRooms();
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getTitle() + " (" + r.getDifficulty() + ")");
        }

        System.out.println("\nStarting room: Study...");
        Room selectedRoom = null;
        for (Room r : rooms) {
            if ("Study".equals(r.getTitle())) {
                selectedRoom = r;
                break;
            }
        }
        if (selectedRoom != null) {
            GameSession session = facade.startGame(selectedRoom);
            if (session != null) {
                System.out.println("Entered the Study.");
            } else {
                System.out.println("Could not start game.");
            }
        }

        Room room = facade.getAllRooms().get(0);
        Puzzle StudyPuzzle = room.getPuzzles().get(0);

        System.out.println("Puzzle: " + StudyPuzzle.getTitle());
        System.out.println("Description: " + StudyPuzzle.getDescription());

        System.out.println("Submitting answer 'red-blue-green-yellow'...");
        facade.submitAnswer(StudyPuzzle.getTitle(), "red-blue-green-yellow");
        if(StudyPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        } else {
            System.out.println("Incorrect answer. Try again.");
        }

        facade.endGame();
        System.out.println("Game ended. Progress saved.");
        facade.logout();
    }

    public void scenario4() {
        System.out.println("\nScenario 4: ");

        if (!facade.login("alice123", "Alice!2025Secure")) {
            System.out.println("Sorry we couldn't login.");
            return;
        }
        System.out.println("Logged in as " + facade.getCurrentUser().getUserName());

        ArrayList<Room> rooms = facade.getAllRooms();
        System.out.println("\nSearching for Easy difficulty rooms...");

        boolean foundEasyRoom = false;
        for (Room r : rooms) {
            if ("Easy".equalsIgnoreCase(r.getDifficulty())) {
                foundEasyRoom = true;
                System.out.println("\n--- Room: " + r.getTitle() + " (" + r.getDifficulty() + ") ---");
        
                Map<User, Integer> leaderboard = facade.getSortedLeaderboard(r);
        
                if (leaderboard.isEmpty()) {
                    System.out.println("No scores recorded yet for this room.");
                } else {
                    System.out.println("Leaderboard:");
                    System.out.println("Rank | Username | Score");
                    System.out.println("-----|----------|-------");
                
                    int rank = 1;
                    for (Map.Entry<User, Integer> entry : leaderboard.entrySet()) {
                        System.out.println(String.format("%-5d| %-8s | %d", 
                            rank, entry.getKey().getUserName(), entry.getValue()));
                        rank++;
                    }
                }
            }
        }

        if (!foundEasyRoom) {
            System.out.println("No Easy difficulty rooms found.");
        }

        facade.logout();
    }



    public static void main(String[] args) {
		EscapeRoomUI EscaperoomInterface = new EscapeRoomUI();
		EscaperoomInterface.run();
        Speek.speak("Welcome to the Virtual Escape Room. You must gather evidence of the real culprit by solving puzzles in each room. Each correctly solved puzzle rewards you with a clue, which points to more than one person. Gather all the clues, and they will point to one person.");;
	}
   
}
