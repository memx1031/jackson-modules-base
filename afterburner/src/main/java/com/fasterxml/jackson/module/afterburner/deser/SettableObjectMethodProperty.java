package com.fasterxml.jackson.module.afterburner.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public final class SettableObjectMethodProperty
    extends OptimizedSettableBeanProperty<SettableObjectMethodProperty>
{
    private static final long serialVersionUID = 1L;

    public SettableObjectMethodProperty(SettableBeanProperty src,
            BeanPropertyMutator mutator, int index)
    {
        super(src, mutator, index);
    }

    @Override
    protected SettableBeanProperty withDelegate(SettableBeanProperty del) {
        return new SettableObjectMethodProperty(del, _propertyMutator, _optimizedIndex);
    }

    @Override
    public SettableBeanProperty withMutator(BeanPropertyMutator mut) {
        return new SettableObjectMethodProperty(delegate, mut, _optimizedIndex);
    }

    /*
    /********************************************************************** 
    /* Deserialization
    /********************************************************************** 
     */

    @Override
    public void deserializeAndSet(JsonParser p, DeserializationContext ctxt,
            Object bean) throws IOException
    {
        // inlined `set()`
        final Object v= deserialize(p, ctxt);
        try {
            _propertyMutator.objectSetter(bean, _optimizedIndex, v);
        } catch (Throwable e) {
            _reportProblem(bean, v, e);
        }
    }

    @Override
    public void set(Object bean, Object v) throws IOException {
        try {
            _propertyMutator.objectSetter(bean, _optimizedIndex, v);
        } catch (Throwable e) {
            _reportProblem(bean, v, e);
        }
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser p,
            DeserializationContext ctxt, Object instance) throws IOException {
        return setAndReturn(instance, deserialize(p, ctxt));
    }    
}
