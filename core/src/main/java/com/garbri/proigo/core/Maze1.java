package com.garbri.proigo.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.garbri.proigo.core.controls.IControls;
import com.garbri.proigo.core.controls.KeyboardControls;
import com.garbri.proigo.core.controls.XboxListener;
import com.garbri.proigo.core.objects.Ball;
import com.garbri.proigo.core.objects.Car;
import com.garbri.proigo.core.objects.Goal;
import com.garbri.proigo.core.objects.Maze;
import com.garbri.proigo.core.utilities.Controls;
import com.garbri.proigo.core.utilities.SpriteHelper;
import com.garbri.proigo.core.utilities.TextDisplayHelper;
import com.garbri.proigo.core.utilities.TimerHelper;

import com.badlogic.gdx.Screen;

public class Maze1 implements Screen {
private long lastRender;
	
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	/**
	 * This is the main box2d "container" object. All bodies will be loaded in
	 * this object and will be simulated through calls to this object.
	 */
	private World world;
	/**
	 * This box2d debug renderer comes from libgdx test code. It draws lines
	 * over all collision boundaries, so it is immensely useful for verifying
	 * that the world collisions are as you expect them to be. It is, however,
	 * slow, so only use it for testing.
	 */
	private Box2DDebugRenderer debugRenderer;	

	private int screenWidth;
	private int screenHeight;	
	private float worldWidth;
	private float worldHeight;
	private static int PIXELS_PER_METER=10;      //how many pixels in a meter
	
	Controller[] controllers = new Controller[4];
	
	Car player1;
	Car player2;
	Car player3;
	Car player4;
	Ball ball;
	Goal leftGoal;
	Goal rightGoal;
	
	ArrayList<IControls> controls =  new ArrayList<IControls>() ;
	
	public Maze maze;
	
	Vector2 center;
	
	private SpriteHelper spriteHelper;
	
	private Sprite finishLine;
	
	Timer countdownTimer;
	

	XboxListener listener;
	private TextDisplayHelper textDisplayer;
	
	private Boolean displayWinMessage;
	private String winMessage;
	
	proigo game;
	
	private TimerHelper timer;
	
	public Maze1(proigo game)
	{
		this.game = game;
		
	}

	private void resetGame()
	{
		dispose();
		spriteBatch = new SpriteBatch();
		
		this.player1.destroyCar();
		this.player2.destroyCar();
		this.player3.destroyCar();
		this.player4.destroyCar();
		
		this.maze.gameFinished = false;
		this.displayWinMessage = false;
		
		this.timer.resetTimer();
		
		this.timer.startCountDown(3);
		
	    createAllCars();
	}
	
