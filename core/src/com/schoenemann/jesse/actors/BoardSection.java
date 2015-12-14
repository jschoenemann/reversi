package com.schoenemann.jesse.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.schoenemann.jesse.Assets;
import com.schoenemann.jesse.BoardSectionState;

/**
 * Created by jesse on 12/10/15.
 */
public class BoardSection extends Group {

    private Drawable white,black, backgroundImage, backgroundValid;
    private Image background;
    private Image gamePiece;

    private BoardSectionState sectionState;

    public BoardSection(BoardSectionState state){
        sectionState = state;

        white = Assets.reversiSkin.getDrawable("white_chip");
        black = Assets.reversiSkin.getDrawable("black_chip");
        backgroundImage = Assets.reversiSkin.getDrawable("board_section2");
        backgroundValid = Assets.reversiSkin.getDrawable("board_section");

        background = new Image(backgroundImage);

        gamePiece = new Image(black);
        addActor(background);
        setBounds(getX(), getY(), background.getWidth(), background.getHeight());
    }

    public BoardSectionState getSectionState() {
        return sectionState;
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        switch(sectionState.getCurrentChip()){
            case NONE : {
                removeActor(gamePiece);
                break;
            }
            case BLACK: {
                gamePiece.setDrawable(black);
                addActor(gamePiece);
                break;
            }case WHITE: {
                gamePiece.setDrawable(white);
                addActor(gamePiece);
                break;
            }
        }
    }

    public void setIsValidMove(boolean isValidMove) {
        if(isValidMove){
            background.setDrawable(backgroundValid);
        }else{
            background.setDrawable(backgroundImage);
        }
    }
}
