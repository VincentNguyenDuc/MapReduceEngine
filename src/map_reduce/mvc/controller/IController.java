package src.map_reduce.mvc.controller;

public interface IController<K, V> {
    void processInput();
}