package com.company.logic;

/**
 * Created by например Андрей on 04.10.2015.
 */
public class Problem {

    private State startState;
    private State endState;

    public Problem(State startState, State endState) {
        this.startState = startState;
        this.endState = endState;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public State getEndState() {
        return endState;
    }

    public void setEndState(State endState) {
        this.endState = endState;
    }
}
