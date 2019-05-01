package pkg;


public class DivideNConquer implements Runnable {
    int rowStartBound, rowEndBound, ColumnStartbound, ColumnEndBound;
    Room localRoom;
    String dir;
    int localMaxAffinity = 0;
    RoomControl controller;
    Person[][] bestSubRoom;

    public DivideNConquer(Room sr, int rsb, int reb, int csb, int ceb, RoomControl rc) {
        localRoom = sr;
        rowStartBound = rsb;
        rowEndBound = reb;
        ColumnStartbound = csb;
        ColumnEndBound = ceb;
        controller = rc;
        bestSubRoom = new Person[sr.residents.length][sr.residents[0].length];
        for (int i = rowStartBound; i < rowEndBound; i++) {
            for (int j = ColumnStartbound; j < ColumnEndBound; j++) {
                bestSubRoom[i][j] = localRoom.residents[i][j];
            }
        }

    }

    public void updateWithBest(){
        for (int i = rowStartBound; i < rowEndBound; i++) {
            for (int j = ColumnStartbound; j < ColumnEndBound; j++) {
                bestSubRoom[i][j] = controller.currentRoom.residents[i][j];
            }
        }
    }

    @Override
    public void run() {
        localMaxAffinity = controller.currentRoom.getSubHappiness(rowStartBound,rowEndBound,ColumnStartbound,ColumnEndBound);
        for (int i = 0; i <Constant.iterations; i++) {
            localRoom.randomSwap(rowStartBound, rowEndBound, ColumnStartbound, ColumnEndBound);
            if (localMaxAffinity<controller.currentRoom.getSubHappiness(rowStartBound,rowEndBound,ColumnStartbound,ColumnEndBound)){
                for (int k = rowStartBound; k < rowEndBound; k++) {
                    for (int j = ColumnStartbound; j < ColumnEndBound; j++) {
                        bestSubRoom[k][j] = localRoom.residents[k][j];
                    }
                }
            }
            updateWithBest();
            //controller.displayCurrentArrangement();
        }
    }
}
