/**
 * Created by Stephen on 4/16/16.
 */
public class JobMetrics {
    public JobMetrics() {
        jobNumber = -1;
        cpuNo = -1;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public int getBlocksUsed() {
        return blocksUsed;
    }

    public void setBlocksUsed(int blocksUsed) {
        this.blocksUsed = blocksUsed;
    }


    public int getCpuNo() {
        return cpuNo;
    }

    public void setCpuNo(int cpuNo) {
        this.cpuNo = cpuNo;
    }

    public int getWaitTime() {
        if(getEndWaitTime() == 0)
            return 0;
        return (int)(getEndWaitTime() - getStartWaitTime());
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getRunTime() {
        if(getEndRunTime() == 0)
            return 0;
        return (int)(getEndRunTime() - getStartRunTime());
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }


    public long getEndWaitTime() {
        return endWaitTime;
    }

    public void setEndWaitTime(long endWaitTime) {
        this.endWaitTime = endWaitTime;
    }

    public long getStartWaitTime() {
        return startWaitTime;
    }

    public void setStartWaitTime(long startWaitTime) {
        this.startWaitTime = startWaitTime;
    }

    public long getEndRunTime() {
        return endRunTime;
    }

    public void setEndRunTime(long endRunTime) {
        this.endRunTime = endRunTime;
    }

    public long getStartRunTime() {
        return startRunTime;
    }

    public void setStartRunTime(long startRunTime) {
        this.startRunTime = startRunTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void update(JobMetrics metrics)
    {
        setBlocksUsed(metrics.getBlocksUsed());
        setTimestamp(metrics.getTimestamp());
        setCpuNo(metrics.getCpuNo());
        setEndRunTime(metrics.getEndRunTime());
        setEndWaitTime(metrics.getEndWaitTime());
        setStartRunTime(metrics.getStartRunTime());
        setStartWaitTime(metrics.getStartWaitTime());
        setJobNumber(metrics.getJobNumber());
        setIos(metrics.getIos());
    }

    public Object getColumn(int col)
    {
        switch (col)
        {
            case 0:
                return getTimestamp();
            case 1:
                return getJobNumber();
            case 2:
                return getCpuNo();
            case 3:
                return getWaitTime();
            case 4:
                return getRunTime();
            case 5:
                return getBlocksUsed();
            case 6:
                return getIos();
        }
        return getJobNumber();
    }

    public int getIos() {
        return ios;
    }

    public void setIos(int ios) {
        this.ios = ios;
    }




    private int jobNumber=-1;
    private int blocksUsed =0;
    private int cpuNo=-1;
    private int waitTime=0;
    private int runTime=0;
    private long timestamp = 0;
    public long startRunTime = 0;
    public long endRunTime = 0;
    public long startWaitTime = 0;
    public long endWaitTime = 0;
    public int ios = 0;




}
