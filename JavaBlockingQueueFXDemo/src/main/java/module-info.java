module JavaBlockingQueueFXDemo {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens MainPackage to javafx.fxml;         // FXML-hez
    exports MainPackage;                     // Main + FXGUI 
    exports CounterPackage;                  // Counter available for MainPackage/FxGUI 
    exports QueuesPackage;                   // If use PutValue/TakValue 
    exports GenerateValueTypePackage;        // If use TypeGenerator-t 
}