	private void createAllCars()
	{
		this.player1 = new Car("player1", world, 2, 4,
	    		this.maze.playerStartPoint[0], (float) Math.PI/2, 60, 20, 180, controls.get(0), spriteHelper.getCarSprite(0), spriteHelper.getWheelSprite());
	    
	    this.player2 = new Car("player2", world, 2, 4,
	    		this.maze.playerStartPoint[0], (float) Math.PI/2, 60, 20, 180, controls.get(1), spriteHelper.getCarSprite(2), spriteHelper.getWheelSprite());
	    this.player3 = new Car("player1", world, 2, 4,
	    		this.maze.playerStartPoint[1], (float) (Math.PI + Math.PI/2), 60, 20, 180, controls.get(2), spriteHelper.getCarSprite(1), spriteHelper.getWheelSprite());
	    
	    this.player4 = new Car("player2", world, 2, 4,
	    		this.maze.playerStartPoint[1], (float) (Math.PI + Math.PI/2), 60, 20, 180, controls.get(3), spriteHelper.getCarSprite(3), spriteHelper.getWheelSprite());
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	@Override
	public void render(float delta) {	
	    Gdx.gl.glClearColor(0, 0f, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		

		this.timer.progressTime();
		
	    // tell the camera to update its matrices.
	    camera.update();
	    
	    //checkForReset
	    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
	    {
	    	resetGame();
	    }
	    
	    //checkForReset
	    if (Gdx.input.isKeyPressed(Input.Keys.F2))
	    {
	    	this.game.setScreen(this.game.soccerScreen);
	    }
	    
		spriteBatch.setProjectionMatrix(camera.combined);

		this.spriteBatch.begin();

		if (this.timer.countDownTimer == 0)
		{
			player1.controlCar();
			player2.controlCar();
			player3.controlCar();
			player4.controlCar();
		}
		else
		{
			if(!this.displayWinMessage)
				textDisplayer.font.draw(spriteBatch, "RACE TO THE MIDDLE TO GAIN THE ADVANTAGE" , (center.x * PIXELS_PER_METER) - ("RACE TO THE MIDDLE TO GAIN THE ADVANTAGE   ".length() * 3) , (center.y - 10f) * PIXELS_PER_METER);
		}

		this.ball.update();
		
		if (!this.displayWinMessage)
		{
			//Player 1 wins if both cars reach it at the same time - MNaybe we should randomize this
		
			if (this.maze.checkForWin(this.player1.body.getPosition(), this.player1.playerName))
			{
				this.displayWinMessage = true;
				this.winMessage = "BLUE TEAM WINS";
				this.timer.startCountDown(3);
			}
			if (this.maze.checkForWin(this.player2.body.getPosition(), this.player2.playerName))
			{
				this.displayWinMessage = true;
				this.winMessage = "RED TEAM WINS";
				this.timer.startCountDown(3);
			}
			if (this.maze.checkForWin(this.player3.body.getPosition(), this.player1.playerName))
			{
				this.displayWinMessage = true;
				this.winMessage = "BLUE TEAM WINS";
				this.timer.startCountDown(3);
			}
			if (this.maze.checkForWin(this.player4.body.getPosition(), this.player2.playerName))
			{
				this.displayWinMessage = true;
				this.winMessage = "RED TEAM WINS";
				this.timer.startCountDown(3);
			}
		}
		
		
		
		
		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
		
		world.clearForces();
		
		this.finishLine.setPosition((this.center.x * PIXELS_PER_METER) - this.finishLine.getWidth()/2, (this.center.y * PIXELS_PER_METER) - this.finishLine.getHeight()/2);
		this.finishLine.draw(spriteBatch);
		
		//Update Player/Car 1		
		player1.updateSprite(spriteBatch, PIXELS_PER_METER);
		
		//Update Player/Car 2
		player2.updateSprite(spriteBatch, PIXELS_PER_METER);
		
		//Update Player/Car 1		
		player3.updateSprite(spriteBatch, PIXELS_PER_METER);
				
		//Update Player/Car 2
		player4.updateSprite(spriteBatch, PIXELS_PER_METER);
		
		//Update Ball
		SpriteHelper.updateSprite(ball.sprite, spriteBatch, PIXELS_PER_METER, ball.body);
		
		if (this.displayWinMessage)
		{
			textDisplayer.font.draw(spriteBatch, this.winMessage , (center.x * PIXELS_PER_METER) - (this.winMessage.length() * 3) , center.y * PIXELS_PER_METER);
			
			if(this.timer.countDownTimer == 0)
			{
				if(this.winMessage.equals("RED TEAM WINS"))
				{
					this.game.soccerScreen.ballOffsetX = 40f; 
				}
				else
				{
					this.game.soccerScreen.ballOffsetX = -40f; 
				}
				
				this.game.setScreen(this.game.soccerScreen);
				
			}
		}
		
		String temp = this.timer.getElapsedTimeAsString();

		textDisplayer.font.draw(spriteBatch, temp , (center.x * PIXELS_PER_METER) - (temp.length() * 3) ,(worldHeight-1f) * PIXELS_PER_METER);
		
		this.spriteBatch.end();
		
		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,PIXELS_PER_METER,PIXELS_PER_METER));
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}



	@Override
	public void show() {
		
		screenWidth = 1400;
		screenHeight = 900;
		
		worldWidth = screenWidth / PIXELS_PER_METER;
		worldHeight = screenHeight / PIXELS_PER_METER;
		
		//worldWidth = 800;
		//worldHeight = 600;

		//Box2d World init
		this.center = new Vector2(worldWidth/2, worldHeight/2);
		
		world = new World(new Vector2(0.0f, 0.0f), true);	
		
		spriteHelper = new SpriteHelper();
		
		int i = 0;
		
		
		for(Controller controller: Controllers.getControllers()) 
		{
		   Gdx.app.log("Main", controller.getName());
		   XboxListener listener = new XboxListener();
		   controller.addListener(listener);
		   listener.getControls();
		   controls.add(listener.getControls());
		   this.controllers[i] = controller;
		   i++;
			   
		}
			
			
				controls.add( new KeyboardControls(Input.Keys.DPAD_UP, Input.Keys.DPAD_DOWN, Input.Keys.DPAD_LEFT, Input.Keys.DPAD_RIGHT));
				controls.add( new KeyboardControls(Input.Keys.DPAD_UP, Input.Keys.DPAD_DOWN, Input.Keys.DPAD_LEFT, Input.Keys.DPAD_RIGHT));
				controls.add( new KeyboardControls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D));
				controls.add( new KeyboardControls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D));
			
			
		
		
		textDisplayer = new TextDisplayHelper();
		this.maze = new Maze(world, worldWidth, worldHeight, center);

		createAllCars();

		
	    this.finishLine = spriteHelper.getFinishLineSprite(20, (int) (worldHeight/(this.maze.numberOfInnerWalls+1))*PIXELS_PER_METER);
	    
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, screenWidth, screenHeight);
	    spriteBatch = new SpriteBatch();		
										
		debugRenderer = new Box2DDebugRenderer();
	    
		this.ball = new Ball(world, center.x, center.y, spriteHelper.getBallSprite());
		
		//this.pitch = new Pitch(world, worldWidth, worldHeight, center);
		
		this.maze = new Maze(world, worldWidth, worldHeight, center);
		
		this.displayWinMessage = false;
		
		this.timer = new TimerHelper();		
		
		this.timer.startCountDown(3);
		
		
		
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}