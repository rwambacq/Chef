package groep10.chef.Class;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoma on 13/04/2018.
 */

public class DTO implements Comparable<DTO> {
    public double price;
    public List<String> lijst = new ArrayList<>();
    public String name;


    public DTO(double price, List<String> lijst, String name){
        this.price = price;
        this.lijst = lijst;
        this.name = name;

    }

    public double getPrice() {
        return price;
    }

    public List<String> getLijst() {
        return lijst;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NonNull DTO dto) {
        if (this.price > dto.price)
            return 1;
        if (this.price < dto.price)
            return -1;
        return 0;
    }
}

