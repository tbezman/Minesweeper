import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Terence on 5/15/2014.
 */
public class MinesweeperButton extends JButton implements MouseListener{

    private int x = 0, y = 0, bombsInRadius = 0;
    private boolean isFlagged = false;
    public boolean isBomb = false;
    private boolean isHidden = true;

    MinesweeperGUI minesweeperGUI;


    public MinesweeperButton(MinesweeperGUI minesweeperGUI){

        this.minesweeperGUI = minesweeperGUI;
        init();
    }

    public MinesweeperButton(int x, int y, MinesweeperGUI minesweeperGUI){
        this.x = x;
        this.y = y;

        this.minesweeperGUI = minesweeperGUI;

        init();
    }

    private void init() {
        addMouseListener(this);
        setBackground(Color.lightGray);
    }

    public void postInit(){
        for (MinesweeperButton button : getBombsInRadiusOne()){
            if (button.isBomb)
                bombsInRadius++;
        }
    }

    public void setFlagged(){
        if (!isFlagged)
        {
            isFlagged = true;
            setBackground(Color.red);
        }else if (isFlagged)
        {
            isFlagged = false;
            setBackground(Color.lightGray);
        }
    }
    
    public void showMine(){
        isHidden = false;

        if (isBomb)
        {
            setBackground(Color.black);
            minesweeperGUI.endGame();
        }else
        {
            setBackground(Color.white);
            if (bombsInRadius > 0)
                setText(String.valueOf(bombsInRadius));
        }

    }

    public void setBomb(){
        this.isBomb = true;
    }

    public int getXOnGrid(){
        return this.x;
    }

    public int getYOnGrid(){
        return this.y;
    }

    public MinesweeperButton getBombAt(int x, int y){
        for (MinesweeperButton button : minesweeperGUI.minesList)
            if (button.getXOnGrid() == x && button.getYOnGrid() == y)
                return button;

        return null;
    }

    public ArrayList<MinesweeperButton> getBombsInRadiusOne(){
        ArrayList<MinesweeperButton> returnList = new ArrayList<MinesweeperButton>();

        returnList.add(getBombAt(getXOnGrid() - 1, getYOnGrid()));
        returnList.add(getBombAt(getXOnGrid() - 1, getYOnGrid()-1));
        returnList.add(getBombAt(getXOnGrid() - 1, getYOnGrid()+1));
        returnList.add(getBombAt(getXOnGrid() + 1, getYOnGrid()));
        returnList.add(getBombAt(getXOnGrid() + 1, getYOnGrid()+1));
        returnList.add(getBombAt(getXOnGrid() + 1, getYOnGrid()-1));
        returnList.add(getBombAt(getXOnGrid(), getYOnGrid()-1));
        returnList.add(getBombAt(getXOnGrid(), getYOnGrid()+1));

        returnList.removeAll(Collections.singleton(null));

        return  returnList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e))
            //If was right clicked
            setFlagged();
        else if (isFlagged)
        {
          setFlagged();
        }else if (isHidden)//If left mouse click
        {
            showMine();

            if (bombsInRadius == 0 && !isBomb)
                clickAndShowRadius(e);
        }

        hasPlayerWon();
    }

    private void hasPlayerWon() {
        boolean hasWon = true;

        for (MinesweeperButton button : minesweeperGUI.minesList)
            if (button.isBomb && (!button.isHidden || !button.isFlagged))
                hasWon = false;

        if (hasWon)
            JOptionPane.showMessageDialog(null, "Congrats, you won!");
    }

    private void clickAndShowRadius(MouseEvent e) {
        for (MinesweeperButton button : getBombsInRadiusOne()){
            if (!button.isBomb && button.isHidden)
                button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
