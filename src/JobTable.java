import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;

/**
 * Created by Stephen on 4/16/16.
 */
public class JobTable extends JTable {
    public JobTable()
    {
        super(tableModel);
        //tableModel = new JobTableModel();
//        colName = new String[] {"Job #", "CPU #", "Wait Time", "Run Time", "RAM Used"};
//        tableModel = new DefaultTableModel(colName,0);
    }

    public static String[] colName = {"Timestamp", "Job #", "CPU #", "Wait Time", "Run Time", "Blocks Used"};
//    public static DefaultTableModel tableModel = new DefaultTableModel(colName,2);
    public static JobTableModel tableModel = new JobTableModel();

    public void clearTable()
    {
        tableModel.clearTable();
    }


}

class JobTableModel extends DefaultTableModel{

    private static final String[] columnNames = {"Timestamp", "Job #", "CPU #", "Wait Time", "Run Time", "Blocks Used", "I/O Used"};
    private final LinkedList<JobMetrics> jobMetricses;

    public JobTableModel() {
        jobMetricses = new LinkedList<JobMetrics>();
    }

    public void addElement(JobMetrics e) {
        // Adds the element in the last position in the list
        jobMetricses.add(e);
        fireTableRowsInserted(jobMetricses.size()-1, jobMetricses.size()-1);
    }

    public void update(JobMetrics e)
    {
        boolean found = false;
        for (JobMetrics metric : jobMetricses)
        {
            if (metric.getTimestamp() == e.getTimestamp() && metric.getJobNumber() == e.getJobNumber())
            {
                metric.update(e);
                found = true;
            }
        }
        if (!found)
            addElement(e);
        else
            fireTableRowsInserted(jobMetricses.size()-1, jobMetricses.size()-1);
    }
    public void clearTable()
    {
        jobMetricses.clear();
        fireTableRowsDeleted(jobMetricses.size()-1, jobMetricses.size()-1);

    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
       if(jobMetricses == null)
           return 0;
        return jobMetricses.size();
    }
    
    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//        switch(columnIndex) {
//            case 0: return jobMetricses.get(rowIndex).getColumnOne();
//            case 1: return jobMetricses.get(rowIndex).getColumnOne();
//        }
        return jobMetricses.get(rowIndex).getColumn(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (jobMetricses.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }




}


