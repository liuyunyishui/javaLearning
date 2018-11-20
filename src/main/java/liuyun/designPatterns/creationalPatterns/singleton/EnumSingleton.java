package liuyun.designPatterns.creationalPatterns.singleton;

public enum  EnumSingleton {
    INSTANCE;

    EnumSingleton() {
        //init
    }

    public void doSomething(){
        System.out.println("do something!");
    }
}
