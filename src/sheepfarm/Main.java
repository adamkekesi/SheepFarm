package sheepfarm;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Farm farm = new Farm();

        while (true) {
            farm.printGame();
            if (farm.isGameOver()) {
                System.out.println("A sheep has escaped");
                return;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}