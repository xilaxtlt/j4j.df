package ru.j4j.mediation.compiler.config;

import java.util.List;
import java.util.Map;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class Pipeline {
    private Map<String, String> inputValues;
    private OutputValue outputValue;
    private List<String> units;

    public Map<String, String> getInputValues() {
        return inputValues;
    }

    @SuppressWarnings("unused")
    public void setInputValues(Map<String, String> inputValues) {
        this.inputValues = inputValues;
    }

    public OutputValue getOutputValue() {
        return outputValue;
    }

    @SuppressWarnings("unused")
    public void setOutputValue(OutputValue outputValue) {
        this.outputValue = outputValue;
    }

    public List<String> getUnits() {
        return units;
    }

    @SuppressWarnings("unused")
    public void setUnits(List<String> units) {
        this.units = units;
    }
}
