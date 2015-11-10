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

    /**
     * Конструктор
     * @param rootState корневой узел дерева
     */
    public TreeOfStatesForDepthSearch(State rootState) {
        watchedTree = new HashSet<>();
        watchedTree.add(rootState);

        stackToWatch = new LinkedList<>();
        stackToWatch.push(rootState);
    }

    /**
     * Геттер коллекции ожидающей просмотра
     * @return
     */
    public LinkedList<State> getStackToWatch() {
        return stackToWatch;
    }

    /**
     * Добавляет дочерние узлы в коллекцию узлов ожидающих просмотра.
     * Дочерние узлы - это все возможные состояния после перехода из переданного.
     * Узлы добавляются в коллекцию ожидающих только в том случае, если их еще нет
     * в коллекции просмотренных узлов.
     * @param state переданное состояние (узел) для порождение дочерних
     */
    public void addChildrenToStack(State state) {
        Coordinates zero = state.getZero();

        // Если пустая клетка не крайняя левая то доступно перемещение влево
        if (zero.getI() > 0) {
            State stateAfterLeftSwap = new State(state).swapLeft();
            if (watchedTree.add(stateAfterLeftSwap)) {
                stackToWatch.push(stateAfterLeftSwap);
            }
        }

        // Если пустая клетка не крайняя правая то доступно перемещение вправо
        if (zero.getI() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterRightSwap = new State(state).swapRight();
            if (watchedTree.add(stateAfterRightSwap)) {
                stackToWatch.push(stateAfterRightSwap);
            }
        }

        // Если пустая клетка не крайняя нижняя то доступно перемещение вниз
        if (zero.getJ() < (Global.MATRIX_SIZE - 1)) {
            State stateAfterDownSwap = new State(state).swapDown();
            if (watchedTree.add(stateAfterDownSwap)) {
                stackToWatch.push(stateAfterDownSwap);
            }
        }

        // Если пустая клетка не крайняя верхняя то доступно перемещение вверх
        if (zero.getJ() > 0) {
            State stateAfterUpSwap = new State(state).swapUp();
            if (watchedTree.add(stateAfterUpSwap)) {
                stackToWatch.push(stateAfterUpSwap);
            }
        }


    }






}
