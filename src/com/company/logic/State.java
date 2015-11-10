package com.company.logic;

import java.util.Arrays;


/**
 * Класс-холдер состояния
 * Created by aturkin on 04.10.2015.
 */
public class State {

    /**
     * Данные состояния (матрица значений)
     */
    private int[][] data;

    /**
     * Координаты нулевого (пустого) состояния
     */
    private Coordinates zero;

    /**
     * Ссылка на родительское состояние
     */
    private State parent = null;

    /**
     * Конгструктор по указанным данным и координате пустой клетки
     * @param data матрица данных
     * @param zero координата пустой клетки в матрице
     */
    public State(int[][] data, Coordinates zero) {
        this.data = new int[Global.MATRIX_SIZE][];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new int[Global.MATRIX_SIZE];
            System.arraycopy( data[i], 0, this.data[i], 0, data[i].length );
        }
        this.zero = new Coordinates(zero);
    }

    /**
     * Конструктор копирования
     * @param parent родительское состояние
     */
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

    /**
     * Переопределение equals только по матричным данным
     * @param o объект для сравнения
     * @return true если аналогичные данные в состоянии, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return Arrays.deepEquals(data, state.data);

    }

    /**
     * Переопределение строкового представления состояния по строковому представлению
     * данных в нем
     * @return строковое представление состояния
     */
    @Override
    public String toString() {
        return Arrays.deepToString(data);
    }

    /**
     * Переопределение hashcode только по данным в состоянии
     * @return хэш состояния
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }

    /**
     * Перемещение пустой клетки влево
     * @return измененное состояние
     */
    public State swapLeft() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ()][zero.getI() - 1];
        data[zero.getJ()][zero.getI() - 1] = temp;
        zero.setI(zero.getI() - 1);
        return this;
    }

    /**
     * Перемещение пустой клетки вправо
     * @return измененное состояние
     */
    public State swapRight() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ()][zero.getI() + 1];
        data[zero.getJ()][zero.getI() + 1] = temp;
        zero.setI(zero.getI() + 1);
        return this;
    }

    /**
     * Перемещение пустой клетки вниз
     * @return измененное состояние
     */
    public State swapDown() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ() + 1][zero.getI()];
        data[zero.getJ() + 1][zero.getI()] = temp;
        zero.setJ(zero.getJ() + 1);
        return this;
    }

    /**
     * Перемещение пустой клетки вверх
     * @return измененное состояние
     */
    public State swapUp() {
        int temp = data[zero.getJ()][zero.getI()];
        data[zero.getJ()][zero.getI()] = data[zero.getJ() - 1][zero.getI()];
        data[zero.getJ() - 1][zero.getI()] = temp;
        zero.setJ(zero.getJ() - 1);
        return this;
    }
}
