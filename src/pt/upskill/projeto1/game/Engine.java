package pt.upskill.projeto1.game;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.Exceptions.EnemyException;
import pt.upskill.projeto1.objects.Exceptions.WallException;
import pt.upskill.projeto1.rogue.utils.*;
import pt.upskill.projeto1.rogue.utils.ManageFiles;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;

import javax.swing.*;

public class Engine {
    Hero hero;
    Room_All roomAll = new Room_All();
    static Dictionary scoreDictionary;
    static Dictionary messageDictionary;
    static Dictionary leaderBoardMessageDictionary;
    File fileScore;
    String username;
    ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

    /** Contains the inicialization */
    public void init(){
        messageDictionary = Utils.populateMessageDictionary();
        username = JOptionPane.showInputDialog(null,"Enter Name");
        try {
            if (username == null || username.isEmpty()) {
                System.exit(0);
            }

        }catch (NullPointerException e){
            e.getMessage();
        }

        int resultLoadGame = validateLoadGame();

       if(resultLoadGame == JOptionPane.YES_OPTION) {
            loadGame();
        }
        else {
            initializeGame();
        }

        Sound.playSoundMusic("sound.wav");
        gui.setStatus("Score: " + hero.getScore()+ messageDictionary.get(1) + username + " :D");

        while (true){
            gui.update();
        }
    }

    /**Changes closed door to an open door and eliminates the key of the inventory */
    private boolean changeDoorStatus(Vector2D vector){
        Door door = roomAll.getPresentRoom().positionHasDoor(hero.getPosition().plus(vector));
        boolean changedDoor = false;
        if(door != null) {
            Room room = roomAll.nextRoom(door);
            Door nextDoor = room.getDoor(door.getNextDoorID());

            if (door instanceof DoorClosed) {
                for (int i = 0; i < hero.getInventory().size(); i++) {
                    if (hero.getInventory().get(i) instanceof Key && ((Key) hero.getInventory().get(i)).getKeyID().equals(door.getKeyID())) {

                        room.removeDoor(door);

                        roomAll.OpenClosedDoor(door);

                        hero.removeItemFromInventory(hero.getInventory().get(i));
                        gui.updateUpperBar(hero);

                        changeHeroRoom(nextDoor, room);

                        roomAll.OpenClosedDoor(nextDoor);

                        updateTiles(null);

                        changedDoor = true;
                    }
                }
            }
            else{
                changeHeroRoom(nextDoor, room);

                updateTiles(null);
                changedDoor = true;
            }
        }
        return changedDoor;
    }

    /**Updates inventory when caught new item*/
    private void updateInventory(){
        boolean catchItem;
        catchItem = hero.catchItem(roomAll.getPresentRoom());

        if(catchItem) {
            Sound.playSoundMusic("soundGrab.wav");
            updateTiles(null);
            gui.updateUpperBar(hero);
        }
    }

    /** Hero Grabs item with condition to store or consume meat */
    private boolean heroCatchItem(Direction direction){
         boolean itemGrabbed = false;
        List<ImageTile> grabItem = roomAll.getPresentRoom().seeTilesInPosition(hero.getPosition());
        for (int i = 0; i < grabItem.size(); i++) {
            if (grabItem.get(i) instanceof Items) {
                if(hero.getInventory().size() < 3) {
                    if(grabItem.get(i) instanceof GoodMeat) {
                        if (hero.getHp() == hero.getMaxHp()) {
                            updateInventory();
                            hero.calculateScore("Grab"+grabItem.get(i).getName());
                            gui.setStatus("Score: " + hero.getScore() + messageDictionary.get(3) + grabItem.get(i).getName() + " --> score's up " + scoreDictionary.get("Grab"+grabItem.get(i).getName()));
                        }
                        else{
                            updateTiles(grabItem.get(i));
                            hero.setHp(hero.getMaxHp());
                            hero.calculateScore("Grab"+grabItem.get(i).getName());
                            gui.setStatus("Score: " + hero.getScore()+ messageDictionary.get(6));
                            gui.updateUpperBar(hero);
                        }
                    }
                    else {
                        updateInventory();
                        hero.calculateScore("Grab"+grabItem.get(i).getName());
                        gui.setStatus("Score: " + hero.getScore() + messageDictionary.get(3) + grabItem.get(i).getName() + " --> score's up " + scoreDictionary.get("Grab"+grabItem.get(i).getName()));
                    }
                    itemGrabbed = true;
                }
                else {
                    if(grabItem.get(i) instanceof GoodMeat && hero.getHp() != hero.getMaxHp()) {
                        updateTiles(grabItem.get(i));
                        hero.setHp(hero.getMaxHp());
                        hero.calculateScore("Grab"+grabItem.get(i).getName());
                        gui.setStatus("Score: " + hero.getScore()+ messageDictionary.get(6));
                        gui.updateUpperBar(hero);
                    }
                    gui.setStatus("Score: " + hero.getScore()+ messageDictionary.get(4) + grabItem.get(i).getName());
                }
            }
        }

        hero.updateAttack();

        return itemGrabbed;
    }

