package uta.cse3310;

import java.util.*;

/**
 * Enum representing different player types with associated colors.
 */
public enum PlayerType {
    BLUE("Blue"),    
    RED("Red"),      
    YELLOW("Yellow"), 
    GREEN("Green"),  
    NO_PLAYER("NoPlayer"); 

    private final String color; // Color associated with each player type

    /**
     * Constructor for PlayerType enum.
     * @param color The color associated with the player type.
     */
    PlayerType(String color) {
        this.color = color;
    }

    /**
     * Get the color associated with the player type.
     * @return The color string.
     */
    public String getColor() {
        return color;
    }
}
 
