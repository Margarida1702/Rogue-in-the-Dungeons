package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorWay extends Door implements ImageTile {

    public DoorWay(Position position, int ID, boolean isLocked, int nextRoomID, int nextDoorID, String keyID) {
        super(position, ID, isLocked, nextRoomID, nextDoorID, keyID);
    }

    public DoorWay(int ID, boolean isLocked, int nextRoomID, int nextDoorID, String keyID) {
        super(ID, isLocked, nextRoomID, nextDoorID, keyID);
    }

    @Override
    public String getName() {
        return "DoorWay";
    }
}
