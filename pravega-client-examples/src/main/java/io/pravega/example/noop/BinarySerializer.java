/*
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package io.pravega.example.noop;

import io.pravega.client.stream.Serializer;
import java.nio.ByteBuffer;

public class BinarySerializer implements Serializer<ByteBuffer> {
    @Override
    public ByteBuffer serialize(ByteBuffer value) {
        return value;
    }

    @Override
    public ByteBuffer deserialize(ByteBuffer serializedValue) {
        return serializedValue;
    }
}
