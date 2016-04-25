/**
 * Created by Stephen on 2/8/16.
 * Simulates a physical disk.
 *
 * @author Stephen
 */
public final class Disk {

    private Disk()
    {
        _diskBlock = new String[4096];
    }

    public static void init()
    {
        _diskBlock = new String[4096];
    }

    private static String _diskBlock[] = new String[4096];

    public static String readDisk(int index){return _diskBlock[index];}

    public static void writeDisk(String val, int index){_diskBlock[index] = val;}

    /**
     * used by the LongTermScheduler when loading a job into memory. The chunk
     * returned should fit within a page of memory.
     * @param index
     * @param size
     * @return array of hex strings from disk.
     */
    public static String[] getChunk(int index, int size)
    {
        String[] temp = new String[size];
        int j = 0;
        for (int i  = index; i < index+size; i++)
        {
            temp[j] = _diskBlock[i];
            j++;
        }
        return temp;
    }
}
