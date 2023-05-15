package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Key extends Items implements ImageTile {

    private Position position;
    private String keyID;

    public Key(Position position, String keyID) {
        super(position,0,0);
        this.position = position;
        this.keyID = keyID;
    }

    public String getKeyID() {
        return keyID;
    }

    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

}
