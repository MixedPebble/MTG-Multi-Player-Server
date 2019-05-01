package pkg;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class GUI {
    JPanel p1;
    JFrame f;
    JLabel[][] labels;
    JLabel info;
    MigLayout layout = new MigLayout("wrap 5");
    RoomControl controller;


    public GUI(Person[][] roster, RoomControl rC) {
        controller = rC;
        f = new JFrame();
        p1 = new JPanel();
        info = new JLabel();
        p1.setLayout(layout);
        labels = new JLabel[Constant.rows][Constant.columns];
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[0].length; j++) {
                labels[i][j] = new JLabel(roster[i][j].name);
                labels[i][j].setForeground(Color.black);
                p1.add(labels[i][j], "width 50:150:150, height 50:150:150");
            }
        }
        info.setText("Thread Count: " + Thread.activeCount());
                //+ "    Max Affinity: " + controller.maxAffinityRoom.getTotalHappiness()
                //+ "    current Affinity: " + controller.currentRoom.getTotalHappiness());
        p1.add(info, "south");
        p1.setBackground(Color.red);
        f.add(p1);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public  void reDraw(Room r) {
        Person[][] p = r.residents.clone();
        //JLabel[][] test = new JLabel[Constant.rows][Constant.columns];
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[0].length; j++) {
                if (!labels[i][j].getText().equals(p[i][j].name)) {
                    labels[i][j].setText(p[i][j].name);
                    //test[i][j].setText(p[i][j].name);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        //labels=test;
        info.setText("Thread Count: " + Thread.activeCount()
                + "    Affinity: " + r.getTotalHappiness());
        //+ "    Max Affinity: " + r.getTotalHappiness()

    }

    //public synchronized void reDraw(Room r) {
      //  System.out.println("Don't use me.....");

        /*
        for (int i = 0; i < r.residents.length; i++) {
            for (int j = 0; j < r.residents[0].length; j++) {
                    labels[i][j].setForeground(Color.BLUE);
                    labels[i][j].setText(r.residents[i][j].name);
            }

        }
        info.setText("Thread Count: " + Thread.activeCount()
                + "    Max Affinity: " + controller.maxAffinityRoom.getTotalHappiness()
                + "    current Affinity: " + controller.currentRoom.getTotalHappiness());
    }
    */
    //}
}
