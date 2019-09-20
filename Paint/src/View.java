
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 *
 * @author Sumit Aggarwal - 000793607
 */
public class View extends Application {
    /** Width for the canvas and scene **/
    private final double WIDTH = 1366;
    /** Height for the canvas and scene **/
    private final double HEIGHT = 768;
    /** Declaring the canvas **/
    private Canvas canvas;
    /** Declaring GraphicsContext **/
    private GraphicsContext gc;
    /** Declares a Geometric object **/
    private GeometricObject c;
    /** Creates an Array list of Geometric Objects **/
    private ArrayList<GeometricObject> canvasShapes = new ArrayList<>();

    private ColorPicker colorPicker;

    private Color color = Color.BLUE;

    /** Updates the canvas **/
    public void updateCanvas() {

        gc.clearRect(0, 0, WIDTH, HEIGHT);

        /** Draw all other objects **/
        for (int i = 0; i < canvasShapes.size(); i++)
            canvasShapes.get(i).draw(gc);
    }

    /** ------------------------------ Mouse Events ------------------------------ **/

    /** On Click Mouse Event **/
    public void pressHandler(MouseEvent me) {

        if (me.getButton().equals(MouseButton.PRIMARY))
            try {
                c.setStartX(me.getX());
                c.setStartY(me.getY());

            } catch (Exception ex) {
                new Alert(Alert.AlertType.WARNING, "Please select a shape").showAndWait();
            }

    }

    /** On mouse release mouse event **/
    private void releaseHandler(MouseEvent me) {
        if (c != null)
            canvasShapes.add(c);

        switch (c.getType()) {
            case "Line":
                c = new Line(color);
                break;
            case "Square":
                c = new Square();
                break;
        }
    }

    /** On drag mouse event **/
    public void dragHandler(MouseEvent me) {
        try {
            c.setEndX(me.getX());
            c.setEndY(me.getY());
            updateCanvas();
            c.draw(gc);
        } catch (Exception ex) {
            new Alert(Alert.AlertType.WARNING, "Please select a shape").showAndWait();
        }
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Paint Program");
        stage.setScene(scene);

        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        /** ------------------------------ Buttons ------------------------------ **/

        /** Allows the user to create a square **/
        Button square = new Button("Square");
        square.relocate(175,625);

        /** Allows the user to create a line **/
        Button line = new Button("Line");
        line.relocate(300,625);

        /** Fills the shape with color **/
        ColorPicker CP = new ColorPicker(color);
        CP.relocate(175,695);
        //CP.setOnAction(this::resetHandler);

        Button b = new Button("Change color");
        b.relocate(200,720);

        Button width = new Button("Change");
        width.relocate(350,725);

        /** A button which removes the last shape created **/
        Button undo = new Button("Undo");
        undo.relocate(600,630);

        /** A button which clears the canvas **/
        Button clear = new Button("Clear All");
        clear.relocate(725,625);

        /** ------------------------------ Labels ------------------------------  **/

        /** Label for the color fill button **/
        Label fillLabel = new Label("Fill");
        fillLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        fillLabel.relocate(175,675);

        /** Label for the color stroke button **/
        Label strokeLabel = new Label("Stroke Width");
        strokeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        strokeLabel.relocate(300,675);

        /** ------------------------------ Text Fields ------------------------------ **/

        /** Creates a text field that accepts number input from the user **/
        TextField strokeWidth = new TextField("2");
        strokeWidth.relocate(350,695);

        /** ------------------------------ On Action Events ------------------------------ **/

        /** Creates a Rectangle Object when rectangle button is clicked **/
        square.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c = new Square();
            }
        });

        /** Creates a Line Object when line button is clicked **/
        line.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c = new Line(color);
            }
        });

        width.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    c.setStrokeWidth(Integer.parseInt(strokeWidth.getText()));
                }
                catch(NullPointerException ex){

                }
            }
        });

        /** remove the latest shape **/
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    canvasShapes.remove(canvasShapes.size() - 1);
                    updateCanvas();
                } catch (IndexOutOfBoundsException ex) {

                }
            }
        });

        /** Clears all the shapes on the screen **/
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvasShapes.clear();
                updateCanvas();
            }
        });

        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    c.setColor(CP.getValue());
                }
                catch(NullPointerException ex){

                }
            }
        });



        /** ------------------------------ Back Ground ------------------------------ **/
        Rectangle border = new Rectangle();
        border.setFill(Color.GREY);
        border.setWidth(WIDTH);
        border.setHeight(160);
        border.setLayoutY(616);


        root.getChildren().addAll(canvas,border, b,square, line, CP, fillLabel, strokeWidth, strokeLabel, undo, clear,width);

        /** Called when left mouse button is clicked **/
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressHandler);
        /** Called when mouse button is clicked and dragged **/
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragHandler);
        /** Called when mouse button is released **/
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::releaseHandler);

        stage.show();
    }

    private void resetHandler(ActionEvent actionEvent){
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0,0,WIDTH,HEIGHT);
        try {
            for (GeometricObject GO : canvasShapes) {
                GO.setColor(colorPicker.getValue());
                GO.draw(gc);
            }
        }
        catch(NullPointerException ex){

            }
    }

    /**
     * Make no changes here.
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

}
