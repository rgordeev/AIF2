package io.aif.language.semantic.weights.edge;

import io.aif.language.semantic.ISemanticNode;


public interface IEdgeWeightCalculator<T> {

    // TODO: rename to calculate
    public double calculateWeight(final T from, final T to);
    
}
