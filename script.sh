#!/bin/bash
# old image javafx
#JAVAFX="/usr/share/openjfx/lib"
# new javafx for new image
JAVAFX="/usr/share/java/lib"
java --module-path "${JAVAFX}" --add-modules javafx.controls,javafx.media,javafx.fxml -jar ./Quinzical.jar
