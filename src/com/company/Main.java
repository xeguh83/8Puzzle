package com.company;

import com.company.logic.*;
import java.util.LinkedList;

/**
 * Класс запускающий приложение
 * @author aturkin
 */
public class Main {

    public static void main(String[] args) throws Exception {

        JSONLoader loader = new JSONLoader();
        String strategy = loader.getStrategy();
        int[][] startData = loader.getStartData();
        int[][] endData = loader.getEndData();
        State startState = new State(startData, new Coordinates(startData));
        //               {6, 0, 8},
        //               {5, 2, 1},
        //              {4, 3, 7}


        State endState = new State(endData, new Coordinates(endData));
        //                {1, 2, 3},
        //                {8, 0, 4},
        //                {7, 6, 5}


        Problem problem = new Problem(startState, endState);

        long startTime = System.nanoTime();
        SolutionFinder solutionFinder = new SolutionFinder();
        LinkedList<State> solution = solutionFinder.generalSearch(problem, strategy);
        if (solution.isEmpty()) {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("There is no solution for this task. It takes " + solutionFinder.getStep() + " steps to find");
            System.out.println("It takes " + (duration / 1000000) + "ms");
        } else {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("Has solution with " + solution.size() + " steps. It takes " + solutionFinder.getStep() + " steps to find");
            System.out.println("It takes " + (duration / 1000000) + "ms");
            if (solution.peek().equals(problem.getStartState())) {
                new FileUploader().writeSolutionToFile(solution.iterator());
            } else {
                new FileUploader().writeSolutionToFile(solution.descendingIterator());
            }
            System.out.println("Solution steps has been written into output.txt");
        }

    }
}