    /** Hero attacks enemy */
    private ImageTile heroAttacksEnemy(Vector2D vector){
        List<ImageTile> enemy = roomAll.getPresentRoom().seeTilesInPosition(hero.getPosition().plus(vector));
        for (int i = 0; i < enemy.size(); i++) {
            if(enemy.get(i) instanceof Enemy){
                hero.attackEnemy((Enemy) enemy.get(i));

                if(((Enemy) enemy.get(i)).getHp() <= 0) {
                    updateTiles(enemy.get(i));

                    hero.calculateScore("Kill" + enemy.get(i).getName());
                    gui.setStatus("Score: " + hero.getScore() + messageDictionary.get(8) + enemy.get(i).getName() + " --> score's up " + scoreDictionary.get("Kill"+enemy.get(i).getName()));
                }
                return enemy.get(i);
            }
        }
        return null;
    }

    /** Drops items if there is not another item or door */
    private void heroDropItem(int index){
        boolean canDrop = true;
        //Condition to not drop any item on other items/keys/door (enemy can pass through items)
        List<ImageTile> listTiles = roomAll.getPresentRoom().seeTilesInPosition(hero.getPosition());
        for (int i = 0; i < listTiles.size(); i++) {
            if (listTiles.get(i) instanceof Items || listTiles.get(i) instanceof Key || listTiles.get(i) instanceof Door) {
                canDrop = false;
            }
        }

        if(canDrop && hero.getInventory().size() > 0) {
            ImageTile img = hero.dropItem(roomAll.getPresentRoom(), index-1);
            gui.setStatus("Player dropped item " + img.getName());
            Sound.playSoundMusic("soundDrop.wav");

            updateTiles(null);
            hero.updateAttack();
            gui.updateUpperBar(hero);
        }
    }

    /** Enemy moves */
    private void enemyMove() {
        for (ImageTile imageTile : roomAll.getPresentRoom().getEnemyInRoom()) {
            int oldHp = hero.getHp();

            ((Enemy) imageTile).move(hero, roomAll.getPresentRoom());

            gui.updateUpperBar(hero);

            if (oldHp != hero.getHp()) {
                hero.calculateScore("HeroAttacked");
                gui.setStatus("Score: " + hero.getScore() + messageDictionary.get(5) + " --> score's up " + scoreDictionary.get("HeroAttacked"));
            }

            if (hero.getHp() <= 0) {
                fileScore = ManageFiles.creationFile("files/scores/", "scores.txt");
                ManageFiles.writeScoreToFile(fileScore.getPath(), username, hero.getScore());
                loadGame();
                LeaderBoard.showPanelEndGame(fileScore.getPath(),false);
            }
        }
    }

    /** Action when keyboard pressed to move / drop items / fireball */
    public void notify(int keyPressed){

        if (keyPressed == KeyEvent.VK_DOWN)
            validateKeyDirection(Direction.DOWN);

        if (keyPressed == KeyEvent.VK_UP)
            validateKeyDirection(Direction.UP);

        if (keyPressed == KeyEvent.VK_LEFT)
            validateKeyDirection(Direction.LEFT);

        if (keyPressed == KeyEvent.VK_RIGHT)
            validateKeyDirection(Direction.RIGHT);

        if (keyPressed == KeyEvent.VK_SPACE) {

            if(hero.getDirectionBeforeFireBall()!=null && hero.getFireBalls().size() > 0) {
                hero.getFireBalls().remove(hero.getFireBalls().get(0));
                gui.updateUpperBar(hero);
                FireOld fireball = new FireOld (hero, roomAll.getPresentRoom());
                gui.addImage(fireball);
                FireBallThread fb = new FireBallThread(hero.getDirectionBeforeFireBall(), fireball);
                fb.start();
            }
        }

        if (keyPressed == KeyEvent.VK_1 || keyPressed == KeyEvent.VK_NUMPAD1)
              validateKeyDropItem(1);

        if (keyPressed == KeyEvent.VK_2 || keyPressed == KeyEvent.VK_NUMPAD2)
            validateKeyDropItem(2);

        if (keyPressed == KeyEvent.VK_3 || keyPressed == KeyEvent.VK_NUMPAD3)
            validateKeyDropItem(3);

        if (keyPressed != KeyEvent.VK_SPACE)
            enemyMove();
    }

