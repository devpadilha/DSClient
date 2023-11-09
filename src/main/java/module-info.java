module augustopadilha.clientdistributedsystems {
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.codec;
    requires org.xerial.sqlitejdbc;
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires jjwt.api;
    requires java.sql;

    opens augustopadilha.clientdistributedsystems to javafx.fxml;
    opens augustopadilha.clientdistributedsystems.controllers to javafx.fxml;
    exports augustopadilha.clientdistributedsystems;
    exports augustopadilha.clientdistributedsystems.controllers;
    exports augustopadilha.clientdistributedsystems.controllers.admin;
    exports augustopadilha.clientdistributedsystems.controllers.comum;
    exports augustopadilha.clientdistributedsystems.models;
    exports augustopadilha.clientdistributedsystems.views;
}