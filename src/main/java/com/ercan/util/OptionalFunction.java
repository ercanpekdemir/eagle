package com.ercan.util;

import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public interface OptionalFunction {
    void run(Optional<ButtonType> result) throws IOException;
}
