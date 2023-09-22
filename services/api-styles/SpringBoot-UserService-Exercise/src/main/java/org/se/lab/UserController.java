package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserController
{
    private static long sequence = 1;
    private static long nextValue()
    {
        return sequence++;
    }

    private Map<Long, User> table;

    UserController()
    {
        // Simulate database table
        table = new ConcurrentHashMap<>();
        long id1 = nextValue();
        table.put(id1, new User(id1, "homer", "2aaab795b3836904f82efc6ca2285d927aed75206214e1da383418eb90c9052f"));
        long id2 = nextValue();
        table.put(id2, new User(id2, "marge", "b4b811fa40505329ae871e52f03527c3720c9af7fb8607819658535c5484c41e"));
        long id3 = nextValue();
        table.put(id3, new User(id3,  "bart", "9551dadbf76a27457946e70d1aebebe2132f8d3bce6378d216c11853524dd3a6"));
        long id4 = nextValue();
        table.put(id4, new User(id4,  "lisa", "d84fe7e07bedb227cffff10009151d96fc944f6a1bd37cff60e8e4626a1eb1c3"));
    }
    // ...
}
