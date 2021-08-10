package com.framework.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class UUIDUtilsTest {

    Logger log = LoggerFactory.getLogger(UUIDUtilsTest.class);

    @Test
    void newIdTest(){
        log.info(UUIDUtils.newId());
    }

}