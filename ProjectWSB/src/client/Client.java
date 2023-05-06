package client;

/**
 *
 * @author dzelazny
 */
import echoserver.Filecreator;

import java.net.*;
import java.io.*;
import java.util.Random;

public class Client {

    public static void main(String args[]) {
        String host = "localhost";
        int port = 0;
        try {
            port = new Integer("6666").intValue();
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy argument: port");
            System.exit(-1);
        }
        //Inicjalizacja gniazda klienckiego
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.out.println("Nieznany host.");
            System.exit(-1);
        } catch (ConnectException e) {
            System.out.println("Połączenie odrzucone.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Błąd wejścia-wyjścia: " + e);
            System.exit(-1);
        }
        System.out.println("Połączono z " + clientSocket);

        //Deklaracje zmiennych strumieniowych 
        String line = null;
        BufferedReader brSockInp = null;
        BufferedReader brLocalInp = null;
        DataOutputStream out = null;

        //Utworzenie strumieni
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            brSockInp = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            brLocalInp = new BufferedReader(
                    new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Błąd przy tworzeniu strumieni: " + e);
            System.exit(-1);
        }

        System.out.println("If you want to see manual type - manual");

        boolean isLogin = false;
        boolean isPassword = false;
        Random random = new Random();
        int userID = random.nextInt(0,4000);
        String login = "" + userID;
        String password = "333";
        String [] dane = {"5000","Piotr", "Kowalski", "11223244555", "4321"};
        System.out.println("Your login is: "+ login);
        Filecreator filecreator = new Filecreator();
        filecreator.createFile(login, true);
        filecreator.initializedUserFile(userID, dane);

        //Pętla główna klienta
        while (true) {
            try {

                if("manual".equals(line)) {
                    System.out.println();
                    System.out.println("You can choose this action:");
                    System.out.println("1 - transfer money"); // jeśli ma środki i konto istnieje
                    System.out.println("2 - check account balance");
                    System.out.println("3 - payment"); // nie może poniżej zera wpłacić
                    System.out.println("4 - withdraw"); // nie może wypłacić poniżej zera
                    System.out.println();
                }

                if (!isLogin) {
                    System.out.println("Pass login");
                }

                if (!isPassword && isLogin){
                    System.out.println();
                    System.out.println("Input password");
                    System.out.println();
                }
                line = brLocalInp.readLine();
                if (line != null) {
                    System.out.println("Wysyłam: " + line);
                    out.writeBytes(line + '\n');
                    out.flush();
                }



                if(isLogin) {
                    if (password.equals(line)) {
                        System.out.println("Correct password");
                        isPassword = true;
                    } else {
                        System.out.println("Try again");
                    }
                }

                if (login.equals(line)) {
                    System.out.println();
                    System.out.println("Correct");
                    System.out.println();
                    isLogin = true;
                } else if(!isLogin){
                    System.out.println();
                    System.out.println("Try again");
                    System.out.println();
                }

                if (isLogin) {
                    System.out.println("Welcome in bank");
                }

                if (line == null || "quit".equals(line)) {
                        System.out.println("Kończenie pracy...");
                        clientSocket.close();
                        System.exit(0);
                    }
                    brSockInp.readLine();
                    System.out.println("Otrzymano: " + line);
                } catch(IOException e){
                    System.out.println("Błąd wejścia-wyjścia: " + e);
                    System.exit(-1);
                }

            }
        }
    }
