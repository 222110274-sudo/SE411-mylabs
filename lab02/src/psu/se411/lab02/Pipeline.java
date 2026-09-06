package psu.se411.lab02;

public class Pipeline<T> {

    private T value;

    public Pipeline(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public <R> Pipeline<R> apply(Transformer<T, R> transformer) {
        R newValue = transformer.transform(this.value);
        return new Pipeline<>(newValue);
    }

    public static void main(String[] args) {
        Pipeline<String> pipeline = new Pipeline<>("42");

        Pipeline<Integer> intPipeline = pipeline.apply(s -> Integer.parseInt(s));
        Pipeline<Integer> doubledPipeline = intPipeline.apply(n -> n * 2);
        Pipeline<String> finalPipeline = doubledPipeline.apply(n -> "Result: " + n);

        System.out.println(finalPipeline.getValue());
    }
}