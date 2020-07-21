package ethicalengine;
/**
 * @author Shengyizhao
 * @author ID:990160
 */
public class Scenario {
    private Character[] passengers;
    private Character[] pedestrians;
    private boolean isLegalCrossing;


    public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing){
        /**
         * this method set a new sscenario
         * @param passengers,pedestrians,isLegalCrossing
         */
        this.passengers=passengers;
        this.pedestrians=pedestrians;
        this.isLegalCrossing=isLegalCrossing;
    }
    public boolean hasYouInCar(){
        /**
         * this method return a boolean if you in car
         * @param you if you in the car
         * @return if you in the car
         */
        boolean you=false;
        for(Character passenger:passengers){
            if(passenger instanceof Person){
                if(((Person) passenger).isYou()){
                    you=((Person) passenger).isYou();
                }
            }
        }
        return you;
    }
    public boolean hasYouInLane(){
        /**
         * this method return a boolean if you in lane
         * @param you if you in lane
         * @return if you in the lane
         */
        boolean you=false;
        for(Character passenger:pedestrians){
            if(passenger instanceof Person){
                if(((Person) passenger).isYou()){
                    you=((Person) passenger).isYou();
                }
            }
        }
        return you;
    }
    public Character[] getPassengers(){
        /**
         * this method return passengers
         * @param tem a copy of passengers
         * @return tem
         */
        Character[] tem=new Character[passengers.length];
        for (int i = 0; i < passengers.length; i++) {
            tem[i]=passengers[i];
        }
        return tem;
    }
    public Character[] getPedestrians(){
        /**
         * this method return pedestrians
         * @param tem a copy of pedestrians
         * @return tem
         */
        Character[] tem=new Character[pedestrians.length];
        for (int i = 0; i < pedestrians.length; i++) {
            tem[i]=pedestrians[i];
        }
        return tem;
    }
    public boolean isLegalCrossing(){
        /**
         * this method return if the crossing islegal
         * @param isLegalCrossing
         * @return isLegalCrossing
         */
        return isLegalCrossing;
    }
    public void setLegalCrossing(boolean isLegalCrossing){
        /**
         * this method set if the crossing islegal
         * @param isLegalCrossing
         */
        this.isLegalCrossing=isLegalCrossing;
    }
    public int getPassengerCount(){
        /**
         * this method return the count of passengers
         * @param  passengers
         * @return passengers.length
         */
        return passengers.length;
    }
    public int getPedestrianCount(){
        /**
         * this method return the count of passengers
         * @param  pedestrians
         * @return pedestrians.length
         */
        return pedestrians.length;
    }
    public String toString(){
        /**
         * this method return string of scenario information
         * @param  one,two,three,four,five,sixth,seventh
         * @return "======================================\n"+"# Scenario\n"+"======================================\n"
         *                 +third+fourth+fifth+sixth+seventh;
         */
        String third="Legal Crossing: no\n";
        if(this.isLegalCrossing){
            third="Legal Crossing: yes\n";
        }
        String fourth="Passengers ("+getPassengerCount()+")\n";
        String fifth="";
        for(Character passenger:passengers){
            fifth=fifth+"- "+passenger.toString()+"\n";
        }
        String sixth="Pedestrians ("+getPedestrianCount()+")\n";
        String seventh="";
        for(Character pedestrian:pedestrians){
            seventh=seventh+"- "+pedestrian.toString()+"\n";
        }
        return "======================================\n"+"# Scenario\n"+"======================================\n"
                +third+fourth+fifth+sixth+seventh;
    }
}
