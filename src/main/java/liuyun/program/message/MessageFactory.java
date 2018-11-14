package liuyun.program.message;

public class MessageFactory implements IMessageFactory {

    private static MessageFactory FACTORY = null;

    private static final String PACKAGE = "liuyun.program.message.";

    private MessageFactory() {
    }

    public static IMessageFactory newMessageFactory() {
        if (null == FACTORY) {
            synchronized (MessageFactory.class) {
                if (null == FACTORY) {
                    FACTORY = new MessageFactory();
                }
            }
        }
        return FACTORY;
    }

    @Override
    public IMessage newMessage(String countryCode) {
        IMessage message = null;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = classloader.loadClass(PACKAGE + countryCode + "Message");
            message = (IMessage) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println(countryCode + ":the message of this country is not defined.");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("build failed.");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("build failed.");
            e.printStackTrace();
        }
        //use the EnglishMessage as default Message
        if (null == message) {
            message = new EnglishMessage();
        }
        return message;
    }
}
