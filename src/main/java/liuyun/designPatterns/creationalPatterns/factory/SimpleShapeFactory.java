package liuyun.designPatterns.creationalPatterns.factory;

import liuyun.designPatterns.creationalPatterns.Circle;
import liuyun.designPatterns.creationalPatterns.Shape;
import liuyun.designPatterns.creationalPatterns.Square;

public class SimpleShapeFactory {

    public Shape getShape(String shapeType) {
        if (null == shapeType) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("Circle")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("Square")) {
            return new Square();
        } else {
            System.out.println("the shape type is error!");
            return null;
        }
    }
}
