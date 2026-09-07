package sa.psu.se411.exceptions;

import sa.psu.se411.Config;

public class InvalidAgeException extends Exception {

    private Integer age;

    public InvalidAgeException(Integer age) {
        super("Age cannot be less than " + Config.MIN_AGE + ". Provided age: " + age);
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

}