package pt.upskill.projeto1.rogue.utils;
import java.util.*;

public class Utils {
    public static Dictionary populateScoreDictionary()
    {
        Dictionary scoreDictionary = new Hashtable();

        scoreDictionary.put("Move", -1);
        scoreDictionary.put("GrabSword", 20);
        scoreDictionary.put("GrabHammer", 10);
        scoreDictionary.put("GrabGoodMeat", 30);
        scoreDictionary.put("GrabKey", 5);
        scoreDictionary.put("SetRoom", 20);
        scoreDictionary.put("HeroAttacked", -5);
        scoreDictionary.put("KillSkeleton", 10);
        scoreDictionary.put("KillBat", 20);
        scoreDictionary.put("KillBadGuy", 40);
        scoreDictionary.put("KillThief", 80);

        return scoreDictionary;
    }
    public static Dictionary populateLeaderBoardMessage()
    {
        Dictionary leaderBoardMessageDictionary = new Hashtable();

        leaderBoardMessageDictionary.put(true, "Congratulations!!! You WIN!!!\n\nLeaderBoarding");
        leaderBoardMessageDictionary.put(false, "Game Over!!! Try Again!!!\n\nLeaderBoarding");

        return leaderBoardMessageDictionary;
    }

    public static Dictionary populateMessageDictionary()
    {
        Dictionary messageDictionary = new Hashtable();

        messageDictionary.put(1, "     |||||     Lets play ");
        messageDictionary.put(2, "     |||||     Hero moved ");
        messageDictionary.put(3, "     |||||     Hero grabbed item ");
        messageDictionary.put(4, "     |||||     Doesn't exist space in inventory to grab item ");
        messageDictionary.put(5, "     |||||     Enemy attacking Hero");
        messageDictionary.put(6, "     |||||     HP Restored");
        messageDictionary.put(7, "     |||||     Hero changed Room");
        messageDictionary.put(8, "     |||||     Hero killed enemy ");

        return messageDictionary;
    }
}



