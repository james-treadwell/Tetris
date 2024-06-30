import javax.swing.*;
import java.awt.*;

public class Block extends JLabel {
    public String color;
    private int row, col, value;
    private Image image;
    private ImageIcon imageIcon;

    public boolean isFilled = false;

    /**
     * Constructor
     * @param col
     * @param row
     */
    public Block(int col, int row, ImageIcon img){
        super(img);
        this.col = col;
        this.row = row;
        this.color = null;
        this.value = 0;
        this.image = new ImageIcon("download.png").getImage();
        this.imageIcon = new ImageIcon(image.getScaledInstance(59, 45, Image.SCALE_SMOOTH));
    }
    public ImageIcon getImageIcon(){
        return this.imageIcon;
    }

    public void setIsFilled(boolean b){
        this.isFilled = b;
    }

    public void setColor(String fileName) {
        this.image = new ImageIcon(fileName).getImage();
        this.imageIcon = new ImageIcon(image.getScaledInstance(59, 45, Image.SCALE_SMOOTH));
        super.setIcon(this.imageIcon);
        this.color = fileName;
        setVisible(true);
    }
    public String getColor() {
        return this.color;
    }
    public boolean getIsFilled(){
        return this.isFilled;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
