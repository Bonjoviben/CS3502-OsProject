import java.util.concurrent.Future;

/**
 * Currently not used since the DMA functionality is hard coded into the cpu
 * @see CPU#executeInstruction(int) case 0 and 1, read and write
 */
public class DMA {

    boolean work = true;
    int num = 0;
    int io = 0;
    int sig;

    void read(int channel, String ramPhysAddress, String startInto){


    }

    void write(int channel, String ramPhysAddress, String startInto){
        //
    }


    Future<String> asynch_read(int channel, String ramPhysAddress, String startInto){
        Future<String> future = null;
        return future;

    }

    //signal sends 1 to cpu when channel ready togo back to cpu, else sends 0
    int signal(int tf){
        //condition here
        return 0;
    }

    void active(int channel, String ramPhysAddress, String startInto){
        while(work){
            switch(num){
                case 0:
                    read(channel, ramPhysAddress, startInto);
                    //if(){

                    //}
                    //else{
                    //    break;
                    //}
                    break;
                case 1:
                    write(channel, ramPhysAddress, startInto);
                    //if(){

                    //}
                    //else{
                    //    break;
                    //}
                    break;
            }
            io = io + 4; // <-- or whatever im trying to do...
        }
        signal(sig);
    }

}
