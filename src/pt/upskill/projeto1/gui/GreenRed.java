package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.rogue.utils.Position;

public class GreenRed implements ImageTile {

    private Position position;

    public GreenRed(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "GreenRed";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
