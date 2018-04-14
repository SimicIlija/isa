package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.model.fanzone.UserProps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPropsServiceTest extends ProjekcijeApplicationTests {
    @Autowired
    UserPropsService service;

    @Test
    public void findByExistingId() {
        final UserProps result = service.findById(1);
        Assert.assertNotNull(result);
    }

    @Test(expected = RuntimeException.class)
    public void findByNotExistingId() {
        final UserProps result = service.findById(-1);
    }
}
