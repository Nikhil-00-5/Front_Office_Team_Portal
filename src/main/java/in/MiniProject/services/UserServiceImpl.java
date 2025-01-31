package in.MiniProject.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.MiniProject.binding.LoginForm;
import in.MiniProject.binding.SignUpForm;
import in.MiniProject.binding.UnlockForm;
import in.MiniProject.entities.UserDtlsEntity;
import in.MiniProject.repositories.UserDtlsRepo;
import in.MiniProject.util.EmailUtils;
import in.MiniProject.util.PwdUtils;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserDtlsRepo  userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override 
	public boolean signup(SignUpForm form) {
		
		
		UserDtlsEntity user=userDtlsRepo.findByEmail(form.getEmail());
		if(user !=null) {
			return false;
		}
	    
		// copy data from binding obj to object
		UserDtlsEntity entity =new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
			
		// generate random pwd and set to object
		String tempPwd =PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		// set Acc status as LOCKED 
		entity.setAccStatus("LOCKED");
		
		// Insert Record
		userDtlsRepo.save(entity);
		
		// Send Email to Unlock the Account 
		String to =form.getEmail();
		String subject ="Unlock Your Account | Ashok IT";
		
		StringBuffer body =new StringBuffer("") ;
		body.append("<h1>Use below temporary password to unlock your account</h1>");
		body.append("Temporary pwd : "+ tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+ to +"\">Click Here To Unlock Your Account</a>");
		
		emailUtils.sendEmail(to, subject, body.toString());
		
		return true;
	}
	
	@Override
	public boolean unlock(UnlockForm form) {
		
		//TODO Check record with email  
		UserDtlsEntity entity=userDtlsRepo.findByEmail(form.getEmail());
		
		// TODO pwd and tempPwd same or not
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userDtlsRepo.save(entity);
			return true;
		}
		return false;
	}
	
	@Override
	public String login(LoginForm form) {
		// TODO retrive data using email id
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		// TODO ENTITY CHECK !=NULL
		if (entity != null) {

			if (entity.getAccStatus().equals("UNLOCKED")) {
				
				session.setAttribute("userId", entity.getUserId());
				
				return "success";
			} else {
				return "Account in LOCKED State";
			}
		} else {

		}
		return "Invalid Credential";
	}
	
	@Override
	public boolean forgotPwd(String email) {
		
		// TODO find record by email
		UserDtlsEntity entity=userDtlsRepo.findByEmail(email);
		
		if(entity==null) {
			return false;
		}
		// TODO  if record present send email else errMsgs
		String subject ="Recover Password";
		String body= "Your Password is : "+entity.getPwd();
		
		emailUtils.sendEmail(email, subject, body);
		
		return true;
	}

	
}
