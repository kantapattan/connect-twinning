package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MCTS_Selector {

    public double UCTValue(int nodeVisitCount, int nodeWinCount, int parentVisitCount) {
//        System.out.println("In UCTValue");
        if (nodeVisitCount == 0) {
            return Integer.MAX_VALUE;}
        return (nodeWinCount / (double)nodeVisitCount) + Math.sqrt(2) * Math.sqrt(Math.log(parentVisitCount) / (double)nodeVisitCount);
    }

    public static double uctValue(
            int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return ((double) nodeWinScore / (double) nodeVisit)
                + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    public Node findNode_UCT(Node node) {
//        System.out.println("In findNode_UCT");
        int parentVisitCount = node.getState().getVisitCount();
        int nodeVisitCount = node.getState().getVisitCount();
        int nodeWinCount = node.getState().getWinCount();

//        List<Double> uctValues = new ArrayList<Double>();
//        for (int i = 0; i < node.getChildList().size(); i++){
//            double uctValue = UCTValue(nodeVisitCount,nodeWinCount,parentVisitCount);
//            uctValues.add(uctValue);
//        }
//
//        double maxUCT = Collections.max(uctValues);
//        int index = uctValues.indexOf(maxUCT);
//         return node.getChildList().get(index);


//        return Collections.max(
//                node.getChildList(),
//                Comparator.comparing(c -> uctValue(c.getParent().getState().getVisitCount(),
//                        c.getState().getWinCount(), c.getState().getVisitCount())));


//        node = Collections.max(
//                node.getChildList(), Comparator.comparing(currentNode -> UCTValue(nodeVisitCount, nodeWinCount, parentVisitCount)));


//        double uctValue = UCTValue(nodeVisitCount,nodeWinCount,parentVisitCount);
//        System.out.println(uctValue);


        return Collections.max(
                node.getChildList(),
                Comparator.comparing(currentNode -> UCTValue(nodeVisitCount, nodeWinCount, parentVisitCount)));
////        System.out.println("Complete comparison");
////        System.out.println("End of findNode_UCT");
//        return node;
    }

    public Node selectPromisingNode(Node node) {
//        System.out.println("In Select Promising Node");
        Node childNode = node;
        while (node.getChildList().size() != 0) {
//            System.out.println("In select promising node while loop");
            childNode = findNode_UCT(node);
//            System.out.println("End of select promising node while loop");
            return childNode;
        }
//        System.out.println("Finished selection");
        return childNode;
    }



}
