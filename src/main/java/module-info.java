module com.example.alirezahaidari_comp228lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.alirezahaidari_comp228lab4 to javafx.fxml;
    exports com.example.alirezahaidari_comp228lab4;
}