import java.security.InvalidParameterException;
import java.util.Formatter;

/**
 * Created by ethan on 3/11/2016.
 */
public class MMU {
    //RAM ram;
    //Disk d;

    static int nextEmptyDiskSlot = 0;
    static int nextEmptyRamSlot = 0;

    int ram = 1024;
    int disk = 4096;
    int page_num;


    public static String read(int virtualAddress)
    {
        int virtualPageNum = getVirtualPageNumber(virtualAddress);
        int offset = getOffset(virtualAddress);

        return readFromPhysical(getPhysicalPageNumber(virtualPageNum), offset);
    }

    public static void write(int virtualAddress, String value)
    {
        int virtualPageNum = getVirtualPageNumber(virtualAddress);
       // System.out.println("XXvirtual page number: " + virtualPageNum);
        int offset = getOffset(virtualAddress);

        writeToPhysical(getPhysicalPageNumber(virtualPageNum), offset, value);
    }

    public static void writeToPhysical(int physicalPageNumber, int offset, String value )
    {
        RAM.writeRam(physicalPageNumber, offset, value);
    }

    public static String readFromPhysical(int physicalPageNumber, int offset)
    {
        return RAM.readRam(physicalPageNumber, offset);
    }

    public static int getVirtualPageNumber(int virtualAddress)
    {
        return virtualAddress/RAM.getPageSize();

    }

    public static int getOffset(int virtualAddress)
    {
        return virtualAddress%RAM.getPageSize();
    }

    public static int getPhysicalPageNumber(int virtualPageNumber)
    {
        return RAM.getPhysicalPageNumber(virtualPageNumber);
    }

    public static void synchronizeCache(PCB job)
    {
        int cacheSize = job.getCacheSize();
        int startDiskAddress = job.getJobDiskAddress();
        int currentDiskAddress = startDiskAddress;
        int currentMemoryAddress = job.getJobMemoryAddress();

      //  System.out.println("SYNCH CACHE JOB " + job.getJobNumber());
        for (int i = 0; currentDiskAddress < startDiskAddress + cacheSize; i++)
        {
            //System.out.println("writing:"+i + "currDisk: " + currentDiskAddress);
            int virtualPage = job.getAllocatedVirtualPages().get(i / RAM.getPageSize());
            int virtualPageTimesSize = virtualPage * RAM.getPageSize();
            int finalAdd = virtualPageTimesSize + i%RAM.getPageSize();
            //System.out.println("Virtual Page: " + virtualPage + " virtualPage * pagesize: " + virtualPageTimesSize + " final address: " + finalAdd + " val: " + job.getCache()[i]);
            write(job.getAllocatedVirtualPages().get(i / RAM.getPageSize()) * RAM.getPageSize() + i%RAM.getPageSize(), job.getCache()[i]);
            Disk.writeDisk(job.getCache()[i], currentDiskAddress);
            currentDiskAddress++;
            currentMemoryAddress++;
        }


    }


    //String EffectiveAddress(String logicalAddress, String baseRegister){
    //    String absoluteAddress = "";
    //    //direct
    //    if(baseRegister == null){absoluteAddress = baseRegister + offset;}
    //    //indirect
    //    else{absoluteAddress = baseRegister + logicalAddress + offset;}
    //    return absoluteAddress;
    //}

    //-save data to disk, when job line finishes sending,
    // return starting disk address, ending disk address,
    // and job size
    //...hopefully...
    //1 byte = 2 hex characters
    //disk is 2048 words, so 8192 bytes, so 16384 hex characters, so byte array of 8192?
    //RAM is 1024 words, so 4096 bytes, so 8192 hex characters, so byte array of 4096?
    //if paging how many addressable bytes?

