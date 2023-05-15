package pt.upskill.projeto1.rogue.utils;

import java.io.Serializable;

/**
 * @author POO2016
 *
 * Cardinal directions
 *
 */
public enum Direction implements Serializable {
	LEFT, UP, RIGHT, DOWN, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;


	public Vector2D asVector() {
		Vector2D vector2D = null;
		if (this == Direction.UP) {
			vector2D = new Vector2D(0, -1);
		}
		if (this == Direction.DOWN) {
			vector2D = new Vector2D(0, +1);
		}
		if (this == Direction.LEFT) {
			vector2D = new Vector2D(-1, 0);
		}
		if (this == Direction.RIGHT) {
			vector2D = new Vector2D(+1, 0);
		}
		if (this == Direction.NORTHEAST){
			vector2D = new Vector2D(+1,-1);
		}
		if (this == Direction.NORTHWEST){
			vector2D = new Vector2D(-1,-1);
		}
		if (this == Direction.SOUTHEAST){
			vector2D = new Vector2D(+1,+1);
		}
		if (this == Direction.SOUTHWEST){
			vector2D = new Vector2D(-1,+1);
		}
		return vector2D;
	}
}
