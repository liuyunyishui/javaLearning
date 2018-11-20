package liuyun.designPatterns.creationalPatterns.abstractFactory;

import liuyun.designPatterns.creationalPatterns.Circle;
import liuyun.designPatterns.creationalPatterns.Shape;

public class CircleFactory implements ShapeFactory {

    @Override
    public Shape getShape() {
        return new Circle();
    }
}
