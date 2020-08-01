/*
 * Copyright (c) 2018 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package io.pravega.sharktank.flink.cozmo;

import java.io.Serializable;

/*
 * CozmoEvent event that contains the word and the summary count
 */
public class CozmoEvent implements Serializable {

    private String event;
    private String val;

    public CozmoEvent() {}

    public CozmoEvent(String event, String val) {
        this.event = event;
        this.val = val;
    }

    @Override
    public String toString() {
        return event + ": " + val;
    }
}
