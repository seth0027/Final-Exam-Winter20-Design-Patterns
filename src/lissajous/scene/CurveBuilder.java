package lissajous.scene;

import javafx.scene.paint.Color;
import lissajous.entity.GenericEntity;
import lissajous.entity.property.Sprite;
import lissajous.entity.sprite.AbstractCurveSprite;
import lissajous.entity.sprite.SpriteFactory;

public class CurveBuilder {
    private int a;
    private int b;
    private double xShift;
    private double yShift;
    private double width;
    private double radius;
    private double increment;
    private double phaseShift;
    private Color color;
    private String spriteName;
    private static final CurveBuilder BUILDER=new CurveBuilder();


private CurveBuilder(){

}
public static CurveBuilder instance(){

    return new CurveBuilder();

}

    public CurveBuilder setRadius(double radius) {
        this.radius = radius;
        return  this;
    }
    public CurveBuilder setStepIncrement(double increment){
        this.increment=increment;
        return this;
    }

    public CurveBuilder setPhaseShiftInDegrees(double phaseShift){
    this.phaseShift=phaseShift;
    return this;
    }

    public CurveBuilder setStroke(Color color,int width){
    this.color=color;
    this.width=width;
    return this;
    }

    public CurveBuilder setPosition(double xShift,double yShift){
    this.xShift=xShift;
    this.yShift=yShift;
    return this;
    }

    public CurveBuilder setABRatio(int a,int b){
    this.a=a;
    this.b=b;
    return this;
    }

    public CurveBuilder setSpriteName(String name){
    this.spriteName=name;
            return this;
    }

    public GenericEntity build(){

        AbstractCurveSprite sprite=SpriteFactory.getSprite(spriteName);

        sprite.setFill(Color.TRANSPARENT);
        sprite.setStroke(color).setWidth(width);
        sprite.setRadiusValue(radius).setStepIncrement(increment).setPhaseShiftInDegrees(phaseShift).setPosition(xShift,yShift).setABRatio(a,b);

        GenericEntity entity=new GenericEntity(sprite);
        return entity;
    }

}
