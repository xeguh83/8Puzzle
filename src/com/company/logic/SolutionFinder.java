package com.company.logic;

import com.company.logic.astar.TreeOfStatesForAStarDisplacement;
import com.company.logic.dfs.TreeOfStatesForDepthSearch;
import com.company.logic.dsws.TreeOfStatesForDoubleSideWidthSearch;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Класс осуществляющий поиск по выбранной стратегии
 * Created by aturkin on 04.10.2015.
 */
public class SolutionFinder {

    private long step = 0;

    /**
     * Перенаправляет исполнение на алгоритм по выбранной стратегии
     * @param problem проблематика (начальное и конечное состояние)
     * @param strategy строковое представление стратегии
     * @return список состояний составляющих решение проблемы
     * @throws Exception при ошибке указания стратегии
     */
    public LinkedList<State> generalSearch(Problem problem, String strategy) throws Exception {
        if (strategy.equalsIgnoreCase("dfs")) {
            return depthFirstSearch(problem);
        } else if (strategy.equalsIgnoreCase("dsws")) {
            return doubleSideWidthSearch(problem);
        } else if (strategy.equalsIgnoreCase("astardisp")) {
            return aStarDisplacementSearch(problem);
        } else if (strategy.equalsIgnoreCase("astarmanh")) {
            return aStarManhettenSearch(problem);
        } else {
            throw new Exception("No correct strategy found");
        }
    }

