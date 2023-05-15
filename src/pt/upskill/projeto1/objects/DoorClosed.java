package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;

public class DoorClosed extends Door implements ImageTile {

     public DoorClosed(int ID, boolean isLocked, int nextRoomID, int nextDoorID, String keyID) {
        super(ID, isLocked, nextRoomID, nextDoorID, keyID);
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }


}

