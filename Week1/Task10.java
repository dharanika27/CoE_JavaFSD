package Week1;

import java.util.*;
import java.util.concurrent.*;

class MatrixMultiplier implements Runnable {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] resultMatrix;
    private int row;
    private int col;

    public MatrixMultiplier(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int row, int col) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < matrixA[0].length; i++) {
            sum += matrixA[row][i] * matrixB[i][col];
        }
        resultMatrix[row][col] = sum;
    }
}

public class Task10 {

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("Matrix A columns must equal Matrix B rows for multiplication.");
        }

        int[][] resultMatrix = new int[rowsA][colsB];
        ExecutorService executor = Executors.newFixedThreadPool(rowsA * colsB);

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                executor.submit(new MatrixMultiplier(matrixA, matrixB, resultMatrix, i, j));
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        return resultMatrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input for matrix A
        System.out.print("Enter number of rows for matrix A: ");
        int rowsA = scanner.nextInt();
        System.out.print("Enter number of columns for matrix A: ");
        int colsA = scanner.nextInt();
        int[][] matrixA = new int[rowsA][colsA];
        System.out.println("Enter elements of matrix A:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                matrixA[i][j] = scanner.nextInt();
            }
        }

        // Input for matrix B
        System.out.print("Enter number of rows for matrix B: ");
        int rowsB = scanner.nextInt();
        System.out.print("Enter number of columns for matrix B: ");
        int colsB = scanner.nextInt();
        int[][] matrixB = new int[rowsB][colsB];
        System.out.println("Enter elements of matrix B:");
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < colsB; j++) {
                matrixB[i][j] = scanner.nextInt();
            }
        }

        // Check if multiplication is possible
        if (colsA != rowsB) {
            System.out.println(
                    "Matrix multiplication is not possible. The number of columns in matrix A must equal the number of rows in matrix B.");
            return;
        }

        // Perform matrix multiplication
        int[][] result = multiplyMatrices(matrixA, matrixB);

        // Output the result
        System.out.println("Result of the multiplication:");
        printMatrix(result);
        scanner.close();
    }
}
