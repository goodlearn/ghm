package com.thinkgem.jeesite.modules.sys.entity.wx;

/**
 * 复杂按钮
 * @author wzy
 *
 */
public class WxComplexButton extends WxButton{
	
	private WxButton[] subButtons;

	public WxButton[] getSubButtons() {
		return subButtons;
	}

	public void setSubButtons(WxButton[] subButtons) {
		this.subButtons = subButtons;
	}

	
}
