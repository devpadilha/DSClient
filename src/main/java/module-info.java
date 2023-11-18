module augustopadilha.clientdistributedsystems {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.codec;
    requires org.xerial.sqlitejdbc;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires jjwt.api;
    requires java.sql;

    opens augustopadilha.clientdistributedsystems to javafx.fxml;
    opens augustopadilha.clientdistributedsystems.controllers to javafx.fxml;
    opens augustopadilha.clientdistributedsystems.controllers.admin to javafx.fxml;
    opens augustopadilha.clientdistributedsystems.controllers.user to javafx.fxml;
    opens augustopadilha.clientdistributedsystems.system.connection to javafx.fxml;
    opens augustopadilha.clientdistributedsystems.models to com.fasterxml.jackson.databind;

    exports augustopadilha.clientdistributedsystems;
    exports augustopadilha.clientdistributedsystems.controllers;
    exports augustopadilha.clientdistributedsystems.controllers.admin;
    exports augustopadilha.clientdistributedsystems.controllers.user;
    exports augustopadilha.clientdistributedsystems.models;
    exports augustopadilha.clientdistributedsystems.views;
    exports augustopadilha.clientdistributedsystems.system;
    exports augustopadilha.clientdistributedsystems.system.connection;
    exports augustopadilha.clientdistributedsystems.system.utilities;
}