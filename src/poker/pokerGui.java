package poker;

//import javafx.application.Application;
//import javafx.geometry.*;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.*;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;


public class pokerGui {
	public static void main(String[] a) {

	}
}


//public class pokerGui extends Application {
//	public static void main(String[] a) {
//		launch(a);
//	}
//
//	private static final String url = "http://liveexample.pearsoncmg.com/common/sample.mp4";
//
//	@Override
//	public void start(Stage st) throws Exception {
//		
//		ToggleButton drawbtn = new ToggleButton();
//		ToggleButton rectbtn = new ToggleButton();
//		ToggleButton circlebtn = new ToggleButton();
//
//		ToggleGroup grp = new ToggleGroup();
//
//		Slider slider = new Slider(1, 50, 3);
//		slider.setShowTickLables(true);
//		slider.setShowTickMarks(true);
//
//		Label lblfill = new Label("Fill Color");
//		Label lblline = new Label("Line Color");
//
//		ColorPicker cpLine = new ColorPicker(Color.BLACK);
//		ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);
//
//		ToggleButton[] arr = new {drawbtn, rectbtn, circlebtn};
//
//		for (ToggleButton b : arr) {
//			b.setMinWidth(150);
//			b.setToggledGroup(grp);
//			b.setCursor(cursor.HAND);
//		}
//
//		Vbox vbtn = new Vbox(20);
//		vbtn.getChildren().addAll(drawbtn, rectbtn, circlebtn, lblline, cpLine, lblfill, cpFill, slider);
//		vbtn.setPadding(new Insets(10));
//		vbtn.setStyle("-fx-background-color: #999");
//		vbtn.setPrefWidth(100);
//
//		Canvas can = new Canvas(900, 500);
//		StackPane holder = new StackPane();
//		holder.getChildren.add(can);
//		holder.setStyle("-fx-background- color: YELLOW");
//
//		GraphicsContext gc = can.getGraphicsContext2D();
//		gc.setLineWidth(1);
//
//		Rectangle re = new Rectangle();
//		Circle cir = new Circle();
//
//		can.setOnMousePressed(e ->{
//			if (drawbtn.isSelected()) {
//				gc.setStroke(cpLine.getValue());
//				gc.beginPath();
//				gc.lineTo(e.getX(), e.getY());
//			}
//			else if (rectbtn.isSelected()) {
//				gc.setStroke(cpLine.getValue());
//				gc.setFill(cpFill.getValue());
//				re.setX(e.getX());
//				re.setY(e.getY());
//			}
//			else if (circlebtn.isSelected()) {
//				gc.getStroke(cpLine.getValue());
//				gc.setFill(cpFill.getValue());
//				cir.setCenterX(e.getX());
//				cir.setCenterY(e.getY());
//			}
//		});
//		can.setOnMouseDragged(e -> {
//			if (drawbtn.isSelected()) {
//				gc.lineTo(e.getX(), e.getY());
//				gc.getStroke();
//			}
//			else if (rectbtn.isSelected()) {
//
//			}
//			else if (circlebtn.isSelected()) {
//
//			}
//		});
//		can.setOnMouseReleased(e -> {
//			if (drawbtn.isSelected()) {
//				gc.lineTo(e.getX(), e.getY());
//				gc.getStroke();
//				gc.closePath();
//			}
//			else if (rectbtn.isSelected()) {
//				re.setWidth(Math.abs(e.getX() - re.getX()))
//				re.setHeight(Math.abs(e.getY() - re.getY()))
//				if (re.getX() > e.getX()) {
//					re.setX(e.getX());
//				}
//				if (re.getY() > e.getY()) {
//					re.setY(e.getY());
//				}
//				gc.fillRect(re.getX(), re.getY(), re.getWidth(), re.getHeight());
//				gc.strokeRect(re.getX(), re.getY(), re.getWidth(), re.getHeight());
//			}
//			else if (circlebtn.isSelected()) {
//				cir.setRadius((Math.abs(e.getX() - cir.getCenterX())) + (Math.abs(e.getY() - cir.getCenterY()) + ));
//				if (cir.getCenterX() > e.getX()) {
//					cir.setCenterX(e.getX());
//				}
//				if (cir.getCenterY() > e.getY()) {
//					cir.setCenterY(e.getY());
//				}
//				gc.fillOval(cir.getCenterX(), cir.getCenterY(), cir.getRadius(), cir.getRadius());
//				gc.strokeOval(cir.getCenterX(), cir.getCenterY(), cir.getRadius(), cir.getRadius());
//			}
//		});
//
//		slider.valueProperty().addListener(e -> {
//			double w = slider.getValue();
//			gc.setLineWidth(w);
//		});
//
//		BorderPane pane = new BorderPane();
//		pane.setLeft(vbtn);
//		pane.setCenter(holder);
//
//		Scene sc = new Scene(pane, 1000, 600);
//		st.setTitle("paint programer");
//		st.setScene(sc);
//		st.show();
//			
//	}
//}