package liuyun.designPatterns.creationalPatterns.abstractFactory;

import liuyun.designPatterns.creationalPatterns.Shape;
import liuyun.designPatterns.creationalPatterns.Square;

public class SquareFactory implements ShapeFactory {

    @Override
    public Shape getShape() {
        return new Square();
    }
}
