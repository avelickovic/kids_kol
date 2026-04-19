package servent.handlers.factorial;

import app.FactorialShared;
import servent.handlers.MessageHandler;
import servent.messeges.Message;

import java.math.BigInteger;

public class FactorialResultHandler implements MessageHandler {
    private Message clientMessage;

    public FactorialResultHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        FactorialShared.friendsHalf.set(new BigInteger(clientMessage.getMessageText()));
    }
}
