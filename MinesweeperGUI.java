import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Terence on 5/15/2014.
 */
public class MinesweeperGUI extends JFrame{

    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;

    public ArrayList<MinesweeperButton> minesList = new ArrayList<MinesweeperButton>();

    public MinesweeperGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);

        BOARD_WIDTH = Integer.valueOf(JOptionPane.showInputDialog(null, "Enter width of game"));
        BOARD_HEIGHT = Integer.valueOf(JOptionPane.showInputDialog(null, "Enter height of game"));

        setLayout(new GridLayout(BOARD_WIDTH, BOARD_HEIGHT));

        init();

        for (MinesweeperButton button : minesList)
            add(button);

        setVisible(true);

    }

    private void init() {

        for (int x = 1; x <= BOARD_WIDTH; x++)
            for (int y = 1; y < BOARD_HEIGHT; y++) {

                MinesweeperButton currentButton = new MinesweeperButton(x, y, this);
                minesList.add(currentButton);

                //Gives current mine 10% chance of having bomb
                if (new Random().nextDouble() <= .10)
                    currentButton.setBomb();

            }

        for (MinesweeperButton button : minesList){
            button.postInit();
        }

    }

    public void endGame() {
        for (MinesweeperButton button : minesList)
            if (button.isBomb)
                button.setBackground(Color.black);
    }
}
