/**
 * Created by Jordan on 3/16/2016.
 */
import java.util.LinkedList;
import java.util.ArrayList;



public class ShortTermScheduler {
    public LinkedList<PCB> readyQueue;
    public PCB block;
    public boolean ready = false;
    PCBManager.PCB_SORT_TYPE sortType;

    public ShortTermScheduler(){
        readyQueue = new LinkedList<PCB>();
    }

    public void PrioSchedule(){
        int totalJobs = 0; //PCB Total jobs
        //Make sure sort type is set correctly to Priority
        if(PCBManager.getCurrentPcbSortType() != PCBManager.PCB_SORT_TYPE.JOB_PRIORITY){
            PCBManager.sortPcbList(PCBManager.PCB_SORT_TYPE.JOB_PRIORITY);
        }
        //Clear the ready queue
//        if(!readyQueue.isEmpty()) {
//            readyQueue.clear();
//        }
        //Add the jobs to the ready queue
        for(int i = 1; i < totalJobs + 1; i++){
            readyQueue.add(PCBManager.getPCB(i));
        }
        sortType = PCBManager.PCB_SORT_TYPE.JOB_PRIORITY;
//        //dispatch
//        dispatch();
    }

    public void FIFOSchedule(){
        int totalJobs = PCBManager.getJobListSize(); //PCB Total jobs
        //Make sure sort type is set correctly to FIFO
//        if(PCBManager.getCurrentPcbSortType() != PCBManager.PCB_SORT_TYPE.JOB_NUMBER) {
        PCBManager.sortPcbList(PCBManager.PCB_SORT_TYPE.JOB_NUMBER);
//        }
        //Clear the ready queue
//        if(!readyQueue.isEmpty()){
//            readyQueue.clear();
//        }


        for(int i = 1; i < totalJobs + 1; i++){
            readyQueue.add(PCBManager.getPCB(i));
        }


        sortType = PCBManager.PCB_SORT_TYPE.JOB_NUMBER;
        //dispatch
    }



    public void Schedule(SchedulingType type)
    {
        PCBManager.PCB_SORT_TYPE sort_type = PCBManager.PCB_SORT_TYPE.JOB_NUMBER;

        //sorting jobs
        switch (type.val())
        {
            case 1:
                sort_type = PCBManager.PCB_SORT_TYPE.JOB_PRIORITY;
                break;
            case 2:
                sort_type = PCBManager.PCB_SORT_TYPE.JOB_NUMBER;
                break;
            case 3:
                sort_type = PCBManager.PCB_SORT_TYPE.SHORTEST_JOB;
                break;
        }

        PCBManager.sortPcbList(sort_type, readyQueue);
    }

    public void addToReadyQueue(PCB pcb)
    {
        readyQueue.add(pcb);
    }





}

