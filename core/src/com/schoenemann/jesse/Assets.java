package com.schoenemann.jesse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Assets {


    public static Skin reversiSkin;

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 752;

    public static final int ROW_LENGTH = 8;


    public static void load () {

        TextureAtlas atlas = new TextureAtlas("skin/uiskin.atlas");
        reversiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        reversiSkin.addRegions(atlas);

        reversiSkin.getFont("default-font").getData().setScale(2f, 2f);

        TextureAtlas appAtlas = new TextureAtlas(Gdx.files.internal("reversi.txt"));
        reversiSkin.addRegions(appAtlas);

    }

}
