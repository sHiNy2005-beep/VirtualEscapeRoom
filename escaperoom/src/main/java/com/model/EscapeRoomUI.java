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
    scenario1_CreateAccount(); 
	scenario2_EnterAnEscapeRoom();
    scenario3_Complete3Puzzles();
    scenario4_DataPersistence(); 
    scenario5_EndGame();
    
	}



    public void scenario1_CreateAccount() //Mashal
    {
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


    public void scenario2_EnterAnEscapeRoom() { // Enter the escape room and hear the story // shiny 
        System.out.println("\nScenario 5: Enter an Escape Room - Hear the Story");

        ArrayList<Room> rooms = facade.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms available to enter.");
            return;
        }

        Room chosenRoom = rooms.get(0);

        String playerName = "Player";
        if (facade.getCurrentUser() == null || facade.getCurrentUser().getUserName() == null) {
            System.out.println("No user logged in. Please log in before entering a room.");
            return;
        } else {
            playerName = facade.getCurrentUser().getUserName();
        }

        System.out.println(playerName + " looks at the list of rooms and chooses: " + chosenRoom.getTitle());
        System.out.println("(There " + (rooms.size() == 1 ? "is only one room" : "are " + rooms.size() + " rooms") + " available to enter.)");

        System.out.println(playerName + " has entered the room: " + chosenRoom.getTitle());

        StringBuilder story = new StringBuilder();
        story.append(playerName).append(" pushes open the heavy door to the ").append(chosenRoom.getTitle()).append(". ");
        story.append("The air is thick with dust and the faint residue of forgotten secrets. ");
        story.append("This room is marked as '").append(chosenRoom.getDifficulty()).append("', and promises ");
        story.append(chosenRoom.getPuzzles().size()).append(" challenge");
        if (chosenRoom.getPuzzles().size() != 1) story.append("s");
        story.append(" to solve. ");

        if (!chosenRoom.getPuzzles().isEmpty()) {
            Puzzle firstPuzzle = chosenRoom.getPuzzles().get(0);
            if (firstPuzzle.getDescription() != null && !firstPuzzle.getDescription().trim().isEmpty()) {
                story.append("On a dusty table lies a note. It reads: \"")
                    .append(firstPuzzle.getDescription()).append("\". ");
            }
        }

        story.append("Shadows gather in the corners; the clock ticks somewhere beyond the walls. ");
        story.append("Solve the puzzles, gather the clues, and escape before time runs out.");

        System.out.println("\nStory:\n" + story.toString());
        Speek.speak(story.toString()); 

        GameSession session = facade.startGame(chosenRoom);
        if (session == null) {
            System.out.println("Could not start game session. Make sure user is logged in.");
            return;
        }

    }

    

    public void scenario3_Complete3Puzzles() //murewa
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

        System.out.println("Submitting answer '140'...");
        facade.submitAnswer(StudyPuzzle.getTitle(), 140);
        if(StudyPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        } else {
            System.out.println("Incorrect answer. Try again.");
        }

        facade.endGame();
        System.out.println("Game ended. Progress saved.");
        facade.logout();
    }




    public void scenario4_DataPersistence () {
        System.out.println("\nScenario 6: Logout -> Login -> Show Data Persistence and Progress");
        String demoUser = "leni_rivers";
        if (facade.getCurrentUser() != null) {
            try {
                facade.logout();
            } catch (Exception e) {
                System.out.println("Logout encountered an issue: " + e.getMessage());
            }
        }
        String demoPass = null;
        for (User u : UserList.getInstance().getUsers()) {
            if (u.getUserName().equalsIgnoreCase(demoUser)) {
                demoPass = u.getPassword();
                break;
            }
        }
        if (demoPass == null) {
            System.out.println("Demo user '" + demoUser + "' not found in user store. Cannot demonstrate persistence.");
            return;
        }
        if (!facade.login(demoUser, demoPass)) {
            System.out.println("Failed to login as '" + demoUser + "' for demo.");
            return;
        }
        User current = facade.getCurrentUser();
        if (current == null) {
            System.out.println("No current user after login. Aborting.");
            return;
        }
        System.out.println("Logged in as: " + current.getUserName());
        if (current.getSessions() == null || current.getSessions().isEmpty()) {
            System.out.println("No saved sessions for user: " + current.getUserName());
        } else {
            System.out.println("User sessions:");
            for (GameSession s : current.getSessions()) {
                Room r = s.getRoom();
                int totalPuzzles = r != null ? r.getPuzzles().size() : 0;
                int solved = 0;
                System.out.println("\nSession: " + s.getSessionId());
                System.out.println("Room: " + (r != null ? r.getTitle() : "Unknown") + " | Completed: " + s.isCompleted());

                for (PuzzleSession ps : s.getPuzzleSessions()) {
                    if (ps.isSolved()) solved++;
                }

                int percent = (totalPuzzles == 0) ? 0 : (int) ((solved * 100.0) / totalPuzzles);
                System.out.println("Progress: " + percent + "% (" + solved + "/" + totalPuzzles + " puzzles solved)");
                System.out.println("Questions answered:");
                for (PuzzleSession ps : s.getPuzzleSessions()) {
                    if (ps.isSolved()) {
                        System.out.println(" - " + ps.getPuzzleTitle() + " -> Answer: " + ps.getFinalAnswer());
                    }
                }
                System.out.println("Hints used per question:");
                for (PuzzleSession ps : s.getPuzzleSessions()) {
                    if (ps.getNumHintsUsed() > 0) {
                        System.out.println(" - " + ps.getPuzzleTitle() + " : " + ps.getNumHintsUsed() + " hint(s)");
                    }
                }

                System.out.println("Total hints used in session: " + s.getHintsUsed());
            }
        }
        System.out.println("\nPersisted json/User.json contents:");
        try {
            java.nio.file.Path p = java.nio.file.Paths.get("json/User.json");
            if (java.nio.file.Files.exists(p)) {
                java.util.List<String> lines = java.nio.file.Files.readAllLines(p);
                for (String line : lines) System.out.println(line);
            } else {
                System.out.println("json/User.json not found in workspace.");
            }
        } catch (Exception e) {
            System.out.println("Failed to read persisted JSON: " + e.getMessage());
        }
    }





    public void scenario5_EndGame() { //dhruv 
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
