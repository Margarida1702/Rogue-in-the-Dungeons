package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

public class Money implements ImageTile, Serializable{

    private Position position;

    public Money(Position position) {
        this.position = position;
    }


    @Override
    public String getName() {
        return "Money";
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
