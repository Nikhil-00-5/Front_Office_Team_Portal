package in.MiniProject.services;

import java.util.List;

import in.MiniProject.binding.DashboardResponse;
import in.MiniProject.binding.EnquiryForm;
import in.MiniProject.binding.EnquirySearchCriteria;
import in.MiniProject.entities.StudentEnqEntity;

public interface EnquiryService {
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public List<String> courseNmaes();
	public List<String> statusNmaes();

	boolean saveEnquriry(EnquiryForm form);
	
	public List<StudentEnqEntity> getEnquiries();
	
	public List<StudentEnqEntity>  getFilterdeEnqs(EnquirySearchCriteria criteria,Integer userId);
	
     
}
