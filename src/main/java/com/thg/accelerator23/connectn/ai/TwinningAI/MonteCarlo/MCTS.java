package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.GameState;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Tree;

public class MCTS {
    MCTS_Selector selector = new MCTS_Selector();
    MCTS_Expander expander = new MCTS_Expander();
    MCTS_Simulator simulator = new MCTS_Simulator();
    MCTS_Updater updater = new MCTS_Updater();
    BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(10,8,4 ));

    public int MCTS_Searcher(Board board, Counter player) throws InvalidMoveException {
        int loop = 0;
        Node rootNode = new Node(board, player);
        Tree tree = new Tree(rootNode);

        long endTime = System.currentTimeMillis() + 8500;
        long currentTime = System.currentTimeMillis();


        if (isBoardEmpty(rootNode.getState().getBoard()) == 0) {
            return 4;
        } else {

            while (System.currentTimeMillis() < endTime) {
                Node promisingNode = selector.selectPromisingNode(rootNode);
                GameState boardStatus = boardAnalyser.calculateGameState(promisingNode.getState().getBoard());
                if (!boardStatus.isEnd()) {
                    promisingNode = expander.expandNode(promisingNode);
                    GameState randomPlayResult = simulator.simulateRandomGame(promisingNode);
                    updater.update(promisingNode, randomPlayResult);
                }
                System.out.println(promisingNode.getChildList().size());
            }
            Node likelyWinningNode = tree.getRoot().getChildWithHighestScore();
            tree.setRoot(likelyWinningNode);
            return likelyWinningNode.getState().getPosition().getX();
        }
    }

    public int isBoardEmpty(Board board){
        int boardEmpty = 0;
        for (int col = 0; col < board.getConfig().getWidth(); col++){
            for (int row = 0; row < board.getConfig().getHeight(); row++){
                if (board.hasCounterAtPosition(new Position(col, row))){
                    boardEmpty++;
                } else { boardEmpty = boardEmpty;}
            }
        }
        return boardEmpty;
    }

}
