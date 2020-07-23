package lissajous.entity.property;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 * @version April 17, 2020
 */
public interface Entity{

	void update();
	
	boolean isDrawable();

	Drawable< ?> getDrawable();
}
