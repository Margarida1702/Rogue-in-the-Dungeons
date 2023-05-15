package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Sword extends Items implements ImageTile {

    public Sword(Position position) {
        super(position, 0, 10);
    }

    @Override
    public String getName() {
        return "Sword";
    }


}
