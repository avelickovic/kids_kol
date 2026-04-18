package app;

import java.util.*;

public class TreeState {
    private NodeType nodeType=NodeType.NONE;
    private int parentId=-1;
    private boolean treeDone=false;
    public static final Object treeLock=new Object();

    private Set<Integer> children=new HashSet<>();
    private Set<Integer> unrelated=new HashSet<>();

    public Boolean checkNodeLinked(){
        List<Integer>cUu=new ArrayList<>();

        cUu.addAll(children);
        cUu.addAll(unrelated);

        if(parentId!=-1 && parentId!=AppConfig.myServentInfo.getId()){
            cUu.add(parentId);
        }
        Collections.sort(cUu);
        treeDone=cUu.equals(AppConfig.myServentInfo.getNeighbors());
        if(treeDone){
            AppConfig.timestampedStandardPrint("Node is done");
            AppConfig.timestampedStandardPrint("Parent: "+parentId);
            AppConfig.timestampedStandardPrint("Children: "+children);
            AppConfig.timestampedStandardPrint("Unrelated:  "+unrelated);
        }
        return treeDone;
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
