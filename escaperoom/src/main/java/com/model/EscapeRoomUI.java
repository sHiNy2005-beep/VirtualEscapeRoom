package com.model;

import java.util.ArrayList;
import java.util.Map;
import com.speech.Speek;

public class EscapeRoomUI {

    private EscapeRoomFacade facade;

    EscapeRoomUI(){
        facade = new EscapeRoomFacade();
    }
    
    public void run() {
        System.out.println("\n****Murder Mystery Escape Room****");
        System.out.println("Welcome to the Hamton Mansion. Mr.Hamton has mysteriously died under suspicious circumstances, and you a detective must investigate his death and find his killer. You must gather evidence of the real culprit by solving puzzles in each room. Each correctly solved puzzle rewards you with a clue, which points to more than one person. Gather all the clues, and they will point to one person--the killer.");
        scenario1_CreateAccount(); 
        scenario2_EnterAnEscapeRoom();
        scenario3_Complete3Puzzles();
        scenario4_DataPersistence(); 
        scenario5_EndGame();
    }

    public void scenario1_CreateAccount() {
        System.out.println("\n===== Create Account - Duplicate User ======");
        
        if (!facade.createAccount("leni_rivers", "bob.dev@example.com", "LeniPass#123")) {
            System.out.println("Account creation failed! Duplicate username or email found.");
            System.out.println("(The email is already taken by another user)");
        } else {
            System.out.println("Account successfully created!");
        }

        System.out.println("\n====== Create Account - Success ===== ");
        boolean accountExists = false;
        for (User u : UserList.getInstance().getUsers()) {
            if (u.getUserName().equals("leni_rivers_detective")) {
                accountExists = true;
                break;
            }
        }
        
        if (!accountExists) {
            if (facade.createAccount("leni_rivers_detective", "leni.rivers@detective.com", "LeniPass#123")) {
                System.out.println("Account successfully created for Leni Rivers!");
            }
        } else {
            System.out.println("Account already exists for Leni Rivers!");
        }
        if (facade.login("leni_rivers_detective", "LeniPass#123")) {
            System.out.println("Login successful for Leni Rivers!");
        } else {
            System.out.println("Login failed for Leni Rivers!");
        }
    }

    public void scenario2_EnterAnEscapeRoom() {
        System.out.println("\n===== Enter an Escape Room - Hear the Story ======");

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
        story.append("The air is thick with dust and the faint scent of old leather and secrets. ");
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
        story.append("Solve the puzzles, gather the clues, and uncover the truth before it's too late.");

        System.out.println("\nStory:\n" + story.toString());
        Speek.speak(story.toString()); 

        RoomSession roomSession = facade.startGame(chosenRoom);
        if (roomSession == null) {
            System.out.println("Could not start game session. Make sure user is logged in.");
            return;
        }
        
        System.out.println("Room session started successfully!");
    }

