package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Room_All implements Serializable {

    private ArrayList<Room> allRooms = new ArrayList();
    Room presentRoom;


    public void createRooms(){
        File folder = new File("rooms");
        File[] listOfFiles = folder.listFiles();
        for (File file: listOfFiles){
            allRooms.add(new Room(file.getPath()));
        }
        presentRoom = roomWhereHeroIs();
    }

    public Room roomWhereHeroIs (){
        for (Room room : allRooms) {
            if(room.isHeroInTheRoom()){
                return room;
            }
        }
        return null;
    }

    public Room nextRoom (Door door) {
        Room room = allRooms.get(door.getNextRoomID());

        if (room != null) {
            return room;
        }
        else {
            return null;
        }
    }

    public Room getPresentRoom() {
        return presentRoom;
    }

    public void setPresentRoom(Room presentRoom) {
        this.presentRoom = presentRoom;
    }

    public void OpenClosedDoor(Door door){
        Door doorAux = new DoorOpen(door.getID(), true, door.getNextRoomID(), door.getNextDoorID(), "");
        doorAux.setPosition(new Position(door.getPosition().getX(), door.getPosition().getY()));
        getPresentRoom().removeTilesFromRoom(door);
        getPresentRoom().addTilesInRoom(doorAux);
    }
}



