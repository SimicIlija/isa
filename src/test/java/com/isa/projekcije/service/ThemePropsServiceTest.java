package com.isa.projekcije.service;


import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.model.fanzone.ThemeProps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for {@link ThemePropsService} class.
 */

public class ThemePropsServiceTest extends ProjekcijeApplicationTests {
    @Autowired
    ThemePropsService themePropsService;

    @Test
    public void findByExistingId() {
        final ThemeProps result = themePropsService.findById(1);
        Assert.assertNotNull(result);
    }

    @Test(expected = RuntimeException.class)
    public void findNotExistingId(){
        final ThemeProps result = themePropsService.findById(-1);
    }

    @Test
    public void addCorrectThemeProps(){
        ThemeProps themeProps = new ThemeProps();
        themeProps.setName("name");
        themeProps.setDescription("description");
        ThemeProps result = themePropsService.create(themeProps);
        Assert.assertNotNull(result);
    }

    @Test(expected = Exception.class)
    public void addWithoutName(){
        ThemeProps themeProps = new ThemeProps();
        themeProps.setDescription("description");
        themePropsService.create(themeProps);
    }

    @Test(expected = Exception.class)
    public void addWithoutDescription(){
        ThemeProps themeProps = new ThemeProps();
        themeProps.setName("name");
        themePropsService.create(themeProps);
    }



}