    //for below to work, think in bytes
    /////////////////////////////////done in bytes, sends back start, end and size////////////////////////////////////////////////
    //write to disk and return starting address, end address, and size
    //change disk and ram memory into byte array in order to work
//    public static int[] writeToDiskByte(String in){
//        int info[] = new int[3];
//        info[0] = nextEmptyDiskSlot;
//        byte[] ba = new byte[in.length() / 2];
//        for(int i = 0; i < in.length(); i+=2){// i++){
//            ba[i / 2] = (byte) ((Character.digit(in.charAt(i), 16) << 4)
//                    + Character.digit(in.charAt(i+1), 16));
//        }
//        for(int i = 0; i < ba.length;i++){
//            //test[nextEmptyDiskSlot] = ba[i];
//           // Disk.writeDisk(ba[i], nextEmptyDiskSlot);
//            nextEmptyDiskSlot = nextEmptyDiskSlot + 1;
//        }
//        info[1] = (nextEmptyDiskSlot - 1);
//        info[2] = (info[1] - info[0] + 1);
//        return info;
//    }
//
//    public int[] writeToRAMByte(String str, String in){
//        int info[] = new int[3];
//        info[0] = nextEmptyRamSlot;
//        byte[] ba = new byte[in.length() / 2];
//        for(int i = 0; i < in.length(); i+=2){// i++){
//            ba[i / 2] = (byte) ((Character.digit(in.charAt(i), 16) << 4)
//                    + Character.digit(in.charAt(i+1), 16));
//        }
//        for(int i = 0; i < ba.length;i++){
//            //test[nextEmptyRamSlot] = ba[i];
//          //  RAM.writeRam(ba[i], nextEmptyDiskSlot);
//            nextEmptyRamSlot = nextEmptyRamSlot + 1;
//        }
//        info[1] = (nextEmptyRamSlot - 1);
//        info[2] = (info[1] - info[0] + 1);
//        return info;
//    }
//
//    //returns string array of instruction/data in 8 hex characters(each) from disk
//    //DONE IN BYTES
//
//    public static String[] retrieveFromDisk(int numOfInstructions, int startIndex){
//        String everything[] = new String[numOfInstructions];
//        int limit = (numOfInstructions * 4);
//        String jumbled = "";
//        int pullFrom = startIndex;
//        Formatter f = new Formatter();
//        for(int i = startIndex; i < startIndex + limit ;i++){
//            f.format("%02x", Disk.readDisk(i));//test[i]);
//        }
//        jumbled = f.toString();
//        String t = "";
//        int c = 0;
//        int k = 0;
//        System.out.println(jumbled);
//        for(int i = 0; i < jumbled.length() ; i++){ //<---------------figure
//            t = t + String.valueOf(jumbled.charAt(i));
//            if(c == 7){
//                everything[k] = t;
//                t = "";
//                c = -1;
//                k++;
//            }
//            c++;
//        }
//        return everything;
//    }
//    //returns string array of instructions/data in 8 hex characters(each) from ram
//
//    public String[] retrieveFromRam(int numofInstructions, int startIndex){
//        String everything[] = new String[numofInstructions];
//        int limit = (numofInstructions * 4);
//        String jumbled = "";
//        int pullFrom = startIndex;
//        Formatter f = new Formatter();
//        for(int i = startIndex; i < startIndex + limit ;i++){
//            f.format("%02x", RAM.readRAM(i));//test[i]);
//        }
//        jumbled = f.toString();
//        String t = "";
//        int c = 0;
//        int k = 0;
//        for(int i = 0; i < jumbled.length() ; i++){ //<---------------figure
//            t = t + String.valueOf(jumbled.charAt(i));
//            if(c == 7){
//                everything[k] = t;
//                t = "";
//                c = -1;
//                k++;
//            }
//            c++;
//        }
//        return everything;
//    }
//
//    /////////////////////////////////done in string/////////////////////////////////////////////////
//    public static void writeToDisk(String str, int in){
//        //int beg = Helpers.convertFromHexToDecimal(in);
//        for(int i = 0; i < str.length(); i++){
//            Disk.writeDisk(String.valueOf(str.charAt(i)), in);
//            in++;
//        }
//    }
//
//    public static void writeToRAM(String str, int in){
//        //int beg = Helpers.convertFromHexToDecimal(in);
//       // for(int i = 0; i < str.length(); i++){
////            RAM.writeRam(String.valueOf(str.charAt(i)), nextEmptyRamSlot);
////            nextEmptyRamSlot = nextEmptyRamSlot + 1;
//          //  RAM.writeRam(String.valueOf(str.charAt(i)), in);
//         //   in++;
//       // }
//        RAM.writeRam(str, in);
//    }
//
//////////////////////////////////////////////////////////////////////////////////////////////
//    public int getRamIndex(){
//        int i = 0;
//
//        return i;
//    }
//
//    public int getDiskIndex(){
//        int i = 0;
//
//        return i;
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static String getFromRAM(int index){
//        return RAM.readRAM(index);
//    }
//
//    public static String getFromDisk(int index){
//        return Disk.readDisk(index);
//    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}