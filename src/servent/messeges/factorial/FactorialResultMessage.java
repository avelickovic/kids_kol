package servent.messeges.factorial;

import app.FactorialShared;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class FactorialResultMessage extends BasicMessage {
    public FactorialResultMessage( ServentInfo originalSenderInfo, ServentInfo receiverInfo, String messageText) {
        super(MessageType.FACTORIAL_RESULT, originalSenderInfo, receiverInfo, messageText);
    }
}
