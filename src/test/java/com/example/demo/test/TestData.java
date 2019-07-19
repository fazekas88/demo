package com.example.demo.test;

import com.example.demo.dto.DataDto;
import com.example.demo.model.BoData;
import com.example.demo.repo.DataRepo;
import com.example.demo.service.DataService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestData {

    private String exmpl1;
    private String exmpl2;
    private String exmpl3;
    private String exmpl4;
    private String exmpl5;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    public DataRepo repo;

    @Autowired
    public DataService dataService;

    @Before
    public void setup() throws Exception {
        this.repo.deleteAll();

        exmpl1 = "_exampleData_1";
        exmpl2 = "?exampleData_2";
        exmpl3 = "exampleData_3>";
        exmpl4 = "exampleData_322>";
        exmpl5 = "1exampleData_2";

    }

    @Test
    public void testSaveLeft() throws Exception {

        dataService.saveData(1L, new DataDto(exmpl1), "left");
        BoData binaryData = repo.findById(1L).get();

        Assert.assertThat(binaryData.getId(), Matchers.is(1L));
        Assert.assertThat(binaryData.getLeft(), Matchers.is(exmpl1));
        Assert.assertThat(binaryData.getRight(), Matchers.isEmptyOrNullString());
    }

    @Test
    public void testSaveRight() {

        dataService.saveData(2L, new DataDto(exmpl2), "right");
        BoData binaryData = repo.findById(2L).get();

        Assert.assertThat(binaryData.getId(), Matchers.is(2L));
        Assert.assertThat(binaryData.getLeft(), Matchers.isEmptyOrNullString());
        Assert.assertThat(binaryData.getRight(), Matchers.is(exmpl2));
    }

    @Test
    public void getMissingDataRightTest() {

        repo.save(new BoData(3L, exmpl1, null));
        String msg = dataService.getDifferences(3L);
        Assert.assertThat(msg, Matchers.is("ERROR: Right data with ID: " + 3L + " don't exist"));
    }

    @Test
    public void getMissingDataLeftTest() {

        repo.save(new BoData(4L, null, exmpl1));
        String msg = dataService.getDifferences(4L);
        Assert.assertThat(msg, Matchers.is("ERROR: Left data with ID: " + 4L + " don't exist"));
    }

    @Test
    public void getEqualDataTest() throws Exception {

        repo.save(new BoData(5L, exmpl2, exmpl2));
        String msg = dataService.getDifferences(5L);
        Assert.assertThat(msg, Matchers.is("Data equal"));
    }

    @Test
    public void getNotEqualDataTest() throws Exception {

        repo.save(new BoData(6L, exmpl1, exmpl4));
        String msg = dataService.getDifferences(6L);
        Assert.assertThat(msg, Matchers.is("Data not equal"));
    }

    @Test
    public void getEqualDataDiffPositionsTest() throws Exception {

        repo.save(new BoData(7L, exmpl1, exmpl5));
        String msg = dataService.getDifferences(7L);
        Assert.assertThat(msg, Matchers.is("Equal data size but diff are in possitions: 0,13,"));
    }
}
