/**
 * Copyright (c) 2012-2012 Malhar, Inc.
 * All rights reserved.
 */
package com.malhartech.api;

import com.malhartech.api.Operator.InputPort;

/**
 * Default abstract implementation for input ports.
 * An operator would typically define a derived inner class with the process method.
 * This class is designed for use with a transient field, i.e. not to be serialized with the operator state.
 */
public abstract class DefaultInputPort<T> implements InputPort<T>, Sink<T>
{
  final private Operator operator;
  protected int count;
  protected boolean connected = false;

  public DefaultInputPort(Operator operator)
  {
    this.operator = operator;
  }

  @Override
  final public Operator getOperator()
  {
    return operator;
  }

  @Override
  public Sink<T> getSink()
  {
    return this;
  }

  @Override
  public void setConnected(boolean connected)
  {
    this.connected = connected;
  }

  @Override
  public Class<? extends StreamCodec<T>> getStreamCodec()
  {
    return null;
  }

  @Override
  public void put(T tuple)
  {
    count++;
    process(tuple);
  }

  @Override
  public int getCount(boolean reset)
  {
    try {
      return count;
    }
    finally {
      if (reset) {
        count = 0;
      }
    }
  }

  public abstract void process(T tuple);

}
