package liuyun.designPatterns.creationalPatterns.factory;

import liuyun.designPatterns.creationalPatterns.Circle;
import liuyun.designPatterns.creationalPatterns.Shape;
import liuyun.designPatterns.creationalPatterns.Square;

public class ShapeFactory {

    public Shape getCircle() {
        return new Circle();
    }

    public Shape getSquare() {
        return new Square();
    }

}
