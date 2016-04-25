import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * JPanel that displays metrics for each cpu.
 * This panel is split into three sections. Top contains the
 * CPU number and job number. Middle contains the current
 * instruction and current state of the CPU. Bottom section
 * contains the progress bar that represents the progress
 * of each job on the CPU.
 *
 *
 * @author Stephen
 * @see MetricsDialog
 */
public class CPUMetricsPanel extends JPanel {

    public CPUMetricsPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(backgroundColor);
        add(getTopPanel());
        setMaximumSize(new Dimension(1000, 80));
        add(getJobPanel());
        bar = getProgressBar();
        add(bar);
        setBorder(new LineBorder(Color.decode("#212121"),2));
    }

    /**
     * Controls the background color of the CpuPanel
     */
    private Color backgroundColor = Color.decode("#E0E0E0");
    JLabel cpuNum = new JLabel("CPU NUM");
    JLabel jobNumLbl = new JLabel("JOB: ");
    JLabel jobNum = new JLabel("12");
    private JLabel stateVal = new JLabel("LOADING");
    private JLabel currentInstrVal = new JLabel("N/A");
    private JProgressBar bar;

    private JPanel getTopPanel()
    {
        JPanel panel = new JPanel();
        BorderLayout borderLayout = new BorderLayout(5,0);
        panel.setLayout(borderLayout);
        panel.setBackground(backgroundColor);
        cpuNum = new JLabel("CPU NUM");
        cpuNum.setAlignmentX(Component.LEFT_ALIGNMENT);
        jobNum = new JLabel("12");
        jobNum.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jobNumLbl = new JLabel("JOB:");

        panel.add(cpuNum, BorderLayout.LINE_START);
        panel.add(jobNumLbl, BorderLayout.CENTER);
        panel.add(jobNum, BorderLayout.LINE_END);
        jobNum.setHorizontalAlignment(JLabel.LEFT);
        jobNumLbl.setHorizontalAlignment(JLabel.RIGHT);

        return panel;
    }


    private JPanel getJobPanel()
    {
        JPanel panel = new JPanel();
        GridLayout grid = new GridLayout(0, 2);

        panel.setLayout(grid);
        panel.setPreferredSize(new Dimension(200, 30));
        panel.setBackground(backgroundColor);
        panel.add(new JLabel("Current State: "));
        panel.add(stateVal);
        panel.add(new JLabel("Current Instr: "));
        panel.add(currentInstrVal);

        return panel;
    }

    private JProgressBar getProgressBar()
    {
        JProgressBar bar = new JProgressBar(0, 10);
        bar.setValue(5);
        return bar;
    }

    public void updateProgressBar(final int val)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bar.setValue(val);
            }
        });

    }

    public void updateMetrics(final CPUMetrics metrics)
    {
                cpuNum.setText(Integer.toString(metrics.cpuNumber));
                jobNum.setText(Integer.toString(metrics.getCurrentJobNumber()));
                bar.setMaximum(metrics.getTotalInstructionsNumber());
                bar.setValue(metrics.getProgramCounter());
                currentInstrVal.setText(metrics.currentInstruction);
                stateVal.setText(metrics.getCurrentState());
                currentInstrVal.validate();
                stateVal.validate();
                cpuNum.validate();
                jobNum.validate();
                bar.validate();
                revalidate();
    }


    /**
     * Had issues with components updating, added this function to make sure that everything is revalidated and repainted.
     */
    public void repaintAndValidate()
    {
        cpuNum.revalidate();
        jobNum.revalidate();
        bar.revalidate();
        currentInstrVal.revalidate();
        stateVal.revalidate();
        revalidate();
    }

}
