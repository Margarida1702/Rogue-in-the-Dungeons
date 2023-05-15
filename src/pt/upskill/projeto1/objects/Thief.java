package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;

public class Thief extends Enemy implements ImageTile {

    public Thief(Position position) {
        super(position,70,10);
    }

    @Override
    public String getName() { return "Thief"; }

    @Override
    public ArrayList <Position> validEnemyPlays (Room room){
        Position [] allPossiblePlay = new Position [4];
        ArrayList<Position> finalPlays = new ArrayList<>();

        allPossiblePlay[0]= getPosition().plus(Direction.NORTHEAST.asVector());
        allPossiblePlay[1]= getPosition().plus(Direction.NORTHWEST.asVector());
        allPossiblePlay[2]= getPosition().plus(Direction.SOUTHWEST.asVector());
        allPossiblePlay[3]= getPosition().plus(Direction.SOUTHEAST.asVector());

        for(int i=0; i<allPossiblePlay.length; i++){
            if(isEnemyPositionValid(room, allPossiblePlay[i])){
                finalPlays.add(allPossiblePlay[i]);
            }
        } return finalPlays;
    }

    @Override
    public void move(Hero hero, Room room){
        int xH = hero.getPosition().getX();
        int yH = hero.getPosition().getY();
        int xE = this.getPosition().getX();
        int yE = this.getPosition().getY();

        ArrayList<Position> copy = validEnemyPlays(room);
        if(copy.size()>0) {

            if (Math.abs(xE - xH) > 3 && Math.abs(yE - yH) > 3) {
                setPosition(copy.get((int) (Math.random() * copy.size())));
            } else if (Math.abs(xE - xH) == 1 && Math.abs(yE - yH) == 1) {
                setPosition(getPosition());
                attackHero(hero);

            } else {
                Position[] aux = new Position[copy.size()];
                aux = copy.toArray(aux);
                Position positionMIN = aux[0];
                for (int i = 1; i < aux.length; i++) {
                    if ((Math.sqrt(Math.pow(xH - aux[i].getX(), 2) + Math.pow(yH - aux[i].getY(), 2))) < (Math.sqrt(Math.pow(xH - positionMIN.getX(), 2) + Math.pow(yH - positionMIN.getY(), 2)))) {
                        positionMIN = aux[i];
                    }
                }
                setPosition(positionMIN);
            }
        } else {
            setPosition(getPosition());
        }
    }
}
