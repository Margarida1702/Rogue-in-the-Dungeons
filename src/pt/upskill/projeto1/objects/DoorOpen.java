package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorOpen extends Door implements ImageTile {

    public DoorOpen(Position position, int ID, boolean isDoor, int nextRoomID, int nextDoorID, String keyID) {
        super(position, ID, isDoor, nextRoomID, nextDoorID, keyID);
    }

    public DoorOpen(int ID, boolean isDoor, int nextRoomID, int nextDoorID, String keyID) {
        super(ID, isDoor, nextRoomID, nextDoorID, keyID);
    }

    @Override
    public String getName() {
        return "DoorOpen";
    }
}
