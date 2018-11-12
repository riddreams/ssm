package controller;

import entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import redis.clients.jedis.Jedis;
import service.ExcelExportService;
import service.UserService;
import service.impl.ExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class UserController {
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private final UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = {"/","/index"},method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String success() {
		return "home";
	}

	/**
	 * 用户登录功能
	 */
	@RequestMapping(value = "/doLogin",method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, HttpSession session, RedirectAttributes attr) throws Exception{
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		User user = userService.findUser(userName,passWord);
		if(user!=null){
			session.setAttribute("user",user);
			return "redirect:/home";
		}else {
			attr.addFlashAttribute("msg","用户名或密码错误");
			return "redirect:/login";
		}
	}

	// 文件上传
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String upload(MultipartFile[] multipartFile, HttpServletRequest request){
		// 普通表单域
		String desc = request.getParameter("desc");
		System.out.println(desc);
		// 循环处理文件上传域
		for (MultipartFile aMultipartFile : multipartFile) {
			// 获取文件名
			String fileName = aMultipartFile.getOriginalFilename();
			if (fileName != null && fileName.length() > 0) {
				System.out.println(fileName);
				// 设置文件存放路径
				File directory = new File(request.getServletContext().getRealPath("/WEB-INF/upload"));
				// 如果目录不存在，创建它
				if(!directory.exists()){
					boolean mkdirs = directory.mkdirs();
				}
				try {
					// 设置包含路径的文件名
					File file = new File(directory+"/"+fileName);
					// 将文件存放到磁盘
					aMultipartFile.transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/home";
	}

	@RequestMapping(value = "/export",method = RequestMethod.GET)
	public ModelAndView export() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		ExcelView excelView = new ExcelView(exportService());
		String fileName = "用户.xls";
		excelView.setFileName(fileName);
		List<User> list = userService.listUser();
		modelAndView.addObject("list",list);
		modelAndView.setView(excelView);
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	private ExcelExportService exportService(){
		return (Map<String,Object> map,Workbook workbook) -> {
			List<User> list = (List<User>)map.get("list");
			Sheet sheet = workbook.createSheet("用户");
			Row title = sheet.createRow(0);
			title.createCell(0).setCellValue("编号");
			title.createCell(1).setCellValue("用户名");
			title.createCell(2).setCellValue("密码");
			title.createCell(3).setCellValue("角色");
			for (int i=0;i<list.size();i++){
				User user = list.get(i);
				int rowIdx = i+1;
				Row row = sheet.createRow(rowIdx);
				row.createCell(0).setCellValue(user.getId());
				row.createCell(1).setCellValue(user.getUserName());
				row.createCell(2).setCellValue(user.getPassWord());
				row.createCell(3).setCellValue(user.getRole());
			}
		};
	}

	@RequestMapping(value = "/jedis",method = RequestMethod.GET)
	@ResponseBody
	public String testJedis(){
		Jedis jedis = new Jedis("localhost",6379);
		int i = 0;
		try{
			long start = System.currentTimeMillis();
			while (true){
				long end = System.currentTimeMillis();
				if(end - start >= 1000){
					break;
				}
				i++;
				jedis.set("test"+i,i+"");
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			jedis.close();
		}
		return "redis executes "+i+" times per second!";
	}

	@ResponseBody
	@RequestMapping(value = "/springRedis",method = RequestMethod.GET)
	public void springRedis(){
		redisTemplate.opsForValue().set("key1","value1");
		String value1 = redisTemplate.opsForValue().get("key1");
		System.out.println(value1);
	}
}