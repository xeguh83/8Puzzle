package com.company.logic;

import com.company.logic.astardisp.TreeOfStatesForAStarDisplacement;
import com.company.logic.dfs.TreeOfStatesForDepthSearch;
import com.company.logic.dsws.TreeOfStatesForDoubleSideWidthSearch;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Created by �������� ������ on 04.10.2015.
 */
public class SolutionFinder {

    private long step = 0;

    public LinkedList<State> generalSearch(Problem problem, String strategy) throws Exception {
        if (strategy.equalsIgnoreCase("dfs")) {
            return depthFirstSearch(problem);
        } else if (strategy.equalsIgnoreCase("dsws")) {
            return doubleSideWidthSearch(problem);
        } else if (strategy.equalsIgnoreCase("astardisp")) {
            return aStarDisplacementSearch(problem);
        } else {
            throw new Exception("No correct strategy found");
        }
    }

    private LinkedList<State> aStarDisplacementSearch(Problem problem) {
        TreeOfStatesForAStarDisplacement tree = new TreeOfStatesForAStarDisplacement(problem.getStartState());
        while (tree.getList().size() != 0) {
            List<State> listToWatch = tree.getList();
            step++;
            int bestChoiceIndex = -1;
            int minDisplacement = Integer.MAX_VALUE;
            for (int i = 0; i < listToWatch.size(); i++) {
                if (listToWatch.get(i).equals(problem.getEndState())) {
                    return createSolutionList(listToWatch.get(i));
                }
                int currentDispCount = displacementCount(listToWatch.get(i));
                if (currentDispCount < minDisplacement) {
                    minDisplacement = currentDispCount;
                    bestChoiceIndex = i;
                }
            }
            State bestChoiceState = listToWatch.get(bestChoiceIndex);
            listToWatch.clear();
            tree.addChildrenToList(bestChoiceState);
        }
        return null;
    }

    private int displacementCount(State state) {
        //TODO Реализовать подсчет количества чисел не на своих позициях
        return 0;
    }

    private LinkedList<State> createSolutionList(State state) {
        LinkedList<State> solutionList = new LinkedList<>();
        solutionList.push(state);
        while (state.getParent() != null) {
            state = state.getParent();
            solutionList.push(state);
        }
        return solutionList;
    }

    private LinkedList<State> depthFirstSearch(Problem problem) {
        TreeOfStatesForDepthSearch tree = new TreeOfStatesForDepthSearch(problem.getStartState());
        while (tree.getStackToWatch().size() != 0) {
            State currentState = tree.getStackToWatch().poll();
            step++;
            if (currentState.equals(problem.getEndState())) {
                LinkedList<State> solutionList = new LinkedList<>();
                solutionList.push(currentState);
                while (currentState.getParent() != null) {
                    currentState = currentState.getParent();
                    solutionList.push(currentState);
                }
                return solutionList;
            }
            tree.addChildrenToStack(currentState);
        }
        return new LinkedList<State>();
    }

    private LinkedList<State> doubleSideWidthSearch(Problem problem) {
        TreeOfStatesForDoubleSideWidthSearch tree = new TreeOfStatesForDoubleSideWidthSearch(problem.getStartState(), problem.getEndState());
        while ((tree.getStartQueue().size() != 0) || (tree.getEndQueue().size() != 0)) {
            step++;
            if (!tree.getStartQueue().isEmpty()){
                State currentStateStartQueue = tree.getStartQueue().poll();
                if (tree.getWatchedEndTree().contains(currentStateStartQueue)) {
                    return getSolutionList(currentStateStartQueue, problem, tree);
                }
                tree.addChildrenToStartQueue(currentStateStartQueue);
            }
            if (!tree.getEndQueue().isEmpty()){
                State currentStateEndQueue = tree.getEndQueue().poll();
                if (tree.getWatchedStartTree().contains(currentStateEndQueue)) {
                    return getSolutionList(currentStateEndQueue, problem, tree);
                }
                tree.addChildrenToEndQueue(currentStateEndQueue);
            }
        }
        return new LinkedList<State>();
    }

    private LinkedList<State> getSolutionList(State crossedState, Problem problem, TreeOfStatesForDoubleSideWidthSearch tree) {

        if (problem.getStartState().equals(problem.getEndState())) {
            LinkedList<State> list = new LinkedList<>();
            list.add(crossedState);
            return list;
        }

        LinkedList<State> solutionList = new LinkedList<>();
        solutionList.push(crossedState);
        while (crossedState.getParent() != null) {
            crossedState = crossedState.getParent();
            solutionList.push(crossedState);
        }

        if (crossedState.equals(problem.getStartState())) {
            //finding crossed in endCollection
            return getConcatenatedSolution(solutionList, tree.getWatchedEndTree());
        } else {
            //finding crossed in startCollection
            return getConcatenatedSolution(solutionList, tree.getWatchedStartTree());
        }
    }

    private LinkedList<State> getConcatenatedSolution(LinkedList<State> solutionList, Set<State> tree) {
        State state = getEqualNodeInCollection(solutionList.getLast(), tree);
        while (state.getParent() != null) {
            state = state.getParent();
            solutionList.addLast(state);
        }
        return solutionList;
    }


    private State getEqualNodeInCollection(State crossedState, Set<State> watchedTree) {
        Iterator<State> iterator = watchedTree.iterator();
        while (iterator.hasNext()) {
            State state = iterator.next();
            if (state.equals(crossedState)) {
                return state;
            }
        }
        return null;
    }


    public long getStep(){
        return step;
    }

}
