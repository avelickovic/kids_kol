package app;

import servent.messeges.tree.NodeDoneMessage;
import servent.messeges.util.MessageUtil;

import java.util.*;

public class TreeState {
    private NodeType nodeType=NodeType.NONE;
    private int parentId=-1;
    private boolean treeDone=false;
    public static final Object treeLock=new Object();
    private boolean finalTree=false;

    public boolean isFinalTree() {
        return finalTree;
    }

    public void setFinalTree(boolean finalTree) {
        this.finalTree = finalTree;
    }

    private Set<Integer> children=new HashSet<>();
    private Set<Integer> unrelated=new HashSet<>();
    private Set<Integer> doneChildern=new HashSet<>();

    public Boolean checkNodeLinked() {
        List<Integer> cUu = new ArrayList<>();
        cUu.addAll(children);
        cUu.addAll(unrelated);

        if (parentId != -1 && parentId != AppConfig.myServentInfo.getId()) {
            cUu.add(parentId);
        }
        Collections.sort(cUu);
        treeDone = cUu.equals(AppConfig.myServentInfo.getNeighbors());

        if (treeDone) {
            AppConfig.timestampedStandardPrint("Node is done");
            AppConfig.timestampedStandardPrint("Parent: " + parentId);
            AppConfig.timestampedStandardPrint("Children: " + children);
            AppConfig.timestampedStandardPrint("Unrelated: " + unrelated);

            if (children.isEmpty()) {
                AppConfig.timestampedStandardPrint("I am leaf, sending NODE_DONE to parent " + parentId);
                MessageUtil.sendMessage(new NodeDoneMessage(AppConfig.getInfoById(parentId)));
            } else {
                AppConfig.timestampedStandardPrint("I am not leaf, waiting for " + children.size() + " children");
                checkAndNotifyParent();
            }
        }

        return treeDone;
    }
    public void checkAndNotifyParent() {
        if (!treeDone) return;

        if (isChildsDone()) {
            boolean isRoot = (parentId == AppConfig.myServentInfo.getId());
            if (isRoot) {
                AppConfig.timestampedStandardPrint("All nodes done, tree is finalized!");
                finalTree = true;
            } else {
                AppConfig.timestampedStandardPrint("All children done, sending NODE_DONE to parent " + parentId);
                MessageUtil.sendMessage(new NodeDoneMessage(AppConfig.getInfoById(parentId)));
            }
        }
    }
    public void addDoneChild(int childId){
        doneChildern.add(childId);
    }
    public boolean isChildsDone(){
        return doneChildern.containsAll(children);
    }
    public Set<Integer> getDoneChildern() {
        return doneChildern;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isTreeDone() {
        return treeDone;
    }

    public void setTreeDone(boolean treeDone) {
        this.treeDone = treeDone;
    }

    public Set<Integer> getChildren() {
        return children;
    }

    public void setChildren(Set<Integer> children) {
        this.children = children;
    }

    public Set<Integer> getUnrelated() {
        return unrelated;
    }

    public void setUnrelated(Set<Integer> unrelated) {
        this.unrelated = unrelated;
    }
}
