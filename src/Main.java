public class Main {
    public static void main(String[] args) {


        try {

            Board.printBoard(); // LESSON 5
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("DONE!");
        }
    }
}
