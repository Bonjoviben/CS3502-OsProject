/**
 * Created by chris on 4/17/2016.
 */
public class OSMetrics {

    public OSMetrics(int totalJobs, int jobsInProgress, int jobsCompleted, double averageWaitTime, double averageRuntime) {
        this.totalJobs = totalJobs;
        this.jobsInProgress = jobsInProgress;
        this.jobsCompleted = jobsCompleted;
        this.averageWaitTime = averageWaitTime;
        this.averageRuntime = averageRuntime;
    }

    public int getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(int totalJobs) {
        this.totalJobs = totalJobs;
    }

    public int getJobsInProgress() {
        return jobsInProgress;
    }

    public void setJobsInProgress(int jobsInProgress) {
        this.jobsInProgress = jobsInProgress;
    }

    public int getJobsCompleted() {
        return jobsCompleted;
    }

    public void setJobsCompleted(int jobsCompleted) {
        this.jobsCompleted = jobsCompleted;
    }

    public double getAverageWaitTime() {
        return averageWaitTime;
    }

    public void setAverageWaitTime(int averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

    public double getAverageRuntime() {
        return averageRuntime;
    }

    public void setAverageRuntime(int averageRuntime) {
        this.averageRuntime = averageRuntime;
    }

    int totalJobs=0;
    int jobsInProgress=0;
    int jobsCompleted=0;
    double averageWaitTime=0;
    double averageRuntime=0;


}
