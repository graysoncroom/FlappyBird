package com.graysoncroom.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

    Texture[] birds;
    int currentBirdIndex = 0;
    float birdY = 0;
    float velocity = 0;

    int gameState = 0;
    float gravity = 3/2;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

        birds = new Texture[2];
	    birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");

        birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
	}

	@Override
	public void render() {

        if (gameState != 0) {
            if (Gdx.input.justTouched()) {
                velocity = -20;
            }

            if (birdY > 0 || velocity < 0) {
                velocity += gravity;
                birdY -= velocity;
            }

        } else if (Gdx.input.justTouched()) {
            gameState = 1;
        }

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture currentBird = birds[getCurrentBirdIndex()];
        batch.draw(currentBird, Gdx.graphics.getWidth() / 2 - currentBird.getWidth() / 2, birdY);
        batch.end();
	}

	public int getCurrentBirdIndex() {
        int index = currentBirdIndex;
        currentBirdIndex = index == 0 ? 1 : 0;
        return index;
    }
}
