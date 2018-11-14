package liuyun;

import liuyun.program.message.IMessage;
import liuyun.program.message.IMessageFactory;
import liuyun.program.message.MessageFactory;
import org.junit.Test;

public class MessageTest {

    @Test
    public void test() {
        IMessageFactory factory = MessageFactory.newMessageFactory();
        IMessage message = factory.newMessage("Chinese");
        message.printMessage();

        IMessage message2 = factory.newMessage("English");
        message2.printMessage();

        IMessage message3 = factory.newMessage("French");
        message3.printMessage();

    }
}
