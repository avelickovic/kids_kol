package servent.handlers.factorial;

import app.AppConfig;
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
        BigInteger friendResult = new BigInteger(clientMessage.getMessageText());
        AppConfig.timestampedStandardPrint("Friend's partial result is: " + friendResult);

        boolean iAmSecond = FactorialShared.submitFriendHalf(friendResult);
        if (iAmSecond) {
            BigInteger finalResult = FactorialShared.computeResult();
            AppConfig.timestampedStandardPrint(
                    "Factorial of " + FactorialShared.getOriginalArgs() + " is: " + finalResult);
        }
    }
}
