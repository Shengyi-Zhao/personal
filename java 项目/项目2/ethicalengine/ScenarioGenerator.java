package ethicalengine;

import java.util.Random;
/**
 * @author Shengyizhao
 * @author ID:990160
 */
public class ScenarioGenerator {
    private int passengerCountMinimum=1;
    private int passengerCountMaximum=5;
    private int pedestrianCountMinimum=1;
    private int pedestrianCountMaximum=5;
    private long seed;
    public ScenarioGenerator(){ }
    public ScenarioGenerator(long seed)
    {
        /**
         * this method set a new scenario generator
         * @param seed the number of seed
         */
        this.seed=seed;
    }
    public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
                             int pedestrianCountMinimum, int pedestrianCountMaximum){
        /**
         * * this method set a new scenario generator and minimum and maximum
         * @param seed,passengerCountMinimum,passengerCountMaximum,pedestrianCountMinimum,pedestrianCountMaximum
         */
        this.seed=seed;
        this.passengerCountMinimum=passengerCountMinimum;
        this.passengerCountMaximum=passengerCountMaximum;
        this.pedestrianCountMinimum=pedestrianCountMinimum;
        this.pedestrianCountMaximum=pedestrianCountMaximum;
    }
    public void setPassengerCountMin(int min){
        /**
         * this method set the minimum passenger
         * @param min
         */
        this.passengerCountMinimum=min;
    }
    public void setPassengerCountMaximum(int max){
        /**
         * this method set the maximum passenger
         * @param max
         */
        this.passengerCountMaximum=max;
    }
    public void setPedestrianCountMinimum(int min){
        /**
         * this method set the maximum passenger
         * @param min
         */
        this.pedestrianCountMinimum=min;
    }
    public void setPedestrianCountMaximum(int max){
        /**
         * this method set the maximum passenger
         * @param min
         */
        this.pedestrianCountMaximum=max;
    }
    public Person getRandomPerson(){
        /**
         * this return a random person
         * @param pro the random int for random profession
         * @param num the random int for random age
         * @param gen the random int for random gender
         * @param pro the random int for random bodytype
         * @param pro the random int for random ispregnant
         * @param pro the random int for random isyou
         * @return new Person(num, Person.Profession.values()[pro], Character.Gender.values()[gen],
         *                 Character.BodyType.values()[bodyt],isp)
         */
        int pro= new Random(seed).nextInt(Person.Profession.values().length);
        int num = new Random(seed).nextInt(81);
        int gen = new Random(seed).nextInt(Character.Gender.values().length);
        int bodyt = new Random(seed).nextInt(Character.BodyType.values().length);
        boolean isp= new Random(seed).nextBoolean();
        boolean isyou=new Random(seed).nextBoolean();
        Person newperson= new Person(num, Person.Profession.values()[pro], Character.Gender.values()[gen],
                Character.BodyType.values()[bodyt],isp);
        newperson.setPregnant(isp);
        newperson.setAsyou(isyou);
        return newperson;
    }
    public Animal getRandomAnimal(){
        /**
         * this return a random person
         * @param num the random int for random age
         * @param gen the random int for random gender
         * @param pro the random int for random bodytype
         * @return new Animal(num, Character.Gender.values()[gen], Character.BodyType.values()[bodyt],specie);
         *         newanimal.setPet(ifpet)
         */
        int num = new Random(seed).nextInt(20);
        int gen = new Random(seed).nextInt(Character.Gender.values().length);
        int bodyt = new Random(seed).nextInt(Character.BodyType.values().length);
        String specie="";
        int sp = new Random(seed).nextInt(3);
        switch (sp){
            case 0:
                specie="dog";
                break;
            case 1:
                specie="cat";
                break;
            case 2:
                specie="bird";
        }
        boolean ifpet = new Random(seed).nextBoolean();

        Animal newanimal= new Animal(num, Character.Gender.values()[gen], Character.BodyType.values()[bodyt],specie);
        newanimal.setPet(ifpet);
        return newanimal;
    }
    public Scenario generate(){
        /**
         * this return a random scenario
         * @param num_passenger the random int for random number of passengers
         * @param num_pedestrian the random int for random number of pedestrians
         * @param r the number for random is legal crossing
         * @return new Scenario(passengers, pedestrians,isLegalCrossing)
         */
        int num_passenger = new Random(seed).nextInt(passengerCountMaximum)+passengerCountMinimum;
        int num_pedestrian = new Random(seed).nextInt(pedestrianCountMaximum)+pedestrianCountMinimum;
        Character[] passengers=new Character[num_passenger];
        for (int i = 0; i < num_passenger; i++) {
            int r=new Random(seed).nextInt(1);
            if(r==0){passengers[i]=getRandomPerson();
            }else {passengers[i]=getRandomAnimal();}
        }
        Character[] pedestrians=new Character[num_pedestrian];
        for (int i = 0; i < num_pedestrian; i++) {
            int r=new Random(seed).nextInt(1);
            if(r==0){pedestrians[i]=getRandomPerson();
            }else {pedestrians[i]=getRandomAnimal();}
        }
        boolean isLegalCrossing;
        int r=new Random(seed).nextInt(1);
        if(r==0){isLegalCrossing=false;
        }else {isLegalCrossing=true;}
        return new Scenario(passengers, pedestrians,isLegalCrossing);
    }
}
