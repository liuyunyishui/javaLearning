package liuyun.designPatterns.creationalPatterns.singleton;

public class SingletonTest {

    public static void main(String[] args) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println(singleton1==singleton2);

        BetterSingleton singleton3 = BetterSingleton.getInstance();
        BetterSingleton singleton4 = BetterSingleton.getInstance();
        System.out.println(singleton3 == singleton4);

        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
        enumSingleton.doSomething();
    }

}
