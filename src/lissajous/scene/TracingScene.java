package lissajous.scene;

import javafx.scene.paint.Color;
import lissajous.entity.GenericEntity;

public class TracingScene extends AbstractScene {
    @Override
    public AbstractScene createScene() {

        CurveBuilder cb=CurveBuilder.instance();

        cb.setStroke(Color.BLACK,1).setRadius(50).setStepIncrement(2).setPhaseShiftInDegrees(90).setSpriteName("tracing");

    for(int i=0;i<7;i++){

        for(int j=1;j<7;j++){
        GenericEntity entity=cb.setPosition(i*100,j*100-25).setABRatio(i,j).build();
            entities.add(entity);
        }
    }

    return this;
    }
}
