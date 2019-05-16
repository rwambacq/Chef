package alarm;

public class Main {
    
    public static void main(String[] args){
        AlarmListenerFactory fac = new AlarmListenerFactory();

        Hospital hospital = fac.createHospital("UZ");
        PoliceDepartment police = fac.createPoliceDepartement();
        FireDepartement fire = fac.createFireDepartement();
        
        EmergencyCallCenter callCenter = new EmergencyCallCenter("112");
        callCenter.addListener(hospital);
        callCenter.addListener(police);
        callCenter.addListener(fire);
        
        callCenter.incomingCall("crash", "Plateaustraat");
        callCenter.incomingCall("assault", "Veldstraat");
        callCenter.incomingCall("fire", "Zwijnaardse Steenweg");
        
    }
}
