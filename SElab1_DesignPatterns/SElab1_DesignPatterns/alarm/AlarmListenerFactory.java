package alarm;

public class AlarmListenerFactory {
    public Hospital createHospital(String naam){
        return new Hospital(naam);
    }

    public PoliceDepartment createPoliceDepartement(){
        return new PoliceDepartment();
    }

    public FireDepartement createFireDepartement(){
        return new FireDepartement();
    }
}
