package mytry.com.oocourse.spec1.main;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MyPersonTest {
    MyPerson p1=new MyPerson(114514,"ch",new BigInteger("233"),20);
    MyPerson p2=new MyPerson(114514,"ch",new BigInteger("233"),20);
    MyPerson p3=new MyPerson(114515,"ch",new BigInteger("233"),20);
    @BeforeClass
    public static void  init(){
        System.out.println("Test Begin.");
    }

    @Test
    public void equals1() {
        Assert.assertTrue(p1.equals(p2));
        Assert.assertFalse(p1.equals(p3));
    }

    @AfterClass
    public static void end(){
        System.out.println("Test end.");
    }
}