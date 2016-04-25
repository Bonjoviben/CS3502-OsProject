import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Stephen on 4/16/16.
 */
public class MetricsDialog extends JDialog {
    JButton addbtn = new JButton("Add stuff");
    public JobTable jobTable = new JobTable();
    private  JPanel topPanel = new JPanel();
    private JScrollPane scrollTopPanel;
    private JPanel mainPanel = new JPanel();

    MetricsDialog(Frame frame, int numOfCpus) {
        super(frame);
        setTitle("METRICS");
        // setSize(new Dimension(200,100));
        setPreferredSize(new Dimension(900, 400));
        setLocationRelativeTo(null);
        //getContentPane().setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        cpuPanels = new CPUMetricsPanel[numOfCpus];
        for (int i = 0; i < cpuPanels.length; i++)
            cpuPanels[i] = new CPUMetricsPanel();


        topPanel.setBackground(Color.decode("#424242"));
        //topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setLayout(new ModifiedFlowLayout());
        for (int i = 0; i < cpuPanels.length; i++)
            topPanel.add(cpuPanels[i]);
        scrollTopPanel = new JScrollPane(topPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTopPanel.setPreferredSize(new Dimension(1000,200));
//        topPanel.add(new CPUMetricsPanel());
//        topPanel.add(new CPUMetricsPanel());
//        topPanel.add(new CPUMetricsPanel());

        //getContentPane().add(new CPUMetricsPanel(), BorderLayout.CENTER);


        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(scrollTopPanel);
       // mainPanel.add(topPanel);
        mainPanel.add(osMetrics);

        jobTable.setAutoCreateRowSorter(true);
        jobTable.getColumnModel().setColumnMargin(10);
        jobTable.clearTable();


        JScrollPane tablePane = new JScrollPane(jobTable);



        addbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add button called");

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cpuPanels[1].updateProgressBar(2);

                    }
                });
            }
        });




//        JPanel tPanel = new JPanel();
//        tPanel.add(addbtn);
//        tPanel.add(tPanel);
        mainPanel.add(tablePane);
//        mainPanel.add(tPanel);
       // mainPanel.add(addbtn);

        // getContentPane().add(topPanel);
//        getContentPane().add(new OSMetricsPanel());
        getContentPane().add(mainPanel);
        getContentPane().setBackground(Color.green);
        //setVisible(true);
        pack();
        mainPanel.revalidate();
    }

    CPUMetricsPanel[] cpuPanels = new CPUMetricsPanel[4];
    OSMetricsPanel osMetrics = new OSMetricsPanel();


    public void init(Frame frame, int numOfCpus, Dimension dim) {
        setTitle("METRICS");
        // setSize(new Dimension(200,100));
        setLocationRelativeTo(null);
        setSize(dim);
        mainPanel.removeAll();
        topPanel.removeAll();
        scrollTopPanel.removeAll();
        //getContentPane().setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        cpuPanels = new CPUMetricsPanel[numOfCpus];
        for (int i = 0; i < cpuPanels.length; i++)
            cpuPanels[i] = new CPUMetricsPanel();


        topPanel.setBackground(Color.decode("#424242"));
        //topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setLayout(new ModifiedFlowLayout());
        for (int i = 0; i < cpuPanels.length; i++)
            topPanel.add(cpuPanels[i]);
        scrollTopPanel = new JScrollPane(topPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTopPanel.setPreferredSize(new Dimension(1000,200));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(scrollTopPanel);
        // mainPanel.add(topPanel);
        mainPanel.add(osMetrics);

        jobTable.setAutoCreateRowSorter(true);
        jobTable.getColumnModel().setColumnMargin(10);
        jobTable.clearTable();


        JScrollPane tablePane = new JScrollPane(jobTable);



        addbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add button called");

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cpuPanels[1].updateProgressBar(2);

                    }
                });
            }
        });

        mainPanel.add(tablePane);

        getContentPane().add(mainPanel);
        getContentPane().setBackground(Color.green);

        pack();
        mainPanel.revalidate();
    }



    public void updateCpuMetrics(final CPUMetrics metrics)
    {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
                cpuPanels[metrics.cpuNumber-1].updateMetrics(metrics);
                cpuPanels[metrics.cpuNumber-1].revalidate();
                revalidate();
//            }
//        });
    }

    public void updateOsMetrics(final OSMetrics metrics)
    {

        osMetrics.updateMetrics(metrics);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                osMetrics.revalidate();
                osMetrics.repaint();
                mainPanel.validate();
            }
        });
    }

    public void updateJobMetrics(final JobMetrics metrics)
    {
        jobTable.tableModel.update(metrics);
    }

    public void repaintAndValidate()
    {
        invalidate();
        scrollTopPanel.revalidate();
        topPanel.revalidate();
        mainPanel.revalidate();
        mainPanel.repaint();
        revalidate();
        repaint();

        for (CPUMetricsPanel panel : cpuPanels)
        {
            panel.repaintAndValidate();
            panel.revalidate();
            panel.repaint();
        }
    }

}

class ModifiedFlowLayout extends FlowLayout {
    public ModifiedFlowLayout() {
        super();
    }

    public ModifiedFlowLayout(int align) {
        super(align);
    }
    public ModifiedFlowLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    public Dimension minimumLayoutSize(Container target) {
        // Size of largest component, so we can resize it in
        // either direction with something like a split-pane.
        return computeMinSize(target);
    }

    public Dimension preferredLayoutSize(Container target) {
        return computeSize(target);
    }

    private Dimension computeSize(Container target) {
        synchronized (target.getTreeLock()) {
            int hgap = getHgap();
            int vgap = getVgap();
            int w = target.getWidth();

            // Let this behave like a regular FlowLayout (single row)
            // if the container hasn't been assigned any size yet
            if (w == 0) {
                w = Integer.MAX_VALUE;
            }

            Insets insets = target.getInsets();
            if (insets == null){
                insets = new Insets(0, 0, 0, 0);
            }
            int reqdWidth = 0;

            int maxwidth = w - (insets.left + insets.right + hgap * 2);
            int n = target.getComponentCount();
            int x = 0;
            int y = insets.top + vgap; // FlowLayout starts by adding vgap, so do that here too.
            int rowHeight = 0;

            for (int i = 0; i < n; i++) {
                Component c = target.getComponent(i);
                if (c.isVisible()) {
                    Dimension d = c.getPreferredSize();
                    if ((x == 0) || ((x + d.width) <= maxwidth)) {
                        // fits in current row.
                        if (x > 0) {
                            x += hgap;
                        }
                        x += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                    else {
                        // Start of new row
                        x = d.width;
                        y += vgap + rowHeight;
                        rowHeight = d.height;
                    }
                    reqdWidth = Math.max(reqdWidth, x);
                }
            }
            y += rowHeight;
            y += insets.bottom;
            return new Dimension(reqdWidth+insets.left+insets.right, y);
        }
    }

    private Dimension computeMinSize(Container target) {
        synchronized (target.getTreeLock()) {
            int minx = Integer.MAX_VALUE;
            int miny = Integer.MIN_VALUE;
            boolean found_one = false;
            int n = target.getComponentCount();

            for (int i = 0; i < n; i++) {
                Component c = target.getComponent(i);
                if (c.isVisible()) {
                    found_one = true;
                    Dimension d = c.getPreferredSize();
                    minx = Math.min(minx, d.width);
                    miny = Math.min(miny, d.height);
                }
            }
            if (found_one) {
                return new Dimension(minx, miny);
            }
            return new Dimension(0, 0);
        }
    }

}
