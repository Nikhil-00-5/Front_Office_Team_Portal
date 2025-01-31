package in.MiniProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.MiniProject.binding.DashboardResponse;
import in.MiniProject.binding.EnquiryForm;
import in.MiniProject.binding.EnquirySearchCriteria;
import in.MiniProject.entities.StudentEnqEntity;
import in.MiniProject.services.EnquiryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService service;
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		
		Integer userId=(Integer) session.getAttribute("userId");
		DashboardResponse dashboardData =service.getDashboardData(userId);
		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";	
	}
	
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		init(model);
		return "add-enquiry";	
	}

	private void init(Model model) {
		List<String> courseNames=service.courseNmaes();
		List<String> statusNames=service.statusNmaes();
		model.addAttribute("courseNames",courseNames);
		model.addAttribute("statusNames",statusNames);
		EnquiryForm form =new EnquiryForm();
		model.addAttribute("enqForm",form);
	}
	
	@PostMapping("/enquiry")
	public String handleEnquiryPage( EnquiryForm form, Model model) {
		init(model);
		boolean status=service.saveEnquriry(form);
		if(status) {
			model.addAttribute("succMsg", "Enquiry Added");
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		return "add-enquiry";	
	}
	
	
	
	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		init(model);
		List<StudentEnqEntity> enquiries = service.getEnquiries();
		model.addAttribute("enquiries",enquiries);
		return "view-enquiries";	
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname,
			@RequestParam String status,
			@RequestParam String mode,
			Model model) {
		
		EnquirySearchCriteria criteria=new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);

		Integer userId=(Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filteredEnqs=service.getFilterdeEnqs(criteria, userId);
		
		model.addAttribute("enquiries",filteredEnqs);
		return "filter-enquiries-page";	
	}
	
	@GetMapping("/edit")
	public String editEnquiry(@ModelAttribute("enqForm") EnquiryForm form,Model model) {
		model.addAttribute("enqForm", form);
		return "add-enquiry";
	}

	
	
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";	
	}
}
