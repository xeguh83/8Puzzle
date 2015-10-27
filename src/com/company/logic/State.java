package com.company.logic;

import java.util.Arrays;


/**
 * Created by например Андрей on 04.10.2015.
 */
public class State {

    private int[][] data;
    private Coordinates zero;
    private State parent = null;


    public State(int[][] data, Coordinates zero) {
        this.data = new int[Global.MATRIX_SIZE][];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new int[Global.MATRIX_SIZE];
            System.arraycopy( data[i], 0, this.data[i], 0, data[i].length );
        }
        this.zero = new Coordinates(zero);
    }

    public State(State parent) {
        this(parent.getData(), parent.getZero());
        this.parent = parent;
    }

    public State getParent() {
        return parent;
    }

    public int[][] getData() {
        return data;
    }

    public Coordinates getZero() {
        return zero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return Arrays.deepEquals(data, state.data);

    }

    @Override
    public String toString() {
        return Arrays.deepToString(data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }

    public State swapLeft() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ()][zero.getI() - 1];
        data[zero.getJ()][zero.getI() - 1] = temp;
        zero.setI(zero.getI() - 1);
        return this;
    }

    public State swapRight() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ()][zero.getI() + 1];
        data[zero.getJ()][zero.getI() + 1] = temp;
        zero.setI(zero.getI() + 1);
        return this;
    }

    public State swapDown() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ() + 1][zero.getI()];
        data[zero.getJ() + 1][zero.getI()] = temp;
        zero.setJ(zero.getJ() + 1);
        return this;
    }

    public State swapUp() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ() - 1][zero.getI()];
        data[zero.getJ() - 1][zero.getI()] = temp;
        zero.setJ(zero.getJ() - 1);
        return this;
    }
}