    /**
     * Поиск решение методом А* с оценкой манхеттоновских расстояний
     * @param problem проблематика (начальное и конечное состояние)
     * @return список состояний составляющих решение проблемы
     */
    private LinkedList<State> aStarManhettenSearch(Problem problem) {
        TreeOfStatesForAStarDisplacement tree = new TreeOfStatesForAStarDisplacement(problem.getStartState());
        while (tree.getListToWatch().size() != 0) {
            List<State> listToWatch = tree.getListToWatch();
            step++;
            int bestChoiceIndex = -1;
            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i < listToWatch.size(); i++) {
                if (listToWatch.get(i).equals(problem.getEndState())) {
                    return createSolutionList(listToWatch.get(i));
                }
                int currentCost = getManhettenCost(listToWatch.get(i), problem);
                if (currentCost < minCost) {
                    minCost = currentCost;
                    bestChoiceIndex = i;
                }
            }
            State bestChoiceState = listToWatch.get(bestChoiceIndex);
            listToWatch.remove(bestChoiceIndex);
            tree.addChildrenToList(bestChoiceState);
        }
        return new LinkedList<>();
    }

    /**
     * Оценивает стоимость по манхеттоновскому расстоянию
     * @param state текущее состояние
     * @param problem проблематика (начальное и конечное состояние)
     * @return стоимость пути по манхеттоновскому расстоянию
     */
    private int getManhettenCost(State state, Problem problem) {
        int dispCount = 0;
        int[][] currentStateData = state.getData();
        int[][] endStateData = problem.getEndState().getData();
        for (int i = 0; i < currentStateData.length; i++) {
            for (int j = 0; j < currentStateData[i].length; j++) {
                if (currentStateData[i][j] != endStateData[i][j]) {
                    dispCount = dispCount + manhettenPrise(state, problem, i ,j);
                }
            }
        }
        return dispCount + getPassedWay(state);
    }

    /**
     * Расчет манхеттоновского расстояния
     * @param state текущее состояние
     * @param problem проблематика (начальное и конечное состояние)
     * @param i координата
     * @param j координата
     * @return стоимость манхеттоновского состояния
     */
    private int manhettenPrise(State state, Problem problem, int i, int j) {
        for (int k = 0; k < problem.getEndState().getData().length; k++) {
            for (int l = 0; l < problem.getEndState().getData()[k].length; l++) {
                if (problem.getEndState().getData()[k][l] == state.getData()[i][j]) {
                    return Math.abs(i - k) + Math.abs(j - l);
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Поиск решения методом А* по несоответствию значения клеток
     * @param problem проблематика (начальное и конечное состояние)
     * @return список состояний составляющих решение проблемы
     */
    private LinkedList<State> aStarDisplacementSearch(Problem problem) {
        TreeOfStatesForAStarDisplacement tree = new TreeOfStatesForAStarDisplacement(problem.getStartState());
        while (tree.getListToWatch().size() != 0) {
            List<State> listToWatch = tree.getListToWatch();
            step++;
            int bestChoiceIndex = -1;
            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i < listToWatch.size(); i++) {
                if (listToWatch.get(i).equals(problem.getEndState())) {
                    return createSolutionList(listToWatch.get(i));
                }
                int currentCost = getCost(listToWatch.get(i), problem);
                if (currentCost < minCost) {
                    minCost = currentCost;
                    bestChoiceIndex = i;
                }
            }
            State bestChoiceState = listToWatch.get(bestChoiceIndex);
            listToWatch.remove(bestChoiceIndex);
            tree.addChildrenToList(bestChoiceState);
        }
        return new LinkedList<>();
    }

    /**
     * Определение цены состояния
     * @param state текущее состояние
     * @param problem проблематика (начальное и конечное состояние)
     * @return стоимость несоответствия состояния
     */
    private int getCost(State state, Problem problem) {
        int dispCount = 0;
        int[][] currentStateData = state.getData();
        int[][] endStateData = problem.getEndState().getData();
        for (int i = 0; i < currentStateData.length; i++) {
            for (int j = 0; j < currentStateData[i].length; j++) {
                if (currentStateData[i][j] != endStateData[i][j]) {
                    dispCount++;
                }
            }
        }
        return dispCount + getPassedWay(state);
    }

    /**
     * Определение пройденного состояния из текущего
     * @param state текущее состояние
     * @return пройденный путь
     */
    private int getPassedWay(State state) {
        int passedWay = 0;
        while (state.getParent() != null) {
            state = state.getParent();
            passedWay++;
        }
        return passedWay;
    }

    /**
     * Создание списка состояний составляющих решение проблемы
     * @param state состояние
     * @return список состояний решения
     */
    private LinkedList<State> createSolutionList(State state) {
        LinkedList<State> solutionList = new LinkedList<>();
        solutionList.push(state);
        while (state.getParent() != null) {
            state = state.getParent();
            solutionList.push(state);
        }
        return solutionList;
    }

    /**
     * Поиск решения методом поиска в глубину
     * @param problem проблематика (начальное и конечное состояние)
     * @return список состояний составляющих решение проблемы
     */
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

    /**
     * Поиск решения методом двустороннего поиска в ширину
     * @param problem проблематика (начальное и конечное состояние)
     * @return список состояний составляющих решение проблемы
     */
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

    /**
     * Создание списка-решения для двустороннего поиска
     * @param crossedState состояние пересечения начального и конечного деревьев
     * @param problem проблематика (начальное и конечное состояние)
     * @param tree класс-холдер дерева
     * @return список состояний составляющих решение проблемы
     */
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

    /**
     * Создание склеиного списка решения для двустороннего поиска в ширину
     * @param solutionList половина списка решения
     * @param tree класс-холдер дерева
     * @return * @return список состояний составляющих решение проблемы
     */
    private LinkedList<State> getConcatenatedSolution(LinkedList<State> solutionList, Set<State> tree) {
        State state = getEqualNodeInCollection(solutionList.getLast(), tree);
        while (state.getParent() != null) {
            state = state.getParent();
            solutionList.addLast(state);
        }
        return solutionList;
    }


    /**
     * Поиск аналогичного состояния по дереву состояний
     * @param crossedState состояние для поиска аналога
     * @param watchedTree дерево для поиска
     * @return состояние аналогичное переданному
     */
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
