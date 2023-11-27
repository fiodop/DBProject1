
public class Person {

    private String name;

    private int age;

    private String gender;


    public Person() {

    }


    public String toString() {
        return "name - " + this.name + ", age - " + this.age + ", gender - " + this.gender;
    }


    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }

    /**
     * присвоение пола
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}