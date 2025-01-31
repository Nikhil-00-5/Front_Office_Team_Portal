package in.MiniProject.services;

import in.MiniProject.binding.LoginForm;
import in.MiniProject.binding.SignUpForm;
import in.MiniProject.binding.UnlockForm;

public interface UserService {
	
	public boolean signup( SignUpForm form);
	
	public boolean unlock(UnlockForm form);
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);
		}
