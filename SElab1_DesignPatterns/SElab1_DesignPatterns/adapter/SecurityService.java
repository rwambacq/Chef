package adapter;

public class SecurityService implements EventConsumer {

    protected String name;
    
    public SecurityService(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public void handleEmergency(String location, String type) {
        System.out.println(name+" sends security team to "+location+" for "+type);
    }
    
}
