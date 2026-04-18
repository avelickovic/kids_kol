package cli.commands;

import app.AppConfig;
import servent.messeges.tree.RangeMessage;
import servent.messeges.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactorielCommand implements CLICommand{
    @Override
    public String commandName() {
        return "factoriel";
    }

    @Override
    public void execute(String args) {
        if (!AppConfig.TREE_STATE.isFinalTree()) {
            AppConfig.timestampedErrorPrint("Tree is not finalized");
            return;
        }
        if (args == null) {
            AppConfig.timestampedErrorPrint("Need a number");
            return;
        }

        int n = Integer.parseInt(args);
        int numOfNodes = AppConfig.getServentCount();
        List<String> ranges = new ArrayList<>();
        Map<Integer, Integer> sizes = AppConfig.TREE_STATE.getChildSubtreeSizes();
        int left = 1;
        for (int i = 0; i < numOfNodes; i++) {
            int step = (n - left + 1) / (numOfNodes - i); // ravnomerna podela ostatka
            int right = left + step - 1;
            ranges.add(left + "," + right);
            left = right + 1;
        }
        // ranges = ["1,2", "3,4", "5,6", "7,8", "9,10", "11,12"] za n=12, nodes=6

        // Koren uzima prvi opseg za sebe i broadcast ostatak deci
        AppConfig.TREE_STATE.setMyRange(ranges.get(0));
        int idx = 1;

        for (Integer childId : AppConfig.TREE_STATE.getChildren()) {
            int childSize = sizes.get(childId); // koliko čvorova u tom podstablu

            // Izvuci tačno childSize opsega za to dete
            List<String> childRanges = ranges.subList(idx, idx + childSize);
            String payload = String.join(";", childRanges);

            MessageUtil.sendMessage(new RangeMessage(AppConfig.getInfoById(childId), payload));
            idx += childSize;
        }
    }
}
