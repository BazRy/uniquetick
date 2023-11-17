package com.hazelcast.uniquetick.web;

import com.hazelcast.uniquetick.process.TickGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic rest endpoint to obtain a unique tick value
 */
@RestController
public class UniqueTickController {

    private final TickGenerator tickGenerator;

    @Autowired
    public UniqueTickController(TickGenerator tickGenerator) {
        this.tickGenerator = tickGenerator;
    }

    @GetMapping("/uniqueTick")
    public long getUniqueTick() {
        return tickGenerator.getUniqueTick();
    }
}
