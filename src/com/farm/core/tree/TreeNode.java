package com.farm.core.tree;

public class TreeNode {
	private static final long serialVersionUID = 1L;
	private String id; // 节点id
	private String nodeState; // 0:集团、1:分公司、2:车队、3:线路
	private String text; // 显示的节点文本
	private String state = "open"; // 节点状态,'open'或者'closed',默认是'open'
	private boolean checked; // 指明检查节点是否选中.

	public TreeNode() {
	}

	public TreeNode(String id, String text, String state, boolean checked) {
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getNodeState() {
		return nodeState;
	}

	public void setNodeState(String nodeState) {
		this.nodeState = nodeState;
	}

}
