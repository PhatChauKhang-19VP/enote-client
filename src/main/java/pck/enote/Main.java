package pck.enote;

import pck.enote.api.API;

public class Main {
    public static void main(String[] args) {
        if (API.connectToServer()) {
            System.out.println("Connected to server");
        } else {
            System.out.println("Failed to connect to server");
        }
        Enote.main(args);
    }
}