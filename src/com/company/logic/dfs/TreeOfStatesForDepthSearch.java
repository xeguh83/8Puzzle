package com.company.logic.dfs;


import com.company.logic.Coordinates;
import com.company.logic.Global;
import com.company.logic.State;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Класс-холдер для коллеции содержащей пройденные узлы и
 * коллекции узлов ожидающих раскрытия
 * Created by aturkin on 08.11.2015.
 */
public class TreeOfStatesForDepthSearch {

    /**
     * Коллекция просмотренных узлов
     */
    private Set<State> watchedTree;

    /**
     * Коллекция узлов ожидающих раскрытия
     */
    private LinkedList<State> stackToWatch;

    public TreeOfStatesForDepthSearch(State rootState) {
        watchedTree = new HashSet<>();
        watchedTree.add(rootState);

        stackToWatch = new LinkedList<>();
        stackToWatch.push(rootState);
    }

    public Set<State> getWatchedTree() {
        return watchedTree;
    }

    public LinkedList<State> getStackToWatch() {
        return stackToWatch;
    }

    public void addChildrenToStack(State state) {
        Coordinates zero = state.getZero();

        // Åñëè òî÷êà íå êðàéíÿÿ ëåâàÿ òî äîñòóïíû ïåðåìåùåíèÿ íàëåâî
        if (zero.getI() > 0) {
            State stateAfterLeftSwap = new State(state).swapLeft();
            if (watchedTree.add(stateAfterLeftSwap)) {
                stackToWatch.push(stateAfterLeftSwap);
            }
        }

        // Åñëè òî÷êà íå êðàéíÿÿ ïðàâàÿ òî äîñòóïíû ïåðåìåùàåíèÿ íàïðàâî
        if (zero.getI() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterRightSwap = new State(state).swapRight();
            if (watchedTree.add(stateAfterRightSwap)) {
                stackToWatch.push(stateAfterRightSwap);
            }
        }

        // Åñëè òî÷êà íå êðàéíÿÿ íèæíÿÿ òî äîñòóïíû ïåðåìåùåíèÿ âíèç
        if (zero.getJ() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterDownSwap = new State(state).swapDown();
            if (watchedTree.add(stateAfterDownSwap)) {
                stackToWatch.push(stateAfterDownSwap);
            }
        }

        // Åñëè òî÷êà íå êðàéíÿÿ âåðõíÿÿ òî äîñòóïíû ïåðåìåùåíèÿ ââåðõ
        if (zero.getJ() > 0) {
            State stateAfterUpSwap = new State(state).swapUp();
            if (watchedTree.add(stateAfterUpSwap)) {
                stackToWatch.push(stateAfterUpSwap);
            }
        }


    }






}
