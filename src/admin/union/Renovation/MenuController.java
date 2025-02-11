/**
 * malltodo
 * ============================================================================
 * * 版权所有 2021-2071 郑州掌勺信息技术有限公司，并保留所有权利。
 * 网站地址: http://www.malltodo.com
 * ----------------------------------------------------------------------------
 * 这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和使用 .
 * 不允许对程序代码以任何形式任何目的的再发布。
 * 如果商业用途务必到官方购买正版授权, 以免引起不必要的法律纠纷.
 * ============================================================================
 * 郑州掌勺信息技术有限公司 2021-09-01
 * 业务电话：13598851835（微信同号） 
 */
package admin.union.Renovation;

import com.javatodo.core.tools.Http;
import com.javatodo.core.tools.T;

import common.BaseRenovation;
import common.Common;

public class MenuController extends BaseRenovation {
	public Boolean init() {
		Boolean b = super.init();
		if (b) {
			this.type = "Union/Index/menu";
			this.platform = "mobile";
			this.assign("page_action", "栏目页面");
			return true;
		} else {
			return false;
		}
	}

	public static void clearCache() {
		Http http = new Http();
		String category_sign = http.get(Common.unionHost + "/Public/category_sign.txt");
		T.S("Index/Index/menu" + category_sign, null);
	}
}
