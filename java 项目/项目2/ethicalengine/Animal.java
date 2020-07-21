package ethicalengine;
/**
 * @author Shengyizhao
 * @author ID:990160
 */
public class Animal extends Character {
    private String species;
    private boolean isPet;
    private boolean ispre=false;
    public Animal(String species) {

        /**
         * this method set a new animal
         * @param species
         */
        this.species=species;
    }
    public Animal(int age, Gender gender, BodyType bodytype,String species){
        /**
         * this method set a new Animal from character
         * @param age,gender,bodytype,species
         */
        super(age, gender, bodytype);
        this.species=species;
    }
    Animal(Animal otherAnimal){
        /**
         * this method copy another animal
         * @param age,gender,bodytype,species
         */
        this.setAge(otherAnimal.getAge());
        super.setGender(otherAnimal.getGender());
        super.setBodytype(otherAnimal.getBodytype());
        this.species=otherAnimal.getSpecies();

    }
    public String getSpecies(){
        /**
         * this method return the species of an animal
         * @param species
         * @return species
         */
        return species;
    }
    public void setSpecies(String species){

        /**
         * this method set the species of an animal
         * @param species
         */
        this.species=species;
    }
    public boolean isPet() {
        /**
         * this method return if the animal is a pet
         * @param isPet
         * @return isPet
         */
        return isPet;
    }
    public void setPet(boolean isPet) {
        /**
         * this method set if an animal is a pet
         * @param isPet
         */
        this.isPet=isPet;
    }
    public BodyType getbodytype(){
        /**
         * this method return the bodytype of an animal
         * @param super.bodytype
         * @return super.bodytype
         */
        return super.getBodytype();
    }
    public Gender getgender(){
        /**
         * this method return the gender of an animal
         * @param super.gender
         * @return super.gender
         */
        return super.getGender();
    }
    public void setpregrent(boolean ispre){
        /**
         * this method set if the animal is pregrent
         * @param ispre
         */
        this.ispre=ispre;
    }
    public boolean getispre(){
        /**
         * this method set if the animal is pregrent
         * @param ispre
         * @return ispre
         */
        return this.ispre;
    }

    @Override
    public String toString() {
        /**
         * this method return the information of animal
         * @param species,isPet,s,p
         * @return s+p
         */
        String s=getSpecies();
        String p="";
        if (isPet){
            p=" is pet";
        }
        return s+p;
    }
}
