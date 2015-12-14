package com.schoenemann.jesse;

/**
 * Created by jesse on 12/10/15.
 */
public class BoardSectionState {

    public enum PLAYER_CHIP { NONE, BLACK, WHITE }

    private int xLocation;
    private int yLocation;

    private PLAYER_CHIP currentChip;

    public BoardSectionState(int xLocation, int yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        currentChip = PLAYER_CHIP.NONE;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public PLAYER_CHIP getCurrentChip() {
        return currentChip;
    }

    public void setCurrentChip(PLAYER_CHIP currentChip) {
        this.currentChip = currentChip;
    }

    public String getMapKey(){
        return getMapKey(this.xLocation, this.yLocation);
    }

    private static final String DELIM =  "_";
    public static String getMapKey(int x, int y){
        return x + DELIM + y;
    }
}
