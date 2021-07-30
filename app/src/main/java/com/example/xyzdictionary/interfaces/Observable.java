package com.example.xyzdictionary.interfaces;

public interface Observable {
    void subscribe(Observer observer);
    void notifyObservers();
}
