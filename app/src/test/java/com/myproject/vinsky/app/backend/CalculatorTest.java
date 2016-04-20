package com.myproject.vinsky.app.backend;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by keisuke on 2016/03/19.
 */
public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @Test
    public void testSum() throws Exception {
        assertThat(mCalculator.sum(1, 5), is(6));
    }

    @Test
    public void testSubstract() throws Exception {
        assertThat(mCalculator.substract(5, 3), is(2));
    }
}