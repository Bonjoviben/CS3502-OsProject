/**
 * Created by chris on 4/11/2016.
 */
public class Page {
    public String[] array;
    public int nextSlot=0;
    int size=0;
    int job;
    Page(int psize, int jobNo)
    {
        array= new String[psize];
        size=psize;
        job=jobNo;
    }
    Page(int psize)
    {
        array= new String[psize];
        size=psize;
    }
    public boolean isFull()
    {
        if(nextSlot>=size)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public void addToPage(String data)
    {
        if(!isFull())
        {
            array[nextSlot]=data;
            nextSlot++;
        }
    }
    public int getPageSize()
    {
        return size;
    }
    public String getData(int index)
    {
        return array[index];
    }
    public int getJob()
    {
        return job;
    }
    public void writeToPage(int offset, String data)
    {
      array[offset]=data;
    }
}
