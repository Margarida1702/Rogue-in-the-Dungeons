package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemy implements ImageTile, Serializable {

    private Position position;
    private int hp;
    private int attack;

    public Enemy(Position position, int hp, int attack) {
        this.position=position;
        this.hp = hp;
        this.attack=attack;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp){
        this.hp=hp;
    }

    public void attackHero (Hero hero){
        hero.setHp(hero.getHp()-this.attack);
    }

    public boolean isEnemyPositionValid (Room room, Position positionOfDestination){
        if (positionOfDestination.getX()<0 || positionOfDestination.getY()<0 || positionOfDestination.getX()>9 || positionOfDestination.getY()>9)
            return false;
        List<ImageTile> tiles = room.seeTilesInPosition(positionOfDestination);
        boolean isValid = true;

        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getName().equals("Wall")) {
                isValid = false;
            } else if (tiles.get(i) instanceof Enemy) {
                isValid = false;
            } else if (tiles.get(i).getName().equals("Hero")) {
                isValid = false;
            }
        }  return isValid;
    }

    public ArrayList <Position> validEnemyPlays (Room room){
        Position [] allPossiblePlay = new Position [4];
        ArrayList<Position> finalPlays = new ArrayList<>();

        allPossiblePlay[0]= getPosition().plus(Direction.DOWN.asVector());
        allPossiblePlay[1]= getPosition().plus(Direction.UP.asVector());
        allPossiblePlay[2]= getPosition().plus(Direction.LEFT.asVector());
        allPossiblePlay[3]= getPosition().plus(Direction.RIGHT.asVector());

        for(int i=0; i<allPossiblePlay.length; i++){
            if(isEnemyPositionValid(room, allPossiblePlay[i])){
                finalPlays.add(allPossiblePlay[i]);
            }
        } return finalPlays;
    }

    public void move(Hero hero, Room room){
        int xH = hero.getPosition().getX();
        int yH = hero.getPosition().getY();
        int xE = this.getPosition().getX();
        int yE = this.getPosition().getY();

        ArrayList<Position> copy = validEnemyPlays(room);
        if (Math.abs(xE-xH)>3 && Math.abs(yE-yH)>3){
            setPosition(copy.get((int) (Math.random()*copy.size())));
        } else if ((Math.abs(xE-xH)==1 && (Math.abs(yE-yH)==0)) || (Math.abs(xE-xH)==0 && Math.abs(yE-yH)==1)){
            setPosition(getPosition());
            attackHero(hero);
        } else {
            Position[] aux= new Position[copy.size()];
            aux = copy.toArray(aux);
            Position positionMIN = aux[0];
            for (int i=1;i<aux.length;i++){
                if((Math.sqrt(Math.pow(xH-aux[i].getX(),2)+Math.pow(yH-aux[i].getY(),2))) < (Math.sqrt(Math.pow(xH-positionMIN.getX(),2)+Math.pow(yH-positionMIN.getY(),2)))){
                    positionMIN=aux[i];
                }
            } setPosition(positionMIN);
        }
    }

}
