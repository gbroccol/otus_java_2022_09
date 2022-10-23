package homework.test;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import static homework.utils.Utils.*;

public class AnnotationsTest {

    @Before
    public void before(){
        System.out.println(ANSI_BLUE + "before" + ANSI_RESET);
    }

    @Before
    public void before2(){
        System.out.println(ANSI_BLUE + "before #2" + ANSI_RESET);
    }

    @Test
    public void test(){
        System.out.println(ANSI_YELLOW + "-> test" + ANSI_RESET);
    }

    @Test
    public void test2(){
        System.out.println(ANSI_YELLOW + "-> test2" + ANSI_RESET);
        throw new NullPointerException();
    }

    @Test
    public void test3(){
        System.out.println(ANSI_YELLOW + "-> test3" + ANSI_RESET);
    }

    @After
    public void after(){
        System.out.println(ANSI_GREEN + "after" + ANSI_RESET);
    }

}
