package com.ecommerce.agriculture.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.agriculture.entity.Admin;
import com.ecommerce.agriculture.entity.Item;
import com.ecommerce.agriculture.service.AdminServiceImplementation;
import com.ecommerce.agriculture.service.ItemServiceImplementation;

/**
 * 
 * @author Soumyadip Chowdhury
 *
 */
@Controller
@RequestMapping("/seller")
public class SellerController {


	private ItemServiceImplementation itemServiceImplementation;
	private AdminServiceImplementation adminServiceImplementation;
	
	
	@Autowired
	public SellerController(ItemServiceImplementation objA,
			AdminServiceImplementation adminServiceImplementation) {
	
		this.itemServiceImplementation=objA;
		this.adminServiceImplementation=adminServiceImplementation;
	}
	
	
	@GetMapping("/index")
	public String index(Model model){

		this.lastseen();
			
//		Item item=new Item();
//		
//		model.addAttribute("item", item);
//		
		return "seller/addItems";
	}
	
	
	@PostMapping("/save")
	public String save(@ModelAttribute("item") Item item) {
		
		this.lastseen();
		item.setId(0);
		System.out.println(item);
		
		itemServiceImplementation.save(item);
		
		return "redirect:/seller/item-details";
	}

	
	@RequestMapping("/item-details")
	public String itemDetails(Model model){
		
		
		this.lastseen();
			         
		List<Item> list=itemServiceImplementation.findAll();
		model.addAttribute("item", list);
		
		
		return "seller/items";
	}
	
	
	
	
	
	
	
	
	@PostMapping("/update")
	public String updateP(@ModelAttribute("item") Item admin) {
		
		this.lastseen();
		System.out.println(admin);
		
		itemServiceImplementation.save(admin);
		
		return "redirect:/seller/item-details";
	}
	
	@GetMapping("/update-item")
	public String Update(@RequestParam("id") int theId,Model theModel) {
		this.lastseen();
		System.out.println(theId);
		
		Item admin=itemServiceImplementation.findById(theId);
		System.out.println(admin);
		
		theModel.addAttribute("admin",admin);
		
		return "seller/update";
	}
	
	@GetMapping("/delete-item")
	public String delete(@RequestParam("id") int theId,Model theModel) {
		this.lastseen();
		System.out.println(theId);
		
		itemServiceImplementation.deleteById(theId);
	
		return "redirect:/seller/item-details";
	}
	
	
	
	
	public void lastseen()
	{
		String username="";
		String Pass = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		   Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);
		  	} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}
		System.out.println("One + "+username+"   "+Pass);
		Admin admin1 = adminServiceImplementation.findByUser(username);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date now = new Date();  
		String log=now.toString();
		admin1.setLastseen(log);
		adminServiceImplementation.save(admin1);
	
	}
	
}
