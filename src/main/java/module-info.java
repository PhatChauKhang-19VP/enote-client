module pck.enoteclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires AnimateFX;

    opens pck.enote to javafx.fxml;
    exports pck.enote;

    opens pck.enote.controller to javafx.fxml;
    exports pck.enote.controller;
}