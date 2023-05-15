package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Exceptions.EnemyException;
import pt.upskill.projeto1.objects.Exceptions.WallException;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Hero implements ImageTile, Serializable  {

    private Position position;
    private Direction directionBeforeFireBall;
    private int hp;
    private int attack;
    private int score;
    private int maxHp = 150;
    private int maxFireBalls = 3;
    private ArrayList<Items> inventory= new ArrayList<>();
    private ArrayList<Fire> fireBalls = new ArrayList<>();

    public Hero(Position position) {
        this.position = position;
        this.hp=this.maxHp;
        this.attack=1;
        this.score=0;
        for (int i=0; i< maxFireBalls; i++){
            this.fireBalls.add(new Fire(this.position));
        }
    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void attackEnemy (Enemy enemy){
        enemy.setHp(enemy.getHp()-this.attack);
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void removeItemFromInventory(Items item){
        inventory.remove((item));
    }

    public ArrayList<Fire> getFireBalls() {
        return fireBalls;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void move(Direction direction){

        setPosition(new Position(position.plus(direction.asVector()).getX(), position.plus(direction.asVector()).getY()));
    }

    public boolean isPositionValid (Room room,Position positionOfDestination) throws WallException, EnemyException {
        if (positionOfDestination.getX()<0 || positionOfDestination.getY()<0 || positionOfDestination.getX()>9 || positionOfDestination.getY()>9)
            return false;
        List<ImageTile> tiles = room.seeTilesInPosition(positionOfDestination);
        boolean isValid = true;

        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) instanceof Wall || tiles.get(i) instanceof DoorClosed) {
                throw new WallException("");
            } else if (tiles.get(i) instanceof Enemy) {
                throw new EnemyException("");
            }
        }
        return isValid;
    }

    public boolean catchItem (Room presentRoom){
        if(inventory.size()<3){
            List<ImageTile> itemsInRoom = presentRoom.getItemsInRoom();
            for (ImageTile items: itemsInRoom){
                Position aux=items.getPosition();
                if (this.getPosition().equals(aux)){
                    inventory.add((Items) items);
                    presentRoom.removeTilesFromRoom(items);
                    return true;
                }
            }
        }
        return false;
    }

    public ImageTile dropItem (Room presentRoom, int number) {
        if(inventory.size()>0){
            Items itemDropped=inventory.remove(number);
            itemDropped.setPosition(this.getPosition());
            presentRoom.addTilesInRoom(itemDropped);
            return itemDropped;
        }
        return null;
    }

    public int calculateScore(String action){
        Dictionary scoreDictionary = Utils.populateScoreDictionary();
        int scoreValue = Integer.parseInt(scoreDictionary.get(action).toString());

        if((getScore() + scoreValue < 0)){
            setScore(0);
            return 0;
        }
        else{
            setScore(getScore() + scoreValue);
            return getScore() + scoreValue;
        }
    }

    public void updateAttack(){
        int attack = 1;

        for (int i = 0; i<getInventory().size(); i++){
            if(getInventory().get(i).getAttack() > attack)
                attack = getInventory().get(i).getAttack();
        }
        setAttack(attack);
    }

    public Direction getDirectionBeforeFireBall() {
        return directionBeforeFireBall;
    }

    public void setDirectionBeforeFireBall(Direction directionBeforeFireBall) {
        this.directionBeforeFireBall = directionBeforeFireBall;
    }
}
