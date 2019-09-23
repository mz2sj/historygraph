package com.mz.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mz.bean.Node;
import com.mz.bean.NodeExample;
import com.mz.bean.NodeExample.Criteria;
import com.mz.dao.NodeMapper;

@Repository
public class Utils {
	@Autowired 
	NodeMapper nodeMapper;
	
	public Node searchNodeById(String nodeId) {
		// TODO Auto-generated method stub
		NodeExample example=new NodeExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(Integer.parseInt(nodeId));
		List<Node> nodeList = nodeMapper.selectByExample(example);
		return nodeList.get(0);
	}
	//按节点名称查询单个节点
	
}
