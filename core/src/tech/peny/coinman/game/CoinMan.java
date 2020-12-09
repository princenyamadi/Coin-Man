package tech.peny.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] man;
	int manState;
	int pause = 0;
	float gravity = 0.2f;
	float velocity = 0;
	int manY = 0;
	Rectangle manRectangle;


	Random random;

	ArrayList<Integer> coinX = new ArrayList<Integer>();
	ArrayList<Integer> coinY = new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
	Texture coin;
	int coinCount;

	ArrayList<Integer> bombX = new ArrayList<Integer>();
	ArrayList<Integer> bombY = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();

	Texture bomb;
	int bombCount;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		man = new Texture[4];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");

		manY = Gdx.graphics.getHeight()/2;

		coin = new Texture("coin.png");
		random = new Random();

		bomb = new Texture("bomb.png");



	}
	public void makeBomb(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombX.add((int) height);
		bombY.add(Gdx.graphics.getWidth());
	}

	public void makeCoin(){
//		coin height
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinY.add((int)height);
		coinX.add(Gdx.graphics.getWidth());


	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(coinCount < 100){
			coinCount++;
		}else {
			coinCount = 0;
			makeCoin();
		}

		if(bombCount < 250){
			bombCount++;
		}else{
			bombCount = 0;
			makeBomb();
		}


		if(Gdx.input.justTouched()){
			velocity = -10;
		}
		coinRectangles.clear();
		for(int i = 0; i < coinX.size();i++){
			batch.draw(coin, coinX.get(i),coinY.get(i));
			coinX.set(i, coinX.get(i)- 4);
			coinRectangles.add(new Rectangle(coinX.get(i),coinY.get(i), coin.getWidth(),coin.getHeight()));
		}

		bombRectangles.clear();


		for(int i = 0; i < bombX.size();i++){
			batch.draw(bomb, bombX.get(i),bombY.get(i));

			bombX.set(i, bombX.get(i)- 8);
			bombRectangles.add(new Rectangle(bombX.get(i),bombY.get(i), bomb.getWidth(),bomb.getHeight()));
		}




		if(pause < 8){
			pause++;
		}else{
			pause = 0;
			if(manState < 3){
				manState++;
			}else{
				manState = 0;
			}
		}

		velocity += gravity;
		manY -= velocity;
		if(manY <= 0){
			manY = 0;
		}


		batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth()/2,manY );
		manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[manState].getWidth()/2,manY,man[manState].getWidth(),man[manState].getHeight());

		for(int i = 0; i < coinRectangles.size(); i++){
			if(Intersector.overlaps(manRectangle,coinRectangles.get(i))){
				Gdx.app.log("Coin!","Collision");
			}
		}
		for(int i = 0; i < bombRectangles.size(); i++){
			if(Intersector.overlaps(manRectangle,bombRectangles.get(i))){
				Gdx.app.log("Bomb!","Collision");
			}
		}







		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
