package com.continental.accelerometerexperimentation;

import com.continental.accelerometerexperimentation.formatter.TextFormatter;

import org.junit.Test;
import static org.junit.Assert.* ;

public class TextFormatterTest {


    private final String[] strings = {"among", "other", "things"};

    @Test
    public void testShouldGenerateAProperFileNameWithDateAndExtensionGivenArguments(){
        String separator = "-";
        String extension = ".txt";
        assertEquals("among-other-things-21062019.txt",TextFormatter.formatWith(strings, separator, extension));
    }
}
