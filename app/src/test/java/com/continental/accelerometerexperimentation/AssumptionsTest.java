package com.continental.accelerometerexperimentation;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssumptionsTest {

    @Test
    public void testIfClearMethodOfListActuallyRemoveAllElements(){
        List<Integer> numbers = new ArrayList<>() ;
        numbers.addAll(Arrays.asList(1,2,3,4,5,8)) ;
        numbers.clear();
        Assert.assertEquals(0,numbers.size());
    }
}
