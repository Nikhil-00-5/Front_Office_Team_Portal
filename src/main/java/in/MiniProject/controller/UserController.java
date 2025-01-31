package in.MiniProject.controller;

import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import in.MiniProject.binding.LoginForm;
import in.MiniProject.binding.SignUpForm;
import in.MiniProject.binding.UnlockForm;
import in.MiniProject.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/signup")
	public String openSignupPage( Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";	
	}
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form ,Model model) {
		boolean status=userService.signup(form);
		if(status) {
			model.addAttribute("succMsg", "Check Your Email");
			}
		      else {
		    	  model.addAttribute("errMsg", "Alredy Account Present With this Mail Id");
		    	  }
		return "signup";
	}
	

	
	@GetMapping("/login")
	public String openLoginPage( Model model) {
		model.addAttribute("loginform",new LoginForm());
		return "login";	
	}
	@PostMapping("/login")
	public String handleLoginForm(@ModelAttribute("loginform") LoginForm form,Model model) {
		String login=userService.login(form);
		if(login.contains("success")) {
			
			return "redirect:/dashboard";
		}else {
			model.addAttribute("errMsg", login);
		return "login";	
		}
	}
	


	@GetMapping("/forgot")
	public String openForgotPwdPage() {
		return "forgotPwd";	
	}
	
	@PostMapping("/forgot")
	public String handleForgotPwdPage(@RequestParam String email,Model model) {
		boolean Status=userService.forgotPwd(email);
		if(Status) {
			model.addAttribute("succMsg", "Password send to Your Email");
			
		}else {
		model.addAttribute("errMsg", "Invalid Email");
		}
		return "forgotPwd";
	}
	
	
	
	@GetMapping("/unlock")
	public String openUnlockPage(@RequestParam String email,Model model) {
		UnlockForm unlockFormObj=new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock",unlockFormObj);

		return "unlock";	
	}
	
	@PostMapping("/unlock")
	public String handleUnlockPage(@ModelAttribute("unlock") UnlockForm form,Model model) {
		
		System.out.println(form);
		if(form.getConfirmPwd().equals(form.getNewPwd())) {
			boolean status=userService.unlock(form);
			if(status) {
				model.addAttribute("succMsg", "Account UnLock Successfully");
			}else {
				model.addAttribute("succMsg", "Temporary pwd are incorrect, Please check Your Email");
			}
		}else {
			model.addAttribute("errMsg", "New Pwd and Confirm Pwd should be same");
		}
		
		
		return "unlock";
	}



 

}
