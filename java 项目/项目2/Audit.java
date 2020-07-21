import ethicalengine.Animal;
import ethicalengine.Character;
import ethicalengine.Person;
import ethicalengine.Scenario;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * @author Shengyizhao
 * @author ID:990160
 */

public class Audit {
    private String AuditType="";
    private int sum_runs=0;
    private int all_ages=0;
    private int all_peopele=0;
    HashMap<String,Integer> hashMap1 = new HashMap<>();
    HashMap<String,Integer> hashMap2 = new HashMap<>();
    private ArrayList<Scenario> scenarios=new ArrayList<Scenario>();
    private EthicalEngine.Decision[] human_decision=new EthicalEngine.Decision[3];
    private EnumMap<Person.AgeCategory, String> enum_age=new EnumMap<Person.AgeCategory, String>
            (Person.AgeCategory.class);
    private EnumMap<Character.BodyType, String> enum_bodytype=new EnumMap<Character.BodyType, String>
            (Character.BodyType.class);
    private EnumMap<Character.Gender, String> enum_gender=new EnumMap<Character.Gender, String>
            (Character.Gender.class);
    private EnumMap<Person.Profession, String> enum_pro=new EnumMap<Person.Profession, String>
            (Person.Profession.class);

    public Audit(){
        /**
         * this method set a new Audit
         * @param enum_age the index of agecategory
         * @param enum_bodytype the index of bodytype
         * @param enum_gender the index of gender
         * @param enum_pro the index of professsion
         */
        this.enum_age.put(Person.AgeCategory.BABY,"baby");
        this.enum_age.put(Person.AgeCategory.CHILD,"child");
        this.enum_age.put(Person.AgeCategory.ADULT,"adult");
        this.enum_age.put(Person.AgeCategory.SENIOR,"senior");
        this.enum_bodytype.put(Character.BodyType.AVERAGE,"average");
        this.enum_bodytype.put(Character.BodyType.ATHLETIC,"athletic");
        this.enum_bodytype.put(Character.BodyType.OVERWEIGHT,"overweight");
        this.enum_bodytype.put(Character.BodyType.UNSPECIFIED,"none");
        this.enum_gender.put(Character.Gender.MALE,"male");
        this.enum_gender.put(Character.Gender.FEMALE,"female");
        this.enum_gender.put(Character.Gender.UNKNOWN,"none");
        this.enum_pro.put(Person.Profession.DOCTOR,"doctor");
        this.enum_pro.put(Person.Profession.CEO,"ceo");
        this.enum_pro.put(Person.Profession.CRIMINAL,"criminal");
        this.enum_pro.put(Person.Profession.HOMELESS,"homeless");
        this.enum_pro.put(Person.Profession.UNEMPLOYED,"unemployed");
        this.enum_pro.put(Person.Profession.UNKNOWN,"unknown");
        this.enum_pro.put(Person.Profession.NONE,"none");
        this.enum_pro.put(Person.Profession.DRIVER,"driver");
        this.enum_pro.put(Person.Profession.TEACHER,"teacher");
    }

    public Audit(Scenario[] scenarios){
        /**
         * this method set a new Audit with some scenarios
         * @param enum_age the index of agecategory
         * @param enum_bodytype the index of bodytype
         * @param enum_gender the index of gender
         * @param enum_pro the index of professsion
         * @param scenarios
         */
        this.enum_age.put(Person.AgeCategory.BABY,"baby");
        this.enum_age.put(Person.AgeCategory.CHILD,"child");
        this.enum_age.put(Person.AgeCategory.ADULT,"adult");
        this.enum_age.put(Person.AgeCategory.SENIOR,"senior");
        this.enum_bodytype.put(Character.BodyType.AVERAGE,"average");
        this.enum_bodytype.put(Character.BodyType.ATHLETIC,"athletic");
        this.enum_bodytype.put(Character.BodyType.OVERWEIGHT,"overweight");
        this.enum_bodytype.put(Character.BodyType.UNSPECIFIED,"none");
        this.enum_gender.put(Character.Gender.MALE,"male");
        this.enum_gender.put(Character.Gender.FEMALE,"female");
        this.enum_gender.put(Character.Gender.UNKNOWN,"none");
        this.enum_pro.put(Person.Profession.DOCTOR,"doctor");
        this.enum_pro.put(Person.Profession.CEO,"ceo");
        this.enum_pro.put(Person.Profession.CRIMINAL,"criminal");
        this.enum_pro.put(Person.Profession.HOMELESS,"homeless");
        this.enum_pro.put(Person.Profession.UNEMPLOYED,"unemployed");
        this.enum_pro.put(Person.Profession.UNKNOWN,"unknown");
        this.enum_pro.put(Person.Profession.NONE,"none");
        this.enum_pro.put(Person.Profession.DRIVER,"driver");
        this.enum_pro.put(Person.Profession.TEACHER,"teacher");
        for(Scenario scenario:scenarios){
            this.scenarios.add(scenario);
        }
    }
    public void add_scenarios(Scenario scenario) {
        /**
         * this method add a new scenario for audit
         * @param scenario
         */
        this.scenarios.add(scenario);
    }
    public void setHuman_decision(EthicalEngine.Decision[]decision){
        /**
         * this method add a human decision to audit
         * @param decision
         */
        for(int i=0;i<decision.length;i++){
            this.human_decision[i]=decision[i];
        }
    }
    private void add_survival_person(String a){
        /**
         * this method add situation of  survival
         * @param a
         * @param hashmap1 the situation of survival
         * @param hashmap2 the situation of participate
         */
        if(this.hashMap2.containsKey(a)){
            int f=this.hashMap2.get(a);
            this.hashMap2.put(a,f+1);
        }else {
            this.hashMap2.put(a,1);
        }
        if(this.hashMap1.containsKey(a)){
            int f=this.hashMap1.get(a);
            this.hashMap1.put(a,f+1);
        }else {
            this.hashMap1.put(a,1);
        }
    }
    private void add_dead_person(String a){
        /**
         * this method add situation of dead
         * @param a
         * @param hashmap1 the situation of survival
         * @param hashmap2 the situation of participate
         */
        if(this.hashMap2.containsKey(a)){
            int f=this.hashMap2.get(a);
            this.hashMap2.put(a,f+1);
        }else {
            this.hashMap2.put(a,1);
        }
    }


