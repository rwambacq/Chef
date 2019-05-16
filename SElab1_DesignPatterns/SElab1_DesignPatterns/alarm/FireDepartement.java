package alarm;

public class FireDepartement implements AlarmListener {

    private String name;

    public FireDepartement(){}

    @Override
    public void alarm(Alarm alarm) {
        System.out.println("Fire squad on the move to " + alarm.getLocation() + " for " + alarm.getType() + " ");
    }

}
