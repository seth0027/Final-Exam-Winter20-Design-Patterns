package lissajous.entity;

import lissajous.entity.property.Entity;
import lissajous.entity.property.Sprite;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 * @version April 17, 2020
 */
public class GenericEntity implements Entity{

	protected Sprite sprite;

	public GenericEntity(){
	}

	public GenericEntity( Sprite sprite){
		this.sprite = sprite;
	}

	@Override
	public void update(){
		// tree has no special update behavior
	}

	@Override
	public boolean isDrawable(){
		return sprite!=null;
	}

	@Override
	public Sprite getDrawable(){
		return sprite;
	}

}
