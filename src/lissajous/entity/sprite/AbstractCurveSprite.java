package lissajous.entity.sprite;

import lissajous.entity.property.Sprite;

public abstract class AbstractCurveSprite extends Sprite{

	protected int a;
	protected int b;
	protected int count;
	protected double[] xList;
	protected double[] yList;
	protected double xShift;
	protected double yShift;
	protected double radius;
	protected double increment;
	protected double phaseShift;

	public AbstractCurveSprite(){
		xList = new double[1000];
		yList = new double[1000];
	}

	public AbstractCurveSprite setABRatio( int a, int b){
		this.a = a;
		this.b = b;
		return this;
	}

	public AbstractCurveSprite setRadiusValue( double radius){
		this.radius = radius;
		return this;
	}

	public AbstractCurveSprite setPosition( double xShift, double yShift){
		this.xShift = xShift;
		this.yShift = yShift;
		return this;
	}

	public AbstractCurveSprite setStepIncrement( double increment){
		this.increment = increment;
		return this;
	}

	public AbstractCurveSprite setPhaseShiftInDegrees( double phaseShift){
		this.phaseShift = phaseShift;
		return this;
	}

}
