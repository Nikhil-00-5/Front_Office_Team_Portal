package in.MiniProject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.MiniProject.binding.DashboardResponse;
import in.MiniProject.binding.EnquiryForm;
import in.MiniProject.binding.EnquirySearchCriteria;
import in.MiniProject.entities.CourseEntity;
import in.MiniProject.entities.EnqStatusEntity;
import in.MiniProject.entities.StudentEnqEntity;
import in.MiniProject.entities.UserDtlsEntity;
import in.MiniProject.repositories.CourseRepo;
import in.MiniProject.repositories.EnqStatusRepo;
import in.MiniProject.repositories.StudentEnqRepo;
import in.MiniProject.repositories.UserDtlsRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService  {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		// TODO create DashboardResponse Obj
		DashboardResponse response =new DashboardResponse();
		
		//TODO Fetch record by UserId
	  Optional<UserDtlsEntity>findById=userDtlsRepo.findById(userId);
		
		// TODO fetch enquires by UserId
      if(findById.isPresent()) {
    	  UserDtlsEntity userEntity=findById.get();
    	  
    	 List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
     
		
		// TODO Count all enquires
		Integer totalCnt = enquiries.size();
		
		// TODO Count enrolled enquires 
		Integer enrolledCnt=enquiries.stream()
				            .filter(e -> e.getEnqStatus().equals("Enrolled"))
				            .collect(Collectors.toList()).size();
		
		
		// TODO Count Lost enquires
		Integer lostCnt=enquiries.stream()
	                       .filter(e -> e.getEnqStatus().equals("Lost"))
	                       .collect(Collectors.toList()).size();
		
		// TODO Send Data to controller
		response.setTotalCnt(totalCnt);
		response.setEnrolledCnt(enrolledCnt);
		response.setLostCnt(lostCnt);
      }	
		return response;
	}

	@Override
	public List<String> courseNmaes() {
		List<CourseEntity> courseEntities=courseRepo.findAll();
		List<String> names=new ArrayList<>();
		for( CourseEntity entity : courseEntities) {
			names.add(entity.getCourseName());		 	
		}
		return names;
	}

	@Override
	public List<String> statusNmaes() {
	
		List<EnqStatusEntity> statusEntities=statusRepo.findAll();
		List<String> statuses=new ArrayList<>();
		for(EnqStatusEntity entity : statusEntities) {
			statuses.add(entity.getStatusName());
		}
		return statuses;
	}

	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		StudentEnqEntity entity =new StudentEnqEntity();
		BeanUtils.copyProperties(form, entity);
		
		Integer userId=(Integer) session.getAttribute("userId");
		UserDtlsEntity userEntity=userDtlsRepo.findById(userId).get();
		entity.setUser(userEntity);
		enqRepo.save(entity);
		
		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		
		Integer userId=(Integer) session.getAttribute("userId");
		
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity=findById.get();
			List<StudentEnqEntity> enquiries=userDtlsEntity.getEnquiries();
			return enquiries;
		}
		
		return null;
	}
	
	@Override
	public List<StudentEnqEntity> getFilterdeEnqs(EnquirySearchCriteria criteria, Integer userId) {
		
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity=findById.get();
			List<StudentEnqEntity> enquiries=userDtlsEntity.getEnquiries();
			
			if(null!=criteria.getCourseName()
					&& !"".equals(criteria.getCourseName())){
				enquiries =enquiries.stream()
						.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
			}
			
			if(null!=criteria.getEnqStatus()
					&& !"".equals(criteria.getEnqStatus())){
				enquiries =enquiries.stream()
						.filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
						.collect(Collectors.toList());
			}
			
			if(null!=criteria.getClassMode()
					&& !"".equals(criteria.getClassMode())){
				enquiries =enquiries.stream()
						.filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}
					
			return enquiries;
				
		}
		return null;
	}
	
	



}
