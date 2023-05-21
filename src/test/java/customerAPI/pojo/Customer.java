package customerAPI.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data // Lombok annotation to provide boilerplate codes
@JsonIgnoreProperties(value = "id", allowSetters = true) // to ignore id field during serialization

public class Customer {
    private int id;
    private String name;
    private String age;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}

