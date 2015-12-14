package com.schoenemann.jesse;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardSectionStateEdgeCasesTest {


    private HashMap<String, BoardSectionState> sectionStateHashMap;

    @Before
    public void setupBoard(){
        sectionStateHashMap = new HashMap<String,BoardSectionState>();
        for(int x=0; x < Assets.ROW_LENGTH; x++){
            for(int y=0; y < Assets.ROW_LENGTH; y++){
                BoardSectionState section = new BoardSectionState(x,y);
                sectionStateHashMap.put(section.getMapKey(), section);
            }
        }
    }

    /***
     * n-n-n-n-b-w-w-w
     * n-n-n-n-n-n-w-b
     * n...
     *
     */
    private void setUpA(){
        sectionStateHashMap.get("7_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        sectionStateHashMap.get("6_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        sectionStateHashMap.get("5_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        sectionStateHashMap.get("4_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        sectionStateHashMap.get("7_1").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        sectionStateHashMap.get("6_1").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
    }

    @Test
    public void verifyWhiteMoveLocationSetupA(){
        setUpA();

        BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.WHITE);
        assertEquals(2, helper.getBlackScore());
        assertEquals(4, helper.getWhiteScore());


        Map<String, Boolean> validMoves = helper.getValidMoveLocations();
        assertEquals(2, validMoves.size());

        assertTrue(validMoves.get("3_0"));
        assertTrue(validMoves.get("7_2"));

    }

    @Test
    public void verifyBlackMoveLocationSetupA(){
        setUpA();
        BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.BLACK);
        assertEquals(2, helper.getBlackScore());
        assertEquals(4, helper.getWhiteScore());

        Map<String, Boolean> validMoves = helper.getValidMoveLocations();
        assertEquals(1, validMoves.size());

        assertTrue(validMoves.get("5_1"));
    }



    /***
     * b-w-b-n-n-n-n-n
     * w-b-n-n-n-n-n-n
     * w-w-n-n-n-n-n-n
     * n-n-n-n...
     * .
     * .
     */
    private void setUpB(){
        sectionStateHashMap.get("0_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        sectionStateHashMap.get("1_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        sectionStateHashMap.get("2_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        sectionStateHashMap.get("0_1").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        sectionStateHashMap.get("1_1").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        sectionStateHashMap.get("0_2").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        sectionStateHashMap.get("1_2").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
    }


    @Test
    public void verifyWhiteMoveLocationSetupB() {
        setUpB();

        BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.WHITE);
        assertEquals(3, helper.getBlackScore());
        assertEquals(4, helper.getWhiteScore());


        Map<String, Boolean> validMoves = helper.getValidMoveLocations();
        assertEquals(2, validMoves.size());

        assertTrue(validMoves.get("3_0"));
        assertTrue(validMoves.get("2_1"));


    }


    @Test
    public void verifyBlackMoveLocationSetupB() {
        setUpB();
        BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.BLACK);
        assertEquals(3, helper.getBlackScore());
        assertEquals(4, helper.getWhiteScore());

        Map<String, Boolean> validMoves = helper.getValidMoveLocations();
        assertEquals(2, validMoves.size());

        assertTrue(validMoves.get("0_3"));
        assertTrue(validMoves.get("1_3"));
    }




}