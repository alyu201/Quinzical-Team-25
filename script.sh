#!/bin/bash
JAVAFX="/home/wqsz7xn/Desktop/javafx-sdk-11.0.2/lib"
java --module-path "${JAVAFX}" --add-modules javafx.controls,javafx.media,javafx.fxml -jar ./Quinzical.jar
