package com.company.logic.dsws;


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
public class TreeOfStatesForDoubleSideWidthSearch {
    /**
     * Коллекция пройденных узлов от начала
     */
    private Set<State> watchedStartTree;

    /**
     * Коллекция пройденных узлов с конца
     */
    private Set<State> watchedEndTree;

    /**
     * Очередь узлов ожидающих раскрытия от начала
     */
    private LinkedList<State> startQueue;

    /**
     * Очередь узлов ожидающих раскрытия с конца
     */
    private LinkedList<State> endQueue;

    /**
     * Конструктор
     * @param rootState корневой узел дерева поиска
     * @param endState целевой узел состояния дерева поиска
     */
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

    /**
     * Добаляет дочерние узлы текущего узла в очередь начала
     * @param state текущий узел
     */
    public void addChildrenToStartQueue(State state){
        addChildrenToQueue(state, startQueue, watchedStartTree);
    }

    /**
     * Добаляет дочерние узлы текущего узла в очередь конца
     * @param state текущий узел
     */
    public void addChildrenToEndQueue(State state) {
        addChildrenToQueue(state, endQueue, watchedEndTree);
    }

    /**
     * Общий метод добавления дочерних узлов текущего узла в указанную очередь
     * @param state текущий узел
     * @param queue указанная очередь
     * @param tree класс-холдер дерева поиска
     */
    private void addChildrenToQueue(State state, LinkedList<State> queue, Set<State> tree) {
        Coordinates zero = state.getZero();

        // Если пустая клетка не крайняя левая то доступно перемещение влево
        if (zero.getI() > 0) {
            State stateAfterLeftSwap = new State(state).swapLeft();
            if (tree.add(stateAfterLeftSwap)) {
                queue.addLast(stateAfterLeftSwap);
            }
        }

        // Если пустая клетка не крайняя правая то доступно перемещение вправо
        if (zero.getI() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterRightSwap = new State(state).swapRight();
            if (tree.add(stateAfterRightSwap)) {
                queue.addLast(stateAfterRightSwap);
            }
        }

        // Если пустая клетка не крайняя нижняя то доступно перемещение вниз
        if (zero.getJ() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterDownSwap = new State(state).swapDown();
            if (tree.add(stateAfterDownSwap)) {
                queue.addLast(stateAfterDownSwap);
            }
        }

        // Если пустая клетка не крайняя верхняя то доступно перемещение вверх
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
