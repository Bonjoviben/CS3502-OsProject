import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by Stephen on 4/16/16.
 */
public class OSMetricsPanel extends JPanel {

    public OSMetricsPanel(){
        setBackground(backgroundColor);
        setLayout(new FlowLayout(FlowLayout.LEADING, 10,5));
        init();
        setPreferredSize(new Dimension(1, 25));
        setMinimumSize(new Dimension(1, 25));
        setMaximumSize(new Dimension(2000,25));


    }

    JLabel jobsRanLabel = new JLabel("Total Jobs Completed: ");
    JLabel jobsTotalLabel = new JLabel("Total Jobs: ");
    JLabel jobsInProgLabel = new JLabel("Jobs in Progress: ");
    JLabel AvgJobWaitLabel = new JLabel("Average Job Wait Time: ");
    JLabel AvgJobRunLabel = new JLabel("Average Job Run Time: ");
    JLabel jobsRanVal = new JLabel("4");
    JLabel jobsTotalVal = new JLabel("5");
    JLabel jobsInProgVal = new JLabel("0");
    JLabel AvgJobWaitVal = new JLabel("0");
    JLabel AvgJobRunVal = new JLabel("0");
    private Color backgroundColor = Color.decode("#E0E0E0");

    public void init()
    {
        add(jobsTotalLabel);
        add(jobsTotalVal);
        add(jobsRanLabel);
        add(jobsRanVal);
        add(jobsInProgLabel);
        add(jobsInProgVal);
        add(AvgJobWaitLabel);
        add(AvgJobWaitVal);
        add(AvgJobRunLabel);
        add(AvgJobRunVal);
    }

    public void updateMetrics(final OSMetrics metrics)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jobsInProgVal.setText(Integer.toString(metrics.jobsInProgress));
                jobsRanVal.setText(Integer.toString(metrics.jobsCompleted));
                jobsTotalVal.setText(Integer.toString(metrics.getTotalJobs()));
                AvgJobRunVal.setText(String.format("%.4f", metrics.averageRuntime));
                AvgJobWaitVal.setText(String.format("%.4f", metrics.averageWaitTime));
                jobsInProgVal.revalidate();
                jobsRanVal.revalidate();
                jobsTotalVal.revalidate();
                AvgJobWaitVal.revalidate();
                AvgJobRunVal.revalidate();
                AvgJobRunVal.repaint();
                validate();
                revalidate();
                repaint();
            }
        });
    }

}
