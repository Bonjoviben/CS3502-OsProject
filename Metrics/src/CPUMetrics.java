/**
 * Created by Stephen on 4/16/16.
 */
public class CPUMetrics {

    public CPUMetrics(int cpuNum)
    {
        cpuNumber = cpuNum;
    }


    public int cpuNumber = 0;
    public int currentJobNumber = 0;
    public int programCounter = 0;
    public int totalInstructionsNumber = 0;
    public enum CPU_STATE {IDLE, RUNNING, LOADING, UNLOADING };
    public String currentInstruction = "N/A";
    public CPU_STATE currentState = CPU_STATE.IDLE;

    public void update(int job, int count, int totalInstr)
    {
        setCurrentJobNumber(job);
        setProgramCounter(count);
        setTotalInstructionsNumber(totalInstr);
    }

    public void update(CPUMetrics metrics)
    {
        setCurrentJobNumber(metrics.currentJobNumber);
        setProgramCounter(metrics.programCounter);
        setTotalInstructionsNumber(metrics.totalInstructionsNumber);
        setCurrentState(metrics.currentState);
        setCurrentInstruction(metrics.currentInstruction);
    }

    public void setCpuNumber(int cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    public void setCurrentJobNumber(int currentJobNumber) {
        this.currentJobNumber = currentJobNumber;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public void setTotalInstructionsNumber(int totalInstructionsNumber) {
        this.totalInstructionsNumber = totalInstructionsNumber;
    }

    public void setCurrentState(CPU_STATE currentState) {
        this.currentState = currentState;
    }

    public void setCurrentInstruction(String currentInstruction) {
        this.currentInstruction = currentInstruction;
    }

    public String getCurrentState()
    {
        switch (currentState)
        {
            case LOADING:
                return "LOADING";
            case RUNNING:
                return "RUNNING";
            case UNLOADING:
                return "UNLOADING";
            default:
                return "IDLE";
        }
    }

    public int getCurrentJobNumber()
    {
        if (currentState == CPU_STATE.IDLE)
        {
            return 0;
        }
        return currentJobNumber;
    }

    public int getTotalInstructionsNumber()
    {
        if (currentState == CPU_STATE.IDLE)
        {
            return 10;
        }
        return totalInstructionsNumber;
    }

    public int getProgramCounter()
    {
        if (currentState == CPU_STATE.IDLE)
        {
            return 0;
        }
        return programCounter;
    }
}
