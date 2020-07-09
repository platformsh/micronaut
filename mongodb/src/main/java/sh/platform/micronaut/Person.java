package sh.platform.micronaut;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class Person {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    private String country;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
