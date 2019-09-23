package com.mz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mz.bean.Category;
import com.mz.bean.Link;
import com.mz.bean.Msg;
import com.mz.bean.Node;
import com.mz.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	SearchService searchService;
	
	@RequestMapping("/search")
	@ResponseBody
	public Msg searchData(@RequestParam("searchNodeName") String searchNodeName) {
		//查询所有category
		System.out.println();
		List<Category> categoryList = searchService.searchCategory();
		//查询节点所属category
		String nodeCategoryNumber=searchService.searchNodeCategory(searchNodeName);
		//查询节点id
		Integer nodeId=searchService.searchNodeId(searchNodeName);
		//查询节点有关的link
		List<Link> linkList=searchService.searchLink(nodeId,nodeCategoryNumber);
		/*
		 * //查询所有节点 List<Node> nodeList=searchService.searchNodes();
		 */
		//查询指定节点
		List<Node> nodeList=searchService.searchNodes(linkList);
		
		/*
		 * //查询node所属cluster String searchCluster =
		 * searchService.searchCluster(searchNodeName); //根据cluster查询所有节点 List<Node>
		 * nodeList = searchService.searchNode(searchCluster); //根据cluster查询所有关系
		 * List<Link> linkList = searchService.searchLink(searchCluster); return
		 * Msg.success().add("category", categoryList).add("cluster",searchCluster)
		 * .add("node",nodeList).add("link",linkList);
		 */
		return Msg.success().add("node",nodeList).add("link",linkList).add("category",categoryList);
	}
}
