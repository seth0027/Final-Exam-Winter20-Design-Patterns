package lissajous.entity.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 * @version April 17, 2020
 */
public class TracingCurveSprite extends AbstractCurveSprite{

	private int finalCount;
	private double totalSteps;

	@Override
	public TracingCurveSprite setPhaseShiftInDegrees( double phaseShift){
		this.phaseShift = Math.toRadians( phaseShift);
		return this;
	}

	@Override
	public void draw( GraphicsContext gc){
		gc.setFill( getFill());
		gc.setStroke( getStroke());
		gc.setLineWidth( getWidth());
		double x = 0, y = 0, t;
		if( totalSteps < 360){
			totalSteps += increment;
			t = Math.toRadians( totalSteps % 360);
			x = radius * Math.sin( a * t + phaseShift) + xShift;
			y = radius * Math.sin( b * t) + yShift;
			xList[count] = x;
			yList[count] = y;
			finalCount++;
			if(totalSteps>=360){
				xList[++count] = xList[0];
				yList[count] = yList[0];
				finalCount++;
			}
		}else if( count >= finalCount){
			count = 0;
		}
		gc.strokePolyline( xList, yList, finalCount);
		gc.setStroke( Color.RED);
		if(a==b){
			gc.setLineWidth( .5);
			gc.strokeLine( xList[count], 0, xList[count], 1000);
			gc.strokeLine( 0, yList[count], 1000, yList[count]);
		}
		gc.strokeOval( xList[count] - 5, yList[count++] - 5, 10, 10);
	}
}
