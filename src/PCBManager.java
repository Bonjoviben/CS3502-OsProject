import java.util.Comparator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;

/**
 * Created by Stephen on 3/12/16.
 */
public final class PCBManager {

    private PCBManager(){}

    public static void init()
    {
        pcbList = new LinkedList<PCB>();
        currentSortType = PCB_SORT_TYPE.JOB_NUMBER;
    }

    private static LinkedList<PCB> pcbList = new LinkedList<PCB>();
    public enum PCB_SORT_TYPE {SHORTEST_JOB, JOB_NUMBER, JOB_PRIORITY}
    private static PCB_SORT_TYPE currentSortType = PCB_SORT_TYPE.JOB_NUMBER;
    public static int getJobListSize()
    {
        return pcbList.size();
    }

    public static void insertPCB(PCB pcb)
    {
        pcbList.add(pcb);
    }

    public static PCB getPCB(int index)
    {
        return pcbList.get(index-1);
    }

    public static void sortPcbList(PCB_SORT_TYPE type)
    {
        _sortPcbList(type, pcbList);
        currentSortType = type;
    }

    public static void sortPcbList(PCB_SORT_TYPE type, LinkedList<PCB> list)
    {
        _sortPcbList(type, list);
    }

    public static PCB_SORT_TYPE getCurrentPcbSortType() {return currentSortType;}

    public static boolean allJobsDone(){
        ListIterator<PCB> it = pcbList.listIterator();
        while (it.hasNext())
        {
            if(it.next().getProcessStatus() != PCB.PROCESS_STATUS.TERMINATE)
                return false;
        }
        return true;
    }

    private static void _sortPcbList(PCB_SORT_TYPE type, LinkedList<PCB> list)
    {
        switch (type) {
            case JOB_NUMBER:
                Collections.sort(list, new Comparator<PCB>() {
                    @Override
                    public int compare(PCB o1, PCB o2) {
                        return o1.getJobNumber()-o2.getJobNumber();
                    }
                });
               // currentSortType=PCB_SORT_TYPE.JOB_NUMBER;
                break;

            case JOB_PRIORITY:
                Collections.sort(list, new Comparator<PCB>() {
                    @Override
                    public int compare(PCB o1, PCB o2) {
                        return o1.getJobPriority()-o2.getJobPriority();
                    }
                });
                //currentSortType=PCB_SORT_TYPE.JOB_PRIORITY;

                break;

            case SHORTEST_JOB:
                Collections.sort(list, new Comparator<PCB>() {
                    @Override
                    public int compare(PCB o1, PCB o2) {
                        return o1.getJobInstructionCount()-o2.getJobInstructionCount();
                    }
                });
                //currentSortType=PCB_SORT_TYPE.SHORTEST_JOB;
                break;
        }


    }

}
