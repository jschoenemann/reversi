package com.schoenemann.jesse;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GameOverScenariosTest {


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
         * .
         * .
         * n-n-n-n-n....
         * n-n-n-n-w-n-b-b
         * n-n-n-n-n-b-b-b
         */
        private void setUpA(){
            sectionStateHashMap.get("7_7").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
            sectionStateHashMap.get("7_6").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
            sectionStateHashMap.get("6_7").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
            sectionStateHashMap.get("6_6").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
            sectionStateHashMap.get("5_7").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
            sectionStateHashMap.get("4_6").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        }

        @Test
        public void verifySkipWhiteTurnSetupA(){
            setUpA();

            BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.WHITE);
            assertEquals(5, helper.getBlackScore());
            assertEquals(1, helper.getWhiteScore());

            assertEquals(helper.getPlayerTurn(), BoardSectionState.PLAYER_CHIP.BLACK);
            Map<String, Boolean> validMoves = helper.getValidMoveLocations();
            assertEquals(1, validMoves.size());

            assertTrue(validMoves.get("3_5"));
        }

        @Test
        public void verifyBlackMoveNotSkippedSetupA(){
            setUpA();

            BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.BLACK);
            assertEquals(5, helper.getBlackScore());
            assertEquals(1, helper.getWhiteScore());

            assertEquals(helper.getPlayerTurn(), BoardSectionState.PLAYER_CHIP.BLACK);
            Map<String, Boolean> validMoves = helper.getValidMoveLocations();
            assertEquals(1, validMoves.size());

            assertTrue(validMoves.get("3_5"));
        }


        @Test
        public void verifyBlackWinSetupA(){
            setUpA();

            BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.BLACK);
            assertEquals(5, helper.getBlackScore());
            assertEquals(1, helper.getWhiteScore());

            assertEquals(helper.getPlayerTurn(), BoardSectionState.PLAYER_CHIP.BLACK);
            helper.preformMove(sectionStateHashMap.get("3_5"));

            assertNull("should be null for a win", helper.getPlayerTurn());
            assertEquals(7, helper.getBlackScore());
            assertEquals(0, helper.getWhiteScore());

        }


        /***
         * w-n-n-n...
         * w-n-n-n...
         * w-n-n-n...
         * w-n-n-n...
         * w-n-n-n...
         * b-n-n-n...
         * w-w-n-n-n-n-n-n
         * w-w-w-n-n-n-n-n
         */
        private void setUpB(){
            sectionStateHashMap.get("0_0").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("0_1").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("0_2").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("0_3").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("0_4").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("0_5").setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
            sectionStateHashMap.get("0_6").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("1_6").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("0_7").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("1_7").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
            sectionStateHashMap.get("2_7").setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        }


        @Test
        public void verifyWhiteTurnGameOver() {
            setUpB();

            BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.WHITE);
            assertEquals(1, helper.getBlackScore());
            assertEquals(10, helper.getWhiteScore());


            Map<String, Boolean> validMoves = helper.getValidMoveLocations();
            assertEquals(0, validMoves.size());

            assertNull("should be null for a win", helper.getPlayerTurn());
        }


        @Test
        public void verifyBlackTurnGameOver() {
            setUpB();

            BoardStateHelper helper = new BoardStateHelper(sectionStateHashMap, BoardSectionState.PLAYER_CHIP.BLACK);
            assertEquals(1, helper.getBlackScore());
            assertEquals(10, helper.getWhiteScore());


            Map<String, Boolean> validMoves = helper.getValidMoveLocations();
            assertEquals(0, validMoves.size());

            assertNull("should be null for a win", helper.getPlayerTurn());
        }

}