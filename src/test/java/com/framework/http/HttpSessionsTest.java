package com.framework.http;

import com.framework.utils.UUIDUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpSessionsTest {

    HttpSession session;
    String id;

    @BeforeEach
    void setUp(){
        id = UUIDUtils.newId();
        session = HttpSessions.getSession(id);
    }

    @Test
    void getSessionTest(){
        Assertions.assertEquals(id, session.getId());
    }

    @Test
    void invalidateTest(){
        session.invalidate();
        Assertions.assertFalse(HttpSessions.exists(id));
    }

}