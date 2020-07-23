package lissajous.entity.sprite;

import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 * @version April 17, 2020
 */
public class ShiftingCurveSprite extends AbstractCurveSprite{

	@Override
	public void draw( GraphicsContext gc){
		gc.setFill( getFill());
		gc.setStroke( getStroke());
		gc.setLineWidth( getWidth());
		double x = 0, y = 0, t;
		for( int i=0; i < 360; i++){
			t = Math.toRadians( i);
			x = radius * Math.sin( a * t + Math.toRadians( phaseShift)) + xShift;
			y = radius * Math.sin( b * t) + yShift;
			xList[count] = x;
			yList[count++] = y;
		}
		xList[count] = xList[0];
		yList[count++] = yList[0];
		phaseShift+=increment;
		gc.strokePolyline( xList, yList, count);
		count=0;
	}
}
