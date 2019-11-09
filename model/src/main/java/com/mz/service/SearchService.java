package com.mz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.bean.Category;
import com.mz.bean.CategoryExample;
import com.mz.bean.Link;
import com.mz.bean.LinkExample;
import com.mz.bean.Node;
import com.mz.bean.NodeExample;
import com.mz.bean.NodeExample.Criteria;
import com.mz.dao.CategoryMapper;
import com.mz.dao.LinkMapper;
import com.mz.dao.NodeMapper;
import com.mz.util.Utils;

@Service
public class SearchService {
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	NodeMapper nodeMapper;
	@Autowired
	LinkMapper linkMapper;
	@Autowired
	Utils utils;
	
	public List<Category> searchCategory() {
		// TODO Auto-generated method stub
		List<Category> categoryList = categoryMapper.selectByExample(null);
		return categoryList;
	}

	/*
	 * public String searchCluster(String nodeName) { // TODO Auto-generated method
	 * stub NodeExample example=new NodeExample(); Criteria
	 * criteria=example.createCriteria(); criteria.andNameEqualTo(nodeName);
	 * List<Node> node = nodeMapper.selectByExample(example); String cluster =
	 * node.get(0).getCluster(); return cluster; }
	 */
	/*
	 * public List<Node> searchNode(String searchCluster) { // TODO Auto-generated
	 * method stub NodeExample example=new NodeExample(); Criteria
	 * criteria=example.createCriteria(); criteria.andClusterEqualTo(searchCluster);
	 * List<Node> nodeList = nodeMapper.selectByExample(example); return nodeList; }
	 */
	/*
	 * public List<Link> searchLink(String searchCluster) { // TODO Auto-generated
	 * method stub LinkExample example=new LinkExample();
	 * com.mz.bean.LinkExample.Criteria criteria=example.createCriteria();
	 * criteria.andClusterEqualTo(searchCluster); List<Link> linkList =
	 * linkMapper.selectByExample(example); return linkList; }
	 */

	public String searchNodeCategory(String searchNodeName) {
		// TODO Auto-generated method stub
		NodeExample example=new NodeExample();
		com.mz.bean.NodeExample.Criteria criteria=example.createCriteria();
		criteria.andNameEqualTo(searchNodeName);
		String categoryNumber = nodeMapper.selectByExample(example).get(0).getCategories();
		return categoryNumber;
	}

	public Integer searchNodeId(String searchNodeName) {
		// TODO Auto-generated method stub
		NodeExample example=new NodeExample();
		Criteria criteria=example.createCriteria();
		criteria.andNameEqualTo(searchNodeName);
		Integer nodeId=nodeMapper.selectByExample(example).get(0).getId();
		return nodeId;
	}
	
	

	public List<Link> searchLink(Integer nodeId, String nodeCategoryNumber) {
		// TODO Auto-generated method stub
		LinkExample example=new LinkExample();
		com.mz.bean.LinkExample.Criteria criteria=example.createCriteria();
		//1类返回s2t,事件对对个
		List<Link> linkList;
		if(nodeCategoryNumber.equals("1")) {
			criteria.andSourceEqualTo(nodeId.toString());
			linkList=linkMapper.selectByExample(example);
		}else if(nodeCategoryNumber.equals("2")){
			criteria.andSourceEqualTo(nodeId.toString());
			linkList=linkMapper.selectByExample(example);
		}
		else{
			//其他类类返回t2s
			criteria.andTargetEqualTo(nodeId.toString());
			linkList = linkMapper.selectByExample(example);
			//其他类source target需要调换位置
			for (Link link : linkList) {
				String temp=link.getSource();
				link.setSource(link.getTarget());
				link.setTarget(temp);
			}
		}
		List<Link> newList=null;
		if(linkList.size()>10) {
			newList = linkList.subList(0,50);
		}else {
			return linkList;
		}
		return newList;
	}

	public List<Node> searchNodes() {
		// TODO Auto-generated method stub
		List<Node> nodeList = nodeMapper.selectByExample(null);
		return nodeList;
	}

	public List<Node> searchNodes(List<Link> linkList) {
		// TODO Auto-generated method stub
		List<Node> nodeList=new ArrayList<Node>();
		List<String> nodeIdList=new ArrayList<String>();
		//将link中的所有节点取出
		for (Link link : linkList) {
			String sourceId=link.getSource();
			String targetId=link.getTarget();
			if(!nodeIdList.contains(sourceId))
			nodeIdList.add(sourceId);
			if(!nodeIdList.contains(targetId))
				nodeIdList.add(targetId);
		}
		
		//查询单个节点
		for (String nodeId : nodeIdList) {
			Node node=utils.searchNodeById(nodeId);
			nodeList.add(node);
		}
		
		return nodeList;
	}

	public List<Node> fuzzyQuery(String partNodeName) {
		// TODO Auto-generated method stub
		List<Node> nodeList = nodeMapper.fuzzyQuery(partNodeName);
		List<Node> newList=null;
		if(nodeList.size()>10) {
			newList = nodeList.subList(0,10);
		}else {
			return nodeList;
		}
		return newList;
	}

	public String searchNodeCategoryById(String id) {
		// TODO Auto-generated method stub
		NodeExample example=new NodeExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(Integer.parseInt(id));
		String categoryNumber = nodeMapper.selectByExample(example).get(0).getCategories();
		return categoryNumber;
	}

}
