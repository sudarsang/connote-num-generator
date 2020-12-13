package com.suda.connotenumgenerator.processor;

import com.suda.connotenumgenerator.domain.CarrierAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.suda.connotenumgenerator.processor.ConnoteGenerator.NEGATIVE_START_POS;
import static com.suda.connotenumgenerator.processor.ConnoteGenerator.NULL_CONS_INDEX;
import static org.junit.jupiter.api.Assertions.*;

public class ConnoteGeneratorTest {
    ConnoteGenerator connoteGenerator;

    @BeforeEach
    public void setUp() {
        connoteGenerator = new ConnoteGenerator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCalculateConsignmentIndex() {
        assertEquals(connoteGenerator.calculateConsignmentIndex(19605, 10), "0000019605");
        assertEquals(connoteGenerator.calculateConsignmentIndex(20343, 8), "00020343");
    }

    @Test
    public void testCalculateConsignmentIndexForLargeValueThanDigits() {
        assertEquals("2034334", connoteGenerator.calculateConsignmentIndex(2034334, 6));
    }

    @Test
    public void testCalculateConsignmentIndexForValidation() {
        assertNull(connoteGenerator.calculateConsignmentIndex(19605, 0));
        assertNull(connoteGenerator.calculateConsignmentIndex(19605, -10));
        assertNull(connoteGenerator.calculateConsignmentIndex(Integer.MIN_VALUE, 10));
        assertNull(connoteGenerator.calculateConsignmentIndex(-19605, 10));
    }

    @Test
    public void testCalculateConsignmentIndexForNegativeIndex() {
        assertNull(connoteGenerator.calculateConsignmentIndex(-19605, 10));
        assertNull(connoteGenerator.calculateConsignmentIndex(0, 10));
    }

    @Test
    public void testCalculateSumForNullNegativeWrongInput() {
        var exception = assertThrows(AssertionError.class, () -> connoteGenerator.calculateSum(null,0));
        assertEquals(NULL_CONS_INDEX, exception.getMessage());

        exception = assertThrows(AssertionError.class, () -> connoteGenerator.calculateSum("0000019345", -2));
        assertEquals(NEGATIVE_START_POS, exception.getMessage());

        exception = assertThrows(AssertionError.class, () -> connoteGenerator.calculateSum("0000019345", Integer.MIN_VALUE));
        assertEquals(NEGATIVE_START_POS, exception.getMessage());

        assertEquals(0, connoteGenerator.calculateSum("45", 0));
        assertEquals(0, connoteGenerator.calculateSum("0000019345", 10));
        assertEquals(0, connoteGenerator.calculateSum("0000019345", 12));
        assertEquals(0, connoteGenerator.calculateSum("0000019345", Integer.MAX_VALUE));
    }

    @Test
    public void testCalculateSum() {
        assertEquals(12, connoteGenerator.calculateSum("0000019605", 0));
        assertEquals(9, connoteGenerator.calculateSum("0000019605", 1));

        assertEquals(16, connoteGenerator.calculateSum("00019708", 0));
        assertEquals(9, connoteGenerator.calculateSum("00019708", 1));

        assertEquals(11, connoteGenerator.calculateSum("0019654", 0));
        assertEquals(14, connoteGenerator.calculateSum("0019654", 1));
    }

    @Test
    public void testGenerateForValidation() {
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 18605, 19000, 20000)));
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 20605, 19000, 20000)));
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, -19605, 19000, 20000)));
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 19605, -19000, 20000)));
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 19605, 19000, -20000)));
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 19605, 19000, 0)));
        assertNull(connoteGenerator.generate(new CarrierAccount(null, "123ABC", 10, 19605, 19000, 0)));
        assertNull(connoteGenerator.generate(new CarrierAccount("FMCC", null, 10, 19605, 19000, 0)));
    }

    @Test
    public void testGenerateForSuccessfulOperationForDiffDigits() {
        assertEquals("FMCC123ABC00000196051", connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 19604, 19000, 20000)));
        assertEquals("ABCDXY12345000196051", connoteGenerator.generate(new CarrierAccount("ABCD", "XY12345", 8, 19604, 19000, 20000)));
        assertEquals("FMCC123ABC0000000196051", connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 12, 19604, 19000, 20000)));
    }

    @Test
    public void testGenerateForDifferentChecksum() {
        assertEquals("FMCC123ABC00000195559", connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 19554, 19000, 20000)));
        assertEquals("FMCC123ABC00000324562", connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 32455, 30000, 40000)));
        assertEquals("FMCC123ABC00000354530", connoteGenerator.generate(new CarrierAccount("FMCC", "123ABC", 10, 35452, 30000, 40000)));

    }
}
