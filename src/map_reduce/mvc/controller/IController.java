package src.map_reduce.mvc.controller;

import java.util.Scanner;

public interface IController<K, V> {
    void processInput();

    void setup(final Scanner scanner);
}