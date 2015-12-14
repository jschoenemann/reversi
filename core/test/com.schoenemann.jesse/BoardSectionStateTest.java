package com.schoenemann.jesse;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardSectionStateTest {

    private BoardStateHelper stateHelper;

    HashMap<String, BoardSectionState> sectionStateHashMap;

    @Before
    public void setupBoard(){
        sectionStateHashMap = new HashMap<String,BoardSectionState>();
        for(int x=0; x < Assets.ROW_LENGTH; x++){
            for(int y=0; y < Assets.ROW_LENGTH; y++){
                BoardSectionState section = new BoardSectionState(x,y);
                sectionStateHashMap.put(section.getMapKey(), section);
            }
        }
        stateHelper = new BoardStateHelper(sectionStateHashMap, Assets.ROW_LENGTH);
    }

    @Test
    public void verifyValidMovesForStartingBoard(){

        assertEquals(2, stateHelper.getBlackScore());
        assertEquals(2, stateHelper.getWhiteScore());

        Map<String, Boolean> validMoves = stateHelper.getValidMoveLocations();
        assertEquals(4, validMoves.size());

        assertTrue(validMoves.get("5_3"));
        assertTrue(validMoves.get("4_2"));
        assertTrue(validMoves.get("2_4"));
        assertTrue(validMoves.get("3_5"));
        assertEquals(BoardSectionState.PLAYER_CHIP.BLACK, stateHelper.getPlayerTurn());
    }

    @Test
    public void verifyValidFirstMove(){
        assertEquals(2, stateHelper.getBlackScore());
        assertEquals(2, stateHelper.getWhiteScore());

        int piecesMoved = stateHelper.preformMove(sectionStateHashMap.get(BoardSectionState.getMapKey(2, 4)));
        assertEquals(2, piecesMoved);
        assertEquals(sectionStateHashMap.get(BoardSectionState.getMapKey(2, 4)).getCurrentChip(), BoardSectionState.PLAYER_CHIP.BLACK);
        assertEquals(sectionStateHashMap.get(BoardSectionState.getMapKey(3, 4)).getCurrentChip(), BoardSectionState.PLAYER_CHIP.BLACK);
        assertEquals(4, stateHelper.getBlackScore());
        assertEquals(1, stateHelper.getWhiteScore());
        assertEquals(BoardSectionState.PLAYER_CHIP.WHITE, stateHelper.getPlayerTurn());
    }

    @Test
    public void verifyInvalidFirstMove45() {
        int piecesMoved = stateHelper.preformMove(sectionStateHashMap.get(BoardSectionState.getMapKey(4,5)));
        assertEquals(0, piecesMoved);
        assertEquals(2, stateHelper.getBlackScore());
        assertEquals(2, stateHelper.getWhiteScore());
        assertEquals(BoardSectionState.PLAYER_CHIP.BLACK, stateHelper.getPlayerTurn());
    }

    @Test
    public void verifyInvalidFirstMove54() {
        int piecesMoved = stateHelper.preformMove(sectionStateHashMap.get(BoardSectionState.getMapKey(5,4)));
        assertEquals(0, piecesMoved);
        assertEquals(2, stateHelper.getBlackScore());
        assertEquals(2, stateHelper.getWhiteScore());
        assertEquals(BoardSectionState.PLAYER_CHIP.BLACK, stateHelper.getPlayerTurn());
    }

    @Test
    public void verifyInvalidFirstMove55() {
        int piecesMoved = stateHelper.preformMove(sectionStateHashMap.get(BoardSectionState.getMapKey(5,5)));
        assertEquals(0, piecesMoved);
        assertEquals(2, stateHelper.getBlackScore());
        assertEquals(2, stateHelper.getWhiteScore());
        assertEquals(BoardSectionState.PLAYER_CHIP.BLACK, stateHelper.getPlayerTurn());
    }


    @Test
     public void verifyMoveInLeftField() {
        int piecesMoved = stateHelper.preformMove(sectionStateHashMap.get(BoardSectionState.getMapKey(0,0)));
        assertEquals(0, piecesMoved);
        assertEquals(2, stateHelper.getBlackScore());
        assertEquals(2, stateHelper.getWhiteScore());
        assertEquals(BoardSectionState.PLAYER_CHIP.BLACK, stateHelper.getPlayerTurn());
     }


}