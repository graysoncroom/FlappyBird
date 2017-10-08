package com.graysoncroom.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

    Texture[] birds;
    int currentBirdIndex = 0;
    float birdY = 0;
    float velocity = 0;

    int gameState = 0;
    float gravity = 3/2;

    Texture topTube;
    Texture bottomTube;

    float gap = 400;
    float maxTubeOffset;
    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float distanceBetweenTubes;
    float[] tubeOffset = new float[numberOfTubes];

    Random random;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

        birds = new Texture[2];
	    birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        random = new Random();

        distanceBetweenTubes = Gdx.graphics.getWidth() / 2;

	    for (int i = 0; i < tubeX.length; i++) {
            tubeOffset[i] = (random.nextFloat() - 0.5F) * (2 * maxTubeOffset);

            tubeX[i] = Gdx.graphics.getWidth() /2 - topTube.getWidth() /2 + i * distanceBetweenTubes;
        }
	}

	@Override
	public void render() {
        // Render the Background Image
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState != 0) {
            // Render the Tubes


            if (Gdx.input.justTouched()) {
                velocity = -20;
            }

            for (int i = 0; i < tubeX.length; i++) {
                if (tubeX[i] < -distanceBetweenTubes*2) {
                    tubeX[i] = Gdx.graphics.getWidth();

                    tubeOffset[i] = (random.nextFloat() - 0.5F) * (2 * maxTubeOffset);
                }
                tubeX[i] -= tubeVelocity;
                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight() + tubeOffset[i]);
            }

            if (birdY > 0 || velocity < 0) {
                velocity += gravity;
                birdY -= velocity;
            }


        } else if (Gdx.input.justTouched()) {
            gameState = 1;
        }

        // Render the Bird
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