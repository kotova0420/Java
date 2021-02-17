package com.kotova;

public class Main {

    public static void main(String[] args) {
        ObservableStringBuilder observableStringBuilder = new ObservableStringBuilder("Like, ");
        observableStringBuilder.setOnChangeListener(builder ->
                System.out.println("Новое состояние: " + builder.toString()));

        observableStringBuilder.append("sweet");
    }
}
