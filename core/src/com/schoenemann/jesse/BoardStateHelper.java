package com.schoenemann.jesse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jesse on 12/10/15.
 */
public class BoardStateHelper {

    private Map<String, BoardSectionState> stateMap;

    private int whiteScore = 2;
    private int blackScore = 2;

    private BoardSectionState.PLAYER_CHIP playerTurn = BoardSectionState.PLAYER_CHIP.BLACK;

    private Map<String, Boolean> validMoveLocations = new HashMap<String, Boolean>();


    /**
     * starts a new game
     * @param boardSectionStateMap
     * @param boardSize
     */
    public BoardStateHelper(HashMap<String, BoardSectionState> boardSectionStateMap, int boardSize){
        stateMap = boardSectionStateMap;
        setStartingPieces(boardSize);
        updateGameState();
    }

    /**
     * resumes game
     * @param boardSectionStateMap
     * @param playerWithNextMove
     */
    public BoardStateHelper(HashMap<String, BoardSectionState> boardSectionStateMap, BoardSectionState.PLAYER_CHIP playerWithNextMove){
        stateMap = boardSectionStateMap;
        playerTurn = playerWithNextMove;
        updateGameState();
    }

    private void setStartingPieces(int rowLength){
        int a = rowLength / 2;
        int b = rowLength / 2 - 1;
        stateMap.get(BoardSectionState.getMapKey(a,a)).setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        stateMap.get(BoardSectionState.getMapKey(a,b)).setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
        stateMap.get(BoardSectionState.getMapKey(b,b)).setCurrentChip(BoardSectionState.PLAYER_CHIP.BLACK);
        stateMap.get(BoardSectionState.getMapKey(b,a)).setCurrentChip(BoardSectionState.PLAYER_CHIP.WHITE);
    }

    private static final int[][] DIRECTIONS = new int[][] {
            new int[] {-1,-1}
            ,new int[] {-1, 0}
            ,new int[] {-1, 1}
            ,new int[] {0,-1}
            ,new int[] {0,1}
            ,new int[] {1,-1}
            ,new int[] {1, 0}
            ,new int[] {1, 1}
    };

    private boolean lastPlayerSkipped = false;

    private void updateGameState(){
        validMoveLocations.clear();
        blackScore = 0;
        whiteScore = 0;

        for(BoardSectionState boardSectionState: stateMap.values()){
            if(boardSectionState.getCurrentChip() == BoardSectionState.PLAYER_CHIP.NONE && isValidLocationToPlay(boardSectionState, playerTurn)){
                validMoveLocations.put(boardSectionState.getMapKey(), true);
            }
            if(boardSectionState.getCurrentChip() == BoardSectionState.PLAYER_CHIP.BLACK){
                blackScore++;
            }else if(boardSectionState.getCurrentChip() == BoardSectionState.PLAYER_CHIP.WHITE){
                whiteScore++;
            }
        }
        if(validMoveLocations.size() == 0){
            if(lastPlayerSkipped){
                playerTurn = null; //game over
            }else{
                lastPlayerSkipped = true;
                nextPlayerTurn();
            }
        }
    }

    private void nextPlayerTurn(){
        if(playerTurn == BoardSectionState.PLAYER_CHIP.BLACK){
            playerTurn = BoardSectionState.PLAYER_CHIP.WHITE;
        }else{
            playerTurn = BoardSectionState.PLAYER_CHIP.BLACK;
        }
        updateGameState();
    }

    private boolean isValidLocationToPlay(BoardSectionState boardSection, BoardSectionState.PLAYER_CHIP playerColor){
        boolean validLocation = false;
        for(int[] xy : DIRECTIONS){
            List<BoardSectionState> anyCaptured = findCapturablePieceForDirection(boardSection, playerColor, xy[0], xy[1]);
            if(anyCaptured != null && anyCaptured.size() > 0){
                validLocation = true;
                break;
            }
        }
        return validLocation;
    }

    // returns the number of pieces captured
    public int preformMove(BoardSectionState desiredMoveLocation){
        List<BoardSectionState> capturedPieces = new ArrayList<BoardSectionState>();
        if(desiredMoveLocation.getCurrentChip() == BoardSectionState.PLAYER_CHIP.NONE ){
            for(int[] xy : DIRECTIONS){
                addCapturablePiecesToList(capturedPieces, desiredMoveLocation, playerTurn, xy[0], xy[1]);
            }
        }

        if(capturedPieces.size() > 0){
            capturedPieces.add(desiredMoveLocation);
            for(BoardSectionState boardSectionState : capturedPieces){
                boardSectionState.setCurrentChip(playerTurn);
            }
            nextPlayerTurn();
        }

        return capturedPieces.size();
    }


    private void addCapturablePiecesToList(List<BoardSectionState> allMyCapturedPieces, BoardSectionState boardSection, BoardSectionState.PLAYER_CHIP chipToPlay, int xDirection, int yDirection){
        List<BoardSectionState> anyCaptured = findCapturablePieceForDirection(boardSection, chipToPlay, xDirection, yDirection);
        if(anyCaptured != null){
            allMyCapturedPieces.addAll(anyCaptured);
        }
    }

    private List<BoardSectionState> findCapturablePieceForDirection(BoardSectionState boardSection, BoardSectionState.PLAYER_CHIP chipToPlay, int xDirection, int yDirection){
        List<BoardSectionState> boardSectionStateList = null;
        int nextX = boardSection.getxLocation() + xDirection;
        int nextY = boardSection.getyLocation() + yDirection;

        BoardSectionState nextPiece = stateMap.get(BoardSectionState.getMapKey(nextX, nextY));
        if(nextPiece == null || nextPiece.getCurrentChip() == BoardSectionState.PLAYER_CHIP.NONE){
            boardSectionStateList = null; //captured nothing!
        }else if(nextPiece.getCurrentChip() != chipToPlay){
            //keep going
            List<BoardSectionState> collectedPieces = findCapturablePieceForDirection(nextPiece, chipToPlay, xDirection, yDirection);
            if(collectedPieces != null){
                //we are capturingPieces
                boardSectionStateList = new ArrayList<BoardSectionState>();
                boardSectionStateList.add(nextPiece);
                boardSectionStateList.addAll(collectedPieces);
            }
            //if it is null return default of null and capture nothing
        }else{
            // found end start collecting captured sections.
            boardSectionStateList = new ArrayList<BoardSectionState>();
        }
        return boardSectionStateList;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public BoardSectionState.PLAYER_CHIP getPlayerTurn() {
        return playerTurn;
    }

    public Map<String, Boolean> getValidMoveLocations() {
        return validMoveLocations;
    }
}
