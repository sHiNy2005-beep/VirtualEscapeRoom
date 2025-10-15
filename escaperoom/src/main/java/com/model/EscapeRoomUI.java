package com.model;

import java.util.Scanner;
public class EscapeRoomUI {

    public static final String WELCOME_MESSAGE="Welcome to the Virtual Escape Room!";
    private EscapeRoomFacade facade;
    private Scanner scanner;

    public EscapeRoomUI(EscapeRoomFacade facade) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println(WELCOME_MESSAGE);
    }

        /* 
        private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Login");
        System.out.println("2. View Available Rooms");
        System.out.println("3. Start Game");
        System.out.println("4. Submit Answer");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }
    */

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (facade.login(username, password) != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials.");
        }
    }
   
}