package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class ResultMessage extends BasicMessage {
    public ResultMessage( ServentInfo receiver, String messageText) {
        super(MessageType.RESULT, AppConfig.myServentInfo, receiver, messageText);
    }
}
