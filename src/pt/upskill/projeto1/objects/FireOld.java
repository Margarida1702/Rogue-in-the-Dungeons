package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public class FireOld implements ImageTile, FireTile {

    private Position position;

    private Hero hero;
    Room presentRoom = null;
    private int attack = 30;


    public FireOld(Hero hero, Room presentRoom) {
        this.hero = hero;
        this.position = hero.getPosition();
        this.presentRoom = presentRoom;
    }

    @Override
    public Position getPosition() { return position; }

    @Override
    public boolean validateImpact() {

        if(this.getPosition().getX() > presentRoom.getMaxX() || this.getPosition().getX() < presentRoom.getMinX() ||
                this.getPosition().getY() > presentRoom.getMaxY() || this.getPosition().getY() < presentRoom.getMinY()){
            return false;
        }

        List<ImageTile> tiles = presentRoom.seeTilesInPosition(position);

        for(ImageTile tile: tiles){
            if(tile instanceof Wall){
                return false;
            }if(tile instanceof Door){
                return false;
            }if(tile instanceof Enemy){
                Engine.fireballAttack(hero,(Enemy)presentRoom.getEnemyInRoom(position),getAttack(), presentRoom);
                return false;
            }
        }
        return true;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire_old";
    }

    public int getAttack() {
        return attack;
    }

}
