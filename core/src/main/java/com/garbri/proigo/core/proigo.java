package com.garbri.proigo.core;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gabri.proigo.core.objects.Goal;

public class proigo implements ApplicationListener {
private long lastRender;
	
	public static final int STEER_NONE=0;
	public static final int STEER_RIGHT=1;
	public static final int STEER_LEFT=2;

	public static final int ACC_NONE=0;
	public static final int ACC_ACCELERATE=1;
	public static final int ACC_BRAKE=2;
	
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
	
	Car car;
	
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
		world = new World(new Vector2(0.0f, 0.0f), true);	
	    
	    this.car = new Car(world, 2, 4,
	    		new Vector2(10, 10), (float) Math.PI, 60, 20, 120);
		
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, screenWidth, screenHeight);
	    spriteBatch = new SpriteBatch();		
										
		debugRenderer = new Box2DDebugRenderer();

	    Vector2 center = new Vector2(worldWidth/2, worldHeight/2);
	    
	    
	    Goal leftGoal = new Goal(world, 2.5f, center.y, "right");
	    Goal rightGoal = new Goal(world, (worldWidth -2.5f), center.y, "left");
	    
	    float touchline = (worldHeight/2) -3.5f;
	    
	    //outer walls
	    BoxProp wall1 = new BoxProp(world, worldWidth, 1, new Vector2 (worldWidth/2,10f)); //bottom
	    
	    //BoxProp wall2 = new BoxProp(world, 1, worldHeight-2, new Vector2 (7.5f, worldHeight/2));//left
	    BoxProp wall22 = new BoxProp(world, 1, touchline, new Vector2 (7.5f, touchline/2));//left
	    BoxProp wall222 = new BoxProp(world, 1, touchline, new Vector2 (7.5f, worldHeight-(touchline/2)));//left
	    
	    
	    BoxProp wall3 = new BoxProp(world,  worldWidth, 1, new Vector2 (worldWidth/2,worldHeight-10f));//top
	    BoxProp wall4 = new BoxProp(world, 1, worldHeight-2, new Vector2 (worldWidth-7.5f, worldHeight/2));	  //right  
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

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			car.accelerate = this.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			car.accelerate = this.ACC_BRAKE;
		else
			car.accelerate = this.ACC_NONE;
		
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			car.steer = this.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			car.steer = this.STEER_RIGHT;
		else
			car.steer = this.STEER_NONE;
		
		car.update(Gdx.app.getGraphics().getDeltaTime());
		
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