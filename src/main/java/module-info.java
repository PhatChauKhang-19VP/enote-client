module pck.enoteclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires AnimateFX;
    requires javafx.media;
    requires org.apache.commons.io;

    opens pck.enote to javafx.fxml;
    exports pck.enote;

    opens pck.enote.controller to javafx.fxml;
    exports pck.enote.controller;

    opens pck.enote.fe.model to javafx.fxml;
    exports pck.enote.fe.model;
    exports pck.enote.controller.views;
    opens pck.enote.controller.views to javafx.fxml;
}