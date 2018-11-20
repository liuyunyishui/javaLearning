package liuyun.designPatterns.creationalPatterns.singleton;

public class BetterSingleton {

    private BetterSingleton() {
    }

    public static BetterSingleton getInstance() {
        return BetterSingletonFactory.instance;
    }

    private static class BetterSingletonFactory {
        private static BetterSingleton instance = new BetterSingleton();
    }

    public Object readResolve() {
        return getInstance();
    }

}
