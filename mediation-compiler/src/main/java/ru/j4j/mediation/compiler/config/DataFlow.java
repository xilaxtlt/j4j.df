package ru.j4j.mediation.compiler.config;

import java.util.Map;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class DataFlow {
    private String packageName;
    private Map<String, Pipeline> pipelines;

    public String getPackageName() {
        return packageName;
    }

    @SuppressWarnings("unused")
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Map<String, Pipeline> getPipelines() {
        return pipelines;
    }

    @SuppressWarnings("unused")
    public void setPipelines(Map<String, Pipeline> pipelines) {
        this.pipelines = pipelines;
    }
}
