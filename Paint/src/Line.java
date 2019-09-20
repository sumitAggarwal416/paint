
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author Sumit Aggarwal - 000793607
 */
public class Line extends GeometricObject {
    private Color lineColor;
    public Line(Color COLOR){
        lineColor = COLOR;
    }

    public void setLineColor(Color newColor){lineColor = newColor;}

    /** Draws a Line in the paint program **/
    public void draw( GraphicsContext gc ){

        gc.setStroke(lineColor);
        gc.setLineWidth(getStrokeWidth());
        gc.strokeLine( super.getStartX(), super.getStartY(), super.getEndX(), super.getEndY() );

    }

    /** Returns the Object type **/
    public String getType() {
        return "Line";

    }
}
