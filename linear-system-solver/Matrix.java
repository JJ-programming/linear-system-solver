package com.company;

import java.util.Arrays;

public class Matrix{
    private final int rows;
    private final int columns;
    private final double[][] elements;

    public Matrix(int variablesCount){
        this.rows = variablesCount;
        this.columns = variablesCount + 1;//Adding an extra column for the answer of each expression (row)
        this.elements = new double[this.rows][this.columns];
    }

    ////////////////////////////////////////////////Matrix operations
    public double[] getMultipliedRow (int rowIndex , double multiplyBy) {
        double[] resultRow = Arrays.copyOf(this.elements[rowIndex], this.elements[rowIndex].length);

        for(int i = 0 ; i<resultRow.length ; i++){
            resultRow[i] *= multiplyBy;
        }

        return resultRow;
    }

    public void swapRows (int row1 , int row2) {
        double[] row1Copy = Arrays.copyOf(this.getRow(row1), this.getRow(row1).length);

        this.setRow(row1, Arrays.copyOf(this.getRow(row2), this.getRow(row2).length));

        this.setRow(row2,row1Copy);
    }

    public void multiplyRow(int rowIndex , double multiplyBy){
        this.setRow(rowIndex,getMultipliedRow(rowIndex,multiplyBy));
    }

    public double[] getDividedRow(int rowIndex , double divideBy){
        return getMultipliedRow(rowIndex,1/divideBy);
    }

    public void divideRow(int rowIndex , double divideBy){
        this.setRow(rowIndex,getDividedRow(rowIndex,divideBy));
    }

    public void subtractRows(int row1,double[] row2){
        for(int i = 0 ; i<elements[row1].length ; i++){
            this.elements[row1][i] -= row2[i];
        }
    }

    //Goes column by column and makes each element of the matrix except it's diagonal equal to 0
    //It does that by choosing the current diagonal segment element from the current column and
    // the element you want to make 0 then multiplying them by each other to get their common multiple
    // and then subtracting both rows but reverting the row that had the column segment in.

    //Example with 3 and 5 to make 5-->0 I could multiply 5*3=15 and subtract 15-(3*5) (Note that these multiplications are applied to the whole rows the elements belong to !)
    //Above example with small matrix:
    /*
        3 6 8 = 1 ---->*5
        5 7 1 = 2 ---->*3

        15 30 40 = 5
        15 21 3 = 6 ---->R2=R2-R1

        15 30 40 = 5 ---->Revert the changes after calculation
        0 -9 -37 = -1

        3 6 8 = 1
        0 -9 -37 =-1
     */
    public void solveMatrix(){
        if(this.elements[0][0]==0){
            for(int row=1;row<this.rows;++row){
                if(this.elements[row][0]!=0){
                    this.swapRows(0,row);
                    break;
                }
            }
        }

        for(int column=0;column<this.columns-1;column++){
            for(int row=0;row<this.rows;row++){
                if(row==column){
                    continue;
                }
                double currentCoefficient=this.elements[row][column];//The coefficient that we are going to make 0 after the transformations below
                if(currentCoefficient==0){//We skip the transformations if it is already 0
                    continue;
                }
                this.multiplyRow(row,this.elements[column][column]);//Multiplying the row we want to set a 0 in by the row that will have a 1 in the end

                this.subtractRows(row,getMultipliedRow(column,currentCoefficient));//subtracting the common multiplies to get a 0 in the row (without changing the row that will contain 1 in the end)

                this.divideRow(row,this.elements[column][column]);//Unscaling the row after the 0 is set
            }
        }

        System.out.println("Solution of the system:");
        for(int row=0;row<this.rows;row++){
            this.divideRow(row,this.elements[row][row]);
            this.elements[row][row]=Math.round(this.elements[row][row]);
            if (Double.isNaN(this.elements[row][this.columns-1])){
                System.out.println("The system has no solution!");
                return;
            }else{
                System.out.println((char)('a'+row)+" = "+this.elements[row][this.columns-1]);
            }
        }
    }
    ////////////////////////////////////////////////

    public double[] getRow(int index){
        return this.elements[index];
    }

    public void setRow(int rowIndex , double[] row){
        this.elements[rowIndex] = row;
    }


    public void print(){
        for(int row=0;row<this.rows;row++){
            System.out.print("[");
            for(int column=0;column<this.columns;column++){
                System.out.print(this.elements[row][column]);
                if(column!=this.columns-1){
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
    }
    
    public void printAsExpression(){
        for(int row=0;row<this.rows;row++){
            for(int column=0;column<this.columns;column++){
                if(column!=this.columns-1){
                    double coefficient=this.elements[row][column];
                    if(coefficient<0){
                        if(column!=0){
                            System.out.print(" - ");
                        }else{
                            System.out.print("-");
                        }
                    }else if(column!=0){
                        System.out.print(" + ");
                    }
                    System.out.print(Math.abs(coefficient)+""+(char)('a'+column));
                }else{
                    System.out.println(" = "+this.elements[row][column]);
                }
            }
        }
    }
}
