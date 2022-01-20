package com.company;

import java.util.Scanner;

//FIXME Fix the bug where if a 0 is calculated on the diagonal of the matrix the results become NaN

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the variables count:");
        int variables = input.nextInt();
        System.out.println(variables);

        Matrix matrix = new Matrix(variables);

        for(int row = 0; row<variables ; row++){
            double[] currentRowCoefficients = new double[variables+1];

            for(int coefficient = 0; coefficient<variables+1 ; coefficient++){
                if(coefficient != variables){
                    System.out.print("Enter the coefficient for the variable '" + (char)('a'+coefficient)+"':");
                }else{
                    System.out.print("Enter the value of the expression:");
                }

                currentRowCoefficients[coefficient] = Double.parseDouble(input.next());//Supports both int and double inputs
            }
            matrix.setRow(row,currentRowCoefficients);
        }

        System.out.println("\n\nExpression:");
        matrix.printAsExpression();

        System.out.println("\n\nMatrix:");
        matrix.print();

        matrix.solveMatrix();
    }
}
