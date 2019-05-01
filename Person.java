package pkg;


import java.util.HashMap;
import java.util.Random;

public class Person {
    private Person neighbors[] = new Person[4];
    private HashMap<Person, Integer> affinity = new HashMap<>();
    public String name;

    public Person(String n) {

        name = n;

    }

    public void createAffinity(Person[][] people) {
        Random r = new Random();
        for (int i = 0; i < people.length; i++) {
            for (int j = 0; j < people[0].length; j++) {
                affinity.put(people[i][j], r.nextInt(200));
            }

        }
    }

    public void setNeighbors(Person[][] residents) {

        int currentRow = -1;
        int currentColumn = -1;
        for (int i = 0; i < residents.length; i++) {
            for (int k = 0; k < residents[0].length; k++) {
                if (residents[i][k] == this) {
                    currentRow = i;
                    currentColumn = k;
                }
            }
        }
        neighbors[0] = null;
        neighbors[1] = null;
        neighbors[2] = null;
        neighbors[3] = null;
        if (currentRow - 1 >= 0) {
            //N S E W
            //0 1 2 3
            neighbors[0] = residents[currentRow - 1][currentColumn];
            residents[currentRow - 1][currentColumn].neighbors[1] = this;
        } else {
            neighbors[0] = null;
        }
        if (currentRow + 1 < residents.length) {
            //south
            neighbors[1] = residents[currentRow + 1][currentColumn];
            residents[currentRow + 1][currentColumn].neighbors[0] = this;
        } else {
            neighbors[1] = null;
        }
        if (currentColumn + 1 < residents[0].length) {
            //east
            neighbors[2] = residents[currentRow][currentColumn + 1];
            residents[currentRow][currentColumn + 1].neighbors[3] = this;
        } else {
            neighbors[2] = null;
        }
        if (currentColumn - 1 >= 0) {
            //west
            neighbors[3] = residents[currentRow][currentColumn - 1];
            residents[currentRow][currentColumn - 1].neighbors[2]=this;
        } else {
            neighbors[3] = null;

        }


    }

    public int getAffinity(Person p) {
        //System.out.println("    "+p.name+" "+affinity.get(p));
        return affinity.get(p);

    }

    public int getTotalAffinity(Person[][] r) {
        int affinity = 0;
        setNeighbors(r);
        for (Person p : neighbors) {
            if (p != null) {
                affinity += getAffinity(p);
            }
        }

        //System.out.println("Returned Affinity: "+affinity);
        return affinity;
    }

    @Override
    public String toString() {
        String info = name + "'s neighbors: ";
        if (neighbors[0] != null) {
            //north
            info += "\n    North: " + neighbors[0].name;

        }
        if (neighbors[1] != null) {
            //south
            info += "\n    South: " + neighbors[1].name;

        }
        if (neighbors[2] != null) {
            //east
            info += "\n    East: " + neighbors[2].name;
            info += " " + affinity.get(neighbors[2]);
            info += " " + neighbors[2].getAffinity(this);

        }
        if (neighbors[3] != null) {
            //west
            info += "\n    West: " + neighbors[3].name;

        }

        return info;
    }


}
