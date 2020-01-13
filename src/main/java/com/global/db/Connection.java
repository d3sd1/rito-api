/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.db;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Connection {

    @EventListener(ApplicationReadyEvent.class)
    public void dbClon() {
        /*
        System.out.println("DB TEST -> ");
        com.rethinkdb.net.Connection conn = r.connection().hostname("localhost").port(28015).connect();

        //r.db("test").tableCreate("tv_shows").run(conn);
        r.table("tv_shows").insert(r.hashMap("name", "Star Trek TNG")).run(conn);
        r.table("tv_shows").getAll();
        for(int i = 0; i < 1000; i++) {

            r.table("tv_shows").insert(r.hashMap("name", "Star Trek TNG")).run(conn);
        }*/
    }
}
