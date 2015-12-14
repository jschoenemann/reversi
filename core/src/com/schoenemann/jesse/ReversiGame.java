package com.schoenemann.jesse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.schoenemann.jesse.screens.AbstractGameScreen;
import com.schoenemann.jesse.screens.ReversiScreen;

public class ReversiGame extends Game {
	SpriteBatch batch;
	Texture img;
	Screen currentScreen;
	
	@Override
	public void create () {
		Assets.load();
		batch = new SpriteBatch();
		currentScreen = new ReversiScreen(this);
		setScreen(currentScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}
