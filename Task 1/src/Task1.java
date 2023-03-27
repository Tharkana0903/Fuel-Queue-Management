import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Task1{

    private static double FuelStock = 6600;
    private static String[][] q1_Customer = new String[6][2];//6 customer in 3 queue {name,status}
    private static String[][] q2_Customer = new String[6][2];//6 customer in 3 queue {name,status}
    private static String[][] q3_Customer = new String[6][2];//6 customer in 3 queue {name,status}
    private static double ServedQTY = 0;
    private static Scanner input = new Scanner(System.in);

    public static void main (String[] args) throws IOException {
        int loop = 0;

        while (loop < 1 ){
            println("");
            println("====================================");
            println("=======Fuel Station System==========");
            println("====================================");
            println("");
            System.out.println("100 or VFQ:\t View all Fuel Queues.");
            System.out.println("101 or VEQ:\t View all Empty Queues.");
            System.out.println("102 or ACQ:\t Add customer to a Queue.");
            System.out.println("103 or RCQ:\t Remove a customer from a Queue.");
            System.out.println("104 or PCQ:\t Remove a served customer.");
            System.out.println("105 or VCS:\t View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD:\t Store Program Data into file.");
            System.out.println("107 or LPD:\t Load Program Data from file.");
            System.out.println("108 or STK:\t View Remaining Fuel Stock.");
            System.out.println("109 or AFS:\t Add Fuel Stock.");
            System.out.println("999 or EXT:\t Exit the Program.");
            System.out.println();


            System.out.print("Please Enter action code : ");
            String code = input.next();


            if (code.equals("100") || code.equals("VFQ") || code.equals("vfq")) {
                viewQueue();
            }
            else if (code.equals("101") || code.equals("VEQ") || code.equals("veq")) {
                viewEmptyQueue();
            }
            else if (code.equals("102") || code.equals("ACQ") || code.equals("acq")) {
                addCustomer();
            }
            else if (code.equals("103") || code.equals("RCQ") || code.equals("rcq")) {
                removeCustomer();
            }
            else if (code.equals("104") || code.equals("PCQ") || code.equals("pcq")) {
                removeServedCustomer();
            }
            else if (code.equals("105") || code.equals("VCS") || code.equals("vcs")) {
                viewCustomerSorted();
            }
            else if (code.equals("106") || code.equals("SPD") || code.equals("spd")) {
                storeDataToFile();
            }
            else if (code.equals("107") || code.equals("LPD") || code.equals("lpd")) {
                loadDataInFile();
            }
            else if (code.equals("108") || code.equals("STK") || code.equals("stk")) {
                viewRemainingFuelStock();
            }
            else if (code.equals("109") || code.equals("AFS") || code.equals("afs")) {
                addFuelStock();
            }
            else if (code.equals("999") || code.equals("EXT") || code.equals("ext")) {
                break;
            }
            else{
                System.out.println("Invalid Option!!");
            }
        }
    }


    private static void viewQueue(){
        println("----------------------------");
        println("---------View Queue---------");
        println( "\n1st Queue customers:");
        for(int c=0; c<6; c++ ){
            String cusName = q1_Customer[c] == null?null:q1_Customer[c][0];
            println("\r\t"+(c+1)+"-"+ ((cusName == null)?"N/A": cusName));
        }
        println( "\n2nd Queue customers:");
        for(int c=0; c<6; c++ ){
            String cusName = q2_Customer[c] == null?null:q2_Customer[c][0];
            println("\r\t"+(c+1)+"-"+ ((cusName == null)?"N/A": cusName));
        }

        println( "\n3rd Queue customers:");
        for(int c=0; c<6; c++ ){
            String cusName = q3_Customer[c] == null?null:q3_Customer[c][0];
            println("\r\t"+(c+1)+"-"+ ((cusName == null)?"N/A": cusName));
        }
        println("----------------------------");

    }
    private static void viewEmptyQueue(){
        println("----------------------------");
        println("----View All Empty Queue----");

        boolean isEmpty_Q1=true;
        for(int c= 0; c<6; c++){
            isEmpty_Q1=q1_Customer[c] ==null || q1_Customer[c][0]==null || q1_Customer[c][0].length() == 0;
            if(!isEmpty_Q1)break;
        }

        boolean isEmpty_Q2=true;
        for(int c= 0; c<6; c++){
            isEmpty_Q2= q2_Customer[c] ==null || q2_Customer[c][0]==null || q2_Customer[c][0].length() == 0;
            if(!isEmpty_Q2)break;
        }

        boolean isEmpty_Q3=true;
        for(int c= 0; c<6; c++){
            isEmpty_Q3=q3_Customer[c] ==null ||  q3_Customer[c][0]==null || q3_Customer[c][0].length() == 0;
            if(!isEmpty_Q3)break;
        }

        print(isEmpty_Q1? "1st Queue is empty\n":"");
        print(isEmpty_Q2? "2nd Queue is empty\n":"");
        print(isEmpty_Q3? "3rd Queue is empty\n":"");

        println("----------------------------");
    }
    private static void addCustomer(){

        print("Please Enter Queue No:");
        int Q_number=-1;
        if(input.hasNextInt()){
            Q_number = input.nextInt();
        }else{
            println("Invalid Queue Number Entered!");
            return;
        }

        print("Please Enter Customer Name:");
        String name = input.next();

        int locationIndex = FindQueueLocation(Q_number);

        if(Q_number == 1){
            q1_Customer[locationIndex]=new String[] {name,"new"};
            System.out.println();
            System.out.println("Successfully added a customer");
        }else if(Q_number == 2){
            q2_Customer[locationIndex]=new String[] {name,"new"};
            System.out.println();
            System.out.println("Successfully added a customer");
        }else if(Q_number == 3){
            q3_Customer[locationIndex]=new String[] {name,"new"};
            System.out.println();
            System.out.println("Successfully added a customer");
        }

        //check fuel estimation
        double fuel_need=0;
        for(int c =0; c<6; c++){
            boolean flag = q1_Customer[c][0]!=null && q1_Customer[c][0].length() != 0;
            if(flag){
                fuel_need+=10;
            }
        }
        for(int c =0; c<6; c++){
            boolean flag = q2_Customer[c][0]!=null && q2_Customer[c][0].length() != 0;
            if(flag){
                fuel_need+=10;
            }
        }
        for(int c =0; c<6; c++){
            boolean flag = q2_Customer[c][0]!=null && q2_Customer[c][0].length() != 0;
            if(flag){
                fuel_need+=10;
            }
        }
        if((FuelStock - fuel_need )<=500){
            println("\n\tWarning - Reached 500L of fuel!");
        }

    }
    private static int FindQueueLocation(int Q_number){
        if(Q_number == 1){
            //find queue location
            for(int c =0; c<6; c++){
                boolean flag = q1_Customer[c][0]==null || q1_Customer[c][0].length() == 0;
                if(flag){
                    return c;
                }
            }
        }
        else if(Q_number == 2){
            //find queue location
            for(int c =0; c<6; c++){
                boolean flag = q2_Customer[c][0]==null || q2_Customer[c][0].length() == 0;
                if(flag){
                    return c;
                }
            }
        }
        else if(Q_number == 3){
            //find queue location
            for(int c =0; c<6; c++){
                boolean flag = q3_Customer[c][0]==null || q3_Customer[c][0].length() == 0;
                if(flag){
                    return c;
                }
            }
        }else{
            println("Invalid Queue Number!");
        }
        return -1;
    }
    private static void removeCustomer(){
        print("Please Enter Queue No:");
        int Q_number=-1;
        if(input.hasNextInt()){
            Q_number = input.nextInt();
        }else{
            println("Invalid Queue Number Entered!");
            return;
        }

        if(Q_number>3 || Q_number<1){
            println("Invalid Queue Number Entered!");
            return;
        }
        //----
        print("Please Enter Location No:");
        int Q_location=-1;
        if(input.hasNextInt()){
            Q_location = input.nextInt();
        }else{
            println("Invalid Queue Location!");
            return;
        }

        if(Q_location>6 || Q_location<1){
            println("Invalid Queue Location Entered!");
            return;
        }

        if(Q_number == 1){
            q1_Customer[Q_location-1]=new String[]{null,null};
        }else if(Q_number ==2 ){
            q2_Customer[Q_location-1]=new String[]{null,null};
        }else if(Q_number ==3 ){
            q3_Customer[Q_location-1]=new String[]{null,null};
        }
        println("Customer Removed Successfully");
    }
    private static void removeServedCustomer(){
        println("Start Served Customer Remove Process..");
        for(int c =0; c<6; c++){
            if( q1_Customer[c][0]!=null && !q1_Customer[c][1].equals("new") ){
                q1_Customer[c] =new String[]{null,null};
            }
            if( q2_Customer[c][0]!=null && !q2_Customer[c][1].equals("new") ){
                q2_Customer[c] =new String[]{null,null};
            }
            if( q3_Customer[c][0]!=null && !q3_Customer[c][1].equals("new") ){
                q3_Customer[c] =new String[]{null,null};
            }
        }
        println("Reordering Queue...");
        //reset queue
        int lastEmptyIndex=-1;
        for(int c =0; c<6; c++){
            boolean flag = q1_Customer[c][0]==null || q1_Customer[c][0].length() == 0;
            if(!flag && lastEmptyIndex != -1 ){
                q1_Customer[lastEmptyIndex]=q1_Customer[c];
                q1_Customer[c]=new String[]{null,null};
            }
            if(flag){
                lastEmptyIndex = c;
            }
        }
        lastEmptyIndex=-1;
        for(int c =0; c<6; c++){
            boolean flag = q2_Customer[c][0]==null || q2_Customer[c][0].length() == 0;
            if(!flag && lastEmptyIndex != -1 ){
                q2_Customer[lastEmptyIndex]=q2_Customer[c];
                q2_Customer[c]=new String[]{null,null};
            }
            if(flag){
                lastEmptyIndex = c;
            }
        }
        lastEmptyIndex=-1;
        for(int c =0; c<6; c++){
            boolean flag = q3_Customer[c][0]==null || q3_Customer[c][0].length() == 0;
            if(!flag && lastEmptyIndex != -1 ){
                q3_Customer[lastEmptyIndex]=q3_Customer[c];
                q3_Customer[c]=new String[]{null,null};
            }
            if(flag){
                lastEmptyIndex = c;
            }
        }
        println("End Served Customer Remove Process!");

    }

    private static void viewCustomerSorted(){

        String[] customers = new String[18];
        int ind=0;
        for(int i=0;i<6;i++){
            if(q1_Customer[i][0]==null || q1_Customer[i][0].length() ==0){
                continue;
            }
            customers[ind++] = q1_Customer[i][0];
        }

        for(int i=0;i<6;i++){
            if(q2_Customer[i][0]==null || q2_Customer[i][0].length() ==0){
                continue;
            }
            customers[ind++] = q2_Customer[i][0];
        }

        for(int i=0;i<6;i++){
            if(q3_Customer[i][0]==null || q3_Customer[i][0].length() ==0){
                continue;
            }
            customers[ind++] = q3_Customer[i][0];
        }

        for(int i=0;i<18;i++){
            for(int j=0;j<=i;j++){
                if(customers[i] == null || customers[j]==null){
                    continue;
                }
                if (customers[i].compareTo(customers[j]) < 0) //compares two  elements of the array
                {
                    String temp = customers[i];
                    customers[i] = customers[j];
                    customers[j] = temp;  //rearranged in a way to make sure it is in alphabetical order
                }
            }
        }
        println("-----Customer List------\n");
        boolean flagHasCustomer=false;
        for(int i=0; i< 18; i++){
            if(customers[i] == null){
                continue;
            }
            flagHasCustomer =true;
            println("\t"+(i+1)+" - "+customers[i]);
        }
        if(!flagHasCustomer){
            println("!!!Empty Queue Customers!!!");
        }
    }
    private static void storeDataToFile()throws IOException {
        File obj = new File("fuelStation.txt");
        obj.createNewFile();  //A new file is created using the method
        FileWriter theWriter = new FileWriter(obj.getName());
        for(int x = 0; x<q1_Customer.length; x++){
            theWriter.write("1@"+x+"@"+q1_Customer[x][0]+"@"+q1_Customer[x][1] + "\n");
        }
        for(int x = 0; x<q2_Customer.length; x++){
            theWriter.write("2@"+x+"@" +q2_Customer[x][0]+"@"+q2_Customer[x][1] + "\n");
        }
        for(int x = 0; x<q3_Customer.length; x++){
            theWriter.write("3@"+x+"@"+q3_Customer[x][0]+"@"+q3_Customer[x][1] + "\n");
        }
        println("Fuel Station Details Stored");
        theWriter.close();
    }
    private static void loadDataInFile() throws IOException {


        File obj = new File("fuelStation.txt");
        Scanner theReader = new Scanner(obj);

        while (theReader.hasNextLine()) {
            String data = theReader.nextLine();
            String[] spData= data.split("@",-2);

            if(spData[0].equals("1")){
                q1_Customer[Integer.parseInt(spData[1])] =new String[] {spData[2],spData[3]};
            }
            if(spData[0].equals("2")){
                q2_Customer[Integer.parseInt(spData[1])] =new String[] {spData[2],spData[3]};
            }
            if(spData[0].equals("3")){
                q3_Customer[Integer.parseInt(spData[1])] =new String[] {spData[2],spData[3]};
            }

        }
        println("Loaded Fuel Station Details");
    }
    private static void viewRemainingFuelStock(){
        println("\n\tRemaining Fuel Stock -"+ FuelStock +"L \n");
    }
    private static void addFuelStock(){
        println("Enter Fuel Qty:");
        if(input.hasNextDouble()){
            double qty = input.nextDouble();
            FuelStock+= qty;
            println("Current Stock - " + FuelStock);
        }
        else{
            println("Invalid Value Entered!");
        }
    }
    private static void FuelServe(){
        print("Please Enter Queue No:");
        int Q_number=-1;
        if(input.hasNextInt()){
            Q_number = input.nextInt();
        }else{
            println("Invalid Queue Number Entered!");
            return;
        }

        if(Q_number>3 || Q_number<1){
            println("Invalid Queue Number Entered!");
            return;
        }
        //----
        print("Please Enter QTY:");
        double qty=0;
        if(input.hasNextDouble()){
            qty = input.nextDouble();
        }else{
            println("Invalid Fuel QTY!");
            return;
        }

        if(qty<0){
            println("Invalid Fuel QTY!");
            return;
        }
        if(qty>FuelStock){
            println("This QTY is not available!\n"+"Current QTY - "+FuelStock+"L" );
            return;
        }

        if(Q_number==1){
            for(int c =0; c<6; c++){
                if( q1_Customer[c][0]!=null && q1_Customer[c][1].equals("new") ){
                    q1_Customer[c] =new String[] {q1_Customer[c][0],"Served"};
                    break;
                }
            }
        }
        if(Q_number==2){
            for(int c =0; c<6; c++){
                if( q2_Customer[c][0]!=null && q2_Customer[c][1].equals("new") ){
                    q2_Customer[c] =new String[] {q2_Customer[c][0],"Served"};
                    break;
                }
            }
        }
        if(Q_number==3){
            for(int c =0; c<6; c++){
                if( q3_Customer[c][0]!=null && q3_Customer[c][1].equals("new") ){
                    q3_Customer[c] =new String[] {q3_Customer[c][0],"Served"};
                    break;
                }
            }
        }
        FuelStock -=qty;
        println("\n\tFuel Served to customer!");
    }
    private static void print(String text){System.out.print(text);}
    private static void println(String text){System.out.println(text);}
}