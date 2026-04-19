package servent.messeges.factorial;

import app.ServentInfo;
import servent.handlers.MessageHandler;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class FactorialHelpMessage extends BasicMessage {

    public FactorialHelpMessage(ServentInfo originalSenderInfo, ServentInfo receiverInfo, String messageText) {
        super(MessageType.FACTORIAL_HELP, originalSenderInfo, receiverInfo, messageText);
    }
}
