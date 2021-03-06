/*
 * Copyright 2011 by Alexei Kaigorodov
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.rfqu.df4j.core;

/**
 * 
 * A kind of dataflow variable: single input, multiple asynchronous outputs.
 * Distributes received value/failure among listeners.
 * Value (or failure) can only be assigned once. It is then saved, and 
 * listeners connected after the assignment still would receive it.
 * 
 * Also acts as a Furure and connects Actors and Threads.
 * Actors are allowed to send messages to it, but not to get from. 
 * Threads are allowed both to send and get.
 *
 * @param <T>  type of result
 */
public class CompletableFuture<T>
    extends CompletableFutureBase<T, Callback<T>>
    implements ListenableFuture<T>
{

    @Override
    protected void passResult(Callback<T> listenerLoc) {
        listenerLoc.post(value);
    }

    @Override
    protected void passFailure(Callback<T> listenerLoc) {
        listenerLoc.postFailure(exc);
    }

    @Override
    public Promise<T> addListener(Callback<T> sink) {
        _addListener(sink);
        return this;
    }
}
