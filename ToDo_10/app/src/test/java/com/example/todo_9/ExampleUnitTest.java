package com.example.todo_9;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {
    private calculator mCalculator;
    @Before
    public void setUp(){
        mCalculator = new calculator();
    }
    @Test
    public void addTwoNumber(){
        double result = mCalculator.add(1d,2d);
        assertThat(result, is(equalTo(3d)));
    }
    @Test
    public void addTwoNumberForNegatives(){
        double result = mCalculator.add(-1d,2d);
        assertThat(result, is(equalTo( 1d)));
    }
    @Test
    public void addTwoNumbersFloats() {
        double resultAdd = mCalculator.add(1.111f, 1.111d);
        assertThat(resultAdd, is(closeTo(2.222, 0.01)));
    }
    @Test
    public void subTwoNumbers(){
        double resultSub = mCalculator.sub(1d, 1d);
        assertThat(resultSub, is(equalTo(0d)));
    }
    @Test
    public void subWorksWithNegativeResult() {
        double resultSub = mCalculator.sub(-1d, 11d);
        assertThat(resultSub, is(equalTo(-12d)));
    }
    @Test
    public void mulTwoNumbers() {
        double resultMul = mCalculator.mul(4d, 2d);
        assertThat(resultMul, is(equalTo(8d)));
    }
    @Test
    public void mulTwoNumbersZero() {
        double resultMul = mCalculator.mul(4d, 0d);
        assertThat(resultMul, is(equalTo(0d)));
    }
    @Test
    public void divTwoNumbers() {
        double resultDiv = mCalculator.div(16d,4d);
        assertThat(resultDiv, is(equalTo(4d)));
    }
    @Test
    public void divTwoNumbersZero() {
        double resultDiv = mCalculator.div(25d,0);
        assertThat(resultDiv, is(equalTo(Double.POSITIVE_INFINITY)));
    }
}