/**
 * Created by Stephen on 4/9/16.
 */
public enum SchedulingType {

    PRIORITY(1),
    FIFO(2),
    SJF(3);

    private final int type;
    public int val(){return type;}

    SchedulingType(int type)
    {
        this.type = type;
    }

    static SchedulingType fromValue(int value) {
        for (SchedulingType my: SchedulingType.values()) {
            if (my.val() == value) {
                return my;
            }
        }
        return null;
    }





}
