module com.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http; // Built-in Java networking for OpenAI

    // Allow JavaFX to access your controllers
    opens com.tracker.controller to javafx.fxml;
    
    // Allow Gson to serialize/deserialize your models
    opens com.tracker.model to com.google.gson;

    exports com.tracker;
}