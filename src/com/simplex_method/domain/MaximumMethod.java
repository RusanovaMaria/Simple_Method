package com.simplex_method.domain;

import java.util.ArrayList;
import java.util.List;

public class MaximumMethod implements SimplexMethod{

    private double [] [] matrix =
                    {{18, 1, 3, 1, 0, 0, 0, 0},
                    {16, 2, 1, 0, 1, 0, 0, 0},
                    {5, 0, 1, 0, 0, 1, 0, 0},
                    {7, 1, 0, 0, 0, 0, 1, 0},
                    {0, -2, -3, 0, 0, 0, 0, 0}};

    private String result = null;

    private int width = matrix[0].length;
    private int height = matrix.length;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String getResult() {
        while (!isPlanOptimal()) {
            implementCycle();
        }
         result = Double.toString(matrix[height-1][0]);
        return result;
    }

    private boolean isPlanOptimal() {
        for (int i = 0; i<width; i++) {
            if (isElementNegative(matrix[height-1][i]))
                return false;
        }
        return true;
    }

    public void implementCycle() {
        int numberOfLeadingColumn = findNumberOfLeadingColumn();
        convertLastColumn(numberOfLeadingColumn);
        int numberOfLeadingRow = findNumberOfLeadingRow();
        convertMatrix(numberOfLeadingColumn, numberOfLeadingRow);
        convertLeadingColumn(numberOfLeadingColumn, numberOfLeadingRow);
        convertLeadingRow(numberOfLeadingColumn, numberOfLeadingRow);
    }

    public void convertLastColumn(int leaderColumnNumber){
        for (int i = 0; i < height-1; i++) {
            double numerator = matrix[i][0];
            double denominator = matrix[i][leaderColumnNumber];

            convertElementInLastColumn(numerator, denominator, i);
        }
    }

    private void convertElementInLastColumn(double numerator, double denominator, int rowNumber) {
        if (denominator!=0) {
            matrix[rowNumber][width-1] = numerator/denominator;
        } else {
            matrix[rowNumber][width-1] = -1;
        }
    }

    public int findNumberOfLeadingColumn(){
        double leastElement = findLeastElementInBasicPlan();
        int number = 0;

        for (int i = 1; i<width-1; i++){
            if (matrix[height-1][i] == leastElement){
                number = i;
            }
        }
        return number;
    }

    private double findLeastElementInBasicPlan() {
        double leastElement = matrix[0][height-1];

        for (int i = 1; i<width-1; i++) {
            leastElement = Math.min(leastElement,matrix[height-1][i]);
        }
        return leastElement;
    }

    public int findNumberOfLeadingRow() {
        double leastPositiveElement = findLeastPositiveElementInLastColumn();
        int number = -1;

        for (int i = 0; i < height-1; i++) {
            if (matrix[i][width-1] == leastPositiveElement){
                number = i;
            }
        }
        return number;
    }

    private double findLeastPositiveElementInLastColumn() {
        if (isMatrixValid()) {
            List<Double> positiveElements = findPositiveElementsInLastColumn();
            double leastElement = positiveElements.get(0);

            for (int i = 0; i < positiveElements.size(); i++){
                leastElement= Math.min(leastElement, positiveElements.get(i));
            }
            return leastElement;
        } else {
            result = "Решения нет";
            throw new IllegalStateException("Значение ЛП не ограничено, решения нет");
        }
    }

    private boolean isMatrixValid() {
        for (int i = 0; i < height; i++) {
            if (!isElementNegative(matrix[i][width-1])){
                return true;
            }
        }
        return false;
    }

    private List<Double> findPositiveElementsInLastColumn() {
        List<Double> positiveElements = new ArrayList();

        for (int i = 0; i < height-1; i++) {
            if (!isElementNegative(matrix[i][width-1])) {
                positiveElements.add(matrix[i][width-1]);
            }
        }
        return positiveElements;
    }

    public void convertLeadingColumn( int numberOfLeadingColumn,  int numberOfLeadingRow) {
        for (int i = 0; i < height; i++) {
            if (i != numberOfLeadingRow) {
                matrix[i][numberOfLeadingColumn] = 0;
            }
        }
    }

    public void convertLeadingRow( int numberOfLeadingColumn,  int numberOfLeadingRow) {
        double resolvingElement = matrix[numberOfLeadingRow][numberOfLeadingColumn];

        for (int i = 0; i < width; i++) {
            if (i != numberOfLeadingColumn) {
                matrix[numberOfLeadingRow] [i] = matrix[numberOfLeadingRow][i]/resolvingElement;
            }
        }
        resolvingElement = 1;
    }


    public void convertMatrix(int numberOfLeadingColumn, int numberOfLeadingRow) {
        double resolvingElement = matrix[numberOfLeadingRow][numberOfLeadingColumn];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width-1; j++) {
                if ((i != numberOfLeadingRow) && (j != numberOfLeadingColumn)) {
                    matrix[i][j] = matrix[i][j] - ((matrix[i][numberOfLeadingColumn] * matrix[numberOfLeadingRow][j]) / resolvingElement);
                }
            }
        }
    }

    private boolean isElementNegative(double element) {
        if (element < 0) {
            return true;
        }
        return false;
    }

    public void printMatrix() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }
}


