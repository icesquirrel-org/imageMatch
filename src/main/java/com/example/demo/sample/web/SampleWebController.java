package com.example.demo.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleWebController {

 
	@RequestMapping(value = "/")	
	public ModelAndView index() throws Exception{				
		ModelAndView mav = new ModelAndView();				
		mav.setViewName("index"); //jsp(html)로 갈때는 setViewName // class로 갈때는 setView				
		return mav;
	}
}
