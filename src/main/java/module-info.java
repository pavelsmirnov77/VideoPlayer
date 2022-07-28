module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens video_player to javafx.fxml;
    exports video_player;
}