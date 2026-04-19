package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class FactorielRequestMessage extends BasicMessage {
    public FactorielRequestMessage(ServentInfo receiver, String number) {
        super(MessageType.FACTORIEL_REQUEST, AppConfig.myServentInfo, receiver, number);
    }
}
