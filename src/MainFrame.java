import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by Stephen on 4/16/16.
 */
public class MainFrame {

    public static JTextField input1=new JTextField("4", 3);
    public static JTextField input2=new JTextField("0", 4);
    public static JComboBox box;
    public static ConsoleConstructor con=new ConsoleConstructor();
    public static Callable driverFunction = new Callable() {
        @Override
        public Object call() throws Exception {
            return null;
        }
    };
    private static JFrame frame;
    public static MetricsDialog metricsDialog;

    public static void buildUI()
    {
        frame = new JFrame("JequirityOS");
        metricsDialog = new MetricsDialog(frame, 4);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(JFrame.WIDTH);

        JPanel options =new JPanel(new FlowLayout());
        options.setBackground(Color.decode("#212121"));
        options.setPreferredSize(new Dimension(JFrame.WIDTH, 40));
        JLabel text1 = new JLabel("Number of CPUs");
        text1.setForeground(Color.decode("#8BC34A"));

        JLabel text2 = new JLabel("Type of Scheduling: ");
        JLabel text3 = new JLabel("Sleep Delay");
        String[] ops= {"Priority (Non Pre-emptive)","FIFO","SJF"};
        box=new JComboBox(ops);
        text2.setForeground(Color.decode("#8BC34A"));
        text3.setForeground(Color.decode("#8BC34A"));
        JButton startButton= new JButton("START");
        final JButton metricButton = new JButton("SHOW METRICS");

        JLabel logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/src/JOSlogoV3.png"));
        //logo.setPreferredSize(new Dimension(JFrame.WIDTH, 200));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setAlignmentX(frame.getContentPane().CENTER_ALIGNMENT);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    con.clear();
                   //metricsDialog = new MetricsDialog(frame, Integer.parseInt(input1.getText()));
                    metricsDialog.init(frame, Integer.parseInt(input1.getText()), metricsDialog.getSize());
                    Driver.init(Integer.parseInt(input1.getText()), SchedulingType.fromValue(box.getSelectedIndex()+1),Integer.parseInt(input2.getText()) );

                    Thread driver = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Driver.run();
                            }
                            catch (IOException ex)
                            {

                            }
                        }
                    });
                    driver.start();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            metricsDialog.repaint();
                            metricsDialog.revalidate();
                        }
                    });
                    metricsDialog.repaint();
                    metricsDialog.validate();
                    metricsDialog.setVisible(true);


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        metricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metricsDialog.setLocationRelativeTo(null);
                System.out.println("Metrics button called");
                metricsDialog.setVisible(true);
            }
        });
        options.add(text1);
        options.add(input1);
        options.add(text2);
        options.add(box);
        options.add(text3);
        options.add(input2);
        options.add(startButton);
        options.add(metricButton);

        MatteBorder border=new MatteBorder(0, 40, 40, 40, Color.decode("#212121"));

        con.setBorder(border);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.getContentPane().add(logo);
        frame.getContentPane().add(options);
        frame.getContentPane().add(con);
        frame.getContentPane().setBackground(Color.decode("#212121"));
        frame.pack();
        frame.setVisible(true);
    }

    public static void updateCPUMetrics(final CPUMetrics metrics)
    {
                metricsDialog.updateCpuMetrics(metrics);
    }

    public static void updateOsMetrics(final OSMetrics metrics)
    {
        metricsDialog.updateOsMetrics(metrics);
    }

    public static void updateJobMetrics(final JobMetrics jobMetrics)
    {
        metricsDialog.updateJobMetrics(jobMetrics);
    }

    public static void signalOSFinish()
    {
        JOptionPane.showMessageDialog(frame,
                String.format("%s jobs completed in %s seconds", PCBManager.getJobListSize(), Driver.calcOSRunTime()),
                "JOS System Run Finished",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(System.getProperty("user.dir") + "/src/JOSlogo.png")
        );
    }
}