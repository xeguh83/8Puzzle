package com.company.logic.dsws;


import com.company.logic.Coordinates;
import com.company.logic.Global;
import com.company.logic.State;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by íàïðèìåð Àíäðåé on 07.10.2015.
 */
public class TreeOfStatesForDoubleSideWidthSearch {

    private Set<State> watchedStartTree;
    private Set<State> watchedEndTree;
    private LinkedList<State> startQueue;
    private LinkedList<State> endQueue;

    public TreeOfStatesForDoubleSideWidthSearch(State rootState, State endState) {
        watchedStartTree = new HashSet<>();
        watchedStartTree.add(rootState);

        watchedEndTree = new HashSet<>();
        watchedEndTree.add(endState);

        startQueue = new LinkedList<>();
        startQueue.push(rootState);

        endQueue = new LinkedList<>();
        endQueue.push(endState);
    }

    public Set<State> getWatchedStartTree() {
        return watchedStartTree;
    }

    public LinkedList<State> getStartQueue() {
        return startQueue;
    }
    public LinkedList<State> getEndQueue() {
        return endQueue;
    }

    public void addChildrenToStartQueue(State state){
        addChildrenToQueue(state, startQueue, watchedStartTree);
    }

    public void addChildrenToEndQueue(State state) {
        addChildrenToQueue(state, endQueue, watchedEndTree);
    }

    private void addChildrenToQueue(State state, LinkedList<State> queue, Set<State> tree) {
        Coordinates zero = state.getZero();

        // Åñëè òî÷êà íå êðàéíÿÿ ëåâàÿ òî äîñòóïíû ïåðåìåùåíèÿ íàëåâî
        if (zero.getI() > 0) {
            State stateAfterLeftSwap = new State(state).swapLeft();
            if (tree.add(stateAfterLeftSwap)) {
                queue.addLast(stateAfterLeftSwap);
            }
        }

        // Åñëè òî÷êà íå êðàéíÿÿ ïðàâàÿ òî äîñòóïíû ïåðåìåùàåíèÿ íàïðàâî
        if (zero.getI() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterRightSwap = new State(state).swapRight();
            if (tree.add(stateAfterRightSwap)) {
                queue.addLast(stateAfterRightSwap);
            }
        }

        // Åñëè òî÷êà íå êðàéíÿÿ íèæíÿÿ òî äîñòóïíû ïåðåìåùåíèÿ âíèç
        if (zero.getJ() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterDownSwap = new State(state).swapDown();
            if (tree.add(stateAfterDownSwap)) {
                queue.addLast(stateAfterDownSwap);
            }
        }

        // Åñëè òî÷êà íå êðàéíÿÿ âåðõíÿÿ òî äîñòóïíû ïåðåìåùåíèÿ ââåðõ
        if (zero.getJ() > 0) {
            State stateAfterUpSwap = new State(state).swapUp();
            if (tree.add(stateAfterUpSwap)) {
                queue.addLast(stateAfterUpSwap);
            }
        }
    }


    public Set<State> getWatchedEndTree() {
        return watchedEndTree;
    }
}
