package com.garbri.proigo.core;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gabri.proigo.core.objects.Ball;
import com.gabri.proigo.core.objects.Car;
import com.gabri.proigo.core.objects.Goal;
import com.garbri.proigo.core.utilities.BoxProp;
import com.garbri.proigo.core.utilities.Controls;

public class proigo implements ApplicationListener {
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
	private static int PIXELS_PER_METER=15;      //how many pixels in a meter
	
	Car player1;
	Car player2;
	Ball ball;
	Goal leftGoal;
	Goal rightGoal;
	
	@Override
	public void create() {		
//		screenWidth = Gdx.graphics.getWidth();
//		screenHeight = Gdx.graphics.getHeight();
		
		screenWidth = 1400;
		screenHeight = 900;
		
		worldWidth = screenWidth / PIXELS_PER_METER;
		worldHeight = screenHeight / PIXELS_PER_METER;
		
		//worldWidth = 800;
		//worldHeight = 600;

		//Box2d World init
		Vector2 center = new Vector2(worldWidth/2, worldHeight/2);
		
		world = new World(new Vector2(0.0f, 0.0f), true);	
	    
	    this.player1 = new Car("player1", world, 2, 4,
	    		new Vector2(15f, center.y), (float) Math.PI/2, 60, 20, 120, new Controls(Input.Keys.DPAD_UP, Input.Keys.DPAD_DOWN, Input.Keys.DPAD_LEFT, Input.Keys.DPAD_RIGHT));
	    
	    this.player2 = new Car("player2", world, 2, 4,
	    		new Vector2((worldWidth -15f), center.y), (float) (Math.PI + Math.PI/2), 60, 20, 120, new Controls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D));
		
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, screenWidth, screenHeight);
	    spriteBatch = new SpriteBatch();		
										
		debugRenderer = new Box2DDebugRenderer();
	    
	    //Create Goal Objects
	    this.leftGoal = new Goal(world, 2.5f, center.y, "right");
	    this.rightGoal = new Goal(world, (worldWidth -2.5f), center.y, "left");
	    
	    float touchlineLength = (worldHeight/2) - (leftGoal.getGoalLength()/2);
	    
	    this.ball = new Ball(world, center.x, center.y);
	    
	    //outer walls
	    BoxProp wall1 = new BoxProp(world, worldWidth, 1, new Vector2 (worldWidth/2,10f)); //bottom
	    
	    BoxProp wall22 = new BoxProp(world, 1, touchlineLength, new Vector2 (7.5f, touchlineLength/2));//left
	    BoxProp wall222 = new BoxProp(world, 1, touchlineLength, new Vector2 (7.5f, worldHeight-(touchlineLength/2)));//left
	    
	    BoxProp wall3 = new BoxProp(world,  worldWidth, 1, new Vector2 (worldWidth/2,worldHeight-10f));//top
	    
	    BoxProp wall44 = new BoxProp(world, 1, touchlineLength, new Vector2 (worldWidth -7.5f, touchlineLength/2));//left
	    BoxProp wall444 = new BoxProp(world, 1, touchlineLength, new Vector2 (worldWidth -7.5f, worldHeight-(touchlineLength/2)));//left 
	 
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	@Override
	public void render() {	
	    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    // tell the camera to update its matrices.
	    camera.update();
	    
		spriteBatch.setProjectionMatrix(camera.combined);

		player1.controlCar();
		player2.controlCar();
		this.ball.update();
		
		Vector2 ballLocation = this.ball.getLocation();
		this.leftGoal.checkForGoal(ballLocation, 0f);
		this.rightGoal.checkForGoal(ballLocation, 0f);
		
		
		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
		
		world.clearForces();
		
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
}