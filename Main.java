package pkg;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) {
        Room r = new Room();
        RoomControl rc = new RoomControl(r);

        //SwingUtilities.invokeLater(() -> {
            Constant.gui = new GUI(r.residents,rc);
            Constant.gui.reDraw(r);

        //});



        ArrayList<Thread> threads = new ArrayList<>();
        Exchanger<Room> exchanger = new Exchanger<>();
        for(int i = 0;i<Constant.kThreads;i++){
            threads.add(new Thread(new RandomAlgo(r,exchanger,rc,Constant.swaps)));
        }
        threads.forEach(Thread::start);

        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Room best = Constant.bestRooms.get(0);
        for (int i =0;i<Constant.bestRooms.size();i++){
            if(best.getTotalHappiness()<Constant.bestRooms.get(i).getTotalHappiness()){
                best=Constant.bestRooms.get(i);

            }

        }
        Constant.gui.reDraw(best);
        Constant.gui.p1.setBackground(Color.GREEN);
        //


    }

}
