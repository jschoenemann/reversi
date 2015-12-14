package com.schoenemann.jesse.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.schoenemann.jesse.Assets;
import com.schoenemann.jesse.BoardSectionState;
import com.schoenemann.jesse.BoardStateHelper;
import com.schoenemann.jesse.ReversiGame;
import com.schoenemann.jesse.actors.BoardSection;

import java.util.HashMap;

/**
 * Created by jesse on 12/10/15.
 */
public class ReversiScreen extends AbstractGameScreen {

    public ReversiScreen(ReversiGame game){
        super(game);
    }

    private HashMap<String, BoardSection> sectionMap = new HashMap<String, BoardSection>();
    private HashMap<String, BoardSectionState> sectionStateMap = new HashMap<String, BoardSectionState>();

    private BoardStateHelper gameStateHelper;

    private Label blackScore, whiteScore;

    private Image currentTurn;
    private Drawable blackPlayerIcon, whitePlayerIcon;
    private Table gameInfoSection;
    private TextButton newGameButton;

    @Override
    public void show() {
        Table screenLayout = new Table();
        screenLayout.setBackground(getColoredBackground(Color.LIGHT_GRAY));
        screenLayout.setFillParent(true);

        Label l = new Label("Jesse's Reversi Game", Assets.reversiSkin);
        l.setColor(Color.RED);


        Table gameBoard = new Table();
        for(int x=0; x < Assets.ROW_LENGTH; x++){
            for(int y=0; y < Assets.ROW_LENGTH; y++){
                BoardSectionState state = new BoardSectionState(x,y);
                sectionStateMap.put(state.getMapKey(), state);
                BoardSection section = new BoardSection(state);
                section.addListener(attemptMoveClickListner);

                sectionMap.put(state.getMapKey(), section);
                gameBoard.add(section).pad(1);
            }
            gameBoard.row();
        }

        gameStateHelper = new BoardStateHelper(sectionStateMap, Assets.ROW_LENGTH);

        whitePlayerIcon = Assets.reversiSkin.getDrawable("white_chip");
        blackPlayerIcon = Assets.reversiSkin.getDrawable("black_chip");

        Table scoringSection = new Table();
        Label whiteLabel = new Label("White", Assets.reversiSkin);
        whiteScore = new Label("0", Assets.reversiSkin);
        scoringSection.add(whiteLabel).row();
        scoringSection.add(new Image(whitePlayerIcon)).row();

        scoringSection.add(whiteScore).row();


        Label blackLabel = new Label("Black", Assets.reversiSkin);
        blackScore = new Label("0", Assets.reversiSkin);
        scoringSection.add(blackLabel).padTop(100).row();
        scoringSection.add(new Image(blackPlayerIcon)).row();

        scoringSection.add(blackScore).row();

        currentTurn = new Image(blackPlayerIcon);

        newGameButton = new TextButton("New Game", Assets.reversiSkin);
        newGameButton.addListener(newGameClickListener);

        gameInfoSection = new Table();
        gameInfoSection.padTop(50);
        gameInfoSection.padBottom(50);
        gameInfoSection.setWidth(100);

        gameInfoSection.setSkin(Assets.reversiSkin);
        displayNextTurn();

        screenLayout.add(l).fillX().expandX().left().pad(5).colspan(3).row();
        screenLayout.add(scoringSection).fill().expandY().width(300);
        screenLayout.add(gameBoard).fill().expand();
        screenLayout.add(gameInfoSection).fill().expandY().width(300).row();


        stage.addActor(screenLayout);
        refreshUI();
        Gdx.input.setInputProcessor(stage);
    }

    private final static String WINNING = "  !!!! WINNER !!!!  ";
    private final static String TIE = "uggg.. a tie.. :(";

    private void refreshUI(){
        blackScore.setText(gameStateHelper.getBlackScore() + "");
        whiteScore.setText(gameStateHelper.getWhiteScore() + "");

        if(gameStateHelper.getPlayerTurn() == BoardSectionState.PLAYER_CHIP.BLACK){
            currentTurn.setDrawable(blackPlayerIcon);
        }else if(gameStateHelper.getPlayerTurn() == BoardSectionState.PLAYER_CHIP.WHITE){
            currentTurn.setDrawable(whitePlayerIcon);
        }else{
            displayWinner();
        }

        for(BoardSection section : sectionMap.values()){
            if(gameStateHelper.getValidMoveLocations().get(section.getSectionState().getMapKey()) != null){
                section.setIsValidMove(true);
            }else{
                section.setIsValidMove(false);
            }
        }
    }

    private void displayNextTurn(){
        gameInfoSection.clearChildren();
        gameInfoSection.add("Current Move:");
        gameInfoSection.add(currentTurn).row();
        gameInfoSection.add(newGameButton).expand().colspan(2).right().bottom().pad(5).row();
    }

    private void displayWinner(){
        gameInfoSection.clearChildren();
        if(gameStateHelper.getBlackScore() == gameStateHelper.getWhiteScore()){
            gameInfoSection.add(TIE).row();
        }else{
            gameInfoSection.add(WINNING).row();
            gameInfoSection.add(currentTurn).row();
            gameInfoSection.add(WINNING).row();
            if(gameStateHelper.getBlackScore() > gameStateHelper.getWhiteScore()){
                currentTurn.setDrawable(blackPlayerIcon);
            }else if(gameStateHelper.getBlackScore() < gameStateHelper.getWhiteScore()){
                currentTurn.setDrawable(whitePlayerIcon);
            }
        }
        gameInfoSection.add(newGameButton).expand().colspan(2).right().bottom().pad(5).row();
    }

    private InputListener attemptMoveClickListner = new InputListener() {
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            BoardSection section = (BoardSection) event.getListenerActor();
            int i = gameStateHelper.preformMove(section.getSectionState());
            refreshUI();
            return true;
        }
    };

    private InputListener newGameClickListener = new InputListener() {
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
          for(BoardSectionState sectionState : sectionStateMap.values()){
              sectionState.setCurrentChip(BoardSectionState.PLAYER_CHIP.NONE);
          }
          gameStateHelper = new BoardStateHelper(sectionStateMap, Assets.ROW_LENGTH);
            displayNextTurn();
            refreshUI();
          return true;
        }
    };


    @Override
    public void render (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage.setDebugAll(true);
        stage.act(delta);
        stage.draw();
    }


    protected Drawable getColoredBackground(com.badlogic.gdx.graphics.Color c){
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(c.r, c.g, c.b, 1f);
        pm1.fill();
        Drawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
        return bg;
    }
}
