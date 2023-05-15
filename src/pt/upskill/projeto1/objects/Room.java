package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Comparator.ObjectsInRoomComparator;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room implements Serializable {

    private int maxX = 9;
    private int minX = 0;
    private int maxY = 9;
    private int minY = 0;
    private ArrayList<ImageTile> objectsInRoom = new ArrayList<>();
    public ArrayList<ImageTile> getObjectsInRoom() {
        return objectsInRoom;
    }
    public ArrayList<Door> doors = new ArrayList<>();


    public Room (String filePath) {
        try{
        Scanner scanner = new Scanner(new File(filePath));
        ArrayList<String[]> keys = new ArrayList<>();
        String[]aux;
        int y=0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                boolean isDoor=false;
                String key="";

                if (line.charAt(0) == '#') { // Reads the lines started with # and saves de info of the doors and keys for later creation with Position
                    if (line.length() > 1) {
                        if (Character.isDigit(line.charAt(2))) {
                            aux = line.split(" ");
                            if (aux[2].equals("D")) {
                                isDoor = true;
                            }
                            if (aux.length > 5) {
                                key = aux[5];
                            }
                            if (isDoor) {
                                if (key.isEmpty()) {
                                    Door door = new DoorOpen(Integer.parseInt(aux[1]), isDoor, Integer.parseInt(aux[3].replaceAll("[^0-9]", "")), Integer.parseInt(aux[4]), key);
                                    doors.add(door);
                                } else {
                                    Door door = new DoorClosed(Integer.parseInt(aux[1]), isDoor, Integer.parseInt(aux[3].replaceAll("[^0-9]", "")), Integer.parseInt(aux[4]), key);
                                    doors.add(door);
                                }
                            } else {
                                Door door = new DoorWay(Integer.parseInt(aux[1]), isDoor, Integer.parseInt(aux[3].replaceAll("[^0-9]", "")), Integer.parseInt(aux[4]), key);
                                doors.add(door);
                            }
                        } else if (Character.isLetter(line.charAt(2))) {
                            aux = line.split(" ");
                            keys.add(aux);
                        }
                    }
                } else {
                    for (int x = 0; x < line.length(); x++) {
                        char charAtX = line.charAt(x);
                        objectsInRoom.add(new Floor(new Position(x, y)));
                        switch (charAtX) {
                            case 'W': objectsInRoom.add(new Wall(new Position(x, y))); break;
                            case 'H': objectsInRoom.add(new Hero(new Position(x, y))); break;
                            case 'S': objectsInRoom.add(new Skeleton(new Position(x, y))); break;
                            case 'G': objectsInRoom.add(new BadGuy(new Position(x, y))); break;
                            case 'B': objectsInRoom.add(new Bat(new Position(x, y))); break;
                            case 'T': objectsInRoom.add(new Thief(new Position(x, y))); break;
                            case 'h': objectsInRoom.add(new Hammer(new Position(x, y))); break;
                            case 's': objectsInRoom.add(new Sword(new Position(x, y))); break;
                            case 'm': objectsInRoom.add(new GoodMeat(new Position(x, y))); break;
                            case 'M': objectsInRoom.add(new Money(new Position(x, y))); break;
                            default:
                                if (Character.isDigit(line.charAt(x))){
                                    for (int i=0; i<doors.size();i++){
                                        if(doors.get(i).getID()==Character.getNumericValue(charAtX)){
                                            doors.get(i).setPosition(new Position(x,y));
                                            objectsInRoom.add(doors.get(i));
                                        }
                                    }
                                } else if (Character.toString(charAtX).equals("k")){

                                    objectsInRoom.add(new Key(new Position(x, y),keys.get(0)[2])); break;
                                }
                        }
                    } y++;
                }
            } objectsInRoom.sort(new ObjectsInRoomComparator());
              scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public Door getDoor(int doorID){
        for(ImageTile imgt: objectsInRoom){
            if(imgt instanceof Door){
                if(((Door) imgt).getID()==doorID){
                    return (Door)imgt;
                }
            }
        }
        return null;
    }

    public Door positionHasDoor (Position position){
        for(ImageTile imgt: objectsInRoom){
            if(imgt instanceof Door){
                if(imgt.getPosition().equals(position)){
                    return (Door)imgt;
                }
            }
        }
        return null;
    }

    public boolean positionHasPrize (Position position){ //Prize means End of the Game (Win money)
        for(ImageTile imgt: objectsInRoom){
            if(imgt instanceof Money){
                if(imgt.getPosition().equals(position)){
                    return true;
                }
            }
        }
        return false;
    }


    public List<ImageTile> seeTilesInPosition(Position position){
        List<ImageTile> imageTiles = new ArrayList<>();

        for(int i=0; i<this.objectsInRoom.size();i++){
            if(objectsInRoom.get(i).getPosition().equals(position)){
                imageTiles.add(objectsInRoom.get(i));
            }
        }
        return imageTiles;
    }

    public void removeDoor(Door door){
        doors.remove(door);
    }

    public List<ImageTile> getEnemyInRoom(){
        List<ImageTile> enemyInRoom = new ArrayList<>();
        for (ImageTile imageTile : objectsInRoom) {
            if (imageTile instanceof Enemy){
                enemyInRoom.add(imageTile);
            }
        }
        return enemyInRoom;
    }

    public ImageTile getEnemyInRoom(Position position){
        for (ImageTile imageTile : objectsInRoom) {
            if (imageTile instanceof Enemy && imageTile.getPosition().getX() == position.getX() && imageTile.getPosition().getY() == position.getY()){
                return imageTile;
            }
        }
        return null;
    }

    public boolean isHeroInTheRoom (){
        for(ImageTile tile: objectsInRoom) {
            if(tile.getName().equals("Hero")){
                return true;
            }
        }
        return false;
    }

    public Hero getHeroInRoom (){
        for(ImageTile tile: objectsInRoom) {
            if(tile.getName().equals("Hero")){
                return (Hero) tile;
            }
        } return null;
    }

    public List<ImageTile> getItemsInRoom(){
        List<ImageTile> itemsInRoom = new ArrayList<>();
        for (ImageTile imageTile: objectsInRoom){
            if (imageTile instanceof Items || imageTile instanceof Key){
                itemsInRoom.add(imageTile);
            }
        }
        return  itemsInRoom;
    }

    public void removeTilesFromRoom(ImageTile imageTile){
        objectsInRoom.remove(imageTile);
    }

    public void addTilesInRoom(ImageTile imageTile){

        objectsInRoom.add(imageTile);
        objectsInRoom.sort(new ObjectsInRoomComparator());
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }
}
