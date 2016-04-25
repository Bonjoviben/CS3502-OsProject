

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Stephen on 2/8/16.
 */

public final class RAM {


    private RAM()
    {
        _memBlock = new Page[1024/PAGE_SIZE];
        _currentIndex = 0;
        _pageTable = new Integer [1024/PAGE_SIZE];
        Arrays.fill(_pageTable, -1);
    }

    public static void init()
    {
        _memBlock = new Page[1024/PAGE_SIZE];
        _currentIndex = 0;
        _pageTable = new Integer [1024/PAGE_SIZE];
        Arrays.fill(_pageTable, -1);
    }

    private final static int PAGE_SIZE = 64; //make sure power of 2

    private static Page _memBlock[];

    private static Integer _currentIndex = 0;

    private static Integer[] _pageTable;


    public static void addEntryToPageTable(int index, int entry)
    {
        _pageTable[index] = entry;
    }

    public static int getPhysicalPageNumber(int virtualPageNumber) throws InvalidParameterException
    {
        if( virtualPageNumber > _pageTable.length)
            throw new InvalidParameterException("virtual page number is greater than pageTable length. Out of Bounds exception");

        return _pageTable[virtualPageNumber];
    }

    public static void writeRam(int pageNumber, int offset, String value)
    {
        _memBlock[pageNumber].writeToPage(offset, value);
    }

    public static String readRam(int pageNumber, int offset)
    {
        return _memBlock[pageNumber].getData(offset);
    }

    public static int getPageSize()
    {
        return PAGE_SIZE;
    }

    public static int getNextAvailableVirtualPageNumber()
    {
        return Arrays.asList(_pageTable).indexOf(-1);
    }

    public static boolean isRAMFull(){
       return Arrays.asList(_memBlock).indexOf(null) == -1;
    }

    public static boolean isNumPagesAvailable(int num)
    {
        int freePages=0;
        for(int i=0; i<_pageTable.length; i++)
        {
            if(_pageTable[i]==-1)
                freePages++;
        }
        return (freePages>=num);
    }

    //alocates sp
    public static ArrayList<Integer> allocate(int pagesNeeded)
    {
        ArrayList<Integer> pages = new ArrayList<Integer>();
        int pagesAllocated = 0;

        if(!isNumPagesAvailable(pagesNeeded))
            return pages;

        for(int i = 0; i<_pageTable.length && pages.size() < pagesNeeded; i++)
        {
            if(_pageTable[i]==-1)
                pages.add(i);
        }

        for(int i = 0; i < _memBlock.length && pagesAllocated < pagesNeeded; i++)
        {
            if(_memBlock[i] == null)
            {
                _pageTable[pages.get(pagesAllocated)] = i;
                pagesAllocated++;
            }
        }

//        int physAddress=-1;
//        physAddress = Arrays.asList(_memBlock).indexOf(null);
//        if (physAddress != -1)
//            _pageTable[virtualPageNum] = physAddress;
//
//        for(int i=0; i<_memBlock.length; i++)
//        {
//            if(_memBlock[i]==null)
//            {
//                _pageTable[virtualPageNum]=i;
//                physAddress=i;
//                break;
//            }
//        }
        return pages;
    }

    public static void deallocatePcb(PCB pcb)
    {
        if(pcb.getProcessStatus() == PCB.PROCESS_STATUS.TERMINATE)
        {
        int startingVirtualPageNum = Helpers.getPageNumberFromAddress(pcb.getJobMemoryAddress());
        for (int i = 0; i < pcb.getAllocatedVirtualPages().size(); i++)
        {
            int virtualPageNum = pcb.getAllocatedVirtualPages().get(i);
            int physicalPageNum = _pageTable[virtualPageNum];
            _memBlock[physicalPageNum] = null;
            _pageTable[virtualPageNum] = -1;
        }
        }
    }

    public static void fillPage(int pageNum, String[] data) throws Exception
    {
        int i=0;
        _memBlock[pageNum] = new Page(RAM.getPageSize());
        while(!_memBlock[pageNum].isFull() && i < data.length)
        {
            _memBlock[pageNum].addToPage(data[i]);
            i++;
        }
        if(i<data.length)
        {
            throw new Exception("page size mismatch");
        }
    }
//    public static String readRAM(int index) throws InvalidParameterException {
//        if(index > _memBlock.length)
//            throw new InvalidParameterException(String.format("Invalid address for RAM. Length of ram is: %s, given address is: %s",_memBlock.length, index));
//        return _memBlock[index];
//    }
//    //public static byte readRAM(int index) throws InvalidParameterException {
//    //    if(index > _memBlock.length)
//    //        throw new InvalidParameterException(String.format("Invalid address for RAM. Length of ram is: %s, given address is: %s",_memBlock.length, index));
//    //    return _memBlock[index];
//    //}
//
//    public static void writeRam(String val, int index){
//        _memBlock[index] = val;}
//    //public static void writeRam(byte val, int index){
//    //    _memBlock[index] = val;
//    //}





//    private RAM()
//    {
//        _memBlock = new String[1024];
//       // _memBlock = new byte[4096];
//        _currentIndex = 0;
//    }
//
//    private static String _memBlock[] = new String[1024];
//    //private static byte _memBlock[] = new byte[4096];
//
//    private static int _currentIndex = 0;
//
//    public static String readRAM(int index) throws InvalidParameterException {
//        if(index > _memBlock.length)
//            throw new InvalidParameterException(String.format("Invalid address for RAM. Length of ram is: %s, given address is: %s",_memBlock.length, index));
//        return _memBlock[index];
//    }
//    //public static byte readRAM(int index) throws InvalidParameterException {
//    //    if(index > _memBlock.length)
//    //        throw new InvalidParameterException(String.format("Invalid address for RAM. Length of ram is: %s, given address is: %s",_memBlock.length, index));
//    //    return _memBlock[index];
//    //}
//
//    public static void writeRam(String val, int index){
//        _memBlock[index] = val;}
//    //public static void writeRam(byte val, int index){
//    //    _memBlock[index] = val;
//    //}

}

class pageTableEntry{
    pageTableEntry(int physPageNum){
        PhysicalPageNum = physPageNum;
    }
    public int PhysicalPageNum;
    public boolean isDirty;
}
