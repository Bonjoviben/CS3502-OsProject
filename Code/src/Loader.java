import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Stephen on 3/11/16.
 */
public class Loader {

    public Loader(String path)
    {
        programFilePath = path;
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (IOException ex)
        {

        }
    }


    public FileReader fileReader;
    public BufferedReader bufferedReader;
    public String programFilePath;
    public boolean loadingComplete = false;

    //card positions
    //job card positions
    final int JOB_NUM_POS = 2;
    final int JOB_INSTR_COUNTR_POS = 3;
    final int JOB_PRIORITY_POS = 4;

    //data card positions
    final int DATA_IN_BUFF_POS = 2;
    final int DATA_OUT_BUFF_POS = 3;
    final int DATA_TEMP_BUFF_POS = 4;


    public void Start() throws IOException
    {
        String line = "";
        int currentIndex = 0;
        int currentJob = 0;
        String[] splitLine;
        PCB pcb;

        while (!loadingComplete)
        {
            line = bufferedReader.readLine();

            if(line != null)
            {
                if(line.contains("JOB"))
                {
                    splitLine = line.split("\\s+");
                    currentJob = Integer.parseInt(splitLine[JOB_NUM_POS], 16);
                    PCBManager.insertPCB(new PCB(currentJob, Integer.parseInt(splitLine[JOB_PRIORITY_POS], 16), Integer.parseInt(splitLine[JOB_INSTR_COUNTR_POS], 16) , currentIndex));
                }
                else if (line.contains("Data"))
                {
                    splitLine = line.split("\\s+");

                    if(PCBManager.getCurrentPcbSortType() != PCBManager.PCB_SORT_TYPE.JOB_NUMBER)
                        PCBManager.sortPcbList(PCBManager.PCB_SORT_TYPE.JOB_NUMBER);

                    PCB currentPCB = PCBManager.getPCB(currentJob);
                    currentPCB.setDataDiskAddress(currentIndex);
                    currentPCB.setInputBuffer(Integer.parseInt(splitLine[DATA_IN_BUFF_POS], 16));
                    currentPCB.setOutputBuffer(Integer.parseInt(splitLine[DATA_OUT_BUFF_POS], 16));
                    currentPCB.setTemporaryBuffer(Integer.parseInt(splitLine[DATA_TEMP_BUFF_POS], 16));
                }
                else if (line.contains("END"))
                {
                    //do nothing with //END line
                }
                else
                {
                    Disk.writeDisk(line, currentIndex);
                    currentIndex++;
                }
            }
            else
                loadingComplete = true;
        }
    }
}
