package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

public abstract class Door implements ImageTile, Serializable {

    private Position position;
    private boolean isDoor;
    private int ID, nextRoomID, nextDoorID;
    private String keyID;

    public Door(Position position, int ID, boolean isDoor, int nextRoomID, int nextDoorID, String keyID) {
        this.position = position;
        this.ID = ID;
        this.isDoor = isDoor;
        this.keyID = keyID;
        this.nextRoomID = nextRoomID;
        this.nextDoorID = nextDoorID;
    }

    public Door(int ID, boolean isDoor, int nextRoomID, int nextDoorID, String keyID) {
        this.ID = ID;
        this.isDoor = isDoor;
        this.keyID = keyID;
        this.nextRoomID = nextRoomID;
        this.nextDoorID = nextDoorID;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public void setDoor(boolean door) {
        isDoor = door;
    }

    public int getID() {
        return ID;
    }

    public String getKeyID() {
        return keyID;
    }

    public int getNextRoomID() {
        return nextRoomID;
    }

    public int getNextDoorID() {
        return nextDoorID;
    }
}