    public void run(int runs){
        /**
         * this method add the data into audit
         * @param tem the orginal number of runs
         * @param dec the decision of who survival
         * @param sum_runs the count of runs
         */
        int tem=this.sum_runs;
        Scenario[] sce=new Scenario[runs];
        for(int j=0;j<runs;j++){
            sce[j]=scenarios.get(tem+j);
        }
        for (int i = 0; i < runs; i++) {
            EthicalEngine.Decision dec;
            if(getAuditType()=="User"){
                dec=this.human_decision[i];

            }else {
                dec=EthicalEngine.decide(sce[i]);
            }
            if(dec== EthicalEngine.Decision.PASSENGERS){
                for(ethicalengine.Character character:sce[i].getPassengers()){
                    if(character instanceof Person){
                        add_survival_person("person");
                        this.all_ages=this.all_ages+character.getAge();
                        this.all_peopele++;
                        String age=enum_age.get(((Person) character).getAgeCategory());
                        add_survival_person(age);
                        String body=enum_bodytype.get(((Person) character).getbodytype());
                        add_survival_person(body);
                        String gen=enum_gender.get(character.getGender());
                        add_survival_person(gen);
                        String prof=enum_pro.get(((Person) character).getProfession());
                        add_survival_person(prof);
                        if(((Person) character).isPregnant()){
                            add_survival_person("pregnant");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_survival_person("green");
                        }else {
                            add_survival_person("red");
                        }
                        if (((Person) character).isYou()){
                            add_survival_person("you");
                        }
                    }else {
                        add_survival_person("animal");

                        if(((Animal)character).isPet()){
                            add_survival_person("pet");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_survival_person("green");
                        }else {
                            add_survival_person("red");
                        }
                        String sp=((Animal) character).getSpecies();
                        add_survival_person(sp);
                    }
                }
                for(ethicalengine.Character character:sce[i].getPedestrians()){
                    if(character instanceof Person){
                        add_dead_person("person");
                        String age=enum_age.get(((Person) character).getAgeCategory());
                        add_dead_person(age);
                        String body=enum_bodytype.get(((Person) character).getbodytype());
                        add_dead_person(body);
                        String gen=enum_gender.get(character.getGender());
                        add_dead_person(gen);
                        String prof=enum_pro.get(((Person) character).getProfession());
                        add_dead_person(prof);
                        if(((Person) character).isPregnant()){
                            add_dead_person("pregnant");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_dead_person("green");
                        }else {
                            add_dead_person("red");
                        }
                        if(((Person) character).isYou()){
                            add_dead_person("you");
                        }
                    }else {
                        add_dead_person("animal");

                        if(((Animal)character).isPet()){
                            add_dead_person("pet");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_dead_person("green");
                        }else {
                            add_dead_person("red");
                        }
                        String sp=((Animal) character).getSpecies();
                        add_dead_person(sp);
                    }
                }
            }else {
                for(ethicalengine.Character character:sce[i].getPedestrians()){
                    if(character instanceof Person){
                        add_survival_person("person");
                        this.all_ages=this.all_ages+character.getAge();
                        this.all_peopele++;
                        String age=enum_age.get(((Person) character).getAgeCategory());
                        add_survival_person(age);
                        String body=enum_bodytype.get(((Person) character).getbodytype());
                        add_survival_person(body);
                        String gen=enum_gender.get(character.getGender());
                        add_survival_person(gen);
                        String prof=enum_pro.get(((Person) character).getProfession());
                        add_survival_person(prof);
                        if(((Person) character).isPregnant()){
                            add_survival_person("pregnant");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_survival_person("green");
                        }else {
                            add_survival_person("red");
                        }
                        if(((Person) character).isYou()){
                            add_survival_person("you");
                        }
                    }else {
                        add_survival_person("animal");

                        if(((Animal)character).isPet()){
                            add_survival_person("pet");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_survival_person("green");
                        }else {
                            add_survival_person("red");
                        }
                        String sp=((Animal) character).getSpecies();
                        add_survival_person(sp);
                    }
                }
                for(ethicalengine.Character character:sce[i].getPassengers()){
                    if(character instanceof Person){
                        add_dead_person("person");
                        String age=enum_age.get(((Person) character).getAgeCategory());
                        add_dead_person(age);
                        String body=enum_bodytype.get(((Person) character).getbodytype());
                        add_dead_person(body);
                        String gen=enum_gender.get(character.getGender());
                        add_dead_person(gen);
                        String prof=enum_pro.get(((Person) character).getProfession());
                        add_dead_person(prof);
                        if(((Person) character).isPregnant()){
                            add_dead_person("pregnant");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_dead_person("green");
                        }else {
                            add_dead_person("red");
                        }
                        if(((Person) character).isYou()){
                            add_dead_person("you");
                        }
                    }else {
                        add_dead_person("animal");

                        if(((Animal)character).isPet()){
                            add_dead_person("pet");
                        }
                        if(sce[i].isLegalCrossing()){
                            add_dead_person("green");
                        }else {
                            add_dead_person("red");
                        }
                        String sp=((Animal) character).getSpecies();
                        add_dead_person(sp);
                    }
                }
            }
        }
        this.sum_runs=this.sum_runs+runs;
    }
    public void setAuditType(String name){
        /**
         * this method set the type of audit
         * @param name the type
         */
        this.AuditType=name;
    }
    public String getAuditType() {
        /**
         * this method return the type of audit
         * @param name the type
         * @return AuditType
         */
        if (AuditType.equalsIgnoreCase("")) {
            return "Unspecified";
        } else { return AuditType;}
    }
    public String toString() {
        /**
         * this method return the information of audit
         * @param one,two,three,four,five,six,seven the lines
         * @param results the count of ratio
         * @param all_ages the count of ages of survival people
         * @param all_people the count of number of survival people
         */
        String one = "======================================\n";
        String two = "# " + getAuditType() + " Audit\n";
        String three = "======================================\n";
        String four = "- % SAVED AFTER " + sum_runs + " RUNS\n";
        String five="";
        Map<String, Double> results = new HashMap<String, Double>();
        for(String key:hashMap2.keySet()){
            int participate=hashMap2.get(key);
            int survival=0;
            if(hashMap1.containsKey(key)){
                survival=hashMap1.get(key);
            }
            if(!key.equals("none")){
                float  f1   = (float) survival/participate;
                results.put(key,Math.floor(f1*10)/10);
            }

        }
        List<Map.Entry<String, Double>> list_Data = new ArrayList<Map.Entry<String, Double>>(results.entrySet());
        Collections.sort(list_Data, new Comparator<Map.Entry<String,Double>>()
                {
                    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
                    {
                        if ((o2.getValue() - o1.getValue())>0)
                            return 1;
                        else if((o2.getValue() - o1.getValue())==0){
                            String first=o1.getKey();
                            String second=o2.getKey();
                            ArrayList<String> they_two=new ArrayList<String>();
                            they_two.add(first);
                            they_two.add(second);
                            Collections.sort(they_two);
                            if(they_two.get(0).equalsIgnoreCase(first)){
                                return -1;
                            }else {
                                return 1;
                            }
                        } else
                            return -1;
                    }
                }
        );

        for(Map.Entry<String, Double> t:list_Data){
            five=five+t.getKey() + ": " +  (Math.floor(t.getValue()*10)/10)+"\n";
        }
        String six="--\n";
        String seven="average age: "+ (Math.floor(((float)all_ages/all_peopele)*10)/10)+"\n";
        return one+two+three+four+five+six+seven;
    }

    public void printStatistic(){
        /**
         * this method print the tostring
         * @param toString()
         * @return toString()
         */
        System.out.print(toString());
    }
    public void printToFile(String filepath) throws IOException {
        /**
         * this method print the tostring to file
         * @param toString()
         * @throws IOException
         */
        String strclassname=Audit.class.getName();
        Logger mylogger= Logger.getLogger(strclassname);
        FileHandler fh=null;
        mylogger.setUseParentHandlers(false);
        fh = new FileHandler("results.log",true);
        mylogger.addHandler(fh);
        mylogger.info(toString());
        fh.close();
    }
}
