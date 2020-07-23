package lissajous.entity.sprite;

public class SpriteFactory {

    private SpriteFactory(){

    }

    public static < T> T getSprite( String name){

        if(name==null){
            throw new NullPointerException();
        }
        switch (name){
            case "tracing":
                return (T) new TracingCurveSprite();

            case "shifting":
                return (T) new ShiftingCurveSprite();

            default:
                throw new UnsupportedOperationException();
        }

    }
}
