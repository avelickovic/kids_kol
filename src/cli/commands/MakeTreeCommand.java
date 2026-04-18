package cli.commands;

import app.AppConfig;
import app.NodeType;
import servent.messeges.tree.TreeQueryMessage;
import servent.messeges.util.MessageUtil;

public class MakeTreeCommand implements CLICommand{
    @Override
    public String commandName() {
        return "make_tree";
    }

    @Override
    public void execute(String args) {
        if(AppConfig.TREE_STATE.getNodeType()== NodeType.NONE) {
            AppConfig.TREE_STATE.setNodeType(NodeType.ROOT);
            AppConfig.TREE_STATE.setParentId(AppConfig.myServentInfo.getId());
            for(Integer neighbor: AppConfig.myServentInfo.getNeighbors()) {
                MessageUtil.sendMessage(new TreeQueryMessage(AppConfig.getInfoById(neighbor)));
            }

        }
        else {
            AppConfig.timestampedErrorPrint("Already in a tree, cannot make a new one");
        }


    }
}
