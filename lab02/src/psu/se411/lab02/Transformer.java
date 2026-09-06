package psu.se411.lab02;

public interface Transformer<T, R> {
    R transform(T input);
}