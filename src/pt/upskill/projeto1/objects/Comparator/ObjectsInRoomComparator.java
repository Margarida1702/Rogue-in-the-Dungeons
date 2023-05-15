package pt.upskill.projeto1.objects.Comparator;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;

import java.util.Comparator;

public class ObjectsInRoomComparator implements Comparator<ImageTile> {

    public int tilesPriorities (ImageTile imageTile){
        int priority = 0;
        if (imageTile instanceof Floor){
            priority=0;
        } else if (imageTile instanceof Wall){
            priority=1;
        } else if (imageTile instanceof Door){
            priority=2;
        } else if (imageTile instanceof Items){
            priority=3;
        }else if (imageTile instanceof Hero || imageTile instanceof Enemy || imageTile instanceof FireOld){
            priority=4;
        }
        return priority;
    }


    @Override
    public int compare(ImageTile o1, ImageTile o2) {
        return tilesPriorities(o1)-tilesPriorities(o2);
    }
}
