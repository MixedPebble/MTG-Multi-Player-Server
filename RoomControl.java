package pkg;

import java.util.ArrayList;
/*
synchronized:
    Code block/method modifier.
    1 Thread acquires a lock
    2 All vars read from main memory
    3 execute block of code
    4 Update vars to main memory
    5 Release lock
Volatile:
    Field modifier
    Prevents threads from having a local copy of a variable
    Must have it's data synchronized across all threads

 */

public class RoomControl implements Runnable{
     Room  currentRoom;
     Room maxAffinityRoom;
    ArrayList<Thread> myThreads;

    public RoomControl(Room r){
        currentRoom = r;
        maxAffinityRoom = new Room(currentRoom.residents);
        myThreads = new ArrayList<>();
    }


    public static synchronized void setBest(Room best, Room nextClass, RandomAlgo algo){
        System.out.println(Thread.currentThread().getId()+ " has the highestRoom!");
        try {
            //System.out.println("highestRoom " +highestRoom.getTotalHappiness());
            //System.out.println("current " +nextClass.getTotalHappiness());
            if(best.getTotalHappiness()<nextClass.getTotalHappiness()){
                algo.highestRoom = algo.exchanger.exchange(nextClass);
                Constant.gui.reDraw(best);
            }

            Thread.sleep(500);
            //System.out.println("Total: "+highestRoom.getTotalHappiness());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId()+ " has opened it up!");

    }

    @Override
    public void run() {
        synchronized(currentRoom) {
            RandomArrangementMethod().start();
            //segmentationMethod();
        }
    }



    public Thread RandomArrangementMethod(){
        Thread t = new Thread(){
            public void run(){

                for (int i =0;i<Constant.iterations;i++){
                    currentRoom.randomSwap();
                }

            }

        };
        return t;
    }


    public void segmentationMethod(){
        myThreads.add(new Thread(new DivideNConquer(currentRoom,0,4,0,2,this)));
        myThreads.add(new Thread(new DivideNConquer(currentRoom,0,4,3,5,this)));
        myThreads.add(new Thread(new DivideNConquer(currentRoom,5,8,0,2,this)));
        myThreads.add(new Thread(new DivideNConquer(currentRoom,5,8,3,5,this)));
        myThreads.forEach(Thread::start);

    }

}
