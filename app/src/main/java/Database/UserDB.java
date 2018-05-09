package Database;

import java.security.Key;
import java.util.ArrayList;

public class UserDB {

    JsonReader jsonReader;

    private int userID;
    private String email, password;
    public String firstName, lastName;

    private ArrayList<String> locationNames;
    ArrayList<String> locationAddresses;
    ArrayList<Double> latitudes;
    ArrayList<Double> longitudes;
    ArrayList<String> comments;

    //existing account...
    public UserDB(String email, String password) throws Exception {

        locationNames = new ArrayList<>();
        locationAddresses = new ArrayList<>();
        latitudes = new ArrayList<>();
        longitudes = new ArrayList<>();
        comments = new ArrayList<>();

        jsonReader = new JsonReader();

        String e = jsonReader.getString(Keys.email, Links.emailVerification + email);
        String p = jsonReader.getString(Keys.password, Links.passwordVerification + password);

        if(e != null && p != null) {
            userID = jsonReader.getInt(Keys.userId, Links.id_through_email + email);
            this.email = email;
            this.password = password;
            firstName = jsonReader.getString(Keys.firstName, Links.firstName_through_id + userID);
            lastName = jsonReader.getString(Keys.lastName, Links.lastName_through_id + userID);
            updateLocations();
        } else {
            userID = 0;
            this.email = null;
            this.password = null;
            firstName = null;
            lastName = null;
        }
    }

    //new account...
    public UserDB(String email, String Password, String firstName, String lastName) {


    }

    public void updateLocations() throws Exception{
        locationNames.clear();
        locationAddresses.clear();
        latitudes.clear();
        longitudes.clear();
        comments.clear();

        locationNames = jsonReader.getStrings(Keys.locationName, Links.locationName_userId + userID);
        locationAddresses = jsonReader.getStrings(Keys.locationAddress, Links.locationAddress_userId + userID);
        latitudes = jsonReader.getDoubles(Keys.latitude, Links.latitude_userId + userID);
        longitudes = jsonReader.getDoubles(Keys.longitude, Links.longitude_userId + userID);
        comments = jsonReader.getStrings(Keys.comments, Links.comments_userId + userID);

    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<String> getLocationNames() {
        return locationNames;
    }

    public ArrayList<String> getLocationAddresses() {
        return locationAddresses;
    }

    public ArrayList<Double> getLatitudes() {
        return latitudes;
    }

    public ArrayList<Double> getLongitudes() {
        return longitudes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
}
