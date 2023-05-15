package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Bat extends Enemy implements ImageTile {

    public Bat(Position position) {
        super(position,20,2);
    }

    @Override
    public String getName() { return "Bat"; }


}
