package app;

import servent.messeges.tree.NodeDoneMessage;
import servent.messeges.tree.ResultMessage;
import servent.messeges.util.MessageUtil;

import java.util.*;

public class TreeState {
    private NodeType nodeType=NodeType.NONE;
    private int parentId=-1;
    private boolean treeDone=false;
    public static final Object treeLock=new Object();
    private boolean finalTree=false;
    private String myRange;
    private long myResult = 1;
    private List<Long> childResults = new ArrayList<>();
    private int expectedChildren = 0;

    public boolean isFinalTree() {
        return finalTree;
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
                MessageUtil.sendMessage(new NodeDoneMessage(AppConfig.getInfoById(parentId),1));
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
                finalTree = true;
                AppConfig.timestampedStandardPrint("Tree finalized! Total nodes: " + subtreeSize);
            } else {
                AppConfig.timestampedStandardPrint("All children done, sending NODE_DONE to parent " + parentId + " with subtree size " + subtreeSize);
                MessageUtil.sendMessage(
                        new NodeDoneMessage(AppConfig.getInfoById(parentId), subtreeSize));
            }
        }
    }

    private int subtreeSize = 1;
    private Map<Integer, Integer> childSubtreeSizes = new HashMap<>();

    public void addChildSubtreeSize(int childId, int size) {
        childSubtreeSizes.put(childId, size);
        subtreeSize += size;
    }

    public int getSubtreeSize() { return subtreeSize; }
    public Map<Integer, Integer> getChildSubtreeSizes() { return childSubtreeSizes; }

    public void sendResultToParent() {
        long total = myResult;
        for (long r : childResults) {
            total *= r;
        }

        boolean isRoot = (parentId == AppConfig.myServentInfo.getId());
        if (isRoot) {
            AppConfig.timestampedStandardPrint("FINAL RESULT: " + total);
        } else {
            MessageUtil.sendMessage(
                    new ResultMessage(AppConfig.getInfoById(parentId), String.valueOf(total)));
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

    public String getMyRange() {
        return myRange;
    }


    public void setMyRange(String myRange) {
        this.myRange = myRange;
        // postavi koliko dece cekamo
        this.expectedChildren = children.size();
    }

    public long getMyResult() {
        return myResult;
    }

    public void setMyResult(long myResult) {
        this.myResult = myResult;
    }



    public void setChildResults(List<Long> childResults) {
        this.childResults = childResults;
    }

    public int getExpectedChildren() {
        return expectedChildren;
    }
    public void setSubtreeSize(int subtreeSize) {
        this.subtreeSize = subtreeSize;
    }

    public void setChildSubtreeSizes(Map<Integer, Integer> childSubtreeSizes) {
        this.childSubtreeSizes = childSubtreeSizes;
    }
    public void addChildResult(long r) { childResults.add(r); }
    public List<Long> getChildResults() { return childResults; }

    public void setExpectedChildren(int n) { this.expectedChildren = n; }
    public boolean allChildResultsReceived() {
        return childResults.size() == expectedChildren;
    }
}
