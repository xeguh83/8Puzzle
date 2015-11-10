package com.company.logic;

/**
 * Класс-холдер начального и конечного состояния
 * Created by aturkin on 04.10.2015.
 */
public class Problem {

    private State startState;
    private State endState;

    /**
     * Конструктор
     * @param startState начальное состояние
     * @param endState целевое состояние
     */
    public Problem(State startState, State endState) {
        this.startState = startState;
        this.endState = endState;
    }

    public State getStartState() {
        return startState;
    }

    public State getEndState() {
        return endState;
    }
}
