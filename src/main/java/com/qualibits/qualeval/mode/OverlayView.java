package com.qualibits.qualeval.mode;

import javafx.scene.Node;

public interface OverlayView<T extends Node> {

    T show();
    void close();
    int getRow();
    int getCol();
    int getRowSpan();
    int getColSpan();
}
