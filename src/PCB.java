import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Stephen on 3/12/16.
 */
public class PCB {

    public PCB(int jobNum, int jobPri, int jobInstrCount, int jobDiskAdd)
    {
        jobNumber = jobNum;
        jobPriority = jobPri;
        jobDiskAddress = jobDiskAdd;
        jobInstructionCount = jobInstrCount;
        jobInMemory = false;
       // programCounter = jobDiskAdd;
        registers=new int[16];
        Arrays.fill(registers,0);
        processStatus = PROCESS_STATUS.NEW;
    }

    public enum PROCESS_STATUS {NEW, READY, WAIT, RUN, TERMINATE}

    //job
    private int jobNumber;
    private int jobPriority;
    private int jobDiskAddress;
    private int jobMemoryAddress;
    private int jobInstructionCount;
    //data
    private int dataDiskAddress;
    private int dataMemoryAddress;

    //page
    private int pageTableStartingIndex;
    private int pagesNeeded;

    private ArrayList<Integer> allocatedVirtualPages;

    //buffer
    private int inputBuffer;
    private int outputBuffer;
    private int temporaryBuffer;

    private boolean IObound;
    private String instruction;
    private boolean jobInMemory;
    private boolean hasJobRan;
    private PROCESS_STATUS processStatus;
    private int[] registers;
    private int programCounter;
    private String[] cache;
    private int cacheSize;

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cachesize) {
        this.cacheSize = cachesize;
    }

    public String[] getCache() {
        return cache;
    }

    public void setCache(String[] cache) {
        this.cache = cache;
    }

    public int getProgramCounter()
    {
        return programCounter;
    }
    public void setProgramCounter(int num)
    {
        programCounter =num;
    }
    public void setRegisters(int[] reg)
    {
        registers=reg;
    }
    public int[] getRegisters()
    {
        return registers;
    }
    public PROCESS_STATUS getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(PROCESS_STATUS processStatus) {
        this.processStatus = processStatus;
    }

    public boolean isHasJobRan() {
        return hasJobRan;
    }

    public void setHasJobRan(boolean hasJobRan) {
        this.hasJobRan = hasJobRan;
    }

    public boolean isJobInMemory() {
        return jobInMemory;
    }

    public void setJobInMemory(boolean jobInMemory) {
        this.jobInMemory = jobInMemory;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isIObound() {
        return IObound;
    }

    public void setIObound(boolean IObound) {
        this.IObound = IObound;
    }

    public int getTemporaryBuffer() {
        return temporaryBuffer;
    }

    public void setTemporaryBuffer(int temporaryBuffer) {
        this.temporaryBuffer = temporaryBuffer;
    }

    public int getOutputBuffer() {
        return outputBuffer;
    }

    public void setOutputBuffer(int outputBuffer) {
        this.outputBuffer = outputBuffer;
    }

    public int getInputBuffer() {
        return inputBuffer;
    }

    public void setInputBuffer(int inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public int getDataMemoryAddress() {
        return dataMemoryAddress;
    }

    public void setDataMemoryAddress(int dataMemoryAddress) {
        this.dataMemoryAddress = dataMemoryAddress;
    }

    public int getDataDiskAddress() {
        return dataDiskAddress;
    }

    public void setDataDiskAddress(int dataDiskAddress) {
        this.dataDiskAddress = dataDiskAddress;
    }

    public int getJobInstructionCount() {
        return jobInstructionCount;
    }

    public void setJobInstructionCount(int jobInstructionCount) {
        this.jobInstructionCount = jobInstructionCount;
    }

    public int getJobMemoryAddress() {
        return jobMemoryAddress;
    }

    public void setJobMemoryAddress(int jobMemoryAddress) {
        this.jobMemoryAddress = jobMemoryAddress;
    }

    public int getJobDiskAddress() {
        return jobDiskAddress;
    }

    public void setJobDiskAddress(int jobDiskAddress) {
        this.jobDiskAddress = jobDiskAddress;
    }

    public int getJobPriority() {
        return jobPriority;
    }

    public void setJobPriority(int jobPriority) {
        this.jobPriority = jobPriority;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public int getPageTableStartingIndex() {
        return pageTableStartingIndex;
    }

    public void setPageTableStartingIndex(int pageTableStartingIndex) {
        this.pageTableStartingIndex = pageTableStartingIndex;
    }

    public int getPagesNeeded() {
        return pagesNeeded;
    }

    public void setPagesNeeded(int pagesNeeded) {
        this.pagesNeeded = pagesNeeded;
    }

    public ArrayList<Integer> getAllocatedVirtualPages() {
        return allocatedVirtualPages;
    }

    public void setAllocatedVirtualPages(ArrayList<Integer> allocatedVirtualPages) {
        this.allocatedVirtualPages = allocatedVirtualPages;
    }
}




