package com.nicolas.qa;

import com.nicolas.qa.Calculator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    Calculator calculator = new Calculator();

    @Test
    void testAdd(){
        assertEquals(5.0, calculator.add(2.0, 3.0), 0.001);
    }
    
    @Test
    void testDivide(){
        assertEquals(2.0, calculator.divide(6.0, 3.0), 0.001);
    }

    @Test
    void testSubtract(){
        assertEquals(10, calculator.subtract(12.0 , 2.0), 0.001);
    }

    @Test
    void testMultiply(){
        assertEquals(32,calculator.multiply(8.0, 4.0),0.001);
    }

    @Test
    void testSquareRoot(){
        assertEquals(5,calculator.squareRoot(25.0),0.001);
    }

    @Test
    void testPower(){
        assertEquals(25,calculator.power(5.0,2.0),0.001);
    }

    @Test
    void testModule(){
        assertEquals(1, calculator.module(87, 2.0),0.001);
    }

    void testDivideByZero(){
        assertThrows(ArithmeticException.class, () -> calculator.divide(10.0,0));
    }
}
