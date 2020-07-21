package ethicalengine;
/**
 * @author Shengyizhao
 * @author ID:990160
 */


public class Person extends Character {


    public enum AgeCategory {BABY, CHILD, ADULT, SENIOR}
    public enum Profession {DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, UNKNOWN,NONE,DRIVER,TEACHER}
    private boolean isPregnant;
    private Profession profession;
    private boolean isYou;
    public Person(int age, Profession profession, Gender gender, BodyType bodytype, boolean isPregnant) {
        /**
         * this method set for a new person class
         * @param age,profession,gender,bodytype,isPregnant
         */
        super(age, gender, bodytype);
        this.profession=profession;
        this.isPregnant=isPregnant;
    }

    Person(Person otherPerson) {
        /**
         * this method copy a another person
         * @param age,profession,gender,bodytype,isPregnant
         */
        super.setAge(otherPerson.getAge());
        super.setBodytype(otherPerson.getBodytype());
        super.setGender(otherPerson.getGender());
        this.setPregnant(otherPerson.isPregnant());
        this.setAsyou(otherPerson.isYou());
        this.profession=otherPerson.getProfession();
    }
    public BodyType getbodytype(){
        return super.getBodytype();
    }

    public AgeCategory getAgeCategory(){
        /**
         * this method return the bodytype of person
         * @param age the age of person
         * @return bodytype
         */
        if (this.getAge() >= 0 && this.getAge() <= 4) {
            return AgeCategory.BABY;
        }
        else if (this.getAge() >= 5 && this.getAge() <= 16) {
            return AgeCategory.CHILD;
        }
        else if (this.getAge() >= 17 && this.getAge() <= 68) {
            return AgeCategory.ADULT;
        }
        else {
            return AgeCategory.SENIOR;
        }
    }
    public void setProfession(Profession profession){
        /**
         * this method set the profession of person
         * @param profession the profession of person
         */
        this.profession=profession;
    }
    public Profession getProfession(){
        if(this.getAgeCategory()==AgeCategory.ADULT){
            return this.profession;
        }else {
            return Profession.NONE;
        }
    }
    public boolean isPregnant(){
        /**
         * this method return if the person is pregnant
         * @param isPregnant
         * @return isPregnant
         * @throws if the person is not female, is not pregnant
         */
        if(this.getGender()==Gender.FEMALE){
            return this.isPregnant;
        }else {return false;}
    }
    public void setPregnant(boolean pregnant) {
        if (this.getGender()==Gender.FEMALE ) {
            this.isPregnant=pregnant;
        }else {
            this.isPregnant=false;
        }
    }
    public boolean isYou(){
        /**
         * this method return if the person is user
         * @param isYou
         * @return isYou
         */
        return isYou;
    }
    public void setAsyou(boolean isYou) {
        /**
         * this method set if the person is user
         * @param isYou
         */
        this.isYou=isYou;
    }
    public String toString(){
        /**
         * this method return the string of personal information
         * @param you,body,age,pro,pre,gen all are information of ths person
         * @return you+body+age+pro+gen+pre
         */
        String you="";
        String body=getBodytype().toString().toLowerCase();
        String age=" "+getAgeCategory().toString().toLowerCase();
        String pro="";
        String gen=" "+getGender().toString().toLowerCase();
        String pre="";
        if(isYou==true){
            you="you ";
        }
        if(getAgeCategory()==AgeCategory.ADULT){
            pro=" "+getProfession().toString().toLowerCase();
        }
        if(isPregnant){
            if (getGender()==Gender.FEMALE) {
                pre = " pregnant";
            }
            else {throw new IllegalStateException(); }
        }
        return you+body+age+pro+gen+pre;
    }
}

