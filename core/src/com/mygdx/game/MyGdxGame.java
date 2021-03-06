package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import javafx.animation.Animation;

public class  MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	float x, y, yv, xv;
	boolean canJump, faceRight;
	float decelerate;
	TextureRegion img;
	TextureRegion frontkick, frontjump, backkick, rightstep, rightjumpstep, backjump, left, rightstand, rightstraightjump;
	Animation walk;
	float time;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;


	static final int DRAW_WIDTH = WIDTH * 3;
	static final int DRAW_HEIGHT = HEIGHT * 2;

	static final int MAX_VELOCITY = 300;
	static final float MAX_JUMP_VELOCITY = 2000;
	static final int MAXIMUM_VELOCITY = 1000;
	static final int GRAVITY = -100;


 	@Override
	public void create() {
		batch = new SpriteBatch();

		Texture tiles = new Texture("tiles.png");

		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);


		frontkick = grid[6][0];
		frontjump = grid[7][0];
		backkick = grid[6][1];
		rightstraightjump = grid[7][2];
		rightstand = grid[6][2];
		rightstep = grid[6][3];
		rightjumpstep = grid[7][3];
		backjump = grid[7][1];

		left = new TextureRegion(rightstep);
//		walk = new Animation(0.2f, grid[0][2], grid[0][3], grid[0][4]);// LINE DOESNT WORK


	}


	@Override
	public void render() {
		time += Gdx.graphics.getDeltaTime();

		move();
		offScreen();

		//changing appearance based on stance.
		TextureRegion img;
		if (y > 0) {
			img = rightjumpstep;
		} else if (x == 0) {
			img = rightstand;
//		}
//		else if (xv != 0) {
//				img = walk.getKeyFrame(time, true); //this line doesnt work for getkeyframe.
		} else if (x < 0) {
			img = left;
		} else {
			img = rightstand;
		}

		Gdx.gl.glClearColor(1, 2, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();


	}


	float decelerate(float velocity) {
		float deceleration = 0.95f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;

	}


	void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && canJump) {
			yv = MAX_JUMP_VELOCITY;
			canJump = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			faceRight = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
//			faceRight = false;

		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			xv = MAXIMUM_VELOCITY;
		}
		yv += GRAVITY;
		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		if (y < 0) {
			y = 0;
			canJump = true;
		}

		yv = decelerate(yv);
		xv = decelerate(xv);

	}

	void offScreen(){
		if (x > 800) {
			x = 0;
//			batch.draw(img, 0, 0, DRAW_WIDTH, DRAW_HEIGHT); // this is my best guess for how to make him reappear. I don't
			//understand why it doesn't work.
		}

	}
}

