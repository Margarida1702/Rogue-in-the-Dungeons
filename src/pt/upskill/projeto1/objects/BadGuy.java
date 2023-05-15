package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class BadGuy extends Enemy implements ImageTile {

    public BadGuy(Position position) {
        super(position, 50, 5);
    }

    @Override
    public String getName() {
        return "BadGuy";
    }

}
