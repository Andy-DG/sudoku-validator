package SudokuValidator;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SudokuSolutionValidator {
    public static boolean check(int[][] sudoku) {
        //  All the rows/columns/3x3-arrays should contain the same elements as this one.
        int[] required = IntStream.rangeClosed(1, 9).toArray();

        //  Check all rows
        for (int row = 0; row < 9; row++) {
            boolean isEqual = compareArrays(sudoku[row], required);
            System.out.printf("Row %d meets requirements: %b\n", row+1, isEqual);
            if (!isEqual) return false;
        }

        //  Transpose array
        int[][] sudokuTranspose = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuTranspose[col][row] = sudoku[row][col];
            }
        }

        //  Check all original columns
        for (int row = 0; row < 9; row++) {
            boolean isEqual = compareArrays(sudokuTranspose[row], required);
            System.out.printf("Column %d meets requirements: %b\n", row+1, isEqual);
            if (!isEqual) return false;
        }

        //  Check all the 3x3-arrays
        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {
                int[][] threeByThree = sub2DArray(sudoku, i, i+3, j, j+3);
                int[] flattenedThree = Arrays.stream(threeByThree).flatMapToInt(Arrays::stream).sorted().toArray();
                boolean is3x3Equal = (compareArrays(flattenedThree, required));
                if (!is3x3Equal) return false;
            }
        }

        return true;
    }

    //  Check if arrays are equal
    public static boolean compareArrays(int[] arr1, int[] arr2) {
        int[] copyOne = Arrays.copyOf(arr1,arr1.length);
        Arrays.sort(copyOne);
        return Arrays.equals(copyOne, arr2);
    }

    //  Create 2D-subarray
    public static int[][] sub2DArray (int[][] srcArray, int row_begin, int row_end
                                , int col_begin, int col_end) {
        int[][] subArray = new int[row_end - row_begin][col_end - col_begin];
        for (int r = row_begin, i=0; r < row_end; r++, i++){
            for (int c = col_begin, j= 0; c < col_end; c++, j++){
                subArray[i][j] = srcArray[r][c];
            }
        }
        return subArray;
    }

    /*
  2D-Array print utility
    public static void print2DArray(int[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
    */

}
