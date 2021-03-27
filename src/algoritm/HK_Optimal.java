package src.algoritm;

import src.fileReader.FileReaderMatrixSize;

import java.io.*;
import java.util.Scanner;


public class HK_Optimal {

    private static int[][] distances;
    private static int optimalDistance = Integer.MAX_VALUE;
    private static String optimalPath = "";

    public static void main(String args[]) throws IOException {
        String fileIn = "C:\\Users\\Grisha\\Desktop\\Ну ЛП\\4 курс\\2 семестр\\Дискретна математика\\Лабораторна робота №3\\GitHub2\\Data\\l3-3.txt";
        String fileOut = "C:\\Users\\Grisha\\Desktop\\Ну ЛП\\4 курс\\2 семестр\\Дискретна математика\\Лабораторна робота №3\\GitHub2\\Data\\Out.txt";
        FileReaderMatrixSize fileReaderMatrixSize = new FileReaderMatrixSize(fileIn, fileOut);


        // The path to the files with the distances is asked
        Scanner input = new Scanner(System.in);
        System.out.println("Please, introduce the path where the text file is stored");
        //String file = input.nextLine();
        String file = "C:\\Users\\Grisha\\Desktop\\Ну ЛП\\4 курс\\2 семестр\\Дискретна математика\\Лабораторна робота №3\\GitHub2\\Data\\Out.txt";

        // The size of the distance matrix is asked
        //System.out.println("Please, introduce the size of the matrix");
        //int size = input.nextInt();
        int size = fileReaderMatrixSize.getM();
        // Distances array is initiated considering the size of the matrix
        distances = new int[size][size];

        // The file in that location is opened

        BufferedReader b = new BufferedReader(f);


        // Our matrix is filled with the values of the file matrix
        for (int row = 0; row < size; row++) {

            // Every value of each row is read and stored
            String line = b.readLine();
            String[] values = line.trim().split("\\s+");

            for (int col = 0; col < size; col++) {
                distances[row][col] = Integer.parseInt(values[col]);
            }
        }

        // Closing file
        b.close();

        /* ------------------------- ALGORITHM INITIALIZATION ----------------------- */

        // Initial variables to start the algorithm
        String path = "";
        int[] vertices = new int[size - 1];

        // Filling the initial vertices array with the proper values
        for (int i = 1; i < size; i++) {
            vertices[i - 1] = i;
        }

        // FIRST CALL TO THE RECURSIVE FUNCTION
        procedure(0, vertices, path, 0);

        System.out.print("Path: " + optimalPath + ". Distance = " + optimalDistance);
    }


    /* ------------------------------- RECURSIVE FUNCTION ---------------------------- */

    private static int procedure(int initial, int vertices[], String path, int costUntilHere) {

        // We concatenate the current path and the vertex taken as initial
        path = path + Integer.toString(initial) + " - ";
        int length = vertices.length;
        int newCostUntilHere;


        // Exit case, if there are no more options to evaluate (last node)
        if (length == 0) {
            newCostUntilHere = costUntilHere + distances[initial][0];

            // If its cost is lower than the stored one
            if (newCostUntilHere < optimalDistance) {
                optimalDistance = newCostUntilHere;
                optimalPath = path + "0";
            }

            return (distances[initial][0]);
        }


        // If the current branch has higher cost than the stored one: stop traversing
        else if (costUntilHere > optimalDistance) {
            return 0;
        }


        // Common case, when there are several nodes in the list
        else {

            int[][] newVertices = new int[length][(length - 1)];
            int costCurrentNode, costChild;
            int bestCost = Integer.MAX_VALUE;

            // For each of the nodes of the list
            for (int i = 0; i < length; i++) {

                // Each recursion new vertices list is constructed
                for (int j = 0, k = 0; j < length; j++, k++) {

                    // The current child is not stored in the new vertices array
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newVertices[i][k] = vertices[j];
                }

                // Cost of arriving the current node from its parent
                costCurrentNode = distances[initial][vertices[i]];

                // Here the cost to be passed to the recursive function is computed
                newCostUntilHere = costCurrentNode + costUntilHere;

                // RECURSIVE CALLS TO THE FUNCTION IN ORDER TO COMPUTE THE COSTS
                costChild = procedure(vertices[i], newVertices[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                int totalCost = costChild + costCurrentNode;

                // Finally we select from the minimum from all possible children costs
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            return (bestCost);
        }
    }
}