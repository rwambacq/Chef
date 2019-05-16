package adapter;

public class Concierge extends SecurityService {
    
    public Concierge(String name){
        super(name);
    }
    
    @Override
    public void handleEmergency(String location, String type) {
        System.out.println(name+" goes to "+location+" for "+type);
    }
    
}
