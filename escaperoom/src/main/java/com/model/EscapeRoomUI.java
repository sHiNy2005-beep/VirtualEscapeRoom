package com.model;

import java.util.ArrayList;
import java.util.Map;
import com.speech.Speek;

public class EscapeRoomUI {

  private EscapeRoomFacade facade;


   EscapeRoomUI(){
    facade =new EscapeRoomFacade();
   }
    
	public void run() {

    System.out.println("\n****Murder Mystery Escape Room****");
    //Speek.speak("Welcome to the Hamton Mansion. Mr.Hamton has mysteriusly died under suspicious circumstances, and you a detective must investigate his death and find his killer. You must gather evidence of the real culprit by solving puzzles in each room. Each correctly solved puzzle rewards you with a clue, which points to more than one person. Gather all the clues, and they will point to one person--the killer.");;
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
        System.out.println("\nScenario 3: Completing 3 Puzzles");

        ArrayList<Room> rooms = facade.getAllRooms();
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getTitle() + " (" + r.getDifficulty() + ")");
        }

        // First Puzzle
        System.out.println("\nStarting room: Library...");
        Room selectedRoom = null;
        for (Room r : rooms) {
            if ("Library".equals(r.getTitle())) {
                selectedRoom = r;
                break;
            }
        }
        if (selectedRoom != null) {
            GameSession session = facade.startGame(selectedRoom);
            if (session != null) {
                System.out.println("Entered the Library.");
            } else {
                System.out.println("Could not start game.");
            }
        }

        Room room = facade.getAllRooms().get(0);
        Puzzle LibraryPuzzle = room.getPuzzles().get(0);

        System.out.println("Puzzle: " + LibraryPuzzle.getTitle());
        System.out.println("Description: " + LibraryPuzzle.getDescription());

        System.out.println("Submitting answer THOMASISDISOWNED...");
        facade.submitAnswer(LibraryPuzzle.getTitle(), "THOMASISDISOWNED");
        if(LibraryPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        } else {
            System.out.println("Incorrect answer. Try again.");
        }

        // Second Puzzle
        System.out.println("\nStarting next room: Garden...");
        selectedRoom = null;
        for (Room r : rooms) {
            if ("Garden".equals(r.getTitle())) {
                selectedRoom = r;
                break;
            }
        }
        if (selectedRoom != null) {
            GameSession session = facade.startGame(selectedRoom);
            if (session != null) {
                System.out.println("Entered the Garden.");
            } else {
                System.out.println("Could not start game.");
            }
        }

        room = facade.getAllRooms().get(0);
        Puzzle GardenPuzzle = room.getPuzzles().get(0);

        System.out.println("Puzzle: " + GardenPuzzle.getTitle());
        System.out.println("Description: " + GardenPuzzle.getDescription());

        System.out.println("Submitting answer 'STATUE'...");
        facade.submitAnswer(GardenPuzzle.getTitle(), "STATUE");
        if(GardenPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        } else {
            System.out.println("Incorrect answer. Try again.");
        }

        // Third Puzzle
        System.out.println("\nStarting the next room: Bedroom...");
        selectedRoom = null;
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
        }

        room = facade.getAllRooms().get(0);
        Puzzle BedroomPuzzle = room.getPuzzles().get(0);

        System.out.println("Puzzle: " + BedroomPuzzle.getTitle());
        System.out.println("Description: " + BedroomPuzzle.getDescription());

        System.out.println("Submitting answer 'secret'...");
        facade.submitAnswer(BedroomPuzzle.getTitle(), "secret");
        if(BedroomPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        } else {
            System.out.println("Incorrect answer. Try again.");
        }
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
        System.out.println("\n json/User.json contents:");
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





    public void scenario5_EndGame() {
        System.out.println("\nScenario 5: Finishing the Game");
        
        if (facade.getCurrentUser() != null) {
            facade.logout();
        }
        
        if (!facade.login("leni_rivers", "LeniPass#123")) {
            System.out.println("Login failed for Leni Rivers!");
            return;
        }
        System.out.println("Login successful for Leni Rivers!");
        
        ArrayList<Room> rooms = facade.getAllRooms();
        Room libraryRoom = null;
        for (Room r : rooms) {
            if ("Library".equals(r.getTitle())) {
                libraryRoom = r;
                break;
            }
        }
        
        if (libraryRoom != null) {
            GameSession existingSession = facade.getExistingSession(libraryRoom);
            
            if (existingSession != null) {
                System.out.println("Found previous session in " + libraryRoom.getTitle());
                System.out.println("Progress: " + existingSession.getCompletionPercent() + "%");
                System.out.println("Would you like to continue this session? (yes/no)");
                
                String userInput = "yes";
                System.out.println("User input: " + userInput);
                
                if (userInput.equalsIgnoreCase("yes")) {
                    facade.continueSession(libraryRoom);
                    System.out.println("Resumed session in Library.");
                } else {
                    facade.startGame(libraryRoom);
                    System.out.println("Started new session in Library.");
                }
            } else {
                facade.startGame(libraryRoom);
                System.out.println("Started new session in Library.");
            }
        }
        
        System.out.println("\nStarting room: Study...");
        Room studyRoom = null;
        for (Room r : rooms) {
            if ("Study".equals(r.getTitle())) {
                studyRoom = r;
                break;
            }
        }
        
        if (studyRoom != null) {
            GameSession session = facade.startGame(studyRoom);
            if (session != null) {
                System.out.println("Entered the Study.");
            }
        }

        Puzzle StudyPuzzle = studyRoom.getPuzzles().get(0);
        System.out.println("Puzzle: " + StudyPuzzle.getTitle());
        System.out.println("Description: " + StudyPuzzle.getDescription());
        System.out.println("Submitting answer '104'...");
        
        facade.submitAnswer(StudyPuzzle.getTitle(), 104);
        if(StudyPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        }

        System.out.println("\nStarting the final room: Conservatory...");
        Room conservatoryRoom = null;
        for (Room r : rooms) {
            if ("Conservatory".equals(r.getTitle())) {
                conservatoryRoom = r;
                conservatoryRoom.setLocked(false);
                break;
            }
        }
        
        if (conservatoryRoom != null) {
            GameSession session = facade.startGame(conservatoryRoom);
            if (session != null) {
                System.out.println("Entered the Conservatory.");
                session.startSession();
            }
        }

        Puzzle ConservatoryPuzzle = conservatoryRoom.getPuzzles().get(0);
        System.out.println("Puzzle: " + ConservatoryPuzzle.getTitle());
        System.out.println("Description: " + ConservatoryPuzzle.getDescription());
        System.out.println("Submitting answer...");
        
        facade.submitAnswer(ConservatoryPuzzle.getTitle(), "Thomas Hamton=Will Page,Lilly Hamton=Love Letter,Mr. Barner=Ledger,Ms. Louise=Bloody Statue");
        if(ConservatoryPuzzle.isSolved()) {
            System.out.println("Puzzle solved!");
            System.out.println("CASE SOLVED! Ms. Louise is the murderer!");
        }
        
        facade.endGame();
        
        User leni = facade.getCurrentUser();
        int totalPuzzlesSolved = 0;
        int totalHints = 0;
        int aggregatedScore = 0;
        
        for (GameSession s : leni.getSessions()) {
            for (PuzzleSession ps : s.getPuzzleSessions()) {
                if (ps.isSolved()) totalPuzzlesSolved++;
                totalHints += ps.getNumHintsUsed();
            }
        }
        
        int baseScore = 10000;
        int puzzleBonus = totalPuzzlesSolved * 1000;
        int hintPenalty = totalHints * 200;
        double difficultyMultiplier = 2.0;
        aggregatedScore = (int)((baseScore + puzzleBonus - hintPenalty) * difficultyMultiplier);
        
        conservatoryRoom.getLeaderboard().put(leni, aggregatedScore);
        
        System.out.println("\nAggregated Score (all puzzle sessions): " + aggregatedScore);
        System.out.println("\nConservatory Leaderboard:");
        
        Map<User, Integer> leaderboard = facade.getSortedLeaderboard(conservatoryRoom);
        int rank = 1;
        for (Map.Entry<User, Integer> entry : leaderboard.entrySet()) {
            System.out.println(rank + ". " + entry.getKey().getUserName() + " - " + entry.getValue());
            rank++;
        }
        
        String certificate = generateCertificate(leni.getUserName(), totalPuzzlesSolved, totalHints, "All Rooms", aggregatedScore);
        System.out.println("\n" + certificate);
        
        try {
            java.io.FileWriter writer = new java.io.FileWriter("certificate_" + leni.getUserName() + ".txt");
            writer.write(certificate);
            writer.close();
            System.out.println("Certificate saved to: certificate_" + leni.getUserName() + ".txt");
        } catch (java.io.IOException e) {
            System.out.println("Error saving certificate: " + e.getMessage());
        }
    }

    private String generateCertificate(String username, int puzzlesSolved, int hintsUsed, String difficulty, int finalScore) {
        StringBuilder cert = new StringBuilder();
        cert.append("═══════════════════════════════════════════════════════════\n");
        cert.append("           CERTIFICATE OF COMPLETION\n");
        cert.append("═══════════════════════════════════════════════════════════\n\n");
        cert.append("                Murder Mystery Escape Room\n");
        cert.append("              Hamton Mansion Investigation\n\n");
        cert.append("═══════════════════════════════════════════════════════════\n\n");
        cert.append("  This certifies that Detective:\n\n");
        cert.append("                  " + username.toUpperCase() + "\n\n");
        cert.append("  has successfully solved the mysterious death of\n");
        cert.append("  Mr. Reginald Hamton and brought the killer to justice!\n\n");
        cert.append("  Through keen observation and logical deduction,\n");
        cert.append("  the truth was revealed: Ms. Louise is the murderer.\n\n");
        cert.append("═══════════════════════════════════════════════════════════\n");
        cert.append("                   INVESTIGATION STATS\n");
        cert.append("═══════════════════════════════════════════════════════════\n\n");
        cert.append("  Difficulty Level:        " + difficulty + "\n");
        cert.append("  Puzzles Solved:          " + puzzlesSolved + "\n");
        cert.append("  Hints Used:              " + hintsUsed + "\n");
        cert.append("  Final Detective Score:   " + finalScore + "\n\n");
        cert.append("═══════════════════════════════════════════════════════════\n");
        cert.append("                    CASE CLOSED\n");
        cert.append("═══════════════════════════════════════════════════════════\n");
        cert.append("  Date: " + java.time.LocalDate.now() + "\n");
        cert.append("  \"Justice Prevails in the Darkness\"\n");
        cert.append("═══════════════════════════════════════════════════════════\n");
        return cert.toString();
    }



    public static void main(String[] args) {
		EscapeRoomUI EscaperoomInterface = new EscapeRoomUI();
		EscaperoomInterface.run();
        
	}
   
}
