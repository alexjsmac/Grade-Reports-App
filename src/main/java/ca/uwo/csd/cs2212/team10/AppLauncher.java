package ca.uwo.csd.cs2212.team10;

/**
 * The entry point of the gradebook program
 * @author Team 10
 */
public class AppLauncher{
    public static void main(String[] args){
        MainWindow window = new MainWindow(); //delegate to the constructor of MainWindow
        window.setVisible(true);
    }
}
