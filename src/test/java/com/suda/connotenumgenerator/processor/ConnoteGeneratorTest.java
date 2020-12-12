package com.suda.connotenumgenerator.processor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConnoteGeneratorTest {
    ConnoteGenerator connoteGenerator;

    @Before
    public void setUp() {
        connoteGenerator = new ConnoteGenerator();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCalculateConsignmentIndex() {
        assertEquals(connoteGenerator.calculateConsignmentIndex(19605, 10), "0000019605");
        assertEquals(connoteGenerator.calculateConsignmentIndex(20343, 8), "00020343");
    }

    @Test
    public void testCalculateConsignmentIndexForLargeValueThanDigits() {
        assertEquals(connoteGenerator.calculateConsignmentIndex(2034334, 6), "2034334");
    }

    @Test
    public void testCalculateConsignmentIndexForWrongDigit() {
        assertNull(connoteGenerator.calculateConsignmentIndex(19605, 0));
        assertNull(connoteGenerator.calculateConsignmentIndex(19605, -10));
    }

    @Test
    public void testCalculateConsignmentIndexForNegativeIndex() {
        assertNull(connoteGenerator.calculateConsignmentIndex(-19605, 10));
        assertNull(connoteGenerator.calculateConsignmentIndex(0, 10));
    }
}
