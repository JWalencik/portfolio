import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.nio.ByteBuffer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.lang.Object;
import java.util.*;
import javafx.util.Duration;
import java.io.File;
import javafx.concurrent.*;
import java.net.*;
import java.awt.Color;
import javafx.beans.*;
import javafx.beans.value.*;
import cam.*;


public class JavaFXApp extends Application //implements ChangeListener<Frames>
 {
  private static final int FRAME_WIDTH  = 800;
  private static final int FRAME_HEIGHT = 680;

  Stage stage;
  Slider zoom, focus, brightness, focus1;
  GraphicsContext gc;
  Canvas canvas;
  Frames frames;
  int result;
  byte buffer[];
  PixelWriter pixelWriter;
  PixelFormat<ByteBuffer> pixelFormat;
  Timeline timeline;
    
  public static void main(String[] args) {
            launch(args);
	        }

@Override
  public void start(Stage primaryStage) {
  BorderPane root = new BorderPane();
  stage = primaryStage;
  root.setPadding(new Insets(20, 0, 20, 20));


  HBox hslider1 = new HBox();
  HBox hslider2 = new HBox();
  HBox hslider3 = new HBox();
  HBox hslider4 = new HBox();
  VBox vSliders = new VBox();
  VBox Buttons = new VBox();

  hslider1.setPrefWidth(900.0d);
  hslider2.setPrefWidth(900.0d);
  hslider3.setPrefWidth(900.0d);
  vSliders.setSpacing(15);
  vSliders.setPadding(new Insets(0, 20, 10, 20));
  Buttons.setSpacing(10);
  Buttons.setPadding(new Insets(0, 20, 10, 20));
  final Label Lzoom = new Label("ZOOM");
  final Label Lfocus = new Label("X");
  final Label Lfocus1 = new Label("Y");
  final Label Lbrightness = new Label("Brightness");

  //slidery
  zoom = new Slider(0, 100, 50);
  zoom.setShowTickMarks(true);
  zoom.setShowTickLabels(true);
  zoom.setMaxWidth( 800.0d );
  root.getChildren().add(zoom);

  focus = new Slider(0, 150, 50);
  focus.setShowTickMarks(true);
  focus.setShowTickLabels(true);
  focus.setMaxWidth( 800.0d );
  root.getChildren().add(focus);

  focus1 = new Slider(0, 150, 50);
  focus1.setShowTickMarks(true);
  focus1.setShowTickLabels(true);
  focus1.setMaxWidth( 800.0d );
  root.getChildren().add(focus1);

  brightness = new Slider(0, 100, 50);
  brightness.setShowTickMarks(true);
  brightness.setShowTickLabels(true);
  brightness.setMaxWidth( 800.0d );
  root.getChildren().add(brightness);

  hslider1.getChildren().addAll(Lzoom, zoom);
  hslider2.getChildren().addAll(Lfocus, focus);
  hslider3.getChildren().addAll(Lbrightness, brightness);
  hslider4.getChildren().addAll(Lfocus1, focus1);
  hslider1.setHgrow(zoom, Priority.ALWAYS);
  hslider2.setHgrow(focus, Priority.ALWAYS);
  hslider3.setHgrow(brightness, Priority.ALWAYS);
  hslider4.setHgrow(focus1, Priority.ALWAYS);
  vSliders.getChildren().addAll(hslider1, hslider3, hslider2, hslider4);
  root.setBottom(vSliders);

  //-----------------------------------------------------------------------

  Button Snapbtn = new Button();
  Snapbtn.setText("SNAP");
  Snapbtn.setOnAction(this::snap);
  root.getChildren().add(Snapbtn);

  FileChooser fileChooser = new FileChooser();
  fileChooser.setTitle("Zapisz jako");
  fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));

  Button Savebtn = new Button();
  Savebtn.setText("Zapisz jako");

  Button Resetbtn = new Button();
  Resetbtn.setText("Play");

  Snapbtn.setMaxWidth(140);
  Resetbtn.setMaxWidth(140);
  Savebtn.setMaxWidth(140);
  root.getChildren().add(Savebtn);
  root.getChildren().add(Resetbtn);

  Resetbtn.setOnAction(this::reset);

      Buttons.getChildren().addAll(Snapbtn, Savebtn, Resetbtn);
      root.setRight(Buttons);
							
  
  primaryStage.setOnCloseRequest(e -> {
                                       e.consume();
                                       exit_dialog();
                                      });
  //--------------------------------------------------------------------------
    frames = new Frames();

      result = frames.open_shm("/frames");

      canvas     = new Canvas(650, 490);
      gc         = canvas.getGraphicsContext2D();


      timeline = new Timeline(new KeyFrame(Duration.millis(130), e->disp_frame()));

      timeline.setCycleCount(Timeline.INDEFINITE);

      timeline.play();
      root.setLeft(canvas);
  //--------------------------------------------------------------------------

  Savebtn.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent event) {
            //Opening a dialog box
            File selectedFile = fileChooser.showSaveDialog(stage);
            BufferedImage bi = frames.convert_to_BI(buffer);

              //  selectedFile = fileChooser.getSelectedFile();
try
{
                ImageIO.write(bi , "png", selectedFile);
}
catch(Exception e) {}
         }
      });

  Scene scene = new Scene(root);
  primaryStage.setTitle("JavaFX App");
  primaryStage.setScene(scene);
  primaryStage.setWidth(FRAME_WIDTH + 10);
  primaryStage.setHeight(FRAME_HEIGHT+ 80);
  primaryStage.show();
  
 }
 
 public void exit_dialog()
  {
   System.out.println("exit dialog");
   

   Alert alert = new Alert(AlertType.CONFIRMATION,
                           "Do you really want to exit the program?.", 
 			    ButtonType.YES, ButtonType.NO);

   alert.setResizable(true);
   alert.onShownProperty().addListener(e -> { 
                                             Platform.runLater(() -> alert.setResizable(false)); 
                                            });

  Optional<ButtonType> result = alert.showAndWait();
  if (result.get() == ButtonType.YES)
   {
    Platform.exit();
   } 
  else 
   {
   }

  }

  private void disp_frame()
     {
      buffer = frames.get_frame();

      BufferedImage bi = frames.convert_to_BI(buffer);

      //bi.adjustBrightness(brightness.getValue());

      double w = (100-zoom.getValue()/2)/100 * bi.getWidth();
      double h = (100-zoom.getValue()/2)/100 * bi.getHeight();
      double x = canvas.getWidth()/2 - w/2;
      double y = canvas.getHeight()/2 - h/2;

      x = Math.max(0, Math.min(640-w, x + focus.getValue()));
      y = Math.max(0, Math.min(480-h, y + focus1.getValue()));

      bi = bi.getSubimage((int)x, (int)y, (int)w, (int)h);

      bi = enhanceBrightness(bi, (int)brightness.getValue());

      gc.drawImage( convertToFxImage(bi), 0, 0, 640, 480);

     }

    private void snap(ActionEvent e)
    {
        timeline.stop();
        System.out.println("Zatrzymal !!");
    }

    private void reset(ActionEvent e)
    {
        timeline.play();
    }

    private static Image convertToFxImage(BufferedImage image) {
    WritableImage wr = null;
    if (image != null) {
        wr = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
    }

    return new ImageView(wr).getImage();
}

public BufferedImage enhanceBrightness(BufferedImage image, int brightnessValue) {
    int width = image.getWidth();
    int height = image.getHeight();
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            Color color = new Color(image.getRGB(x, y));
            int red = clamp(color.getRed() + brightnessValue);
            int green = clamp(color.getGreen() + brightnessValue);
            int blue = clamp(color.getBlue() + brightnessValue);
            Color newColor = new Color(red, green, blue);
            image.setRGB(x, y, newColor.getRGB());
        }
    }
    return image;
}
private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
}
}
