package video_player;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    private MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView;
    private String filePath;
    @FXML
    private Slider slider;
    @FXML
    private Slider seekSlider;
    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    private void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();

        if (filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            //mediaView = new MediaView(mediaPlayer);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue()/100);
                }
            });
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                    seekSlider.setValue(newValue.toSeconds());
                }
            });

            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
            });
            mediaPlayer.play();
        }
    }

    @FXML
    void pause(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    void play(ActionEvent event) {
        mediaPlayer.play();
    }

    @FXML
    void slow(ActionEvent event) {
        mediaPlayer.setRate(0.75);
    }

    @FXML
    void slower(ActionEvent event) {
        mediaPlayer.setRate(0.5);
    }

    @FXML
    void fast(ActionEvent event) {
        mediaPlayer.setRate(1.5);
    }

    @FXML
    void faster(ActionEvent event) {
        mediaPlayer.setRate(2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