    /** Loads the game */
    private void loadGame(){
        scoreDictionary = Utils.populateScoreDictionary();
        messageDictionary = Utils.populateMessageDictionary();
        leaderBoardMessageDictionary = Utils.populateLeaderBoardMessage();

        roomAll = new Room_All();
        roomAll.createRooms();
        roomAll = ManageFiles.readSave(username);
        roomAll.setPresentRoom(roomAll.roomWhereHeroIs());
        hero = roomAll.getPresentRoom().getHeroInRoom();
        updateTiles(null);
        gui.updateUpperBar(hero);
        gui.setEngine(this);
        gui.go();
    }

    /** function when we want to update tiles */
    private void updateTiles(ImageTile removed){
        if(removed != null)
            roomAll.getPresentRoom().removeTilesFromRoom(removed);

        ArrayList<ImageTile> tiles = new ArrayList<>();
        tiles.addAll(roomAll.getPresentRoom().getObjectsInRoom());
        gui.newImages(tiles);
    }

    /** Teleports hero to one room to another when pass door */
    private void changeHeroRoom(Door nextDoor, Room room){
        roomAll.getPresentRoom().removeTilesFromRoom(hero);
        hero.setPosition(new Position(nextDoor.getPosition().getX(), nextDoor.getPosition().getY()));
        roomAll.setPresentRoom(room);
        roomAll.getPresentRoom().addTilesInRoom(hero);

        hero.calculateScore("SetRoom");
        gui.setStatus("Score: " + hero.getScore() + messageDictionary.get(7) + " --> score's up " + scoreDictionary.get("SetRoom"));
    }

    /** Validates if the direction moved is valid or has items to catch */
    private void validateKeyDirection(Direction direction){
        try{
            boolean changedDoor;
            boolean itemGrabbed = false;

            hero.setDirectionBeforeFireBall(direction);

            changedDoor = changeDoorStatus(direction.asVector());

            if(hero.isPositionValid(roomAll.getPresentRoom(),hero.getPosition().plus(direction.asVector()))){
                hero.move(direction);
                itemGrabbed = heroCatchItem(direction);
            }

            if(changedDoor)
                ManageFiles.writeSave(roomAll, username);

            if(roomAll.getPresentRoom().positionHasPrize(hero.getPosition())){
                fileScore = ManageFiles.creationFile("files/scores/", "scores.txt");
                ManageFiles.writeScoreToFile(fileScore.getPath(), username, hero.getScore());
                LeaderBoard.showPanelEndGame(fileScore.getPath(),true);

                initializeGame();
            }
            if(!changedDoor && !itemGrabbed) {
                hero.calculateScore("Move");
                gui.setStatus("Score: " + hero.getScore() + messageDictionary.get(2) + (direction.name()).toLowerCase() + " --> score's up " + scoreDictionary.get("Move"));
            }
        } catch (WallException e){
            System.out.println(e.getMessage());
        } catch (EnemyException e){
            heroAttacksEnemy(direction.asVector());
        }
    }

    /**Drops the item on the index given and throws exception if there isnt one */
    private void validateKeyDropItem(int index){
        try {
            heroDropItem(index);
        }
        catch (IndexOutOfBoundsException e){
            gui.setStatus("Doesn't exist any item in that position");
        }
    }

    /** checks if there is any save from the player name and asks if want to load or start new game */
    private int validateLoadGame(){
        int result = -1;
        File folder = new File("files/saves");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i<listOfFiles.length; i++){
        if(listOfFiles[i].getName().equals(username+"_save.txt")){
             result = JOptionPane.showConfirmDialog(null,"Do you want to continue the previous game?", "Load Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

            }
        }
        return result;
    }

    /** function to initialize the game again */
    private void initializeGame(){
        scoreDictionary = Utils.populateScoreDictionary();
        messageDictionary = Utils.populateMessageDictionary();
        leaderBoardMessageDictionary = Utils.populateLeaderBoardMessage();

        roomAll.createRooms();
        hero = roomAll.getPresentRoom().getHeroInRoom();

        gui.updateUpperBar(hero);

        updateTiles(null);
        gui.setEngine(this);
        gui.go();
    }

    /** Fireball attack */
    public static void fireballAttack(Hero hero, Enemy enemy, int attack, Room room){

        int enemyHp = enemy.getHp()-attack;

        if(enemyHp < 0)
            enemyHp = 0;

        enemy.setHp(enemyHp);

        if(enemyHp == 0){
            room.removeTilesFromRoom(enemy);

            hero.calculateScore("Kill"+enemy.getName());
        }

        ImageMatrixGUI.getInstance().updateUpperBar(hero);

        ImageMatrixGUI.getInstance().setStatus("Score: " + hero.getScore() + messageDictionary.get(8) + enemy.getName() + " --> score's up " + scoreDictionary.get("Kill"+enemy.getName()));

        ArrayList<ImageTile> tiles = new ArrayList<>();
        tiles.addAll(room.getObjectsInRoom());
        ImageMatrixGUI.getInstance().newImages(tiles);
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }
}