package liuyun.designPatterns.creationalPatterns.abstractFactory;

import liuyun.designPatterns.creationalPatterns.Shape;

public class AbstractFactoryTest {

    public static void main(String[] args) {

//        ShapeFactory factory = new CircleFactory();
        ShapeFactory factory = new SquareFactory();


        Shape shape = factory.getShape();
        shape.draw();

    }
}
