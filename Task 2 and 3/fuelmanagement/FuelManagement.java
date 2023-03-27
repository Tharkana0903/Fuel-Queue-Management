package fuelmanagement;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 SD2 Course Work Task 02/03 (Classes Version & waiting List).
 Fuel Center Program.  E.M.T.P.S. Bandara- w1903065 (20211274)
 */


public class FuelManagement {

    private double FuelStock = 6600;
    // private static String[][] q1_Customer = new String[6][2];//6 customer in 3 queue {name,status}
    // private static String[][] q2_Customer = new String[6][2];//6 customer in 3 queue {name,status}
    // private static String[][] q3_Customer = new String[6][2];//6 customer in 3 queue {name,status}

    private FuelQueue[] feulQ = new FuelQueue[5];
    private ArrayList<Passenger> waitingList = new ArrayList<Passenger>();

    private double ServedQTY = 0;
    private double fuelPrice = 430;
    private Scanner input = new Scanner(System.in);

    public FuelManagement() {
        for (int i = 0; i < feulQ.length; i++) {
            feulQ[i] = new FuelQueue();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int loop = 0;
        FuelManagement fuelManagement = new FuelManagement();
        while (loop < 1) {
            println("");
            println("====================================");
            println("=======Fuel Station System==========");
            println("====================================");
            println("");
            println("100 or VFQ:\t View all Fuel Queues.");
            println("101 or VEQ:\t View all Empty Queues.");
            println("102 or ACQ:\t Add customer to a Queue.");
            println("103 or RCQ:\t Remove a customer from a Queue.");
            println("104 or PCQ:\t Remove a served customer.");
            println("105 or VCS:\t View Customers Sorted in alphabetical order");
            println("106 or SPD:\t Store Program Data into file.");
            println("107 or LPD:\t Load Program Data from file.");
            println("108 or STK:\t View Remaining Fuel Stock.");
            println("109 or AFS:\t Add Fuel Stock.");
            println("999 or EXT:\t Exit the Program.");
            println("110 or IFQ:\t print the income of each Fuel queue.");
            println("");

            print("Please Enter action code : ");
            String code = fuelManagement.input.next();

            if (code.equals("100") || code.equals("VFQ") || code.equals("vfq")) {
                fuelManagement.viewQueue();
            } else if (code.equals("101") || code.equals("VEQ") || code.equals("veq")) {
                fuelManagement.viewEmptyQueue();
            } else if (code.equals("102") || code.equals("ACQ") || code.equals("acq")) {
                fuelManagement.addCustomer();
            } else if (code.equals("103") || code.equals("RCQ") || code.equals("rcq")) {
                fuelManagement.removeCustomer();
            } else if (code.equals("104") || code.equals("PCQ") || code.equals("pcq")) {
                fuelManagement.removeServedCustomer();
            } else if (code.equals("105") || code.equals("VCS") || code.equals("vcs")) {
                fuelManagement.viewCustomerSorted();
            } else if (code.equals("106") || code.equals("SPD") || code.equals("spd")) {
                fuelManagement.storeDataToFile();
            } else if (code.equals("107") || code.equals("LPD") || code.equals("lpd")) {
                fuelManagement.loadDataInFile();
            } else if (code.equals("108") || code.equals("STK") || code.equals("stk")) {
                fuelManagement.viewRemainingFuelStock();
            } else if (code.equals("109") || code.equals("AFS") || code.equals("afs")) {
                fuelManagement.addFuelStock();
            } else if (code.equals("000") || code.equals("SRV") || code.equals("srv")) {
                fuelManagement.FuelServe();
            } else if (code.equals("110") || code.equals("IFQ") || code.equals("ifq")) {
                fuelManagement.PrintQIncome();
            } else if (code.equals("999") || code.equals("EXT") || code.equals("ext")) {
                break;
            } else {
                println("Invalid Option!!");
            }
        }
    }

    private void viewQueue() {
        println("----------------------------");
        println("---------View Queue---------");

        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];
            println("\n Queue No. " + (q + 1) + " customers:");

            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                String cusName = passenger == null ? "N/A" : passenger.firstName + " " + passenger.secondName + " - " +passenger.noOfLiters + "L";
                println(cusName);
            }
        }
        println("----------------------------");

    }

    private void viewEmptyQueue() {
        println("----------------------------");
        println("----View All Empty Queue----");

        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];

            boolean isEmpty_Q = true;
            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                isEmpty_Q = passenger == null;
                if (!isEmpty_Q) {
                    break;
                }
            }
            if (isEmpty_Q) {
                print("Queue " + (q + 1) + " is empty\n");
            }
        }
        println("----------------------------");
    }

    private void addCustomer() {

        int minQNo = 0;
        int minLength = -1;
        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQ = feulQ[q];

            for (int c = 0; c < fuelQ.passengers.length; c++) {
                Passenger passenger = fuelQ.passengers[c];
                if (passenger == null) {
                    if (minLength > c) {
                        minLength = c;
                        minQNo = q;
                    } else if (minLength == -1) {
                        minLength = c;
                        minQNo = q;
                    }
                    break;
                }
                if (fuelQ.passengers.length - 1 == c) {
                    minQNo = q + 1;
                }
            }
        }

        print("Please Enter Customer First Name:");
        String fname = input.next();

        print("Please Enter Customer Second Name:");
        String sname = input.next();

        print("Please Enter Vehicle  No:");
        String vNo = input.next();

        print("Please Enter Required QTY (Liters):");
        double rQTY;
        if (input.hasNextDouble()) {
            rQTY = input.nextDouble();
        } else {
            println("Invalid Required QTY Entered!");
            return;
        }
        if (minQNo >= feulQ.length) {
            waitingList.add(new Passenger(fname, sname, vNo, rQTY));
            println("\nSuccessfully added a customer to waiting list");
            return;
        }
        int locationIndex = FindQueueLocation(minQNo);
        FuelQueue fuelQueue = feulQ[minQNo];
        if (locationIndex == -1) {
            println("\n Invalid Location found");
            return;
        }
        fuelQueue.passengers[locationIndex] = new Passenger(fname, sname, vNo, rQTY);
        println("\nSuccessfully added a customer to queue");

        //check fuel estimation
        double fuel_need = 0;

        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQ = feulQ[q];

            for (int c = 0; c < fuelQ.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                if (passenger != null) {
                    fuel_need += passenger.noOfLiters;
                }
            }

            if ((FuelStock - fuel_need) <= 500) {
                println("\n\tWarning - Reached 500L of fuel!");
            }
        }
    }

    private int FindQueueLocation(int Q_number) {

        if (feulQ.length < Q_number || Q_number < 0) {
            println("Invalid Queue Number!");
            return -1;
        }
        FuelQueue fuelQueue = feulQ[Q_number];
        for (int i = 0; i < fuelQueue.passengers.length; i++) {
            if (fuelQueue.passengers[i] == null) {
                return i;
            }
        }
        return 0;
    }

    private void removeCustomer() {
        print("Please Enter Queue No:");
        int Q_number = -1;
        if (input.hasNextInt()) {
            Q_number = input.nextInt();
        } else {
            println("Invalid Queue Number Entered!");
            return;
        }

        if (Q_number > 3 || Q_number < 1) {
            println("Invalid Queue Number Entered!");
            return;
        }
        //----
        print("Please Enter Location No:");
        int Q_location = -1;
        if (input.hasNextInt()) {
            Q_location = input.nextInt();
        } else {
            println("Invalid Queue Location!");
            return;
        }

        if (Q_location > 6 || Q_location < 1) {
            println("Invalid Queue Location Entered!");
            return;
        }
        feulQ[Q_number - 1].passengers[Q_location - 1] = null;

        println("Customer Removed Successfully");
    }

    private void removeServedCustomer() {
        println("Strat Served Customer Remove Process..");
        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];

            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                if (passenger == null) {
                    continue;
                }
                if (!passenger.status.equals("New")) {
                     fuelQueue.passengers[c] = null;
                }
            }
        }

        println("Reordering Queue...");
        //reset queue
        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];
            int lastEmptyIndex = -1;
            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];

                boolean flag = passenger == null;
                if (!flag && lastEmptyIndex != -1) {
                    fuelQueue.passengers[lastEmptyIndex] = fuelQueue.passengers[c];
                    fuelQueue.passengers[c] = null;
                }
                if (flag) {
                    lastEmptyIndex = c;
                }
            }
        }
        //add waiting list to q
        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];
            int lastEmptyIndex = -1;
            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                if (passenger == null && !waitingList.isEmpty()) {
                    fuelQueue.passengers[c] = waitingList.get(0);
                    waitingList.remove(0);
                }
                if (waitingList.isEmpty()) {
                    break;
                }
            }
        }
        println("End Served Customer Remove Process!");

    }

    private void viewCustomerSorted() {

        String[] customers = new String[feulQ.length * feulQ[0].passengers.length];
        int ind = 0;

        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];

            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                if (passenger == null) {
                    continue;
                }
                customers[ind++] = passenger.firstName + " " + passenger.secondName;
            }
        }
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j <= i; j++) {
                if (customers[i] == null || customers[j] == null) {
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
        boolean flagHasCustoemr = false;
        for (int i = 0; i < 18; i++) {
            if (customers[i] == null) {
                continue;
            }
            flagHasCustoemr = true;
            println("\t" + (i + 1) + " - " + customers[i]);
        }
        if (!flagHasCustoemr) {
            println("!!!Empty Queue Customers!!!");
        }
    }

    private void storeDataToFile()  {

        try {
            FileWriter writer = new FileWriter("DetailsOfFuelStation");
            writer.write("Fuel remaining stock: "+ FuelStock);
            for (int i = 0; i < feulQ.length; i++) {
                writer.write("\nQueue "+ (i+1) +" data\n");
                for (int j = 0; j < feulQ[i].passengers.length; j++) {
                    writer.write(feulQ[i].passengers[j]+"\n");
                }
                writer.write("");
            }
            writer.close();
            System.out.println("Successfully stored data into the file.");
        }
        catch (IOException e) {     //Runs if there was an error in file creating or writing
            System.out.println("An error occurred while storing data into the file. Please try again later.");
            e.printStackTrace();    //Tool used to handle exceptions and errors (gives the line number and class name where exception happened)
        }
    }

    private void loadDataInFile() throws IOException, ClassNotFoundException {

        String fileName = "fuelStationClassV.txt";
        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        FuelManagement fuelManagement = (FuelManagement) ois.readObject();
        ois.close();
        this.FuelStock = fuelManagement.FuelStock;
        this.ServedQTY = fuelManagement.ServedQTY;
        this.feulQ = fuelManagement.feulQ;
        this.fuelPrice = fuelManagement.fuelPrice;
    }

    private void viewRemainingFuelStock() {
        println("\n\tRemaining Fuel Stock :- " + FuelStock + "L \n");
    }

    private void addFuelStock() {
        println("Enter Fuel Qty:");
        if (input.hasNextDouble()) {
            double qty = input.nextDouble();
            if (qty + FuelStock > 6600) {
                println("Can't add fuel stock!, exceed capacity.");
                return;
            }
            FuelStock += qty;
            println("Current Stock - " + FuelStock);
        } else {
            println("Invalid Value Entered!");
        }
    }

    private void FuelServe() {
        print("Please Enter Queue No:");
        int Q_number = -1;
        if (input.hasNextInt()) {
            Q_number = input.nextInt();
        } else {
            println("Invalid Queue Number Entered!");
            return;
        }

        if (Q_number > feulQ.length || Q_number < 1) {
            println("Invalid Queue Number Entered!");
            return;
        }

        double qty = 0;
        for (int c = 0; c < feulQ[Q_number-1].passengers.length; c++) {
            Passenger passenger = feulQ[Q_number-1].passengers[c];
            if (passenger != null && passenger.status.equals("New")) {
                if (passenger.noOfLiters > FuelStock) {
                    println("This QTY is not available!\n" + "Currnet QTY - " + FuelStock + "L");
                    return;
                }
                qty = passenger.noOfLiters;
                passenger.status = "Served";
                break;
            }
        }

        FuelStock -= qty;
        println("\n\tFuel Served to customer!");
    }

    public void PrintQIncome() {
        for (int q = 0; q < feulQ.length; q++) {
            FuelQueue fuelQueue = feulQ[q];
            double income = 0;
            for (int c = 0; c < fuelQueue.passengers.length; c++) {
                Passenger passenger = fuelQueue.passengers[c];
                if (passenger == null) {
                    continue;
                }
                income += passenger.noOfLiters * fuelPrice * 1.0;
            }
            println("Queue " + (q + 1) + "Estimate price Rs. " + income);
        }
    }

    private static void print(String text) {
        System.out.print(text);
    }

    private static void println(String text) {
        System.out.println(text);
    }
}
