package pt.upskill.projeto1.rogue.utils;

import pt.upskill.projeto1.objects.Room_All;

import java.io.*;
import java.util.Scanner;

public class ManageFiles implements Serializable {

    /**Writes Save under username name */
    public static void writeSave(Room_All roomAll, String username) {
        try {

            File outFile = new File("files/saves/" + username + "_save.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(roomAll);

            fileOutputStream.close();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }

    }

    /**Reads Saved Game from username name */
    public static Room_All readSave(String username) {
        File inFile = new File("files/saves/" + username + "_save.txt");
        Room_All roomAll = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(inFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            roomAll = (Room_All) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return roomAll;
    }

    /** See if file with username exists and if not, creates one */
    public static File creationFile(String directory, String filename){
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        boolean existsFile = false;

        for (int i = 0; i < listOfFiles.length; i++){
            if(listOfFiles[i].getName().equals(filename)){
                return listOfFiles[i];
            }
        }
        if(!existsFile){
            try{
                File myObj = new File(directory+filename);
                myObj.createNewFile();
                return myObj;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**Write score game to File scores**/
    public static void writeScoreToFile(String filePath, String username, int score){
        try {
            Scanner scanner = new Scanner(new File(filePath));

            FileWriter myWriter = new FileWriter(filePath, true);
            myWriter.write(username + ";" + score + "\n");
            myWriter.close();

            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


