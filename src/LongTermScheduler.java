/**
 * Created by Stephen on 2/8/16.
 */
import java.util.ArrayList;
import java.util.LinkedList;


public class LongTermScheduler {
    public LongTermScheduler() {

    }

    public void Schedule()
    {
        if(!RAM.isRAMFull())
        {
            int totalJobs = PCBManager.getJobListSize();
            for (int i  = 1; i <= totalJobs; i++)
            {
                if(!PCBManager.getPCB(i).isJobInMemory() && PCBManager.getPCB(i).getProcessStatus() == PCB.PROCESS_STATUS.NEW && !RAM.isRAMFull())
                    loadJobToRAM(PCBManager.getPCB(i));
            }
        }
    }


    public void loadJobToRAM(PCB block) {

//        if (PCBManager.getCurrentPcbSortType() != PCBManager.PCB_SORT_TYPE.JOB_NUMBER){
//            PCBManager.sortPcbList(PCBManager.PCB_SORT_TYPE.JOB_NUMBER);
//        }

        int jobNo = block.getJobNumber();
        int i = 0;
        int k;
        int m = PCBManager.getPCB(jobNo).getJobInstructionCount();
        int dataCardSize = block.getInputBuffer() + block.getOutputBuffer() + block.getTemporaryBuffer(); //44 or 40% of (5)
        int memory = PCBManager.getPCB(jobNo).getJobInstructionCount() + dataCardSize;
        int numPages= (int)Math.ceil((double)memory/(double)RAM.getPageSize());
        int startAddress=block.getJobDiskAddress();
        int currentDiskAddress=startAddress;
        int physPageNo;
        int virtualPageNo = RAM.getNextAvailableVirtualPageNumber();
        String[] chunk;
        ArrayList<Integer> virtualAllocatedPages = RAM.allocate(numPages);
        if(virtualAllocatedPages.size() != 0)
        {
            System.out.println(jobNo);
            PCBManager.getPCB(jobNo).setJobInMemory(true);
            PCBManager.getPCB(jobNo).setJobMemoryAddress( virtualPageNo * RAM.getPageSize());
            PCBManager.getPCB(jobNo).setProcessStatus(PCB.PROCESS_STATUS.READY);
            PCBManager.getPCB(jobNo).setPagesNeeded(numPages);
            PCBManager.getPCB(jobNo).setAllocatedVirtualPages(virtualAllocatedPages);

            int n = RAM.getNextAvailableVirtualPageNumber();

            for (int j=0;j<virtualAllocatedPages.size(); j++)
            {
                //physPageNo=RAM.allocate(Helpers.getPageNumberFromAddress(currentDiskAddress));
                physPageNo=RAM.getPhysicalPageNumber(virtualAllocatedPages.get(j));

//                chunk=Disk.getChunk(currentDiskAddress, (currentDiskAddress+RAM.getPageSize()>startAddress+memory)
//                        ? (startAddress+memory)-currentDiskAddress
//                        : RAM.getPageSize());

                chunk=Disk.getChunk(currentDiskAddress, calulateChunkSize(block, currentDiskAddress));

                try {
                    RAM.fillPage(physPageNo, chunk);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }

                currentDiskAddress+=RAM.getPageSize();
            }
            JobMetrics metrics = new JobMetrics();
            metrics.setTimestamp(System.currentTimeMillis());
            metrics.setJobNumber(jobNo);
            metrics.setStartWaitTime(metrics.getTimestamp());
            metrics.setBlocksUsed(memory);
            Driver.jobMetricses[jobNo-1].update(metrics);
            Driver.ShortTermScheduler.addToReadyQueue(PCBManager.getPCB(jobNo));
        }



}
    private int calulateChunkSize(PCB pcb, int currentDiskAddress)
    {
        int dataCardSize = pcb.getInputBuffer() + pcb.getOutputBuffer() + pcb.getTemporaryBuffer(); //44 or 40% of (5)
        int memory = pcb.getJobInstructionCount() + dataCardSize;
        if(currentDiskAddress+RAM.getPageSize()>pcb.getJobDiskAddress()+memory)
            return (pcb.getJobDiskAddress()+memory)-currentDiskAddress;
        else
            return RAM.getPageSize();

    }

//    // Initial job load. Places jobs into RAM based on sort type selected (FIFO vs Prio)
//    public void loadJobList(PCBManager.PCB_SORT_TYPE st) {
//        int i = 0;
//        int j;
//        int memory;
//        //Check sort type, set PCB_SORT_TYPE to st (sortType)
//        if(st == PCBManager.PCB_SORT_TYPE.JOB_NUMBER){
//            if(PCBManager.getCurrentPcbSortType() != PCBManager.PCB_SORT_TYPE.JOB_NUMBER)
//                PCBManager.sortPcbList(PCBManager.PCB_SORT_TYPE.JOB_NUMBER);
//        }
//        else{
//            if(PCBManager.getCurrentPcbSortType() != PCBManager.PCB_SORT_TYPE.JOB_PRIORITY)
//                PCBManager.sortPcbList(PCBManager.PCB_SORT_TYPE.JOB_PRIORITY);
//        }
//
//        for(int l = 1; l <= PCBManager.getJobListSize(); l++){
//            memory = PCBManager.getPCB(l).getJobInstructionCount() + 5; //+
//            int m = PCBManager.getPCB(l).getJobNumber();
//
//            if((ramSize - (i + memory) >= 0)){
//
//                PCBManager.getPCB(l).setJobMemoryAddress(i);
//                PCBManager.getPCB(l).setDataMemoryAddress(i + PCBManager.getPCB(i).getJobInstructionCount());
//                PCBManager.getPCB(l).setJobInMemory(true);
//
//                for(j = PCBManager.getPCB(l).getJobDiskAddress(); j < PCBManager.getPCB(l).getJobDiskAddress()+ memory; j++) {
//                    MMU.writeToRAM(Disk.readDisk(j), i);
//                }
//                i++;
//            }
//        }
//
//    }


}



