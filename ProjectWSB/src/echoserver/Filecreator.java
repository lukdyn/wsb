package echoserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Filecreator {

    public void createFile(String fileName, boolean isUser){
        try {
            File myFile;

            if (isUser){
                String fileNameUser = "src\\users\\" + fileName;
                myFile = new File(fileNameUser + ".txt");
            }else {
                myFile = new File(fileName + ".txt");
            }

            if (myFile.createNewFile()) {
                System.out.println("Create new file: " + myFile.getName());
            }else {
                System.out.println("File already exists");
            }
        }catch (IOException e){
            System.out.println("Error");
        }


    } 

    public void initializedUserFile(int index, String[] dane){
        try{
            FileWriter myWriter = new FileWriter("src\\users\\" + index + ".txt");
            myWriter.write("saldo = " + dane[0] + "\n");
            myWriter.write("imie = " + dane[1] + "\n");
            myWriter.write("nazwisko = " + dane[2] + "\n");
            myWriter.write("pesel = " + dane[3] + "\n");
            myWriter.write("numerkonta = " + dane[4] + "\n");
            myWriter.write("index = " + index + "\n");
            myWriter.close();

        }catch (IOException e){
            System.out.println("Error");
        }
    }
}
