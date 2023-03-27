package fuelmanagement;

public class Passenger {

    public String firstName;
    public String secondName;
    public String vehicleNo;
    public double noOfLiters;
    public String status;

    public Passenger() {

    }
    public Passenger(String firstName, String secondName, String vehicleNo, double noOfLiters) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.vehicleNo = vehicleNo;
        this.noOfLiters = noOfLiters;
        this.status= "New";
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", noOfLiters=" + noOfLiters +
                ", status='" + status + '\'' +
                '}';
    }
}
