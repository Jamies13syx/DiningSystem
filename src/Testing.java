import java.util.Calendar;

public class Testing {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        System.out.println(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        System.out.println(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(cal.get(Calendar.WEEK_OF_MONTH));
        System.out.println(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cal.getTime());
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_MONTH, 1 - cal.get(Calendar.DAY_OF_WEEK));
        System.out.println(cal.getTime());
    }
}
