package me.dchuiko.spa;

import me.dchuiko.spa.persistence.DaoFactory;
import org.junit.After;

public abstract class BaseTest {

    @After
    public void after() {
        DaoFactory.clear();
    }
}
