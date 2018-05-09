package Database;

public class Main {

    public static void main(String[] args) throws Exception{

        String email = "eric.roose@student.kuleuven.be";
        String password = "er123";

        UserDB userDB = new UserDB(email, password);

        for(String comment : userDB.getComments()) {
            System.out.println(comment);
        }
    }
}
