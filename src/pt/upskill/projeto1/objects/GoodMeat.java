package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class GoodMeat extends Items implements ImageTile {

    public GoodMeat(Position position) {
        super(position, 5, 0);
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

}
