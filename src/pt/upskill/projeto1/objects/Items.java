package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

public abstract class Items implements ImageTile, Serializable {

    private Position position;
    private int hp;
    private int attack;

    public Items(Position position, int hp, int attack) {
        this.position = position;
        this.hp = hp;
        this.attack = attack;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getAttack() {
        return attack;
    }
}
