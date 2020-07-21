package ethicalengine;
/**
 * @author Shengyizhao
 * @author ID:990160
 */
public abstract class Character {


    public enum Gender{
        FEMALE,MALE,UNKNOWN
    }
    public enum BodyType{
        AVERAGE, ATHLETIC,OVERWEIGHT,UNSPECIFIED
    }
    private int age;
    private Gender gender;
    private BodyType bodytype;
    public Character(){}
    public Character(int age,Gender gender,BodyType bodytype){
        /**
         * this method set for a new character
         * @param age,profession,gender,bodytype
         * @return
         * @throws IllegalStateException
         */
        if (age < 0) { throw new IllegalStateException(); }
        else{
            this.age=age;
            this.gender=gender;
            this.bodytype=bodytype;
        }
    }

    public int getAge(){
        /**
         * this method return the age of character
         * @param  age the age of character
         * @return age
         */
        return this.age;
    }

    public Gender getGender(){

        /**
         * this method return the gender of character
         * @param gender the enum of gender
         * @return gender
         */
        return this.gender;
    }

    public BodyType getBodytype(){
        /**
         * this method return the bodytype of a character
         * @param bodytype
         * @return
         * @throws IllegalStateException
         */
        return this.bodytype;}

    public void setAge(int age) {
        /**
         * this method set the age of character
         * @param  age
         * @throws IllegalStateException();
         */
        if (age < 0) { throw new IllegalStateException(); }
        else{this.age = age;}
    }

    public void setGender(Gender gender) {
        /**
         * this method set the gender of character
         * @param  gender
         */
        this.gender = gender;
    }

    public void setBodytype(BodyType bodytype) {

        /**
         * this method set the gender of character
         * @param  bodytype
         */this.bodytype = bodytype;
    }
}
