import ethicalengine.*;
import ethicalengine.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Shengyizhao
 * @author ID:990160
 */
public class EthicalEngine {

    public enum Decision {PEDESTRIANS, PASSENGERS}
    public static  void main(String[] args) {
        /**
         * this method used to get users command and give the output
         * @param help if the user need help page
         * @param config if the user want to use config file
         * @param result if the user want to save the result
         * @param inter if the user need interact page
         * @param args the input when starting the program
         * @param path the path of config file
         * @param list the list of scenarios
         * @param index the line of config file
         * @param item the item of string in the config file
         * @param list1 the list of passengers
         * @param list2 the list of pedestrian
         * @param is_pass if this line can be used in config file
         * @param judge the audit classs used in the program
         * @param run the number of runs of audit in a loop
         * @param number the number of all runs
         * @throws FileNotFoundException
         * @throws IOException
         */
        boolean help = false;
        boolean config = false;
        boolean result = false;
        boolean inter = false;
        String file_path = "";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    help = true;
                    break;
                case "-c":
                case "--config":
                    if (i + 1 <= args.length && !args[i + 1].equalsIgnoreCase("-h") &&
                            !args[i + 1].equalsIgnoreCase("--help") &&
                            !args[i + 1].equalsIgnoreCase("-r") &&
                            !args[i + 1].equalsIgnoreCase("--results") &&
                            !args[i + 1].equalsIgnoreCase("-i") &&
                            !args[i + 1].equalsIgnoreCase("--interactive")) {
                        config = true;
                        file_path = args[i + 1];
                    } else {
                        help = true;
                    }
                    break;
                case "-r":
                case "--results":
                    result = true;
                    break;
                case "-i":
                case "--interactive":
                    inter = true;
                    break;
            }
        }
        if (help) {
            System.out.print("EthicalEngine - COMP90041 - Final Project\n");
            System.out.println();
            System.out.print("Usage: java EthicalEngine [arguments]\n");
            System.out.println();
            System.out.print("Arguments:\n");
            System.out.print("   -c or --config Optional: path to config file\n");
            System.out.print("   -h or --help Print Help (this message) and exit\n");
            System.out.print("   -r or --results Optional: path to results log file\n");
            System.out.print("   -i or --interactive Optional: launches interactive mode\n");
        }else {
            if(inter){
                System.out.print(readFile("welcome.ascii")+"\n");
                System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n");
                Scanner keyboard = new Scanner(System.in);
                boolean save_log = false;
                boolean current_answer = false;
                while (!current_answer) {
                    String user_answer = keyboard.nextLine();
                    if (user_answer.equalsIgnoreCase("yes")) {
                        save_log = true;
                        current_answer = true;
                    } else if (user_answer.equalsIgnoreCase("no")) {
                        save_log = false;
                        current_answer = true;
                    } else {
                        System.out.println("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)");
                    }
                }
                boolean if_continue=true;
                List<Scenario> list = new ArrayList<Scenario>();
                int number=0;
                if (config) {
                    String path = file_path;
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(path));
                        String line = null;
                        boolean if_legal = false;
                        int index = 0;
                        List<Character> list1 = new ArrayList<Character>();
                        List<Character> list2 = new ArrayList<Character>();
                        int number_scenario = -1;
                        while ((line = reader.readLine()) != null) {
                            String item[] = line.split(",");
                            if (item.length > 10) {
                                System.out.println("WARNING: invalid data format in config file in line "+index);
                            } else {
                                if (item[0].equalsIgnoreCase("class")){

                                }else if (item[0].equalsIgnoreCase("scenario:green")) {
                                    number_scenario++;
                                    if (number_scenario == 0) {
                                        if_legal = true;
                                    } else {
                                        Character[] passengers = list1.toArray(new Character[list1.size()]);
                                        Character[] pedestrians = list2.toArray(new Character[list2.size()]);
                                        list.add(new Scenario(passengers, pedestrians, if_legal));
                                        list1.clear();
                                        list2.clear();
                                        if_legal=true;
                                    }
                                } else if (item[0].equalsIgnoreCase("scenario:red")) {
                                    number_scenario++;
                                    if (number_scenario == 0) {
                                        if_legal = false;
                                    } else {
                                        Character[] passengers = list1.toArray(new Character[list1.size()]);
                                        Character[] pedestrians = list2.toArray(new Character[list2.size()]);
                                        list.add(new Scenario(passengers, pedestrians, if_legal));
                                        list1.clear();
                                        list2.clear();
                                        if_legal=false;
                                    }
                                } else if (item[0].equalsIgnoreCase("person")) {
                                    boolean is_pass = false;
                                    Person.Profession profession = Person.Profession.NONE;
                                    switch (item[4].trim()) {
                                        case "doctor":
                                            profession = Person.Profession.DOCTOR;
                                            break;
                                        case "ceo":
                                            profession = Person.Profession.CEO;
                                            break;
                                        case "criminal":
                                            profession = Person.Profession.CRIMINAL;
                                            break;
                                        case "homeless":
                                            profession = Person.Profession.HOMELESS;
                                            break;
                                        case "unemployed":
                                            profession = Person.Profession.UNEMPLOYED;
                                            break;
                                        case "unknown":
                                            profession = Person.Profession.UNKNOWN;
                                            break;
                                        case "driver":
                                            profession = Person.Profession.DRIVER;
                                            break;
                                        case "teacher":
                                            profession = Person.Profession.TEACHER;
                                            break;
                                        case "":
                                            profession = Person.Profession.NONE;
                                            break;
                                        case "none":
                                            profession = Person.Profession.NONE;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    Character.Gender gender = Character.Gender.UNKNOWN;
                                    switch (item[1].trim()) {
                                        case "female":
                                            gender = Character.Gender.FEMALE;
                                            break;
                                        case "male":
                                            gender = Character.Gender.MALE;
                                            break;
                                        case "":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        case "unknown":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    Character.BodyType bodytype = Character.BodyType.UNSPECIFIED;
                                    switch (item[3].trim()) {
                                        case "average":
                                            bodytype = Character.BodyType.AVERAGE;
                                            break;
                                        case "athletic":
                                            bodytype = Character.BodyType.ATHLETIC;
                                            break;
                                        case "overweight":
                                            bodytype = Character.BodyType.OVERWEIGHT;
                                            break;
                                        case "":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        case "unspecified":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    boolean isPregnant = false;
                                    switch (item[5].trim()) {
                                        case "false":
                                            isPregnant = false;
                                            break;
                                        case "true":
                                            isPregnant = true;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    boolean isyou = false;
                                    switch (item[6].trim()) {
                                        case "false":
                                            isyou = false;
                                            break;
                                        case "true":
                                            isyou = true;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    try {
                                        int i = Integer.parseInt(item[2]);
                                        if (is_pass) {
                                            System.out.println("WARNING: invalid characteristic in config file in line "
                                                    + index);
                                        } else {
                                            Person newperson = new Person(i, profession, gender, bodytype, isPregnant);
                                            if(item[5].equalsIgnoreCase("true")){
                                                newperson.setPregnant(true);
                                            }
                                            newperson.setAsyou(isyou);
                                            switch (item[9]) {
                                                case "passenger":
                                                    list1.add(newperson);
                                                    break;
                                                case "pedestrian":
                                                    list2.add(newperson);
                                                    break;
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.print("WARNING: invalid number format in config file in line " + index);
                                    }
                                } else if (item[0].equalsIgnoreCase("animal")) {
                                    boolean is_pass = false;
                                    Character.Gender gender = Character.Gender.UNKNOWN;
                                    switch (item[1].trim()) {
                                        case "female":
                                            gender = Character.Gender.FEMALE;
                                            break;
                                        case "male":
                                            gender = Character.Gender.MALE;
                                            break;
                                        case "":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        case "unknown":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    Character.BodyType bodytype = Character.BodyType.UNSPECIFIED;
                                    switch (item[3].trim()) {
                                        case "average":
                                            bodytype = Character.BodyType.AVERAGE;
                                            break;
                                        case "athletic":
                                            bodytype = Character.BodyType.ATHLETIC;
                                            break;
                                        case "overweight":
                                            bodytype = Character.BodyType.OVERWEIGHT;
                                            break;
                                        case "":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        case "unspecified":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    boolean ispet = false;
                                    switch (item[8].trim()) {
                                        case "false":
                                            ispet = false;
                                            break;
                                        case "true":
                                            ispet = true;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    try {
                                        String species = item[7].trim();
                                        int i = Integer.parseInt(item[2]);
                                        if (is_pass) {
                                            System.out.println("WARNING: invalid characteristic in config file in line "
                                                    + index);
                                        } else {
                                            Animal newanimal = new Animal(i, gender, bodytype, species);
                                            boolean ispre=false;
                                            if(item[5].equalsIgnoreCase("true")){
                                                ispre=true;
                                                newanimal.setpregrent(true);
                                            }
                                            newanimal.setPet(ispet);
                                            switch (item[9].trim()) {
                                                case "passenger":
                                                    list1.add(newanimal);
                                                    break;
                                                case "pedestrian":
                                                    list2.add(newanimal);
                                                    break;
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.print("WARNING: invalid number format in config file in line " + index);
                                    }
                                }
                            }
                            index++;
                        }
                        Character[] passengers = list1.toArray(new Character[list1.size()]);
                        Character[] pedestrians = list2.toArray(new Character[list2.size()]);
                        Scenario scenario=new Scenario(passengers, pedestrians, if_legal);
                        list.add(scenario);
                    } catch (FileNotFoundException e) {
                        System.out.println("ERROR: could not find config file.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Audit judge=new Audit();
                judge.setAuditType("User");
                while (if_continue){
                    Decision[] human=new Decision[3];
                    if(!config){
                        {ScenarioGenerator g1=new ScenarioGenerator((long) number);
                            ScenarioGenerator g2=new ScenarioGenerator((long) number+1);
                            ScenarioGenerator g3=new ScenarioGenerator((long) number+2);
                            list.add(g1.generate());
                            list.add(g2.generate());
                            list.add(g3.generate());
                        }
                    }
                    int run=0;
                    loop:for (int i=0;i<3;i++){
                        if(number+i>=list.size()){
                            judge.setHuman_decision(human);
                            judge.run(i);
                            judge.printStatistic();
                            System.out.print("That’s all. Press Enter to quit.");
                            System.out.println();
                            if(save_log){
                                try {
                                    judge.printToFile("user.log");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            System.exit(0);
                        }
                        judge.add_scenarios(list.get(number+i));
                        System.out.print(list.get(number+i).toString());
                        System.out.print("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])\n");
                        String answer=keyboard.nextLine();
                        switch (answer){
                            case "pasenger":
                            case "passengers":
                            case "1":
                                human[i]=Decision.PASSENGERS;
                                break;
                            case "pedestrian":
                            case "pedestrians":
                            case "2":
                                human[i]=Decision.PEDESTRIANS;
                                break;
                            default:
                        }
                        run=i;
                    }
                    judge.setHuman_decision(human);
                    judge.run(run+1);
                    judge.printStatistic();
                    number=number+run+1;
                    if(number==list.size()){
                        System.out.print("That’s all. Press Enter to quit.");
                        System.out.println();
                        if(save_log){
                            try {
                                judge.printToFile("user.log");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.exit(0);
                    }
                    System.out.print("Would you like to continue? (yes/no)\n");
                    String conti=keyboard.nextLine();
                    if_continue= conti.equals("yes");
                }
                if(save_log){
                    try {
                        judge.printToFile("user.log");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(keyboard.nextLine().equals("\n")){
                        System.exit(0);
                    }
                }else {
                    if(keyboard.nextLine().equals("\n")){
                        System.exit(0);
                    }
                }
            }else {

                boolean if_continue=true;
                List<Scenario> list = new ArrayList<Scenario>();
                int number=0;
                if (config) {
                    String path = file_path;
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(path));
                        String line = null;
                        boolean if_legal = false;
                        int index = 0;
                        List<Character> list1 = new ArrayList<Character>();
                        List<Character> list2 = new ArrayList<Character>();
                        int number_scenario = -1;
                        while ((line = reader.readLine()) != null) {
                            String item[] = line.split(",");
                            if (item.length > 10) {
                                System.out.println("WARNING: invalid data format in config file in line"+index);
                            } else {
                                if (item[0].equalsIgnoreCase("class")){

                                }else if (item[0].equalsIgnoreCase("scenario:green")) {
                                    number_scenario++;
                                    if (number_scenario == 0) {
                                        if_legal = true;
                                    } else {
                                        Character[] passengers = list1.toArray(new Character[list1.size()]);
                                        Character[] pedestrians = list2.toArray(new Character[list2.size()]);
                                        list.add(new Scenario(passengers, pedestrians, if_legal));
                                        list1.clear();
                                        list2.clear();
                                        if_legal=true;
                                    }
                                } else if (item[0].equalsIgnoreCase("scenario:red")) {
                                    number_scenario++;
                                    if (number_scenario == 0) {
                                        if_legal = false;
                                    } else {
                                        Character[] passengers = list1.toArray(new Character[list1.size()]);
                                        Character[] pedestrians = list2.toArray(new Character[list2.size()]);
                                        list.add(new Scenario(passengers, pedestrians, if_legal));
                                        list1.clear();
                                        list2.clear();
                                        if_legal=false;
                                    }
                                } else if (item[0].equalsIgnoreCase("person")) {
                                    boolean is_pass = false;
                                    Person.Profession profession = Person.Profession.NONE;
                                    switch (item[4].trim()) {
                                        case "doctor":
                                            profession = Person.Profession.DOCTOR;
                                            break;
                                        case "ceo":
                                            profession = Person.Profession.CEO;
                                            break;
                                        case "criminal":
                                            profession = Person.Profession.CRIMINAL;
                                            break;
                                        case "homeless":
                                            profession = Person.Profession.HOMELESS;
                                            break;
                                        case "unemployed":
                                            profession = Person.Profession.UNEMPLOYED;
                                            break;
                                        case "unknown":
                                            profession = Person.Profession.UNKNOWN;
                                            break;
                                        case "driver":
                                            profession = Person.Profession.DRIVER;
                                            break;
                                        case "teacher":
                                            profession = Person.Profession.TEACHER;
                                            break;
                                        case "":
                                            profession = Person.Profession.NONE;
                                            break;
                                        case "none":
                                            profession = Person.Profession.NONE;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    Character.Gender gender = Character.Gender.UNKNOWN;
                                    switch (item[1].trim()) {
                                        case "female":
                                            gender = Character.Gender.FEMALE;
                                            break;
                                        case "male":
                                            gender = Character.Gender.MALE;
                                            break;
                                        case "":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        case "unknown":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    Character.BodyType bodytype = Character.BodyType.UNSPECIFIED;
                                    switch (item[3].trim()) {
                                        case "average":
                                            bodytype = Character.BodyType.AVERAGE;
                                            break;
                                        case "athletic":
                                            bodytype = Character.BodyType.ATHLETIC;
                                            break;
                                        case "overweight":
                                            bodytype = Character.BodyType.OVERWEIGHT;
                                            break;
                                        case "":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        case "unspecified":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    boolean isPregnant = false;
                                    switch (item[5].trim()) {
                                        case "false":
                                            isPregnant = false;
                                            break;
                                        case "true":
                                            isPregnant = true;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    boolean isyou = false;
                                    switch (item[6].trim()) {
                                        case "false":
                                            isyou = false;
                                            break;
                                        case "true":
                                            isyou = true;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    try {
                                        int i = Integer.parseInt(item[2]);
                                        if (is_pass) {
                                            System.out.println("WARNING: invalid characteristic in config file in line "
                                                    + index);
                                        } else {
                                            Person newperson = new Person(i, profession, gender, bodytype, isPregnant);
                                            if(item[5].equalsIgnoreCase("true")){
                                                newperson.setPregnant(true);
                                            }
                                            newperson.setAsyou(isyou);
                                            switch (item[9]) {
                                                case "passenger":
                                                    list1.add(newperson);
                                                    break;
                                                case "pedestrian":
                                                    list2.add(newperson);
                                                    break;
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.print("WARNING: invalid number format in config file in line " + index);
                                    }
                                } else if (item[0].equalsIgnoreCase("animal")) {
                                    boolean is_pass = false;
                                    Character.Gender gender = Character.Gender.UNKNOWN;
                                    switch (item[1].trim()) {
                                        case "female":
                                            gender = Character.Gender.FEMALE;
                                            break;
                                        case "male":
                                            gender = Character.Gender.MALE;
                                            break;
                                        case "":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        case "unknown":
                                            gender = Character.Gender.UNKNOWN;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    Character.BodyType bodytype = Character.BodyType.UNSPECIFIED;
                                    switch (item[3].trim()) {
                                        case "average":
                                            bodytype = Character.BodyType.AVERAGE;
                                            break;
                                        case "athletic":
                                            bodytype = Character.BodyType.ATHLETIC;
                                            break;
                                        case "overweight":
                                            bodytype = Character.BodyType.OVERWEIGHT;
                                            break;
                                        case "":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        case "unspecified":
                                            bodytype = Character.BodyType.UNSPECIFIED;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    boolean ispet = false;
                                    switch (item[8].trim()) {
                                        case "false":
                                            ispet = false;
                                            break;
                                        case "true":
                                            ispet = true;
                                            break;
                                        default:
                                            is_pass = true;
                                    }
                                    try {
                                        String species = item[7].trim();
                                        int i = Integer.parseInt(item[2]);
                                        if (is_pass) {
                                            System.out.println("WARNING: invalid characteristic in config file in line "
                                                    + index);
                                        } else {
                                            Animal newanimal = new Animal(i, gender, bodytype, species);
                                            boolean ispre=false;
                                            if(item[5].equalsIgnoreCase("true")){
                                                ispre=true;
                                                newanimal.setpregrent(true);
                                            }
                                            newanimal.setPet(ispet);
                                            switch (item[9].trim()) {
                                                case "passenger":
                                                    list1.add(newanimal);
                                                    break;
                                                case "pedestrian":
                                                    list2.add(newanimal);
                                                    break;
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.print("WARNING: invalid number format in config file in line " + index);
                                    }
                                }
                            }
                            index++;
                        }
                        Character[] passengers = list1.toArray(new Character[list1.size()]);
                        Character[] pedestrians = list2.toArray(new Character[list2.size()]);
                        Scenario scenario=new Scenario(passengers, pedestrians, if_legal);
                        list.add(scenario);
                    } catch (FileNotFoundException e) {
                        System.out.println("ERROR: could not find config file.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Audit judge=new Audit();
                judge.setAuditType("Algorithm");
                judge.run(list.size());
                judge.printStatistic();
            }
            }
        }


    public static Decision decide(Scenario scenario) {
        /**
         * this method return the decision of a scenario by machine
         * @param aveispassenger the boolean for judge
         * @param iscri if a passenger is criminal
         * @param isfat if a pedestrian is fat
         * @return decide
         */
        boolean saveispassenger = true;
        if (scenario.isLegalCrossing()) {
            boolean iscri=false;
            for (Character person : scenario.getPassengers()) {
                if (person instanceof Person) {
                    if (((Person) person).getProfession() == Person.Profession.CRIMINAL ) {
                        iscri = true;
                    }
                }
            }
            boolean isfat=false;
            for (Character person : scenario.getPedestrians()) {
                if (person instanceof Person) {
                    if (((Person) person).getbodytype()== Character.BodyType.OVERWEIGHT) {
                        isfat=true;
                    }
                }
            }
            if (iscri||!isfat){
                saveispassenger=true;
            }else {saveispassenger=false;}
        } else {
            for (Character person : scenario.getPedestrians()) {
                if (person instanceof Person) {
                    if (((Person) person).getAgeCategory() == Person.AgeCategory.BABY ||
                            ((Person) person).getAgeCategory() == Person.AgeCategory.CHILD) {
                        saveispassenger = false;
                    }
                }
            }
            for (Character person : scenario.getPedestrians()) {
                if (person instanceof Animal) {
                    if (!((Animal) person).isPet()) {
                        saveispassenger = false;
                    }
                }
            }
        }
        if (saveispassenger){
            return Decision.PASSENGERS;
        }else {return Decision.PEDESTRIANS;}
    }


    public static String getFileEncode(String path) {
        /**
         * this method judge the code of config file
         * @return the way of encode
         * @throws IOException
         * @throws Exception
         */
        String charset ="asci";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(path));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "Unicode";//UTF-16LE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "Unicode";//UTF-16BE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int len = 0;
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                }
            }
        }
        return charset;
    }



    public static String readFile(String path){
        /**
         * this method read the config file
         * @return data
         * @throws IOException
         * @throws Exception
         */
        String data = null;
        File file = new File(path);
        if(!file.exists()){
            return data;
        }
        String code =getFileEncode(path);
        InputStreamReader isr = null;
        try{
            if("asci".equals(code)){
                code = "GBK";
            }
            isr = new InputStreamReader(new FileInputStream(file),code);
            int length = -1 ;
            char[] buffer = new char[1024];
            StringBuffer sb = new StringBuffer();
            while((length = isr.read(buffer, 0, 1024) ) != -1){
                sb.append(buffer,0,length);
            }
            data = new String(sb);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(isr != null){
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
