#!/bin/bash
JAVAFX="/home/se2062020/javafx-sdk-11.0.2/lib"
java --module-path "${JAVAFX}" --add-modules javafx.controls,javafx.media,javafx.fxml -jar ./Jeopardy.jar
