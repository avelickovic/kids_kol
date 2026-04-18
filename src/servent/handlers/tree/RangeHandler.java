package servent.handlers.tree;

import app.AppConfig;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.tree.RangeMessage;
import servent.messeges.util.MessageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RangeHandler implements MessageHandler {

    Message clientMessage;

    public RangeHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        String[] allRanges = clientMessage.getMessageText().split(";");

        AppConfig.TREE_STATE.setMyRange(allRanges[0]);

        // Podeli deci
        Map<Integer, Integer> sizes = AppConfig.TREE_STATE.getChildSubtreeSizes();
        int idx = 1;
        for (Integer childId : AppConfig.TREE_STATE.getChildren()) {
            int childSize = sizes.get(childId);
            List<String> childRanges = Arrays.asList(allRanges).subList(idx, idx + childSize);
            String payload = String.join(";", childRanges);
            MessageUtil.sendMessage(new RangeMessage(AppConfig.getInfoById(childId), payload));
            idx += childSize;
        }

        // Izračunaj i pošalji ako je list
        compute(allRanges[0]);
    }

    private void compute(String range) {
        String[] parts = range.split(",");
        int left = Integer.parseInt(parts[0]);
        int right = Integer.parseInt(parts[1]);

        long result = 1;
        for (int i = left; i <= right; i++) {
            result *= i;
        }
        AppConfig.TREE_STATE.setMyResult(result);
        AppConfig.timestampedStandardPrint("Computed [" + left + "," + right + "] = " + result);

        if (AppConfig.TREE_STATE.getChildren().isEmpty()) {
            AppConfig.TREE_STATE.sendResultToParent();
        }
    }
}