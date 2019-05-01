package pkg;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class Room {
    public Person[][] residents;
    int rows;
    int columns;

    Room(){
        rows = Constant.rows;
        columns = Constant.columns;
        residents = new Person[rows][columns];
        int nameCount = 0;
        for (int i = 0; i < residents.length; i++) {
            for (int j = 0; j < residents[0].length; j++) {
                residents[i][j] = new Person(Constant.names[nameCount]);
                nameCount++;
            }
        }
        for (int i = 0; i < residents.length; i++) {
            for (int j = 0; j < residents[0].length; j++) {
                residents[i][j].createAffinity(residents);
                residents[i][j].setNeighbors(residents);
            }
        }
    }
    Room(Person[][] people){
        rows = people.length;
        columns = people[0].length;
        residents = new Person[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                residents[i][j] = new Person(people[i][j].name);
            }
        }
        for (int i = 0; i < residents.length; i++) {
            for (int j = 0; j < residents[0].length; j++) {
                residents[i][j].createAffinity(residents);
                residents[i][j].setNeighbors(residents);

            }
        }
    }


    Room(Person[][] sub, int startRow,int stopRow,int startColumn, int StopColumn){
        rows = stopRow-startRow;
        columns = StopColumn-startColumn;

        residents = new Person[rows][columns];

        for (int i = startRow; i < stopRow; i++) {
            for (int j = startColumn; j < StopColumn; j++) {
                //System.out.println("Name " +sub[i][j].name+ " "+i+" "+j);
                residents[i-startRow][j-startColumn] = new Person(sub[i][j].name);
                //colSpot++;
            }
            //rowSpot++;
        }
        for (int i = 0; i < residents.length; i++) {
            for (int j = 0; j < residents[0].length; j++) {
                residents[i][j].createAffinity(residents);
            }
        }

    }
    public int getTotalHappiness(){
        int happiness=0;
        for (int i = 0;i<residents.length;i++) {
            for (int j = 0; j < residents[j].length; j++) {
                happiness += residents[i][j].getTotalAffinity(residents);
            }
        }
        return happiness;
    }

    public int getSubHappiness( int rowStartBound, int rowEndBound, int ColumnStartbound, int ColumnEndBound){
        int happiness=0;
        for (int i = rowStartBound; i < rowEndBound; i++) {
            for (int j = ColumnStartbound; j < ColumnEndBound; j++) {
                happiness += residents[i][j].getTotalAffinity(residents);
            }
        }
        return happiness;

    }


    public void randomSwap(){
        Random r = new Random();
        int[] rands = new int[4];
        rands[0]=r.nextInt(rows);
        rands[1]=r.nextInt(columns);
        rands[2]=r.nextInt(rows);
        rands[3]=r.nextInt(columns);
        Person temp1 = residents[rands[0]][rands[1]];
        residents[rands[0]][rands[1]]=residents[rands[2]][rands[3]];
        residents[rands[2]][rands[3]]=temp1;
        residents[rands[0]][rands[1]].setNeighbors(residents);
        residents[rands[2]][rands[3]].setNeighbors(residents);
    }
    public void randomSwap(int rowStart, int rowEnd, int ColStart, int colEnd){
        //Find highestRoom arrangement using random seating
        //Random r = new Random();
        int[] rands = new int[4];
        //Person 1
        rands[0]=ThreadLocalRandom.current().nextInt(rowStart, rowEnd );
        rands[1]=ThreadLocalRandom.current().nextInt(ColStart, colEnd );

        //Person 2
        rands[2]=ThreadLocalRandom.current().nextInt(rowStart, rowEnd );
        rands[3]=ThreadLocalRandom.current().nextInt(ColStart, colEnd );

        Person temp1 = residents[rands[0]][rands[1]];
        residents[rands[0]][rands[1]]=residents[rands[2]][rands[3]];
        residents[rands[2]][rands[3]]=temp1;
        residents[rands[0]][rands[1]].setNeighbors(residents);
        residents[rands[2]][rands[3]].setNeighbors(residents);
    }

}
