package lissajous.scene;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lissajous.entity.property.Entity;

public abstract class AbstractScene{

	protected List< Entity> entities;
	protected BooleanProperty drawFPS;
	protected Entity background;

	public AbstractScene(){
		entities = new ArrayList<>( 100);
		drawFPS = new SimpleBooleanProperty( false);
	}

	public BooleanProperty drawFPSProperty(){
		return drawFPS;
	}

	public boolean getDrawFPS(){
		return drawFPS.get();
	}

	public Entity getBackground(){
		return background;
	}

	public List< Entity> entities(){
		return entities;
	}
	
	public abstract AbstractScene createScene();
}
