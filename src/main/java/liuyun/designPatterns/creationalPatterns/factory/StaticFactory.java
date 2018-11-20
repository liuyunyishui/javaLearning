package liuyun.designPatterns.creationalPatterns.factory;

import liuyun.designPatterns.creationalPatterns.Shape;

public class StaticFactory {
    private static final String PACKAGE = "liuyun.designPatterns.creationalPatterns.";

    public static Shape getShape(String shapeType) {
        Shape shape = null;
        try {
            ClassLoader classloader = StaticFactory.class.getClassLoader();
            Class<?> clazz = classloader.loadClass(PACKAGE + shapeType);
            shape = (Shape) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println(shapeType + ":the shape is not defined.");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return shape;
    }
}
