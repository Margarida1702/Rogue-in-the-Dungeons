package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer extends Items implements ImageTile {

    public Hammer(Position position) {
        super(position, 0, 5);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

}
