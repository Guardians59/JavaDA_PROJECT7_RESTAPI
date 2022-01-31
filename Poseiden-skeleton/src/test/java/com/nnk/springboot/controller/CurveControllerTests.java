package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurveControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ICurvePointService curvePointService;

    @BeforeEach
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste curve")
    public void getCurveListPageTest() throws Exception {
	// GIVEN
	List<CurvePoint> list = new ArrayList<>();
	CurvePoint curve = new CurvePoint();
	curve.setId(25);
	curve.setCurveId(11);
	curve.setTerm(20.0);
	curve.setValue(15.0);
	list.add(curve);
	// WHEN
	when(curvePointService.getAllCurvePoint()).thenReturn(list);
	mockMvc.perform(get("/curvePoint/list"))
		// THEN
		.andExpect(view().name("curvePoint/list")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout curve")
    public void getCurveAddPageTest() throws Exception {
	// WHEN
	mockMvc.perform(get("/curvePoint/add"))
		// THEN
		.andExpect(view().name("curvePoint/add")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'ajout d'un curve")
    public void addCurveTest() throws Exception {
	// GIVEN
	CurvePoint curve = new CurvePoint();
	curve.setCurveId(10);
	curve.setTerm(22.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.addCurvePoint(curve)).thenReturn(true);
	mockMvc.perform(post("/curvePoint/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("curvePoint", curve))
		// THEN
		.andExpect(view().name("curvePoint/list")).andExpect(model().attributeExists("success"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de l'ajout d'un curve")
    public void addErrorCurveTest() throws Exception {
	// GIVEN
	CurvePoint curve = new CurvePoint();
	curve.setCurveId(10);
	curve.setTerm(22.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.addCurvePoint(curve)).thenReturn(false);
	mockMvc.perform(post("/curvePoint/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("curvePoint", curve))
		// THEN
		.andExpect(view().name("curvePoint/add")).andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de l'ajout d'un curve")
    public void addBindingErrorCurveTest() throws Exception {
	// GIVEN
	CurvePoint curve = new CurvePoint();
	curve.setCurveId(0);
	curve.setTerm(22.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.addCurvePoint(curve)).thenReturn(false);
	mockMvc.perform(post("/curvePoint/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("curvePoint", curve))
		// THEN
		.andExpect(view().name("curvePoint/add")).andExpect(model().attributeDoesNotExist("success"))
		.andExpect(model().attributeDoesNotExist("error")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour curve")
    public void getCurveEditPageTest() throws Exception {
	// GIVEN
	CurvePoint curve = new CurvePoint();
	curve.setId(30);
	curve.setCurveId(10);
	curve.setTerm(22.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.getCurvePointById(30)).thenReturn(curve);
	mockMvc.perform(get("/curvePoint/edit/30"))
		// THEN
		.andExpect(view().name("curvePoint/update")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la mis à jour curve")
    public void updateCurvePageTest() throws Exception {
	// GIVEN
	CurvePoint curve = new CurvePoint();
	curve.setCurveId(10);
	curve.setTerm(22.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.updateCurvePoint(30, curve)).thenReturn(true);
	mockMvc.perform(post("/curvePoint/update/30").with(csrf()).flashAttr("curvePoint", curve))
		// THEN
		.andExpect(view().name("curvePoint/list")).andExpect(model().attributeExists("updateSuccess"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de la mis à jour curve")
    public void updateErrorCurvePageTest() throws Exception {
	// GIVEN
	CurvePoint oldCurve = new CurvePoint();
	oldCurve.setCurveId(10);
	oldCurve.setTerm(22.0);
	oldCurve.setValue(20.0);
	CurvePoint curve = new CurvePoint();
	curve.setCurveId(10);
	curve.setTerm(24.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.updateCurvePoint(30, curve)).thenReturn(false);
	when(curvePointService.getCurvePointById(30)).thenReturn(curve);
	mockMvc.perform(post("/curvePoint/update/30").with(csrf()).flashAttr("curvePoint", curve))
		// THEN
		.andExpect(view().name("curvePoint/update")).andExpect(model().attributeExists("updateError"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de la mis à jour curve")
    public void updateBindingErrorCurvePageTest() throws Exception {
	// GIVEN
	CurvePoint oldCurve = new CurvePoint();
	oldCurve.setCurveId(10);
	oldCurve.setTerm(22.0);
	oldCurve.setValue(20.0);
	CurvePoint curve = new CurvePoint();
	curve.setCurveId(0);
	curve.setTerm(23.0);
	curve.setValue(20.0);
	// WHEN
	when(curvePointService.updateCurvePoint(30, curve)).thenReturn(false);
	when(curvePointService.getCurvePointById(30)).thenReturn(curve);
	mockMvc.perform(post("/curvePoint/update/30").with(csrf()).flashAttr("curvePoint", curve))
		// THEN
		.andExpect(view().name("curvePoint/update")).andExpect(model().attributeDoesNotExist("updateError"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de suppression curve")
    public void getCurveDeletePageTest() throws Exception {
	// WHEN
	when(curvePointService.deleteCurvePoint(50)).thenReturn(true);
	mockMvc.perform(get("/curvePoint/delete/50"))
		// THEN
		.andExpect(view().name("curvePoint/list")).andExpect(model().attributeExists("deleteSuccess"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue avec l'erreur de suppression curve")
    public void getErrorCurveDeletePageTest() throws Exception {
	// WHEN
	when(curvePointService.deleteCurvePoint(51)).thenReturn(false);
	mockMvc.perform(get("/curvePoint/delete/51"))
		// THEN
		.andExpect(view().name("curvePoint/list")).andExpect(model().attributeExists("deleteError"))
		.andExpect(status().isOk());
    }

}
