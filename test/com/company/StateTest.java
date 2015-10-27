package com.company;

import com.company.logic.Coordinates;
import com.company.logic.State;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by �������� ������ on 10.10.2015.
 */
public class StateTest {

    @Test
    public void shouldSwapLeftWhenCalledCentre() throws Exception {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterLeftSwap = new State(fixture).swapLeft();
        int[][]  dataForCheck = new int[][] {
                {1,2,3},
                {0,4,6},
                {7,8,9}
        };
        State swapedForCheck = new State(dataForCheck, new Coordinates(0, 1));
        assertEquals(swapedForCheck, afterLeftSwap);
    }

    @Test
    public void shouldSwapRightWhenCalledCentre() throws Exception {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterLeftSwap = new State(fixture).swapRight();
        int[][]  dataForCheck = new int[][] {
                {1,2,3},
                {4,6,0},
                {7,8,9}
        };
        State swapedForCheck = new State(dataForCheck, new Coordinates(2, 1));
        assertEquals(swapedForCheck, afterLeftSwap);
    }

    @Test
    public void shouldSwapDownWhenCalledCentre() throws Exception {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterLeftSwap = new State(fixture).swapDown();
        int[][]  dataForCheck = new int[][] {
                {1,2,3},
                {4,8,6},
                {7,0,9}
        };
        State swapedForCheck = new State(dataForCheck, new Coordinates(1, 2));
        assertEquals(swapedForCheck, afterLeftSwap);
    }

    @Test
    public void shouldSwapUpWhenCalledCentre() throws Exception {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterLeftSwap = new State(fixture).swapUp();
        int[][]  dataForCheck = new int[][] {
                {1,0,3},
                {4,2,6},
                {7,8,9}
        };
        State swapedForCheck = new State(dataForCheck, new Coordinates(1, 2));
        assertEquals(swapedForCheck, afterLeftSwap);
    }

    @Test
    public void shouldUpdateCoordinatesWhenSwapLeft() {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterLeftSwap = new State(fixture).swapLeft();
        assertEquals(new Coordinates(0,1), afterLeftSwap.getZero());
    }

    @Test
    public void shouldUpdateCoordinatesWhenSwapRight() {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterRightSwap = new State(fixture).swapRight();
        assertEquals(new Coordinates(2, 1), afterRightSwap.getZero());
    }

    @Test
    public void shouldUpdateCoordinatesWhenSwapDown() {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterDownSwap = new State(fixture).swapDown();
        assertEquals(new Coordinates(1, 2), afterDownSwap.getZero());
    }

    @Test
    public void shouldUpdateCoordinatesWhenSwapUp() {
        int[][]  data = new int[][] {
                {1,2,3},
                {4,0,6},
                {7,8,9}
        };
        State fixture = new State(data, new Coordinates(1, 1));
        State afterUpSwap = new State(fixture).swapUp();
        assertEquals(new Coordinates(1, 0), afterUpSwap.getZero());
    }
}