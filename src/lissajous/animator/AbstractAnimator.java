package lissajous.animator;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lissajous.entity.FpsCounter;
import lissajous.entity.property.Drawable;
import lissajous.scene.AbstractScene;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 * @version April 17, 2020
 */
public abstract class AbstractAnimator extends AnimationTimer{

	protected AbstractScene map;
	private Canvas canvas;
	private FpsCounter fps;

	public AbstractAnimator(){
		fps = new FpsCounter( 10, 25);
		Drawable< ?> fpsSprite = fps.getDrawable();
		fpsSprite.setFill( Color.BLACK);
		fpsSprite.setStroke( Color.WHITE);
		fpsSprite.setWidth( 1);
	}

	public void setCanvas( Canvas canvas){
		this.canvas = canvas;
	}

	public void setMapScene( AbstractScene map){
		this.map = map;
	}

	public void clearAndFill( GraphicsContext gc, Color background){
		gc.setFill( background);
		gc.clearRect( 0, 0, canvas.getWidth(), canvas.getHeight());
		gc.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight());
	}

	@Override
	public void handle( long now){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if( map.getDrawFPS())
			fps.calculateFPS( now);
		handle( gc, now);
		if( map.getDrawFPS())
			fps.getDrawable().draw( gc);
	}

	protected abstract void handle( GraphicsContext gc, long now);
}
