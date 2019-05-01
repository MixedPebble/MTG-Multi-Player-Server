package pkg;


import java.util.Random;
import java.util.concurrent.Exchanger;

public class RandomAlgo implements Runnable {

    volatile Room highestRoom;
    final int iterations = 100;
    volatile boolean shutdown = false;
    Exchanger<Room> exchanger;
    RoomControl rC;
    int swaps;

    RandomAlgo(Room rm, Exchanger<Room> exc, RoomControl rc, int sw) {
        highestRoom = rm;
        this.exchanger = exc;
        rC = rc;
        swaps = sw;
    }

    @Override
    public void run() {
        int currentIteration = 0;
        Person[][] nextPeople;
        Room nextClass;
        while (currentIteration < Constant.iterations && !shutdown) {
            if (highestRoom.getTotalHappiness() > 20000) {
                System.out.println("ABOVE A BIG NUMBER");
                shutdown();
            }
            nextPeople = randomSwap(highestRoom.residents, swaps);
            nextClass = new Room(nextPeople);
            while (highestRoom.getTotalHappiness() > nextClass.getTotalHappiness()) {

                nextPeople = randomSwap(highestRoom.residents, swaps);
                nextClass = new Room(nextPeople);
            }

            System.out.println(Thread.currentThread().getId() + " has the highestRoom!");
            try {
                //System.out.println("highestRoom " +highestRoom.getTotalHappiness());
                //System.out.println("current " +nextClass.getTotalHappiness());
                if (highestRoom.getTotalHappiness() < nextClass.getTotalHappiness()) {
                    highestRoom = exchanger.exchange(nextClass);
                    Constant.gui.reDraw(highestRoom);
                }

                //Thread.sleep(500);
                //System.out.println("Total: "+highestRoom.getTotalHappiness());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getId() + " has opened it up!");

            currentIteration++;
        }
        Constant.bestRooms.add(highestRoom);
        System.out.println(Thread.currentThread().getId() + " has finished");
    }

    private void shutdown() {
        System.out.println("shutdown is true!");
        shutdown = true;
    }

    public void exchange() {
        try {
            highestRoom = exchanger.exchange(highestRoom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Person[][] randomSwap(Person[][] residents, int iter) {
        Random r = new Random();
        int[] rands = new int[4];
        //Person 1
        for (int i = 0; i < iter; i++) {
            rands[0] = r.nextInt(residents.length);
            rands[1] = r.nextInt(residents[0].length);
            //Person 2
            rands[2] = r.nextInt(residents.length);
            rands[3] = r.nextInt(residents[0].length);
            Person temp1 = residents[rands[0]][rands[1]];
            residents[rands[0]][rands[1]] = residents[rands[2]][rands[3]];
            residents[rands[2]][rands[3]] = temp1;
            residents[rands[0]][rands[1]].setNeighbors(residents);
            residents[rands[2]][rands[3]].setNeighbors(residents);
        }


        return residents;
    }
}
