package com.nvidia.triton.contrib.pojo;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class InferenceResponseTest {

    @Test
    public void getOutputByName() {
        InferenceResponse resp = new InferenceResponse();
        resp.setOutputs(new ArrayList<>());
        for (String name : Arrays.asList("a", "b")) {
            IOTensor tensor = new IOTensor();
            tensor.setName(name);
            resp.getOutputs().add(tensor);
        }

        IOTensor tensorB = resp.getOutputByName("b");
        assertEquals(tensorB, resp.getOutputs().get(1));
        assertNull(resp.getOutputByName("c"));
    }
}