    public void scenario3_Complete3Puzzles() {
        System.out.println("\n======= Completing 3 Puzzles =======");

        ArrayList<Room> rooms = facade.getAllRooms();
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getTitle() + " (" + r.getDifficulty() + ")");
        }

        System.out.println("\n===== Puzzle 1: Library =====");
        Room libraryRoom = null;
        for (Room r : rooms) {
            if ("Library".equals(r.getTitle())) {
                libraryRoom = r;
                break;
            }
        }
        
        if (libraryRoom != null && !libraryRoom.getPuzzles().isEmpty()) {
            RoomSession session = facade.startGame(libraryRoom);
            if (session != null) {
                System.out.println("Entered the Library.");
                
                Puzzle libraryPuzzle = libraryRoom.getPuzzles().get(0);
                System.out.println("\nPuzzle: " + libraryPuzzle.getTitle());
                System.out.println("Description: " + libraryPuzzle.getDescription());
                
                System.out.println("\nLeni uses a hint...");
                String hint = facade.useHint(libraryPuzzle.getTitle());
                System.out.println("Hint: " + hint);
                
                System.out.println("\nSubmitting answer 'THOMASISDISOWNED'...");
                facade.submitAnswer(libraryPuzzle.getTitle(), "THOMASISDISOWNED");
                if(libraryPuzzle.isSolved()) {
                    System.out.println("✓ Puzzle solved! Clue found: Thomas was disowned from the will.");
                } else {
                    System.out.println("✗ Incorrect answer. Try again.");
                }
            }
        }

        System.out.println("\n===== Puzzle 2: Garden - Using Items =====");
        Room gardenRoom = null;
        for (Room r : rooms) {
            if ("Garden".equals(r.getTitle())) {
                gardenRoom = r;
                break;
            }
        }
        
        if (gardenRoom != null && !gardenRoom.getPuzzles().isEmpty()) {
            RoomSession session = facade.startGame(gardenRoom);
            if (session != null) {
                System.out.println("Entered the Garden.");
                
                System.out.println("\nLeni collects items from the garden:");
                for (String item : gardenRoom.getItems()) {
                    session.collectItem(item);
                    System.out.println(" - Collected: " + item);
                }
                
                Puzzle gardenPuzzle = gardenRoom.getPuzzles().get(0);
                System.out.println("\nPuzzle: " + gardenPuzzle.getTitle());
                System.out.println("Description: " + gardenPuzzle.getDescription());
                
                if (gardenPuzzle instanceof ItemPuzzle) {
                    ItemPuzzle itemPuzzle = (ItemPuzzle) gardenPuzzle;
                    System.out.println("Required items: " + itemPuzzle.getRequiredItems());
                    System.out.println("Leni uses the shovel and rope to dig...");
                }
                
                System.out.println("\nLeni uses another hint...");
                String hint = facade.useHint(gardenPuzzle.getTitle());
                System.out.println("Hint: " + hint);
                
                System.out.println("\nSubmitting answer 'STATUE'...");
                facade.submitAnswer(gardenPuzzle.getTitle(), "STATUE");
                if(gardenPuzzle.isSolved()) {
                    System.out.println("✓ Puzzle solved! Clue found: A bloody statue was hidden in the garden.");
                } else {
                    System.out.println("✗ Incorrect answer. Try again.");
                }
            }
        }
        
        System.out.println("\n===== Puzzle 3: Bedroom =====");
        Room bedroomRoom = null;
        for (Room r : rooms) {
            if ("Bedroom".equals(r.getTitle())) {
                bedroomRoom = r;
                break;
            }
        }
        
        if (bedroomRoom != null && !bedroomRoom.getPuzzles().isEmpty()) {
            RoomSession session = facade.startGame(bedroomRoom);
            if (session != null) {
                System.out.println("Entered the Bedroom.");
                
                Puzzle bedroomPuzzle = bedroomRoom.getPuzzles().get(0);
                System.out.println("\nPuzzle: " + bedroomPuzzle.getTitle());
                System.out.println("Description: " + bedroomPuzzle.getDescription());
                
                System.out.println("\nSubmitting answer 'secret'...");
                facade.submitAnswer(bedroomPuzzle.getTitle(), "secret");
                if(bedroomPuzzle.isSolved()) {
                    System.out.println("✓ Puzzle solved! Clue found: A secret affair was discovered.");
                } else {
                    System.out.println("✗ Incorrect answer. Try again.");
                }
            }
        }
        
        System.out.println("\n3 puzzles completed successfully!");
    }

    public void scenario4_DataPersistence() {
        System.out.println("\n======= Logout -> Login -> Show Data Persistence and Progress =======");
        
        String demoUser = "leni_rivers_detective";
        
        if (facade.getCurrentUser() != null) {
            System.out.println("Logging out " + facade.getCurrentUser().getUserName() + "...");
            facade.logout();
            System.out.println("Logged out successfully.");
        }
        
        System.out.println("\nLogging back in as " + demoUser + "...");
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
        
        System.out.println("✓ Logged in as: " + current.getUserName());
        
        if (current.getSessions() == null || current.getSessions().isEmpty()) {
            System.out.println("No saved sessions for user: " + current.getUserName());
        } else {
            System.out.println("\n===== USER SESSION DATA =====");
            
            for (GameSession gameSession : current.getSessions()) {
                System.out.println("\nGame Session ID: " + gameSession.getSessionId());
                System.out.println("Session Completed: " + gameSession.isSessionCompleted());
                System.out.println("Rooms Visited: " + gameSession.getVisitedRoomsCount());
                System.out.println("Rooms Completed: " + gameSession.getCompletedRoomsCount());
                System.out.println("Total Hints Used: " + gameSession.getTotalHintsUsed());
                System.out.println("Total Puzzles Solved: " + gameSession.getTotalPuzzlesSolved());
                
                System.out.println("\n--- Room-by-Room Progress ---");
                for (Map.Entry<String, RoomSession> entry : gameSession.getAllRoomSessions().entrySet()) {
                    RoomSession roomSession = entry.getValue();
                    System.out.println("\n  Room: " + roomSession.getRoomTitle());
                    System.out.println("  Completion: " + roomSession.getCompletionPercent() + "%");
                    System.out.println("  Puzzles Solved: " + roomSession.getSolvedCount() + "/" + roomSession.getTotalPuzzles());
                    System.out.println("  Hints Used: " + roomSession.getHintsUsed());
                    
                    System.out.println("  Puzzle Details:");
                    for (PuzzleSession ps : roomSession.getPuzzleSessions()) {
                        System.out.println("    • " + ps.getPuzzleTitle());
                        System.out.println("      Solved: " + ps.isSolved());
                        if (ps.isSolved()) {
                            System.out.println("      Answer: " + ps.getFinalAnswer());
                        }
                        System.out.println("      Hints Used: " + ps.getNumHintsUsed());
                    }
                }
            }
        }

        System.out.println("\n===== json/User.json contents =====");
        try {
            java.nio.file.Path p = java.nio.file.Paths.get("json/User.json");
            if (java.nio.file.Files.exists(p)) {
                java.util.List<String> lines = java.nio.file.Files.readAllLines(p);
                for (String line : lines) {
                    System.out.println(line);
                }
            } else {
                System.out.println("json/User.json not found in workspace.");
            }
        } catch (Exception e) {
            System.out.println("Failed to read persisted JSON: " + e.getMessage());
        }
    }

    public void scenario5_EndGame() {
        System.out.println("\n======== Finishing the Game and Generating Certificate ========");
        
        if (facade.getCurrentUser() == null) {
            if (!facade.login("leni_rivers_detective", "LeniPass#123")) {
                System.out.println("Login failed!");
                return;
            }
        }
        System.out.println("Continuing as detective " + facade.getCurrentUser().getUserName() + "...");
        
        ArrayList<Room> rooms = facade.getAllRooms();
        
        GameSession currentSession = facade.getCurrentSession();
        
        System.out.println("\n===== Restoring Session Data =====");
        if (currentSession != null) {
            System.out.println("Current session found: " + currentSession.getSessionId());
            System.out.println("Rooms in session: " + currentSession.getVisitedRoomsCount());
            System.out.println("Total puzzles solved in session: " + currentSession.getTotalPuzzlesSolved());
            
            for (Map.Entry<String, RoomSession> entry : currentSession.getAllRoomSessions().entrySet()) {
                RoomSession roomSession = entry.getValue();
                System.out.println("\nRestoring room: " + roomSession.getRoomTitle());
                System.out.println("  Puzzles solved in this room: " + roomSession.getSolvedCount());
                
                for (Room room : rooms) {
                    if (room.getRoomId().equals(roomSession.getRoomId())) {
                        for (Puzzle puzzle : room.getPuzzles()) {
                            for (PuzzleSession ps : roomSession.getPuzzleSessions()) {
                                if (ps.getPuzzleTitle().equals(puzzle.getTitle()) && ps.isSolved()) {
                                    puzzle.setSolved(true);
                                    System.out.println("  ✓ Restored: " + puzzle.getTitle() + " (answer: " + ps.getFinalAnswer() + ")");
                                }
                            }
                        }
                        break;
                    }
                }
            }
        } else {
            System.out.println("No previous session found. Starting fresh.");
        }
        
        System.out.println("\n===== Solving Study Room Puzzle =====");
        Room studyRoom = null;
        for (Room r : rooms) {
            if ("Study".equals(r.getTitle())) {
                studyRoom = r;
                break;
            }
        }
        
        if (studyRoom != null && !studyRoom.getPuzzles().isEmpty()) {
            RoomSession session = facade.startGame(studyRoom);
            if (session != null) {
                System.out.println("Entered the Study.");
                
                Puzzle studyPuzzle = studyRoom.getPuzzles().get(0);
                System.out.println("\nPuzzle: " + studyPuzzle.getTitle());
                System.out.println("Description: " + studyPuzzle.getDescription());
                System.out.println("Submitting answer '104'...");
                
                facade.submitAnswer(studyPuzzle.getTitle(), 104);
                if(studyPuzzle.isSolved()) {
                    System.out.println("✓ Puzzle solved! The safe opens revealing a ledger.");
                }
            }
        } else {
            System.out.println("Study room not available or has no puzzles.");
        }

        System.out.println("\n===== Final Room: Conservatory - The Revelation =====");
        Room conservatoryRoom = null;
        for (Room r : rooms) {
            if ("Conservatory".equals(r.getTitle())) {
                conservatoryRoom = r;
                conservatoryRoom.setLocked(false);
                break;
            }
        }
        
        if (conservatoryRoom != null && !conservatoryRoom.getPuzzles().isEmpty()) {
            RoomSession session = facade.startGame(conservatoryRoom);
            if (session != null) {
                System.out.println("Entered the Conservatory - All evidence points here...");
                
                Puzzle finalPuzzle = conservatoryRoom.getPuzzles().get(0);
                System.out.println("\nFinal Puzzle: " + finalPuzzle.getTitle());
                System.out.println("Description: " + finalPuzzle.getDescription());
                System.out.println("\nMatching suspects to evidence...");
                
                facade.submitAnswer(finalPuzzle.getTitle(), 
                    "Thomas Hamton=Will Page,Lilly Hamton=Love Letter,Mr. Barner=Ledger,Ms. Louise=Bloody Statue");
                
                if(finalPuzzle.isSolved()) {
                    System.out.println("\n✓✓✓ CASE SOLVED! ✓✓✓");
                    System.out.println("Ms. Louise is the murderer!");
                    System.out.println("The bloody statue from the garden, combined with her access and motive, reveals the truth.");
                }
            }
        } else {
            System.out.println("Conservatory room not available or has no puzzles.");
        }
        
        facade.endGame();
        
        User leni = facade.getCurrentUser();
        int totalPuzzlesSolved = 0;
        int totalHints = 0;
        
        for (GameSession gs : leni.getSessions()) {
            totalPuzzlesSolved += gs.getTotalPuzzlesSolved();
            totalHints += gs.getTotalHintsUsed();
        }
        
        int finalScore = facade.getScore();
        
        if (conservatoryRoom != null) {
            conservatoryRoom.getLeaderboard().put(leni, finalScore);
            
            for (User u : UserList.getInstance().getUsers()) {
                if (!u.getUserName().equals(leni.getUserName()) && 
                    conservatoryRoom.getLeaderboard().size() < 5) {
                    int otherScore = 0;
                    if (u.getUserName().equals("alice123")) {
                        otherScore = 85000; 
                    } else if (u.getUserName().equals("bob_dev")) {
                        otherScore = 72000;
                    } else if (u.getUserName().equals("charlie_x")) {
                        otherScore = 79500;
                    }
                    
                    if (otherScore > 0) {
                        conservatoryRoom.getLeaderboard().put(u, otherScore);
                    }
                }
            }
            
            System.out.println("\n===== LEADERBOARD =====");
            Map<User, Integer> leaderboard = facade.getSortedLeaderboard(conservatoryRoom);
            int rank = 1;
            for (Map.Entry<User, Integer> entry : leaderboard.entrySet()) {
                System.out.println(rank + ". " + entry.getKey().getUserName() + " - " + entry.getValue() + " points");
                rank++;
            }
        }
        
        String certificate = generateCertificate(
            leni.getUserName(), 
            totalPuzzlesSolved, 
            totalHints, 
            "Murder Mystery", 
            finalScore
        );
        
        System.out.println("\n" + certificate);
        
        try {
            java.io.FileWriter writer = new java.io.FileWriter("certificate_" + leni.getUserName() + ".txt");
            writer.write(certificate);
            writer.close();
            System.out.println("\n✓ Certificate saved to: certificate_" + leni.getUserName() + ".txt");
        } catch (java.io.IOException e) {
            System.out.println("Error saving certificate: " + e.getMessage());
        }
    }

    private String generateCertificate(String username, int puzzlesSolved, int hintsUsed, String difficulty, int finalScore) {
        StringBuilder cert = new StringBuilder();
        cert.append("╔═══════════════════════════════════════════════════════════╗\n");
        cert.append("║           CERTIFICATE OF COMPLETION                      ║\n");
        cert.append("╚═══════════════════════════════════════════════════════════╝\n\n");
        cert.append("                Murder Mystery Escape Room\n");
        cert.append("              Hamton Mansion Investigation\n\n");
        cert.append("═════════════════════════════════════════════════════════════\n\n");
        cert.append("  This certifies that Detective:\n\n");
        cert.append("                  " + username.toUpperCase() + "\n\n");
        cert.append("  has successfully solved the mysterious death of\n");
        cert.append("  Mr. Reginald Hamton and brought the killer to justice!\n\n");
        cert.append("  Through keen observation and logical deduction,\n");
        cert.append("  the truth was revealed: Ms. Louise is the murderer.\n\n");
        cert.append("═════════════════════════════════════════════════════════════\n");
        cert.append("                   INVESTIGATION STATS\n");
        cert.append("═════════════════════════════════════════════════════════════\n\n");
        cert.append("  Difficulty Level:        " + difficulty + "\n");
        cert.append("  Puzzles Solved:          " + puzzlesSolved + "\n");
        cert.append("  Hints Used:              " + hintsUsed + "\n");
        cert.append("  Final Detective Score:   " + finalScore + "\n\n");
        cert.append("═════════════════════════════════════════════════════════════\n");
        cert.append("                    CASE CLOSED\n");
        cert.append("═════════════════════════════════════════════════════════════\n");
        cert.append("  Date: " + java.time.LocalDate.now() + "\n");
        cert.append("  \"Justice Prevails in the Darkness\"\n");
        cert.append("═════════════════════════════════════════════════════════════\n");
        return cert.toString();
    }

    public static void main(String[] args) {
        EscapeRoomUI escapeRoomInterface = new EscapeRoomUI();
        escapeRoomInterface.run();
    }
}