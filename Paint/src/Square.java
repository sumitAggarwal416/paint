
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author Sumit Aggarwal - 000793607
 */
public class Square extends GeometricObject {
    /** Side of the square **/
    private double side;

    private Color fillColor = super.getColor();

    public void setFillColor(Color newColor){fillColor = newColor;}
    /** Draws the rectangle **/
    public void draw(GraphicsContext gc){

        // Starting point
        side = super.getStartX() - super.getEndX();

        if (super.getStartX() < super.getEndX())
            side =  super.getEndX() - super.getStartX();

        // x position
        double x = super.getStartX();
        if (super.getStartX() > super.getEndX())
            x = super.getEndX();

        // y position
        double y = super.getStartY();
        if (super.getStartY() > super.getEndY())
            y = super.getEndY();

        gc.setFill(fillColor);
        gc.fillRect(x, y, side, side);
        gc.strokeRect(x, y, side, side);


    }

    /** Returns the object type **/
    public String getType() {
        return "Square";
    }



}
