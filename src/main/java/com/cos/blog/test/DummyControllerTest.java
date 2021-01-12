package com.cos.blog.test;


import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller =RestController
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository UserRepository;
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return UserRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴받아 볼 예정
	//http://localhost:8000/blog/dummy/user
	//http://localhost:8000/blog/dummy/user?page=0 ~
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUser = UserRepository.findAll(pageable); // 세부정보까지
		
		List<User> users = pagingUser.getContent(); //내용만 받아오게
		return users;
	}
	
	
	
	
	
	
	
	
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4을 찾으면 내가 데이터베이스에서 못찾아오게되면 user가 null이 될 것 아냐?
		// 그럼 return null이 리턴되자나... 그럼 프로그램에 문제가 있지 않겠니?
		// Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
		//.get()으로 하게되면 메세지 없이 오류창만 뜨게됨
		
		//람다식
		//		User user = UserRepository.findById(id).orElseThrow(()->{
		//			return new illegalArgumentException("해당 사용자는 없습니다.");
		//		});
		
		User user = UserRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다.id: " + id);
			}
		});
		//요청 : 웹브라우저
		//user 객체  = 자바 오브젝트 
		//변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다. 
		return user;
	}
	

	//http://localhost:8000/blog/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping( "/dummy/join")
	public String join(User user) { //key = value(약속된 규칙)
		System.out.println("id: " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		UserRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}

}
