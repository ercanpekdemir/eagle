package com.ercan.component;

import javafx.scene.control.Button;

public class MyButton extends Button {
    private int index;

    public MyButton(){
        super();
    }

    public MyButton(String name){
        super(name);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
