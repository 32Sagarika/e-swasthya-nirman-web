package nirmalya.aathithya.webmodule.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.admin.model.AssignCouponModel;
import nirmalya.aathithya.webmodule.admin.model.PatientListingModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller

@RequestMapping(value = { "admin/" })
public class AssignCouponController {
	Logger logger = LoggerFactory.getLogger(AssignCouponController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "admin-assigncoupon" })

	public String adminbnciti(Model model, HttpSession session) {
		logger.info("Method :city starts");

		logger.info("Method : city ends");
		return "admin/assignCoupon";
	}
	
	//view patient list
	@SuppressWarnings("unchecked")
	@GetMapping("admin-assigncoupon-viewDetails")
	public @ResponseBody List<AssignCouponModel> viewPatientDetails(HttpSession session) {

		logger.info("Method :viewPatientDetails starts");

		JsonResponse<List<AssignCouponModel>> resp = new JsonResponse<List<AssignCouponModel>>();
		String userId = "";
	try {
			userId = (String) session.getAttribute("USER_ID");
			resp = restTemplate.getForObject(env.getAdminUrl() + "viewAdminAssignCouponDetails", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<AssignCouponModel>  AssignCouponModel= mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignCouponModel>>() {
				});
		
		resp.setBody(AssignCouponModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
			//System.out.println("VIEW"+resp.getBody());
		logger.info("Method :viewPatientDetails ends");
		return resp.getBody();
	}
}
