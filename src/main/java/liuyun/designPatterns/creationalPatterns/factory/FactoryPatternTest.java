package liuyun.designPatterns.creationalPatterns.factory;


import liuyun.designPatterns.creationalPatterns.Shape;

public class FactoryPatternTest {

    public static void main(String[] args) {
        SimpleShapeFactory simpleShapeFactory = new SimpleShapeFactory();

        //get the Circle object, and call its draw method
        Shape circle = simpleShapeFactory.getShape("Circle");
        circle.draw();

        //get the Square object, and call its draw method
        Shape square = simpleShapeFactory.getShape("Square");
        square.draw();


        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle2 = shapeFactory.getCircle();
        circle2.draw();


        Shape circle3 = StaticFactory.getShape("Circle");
        circle3.draw();
    }
}
