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
package admin.union.Member;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.javatodo.core.model.W;
import com.javatodo.core.tools.Page;
import com.javatodo.core.tools.T;

import admin.home.Index.CommonController;
import common.MU;
import common.database.MEMBER;
import freemarker.template.TemplateException;
import model.Balance;

public class IndexController extends CommonController {
	public void indexPage() throws SQLException, IOException, ServletException, TemplateException {
		Map<String, W> where = new HashMap<>();
		where.put(MEMBER.is_del, new W("eq", 0));
		if (!I(MEMBER.mobile).equals("")) {
			where.put(MEMBER.mobile, new W("like", "%" + I(MEMBER.mobile) + "%"));
		}
		if (!I(MEMBER.username).equals("")) {
			where.put(MEMBER.username, new W("like", "%" + I(MEMBER.username) + "%"));
		}
		Long count = new MU(MEMBER._table_name).where(where).count();
		Page page = this.page(count, 16);
		this.assign("page", page.show());
		List<Map<String, Object>> list = new MU(MEMBER._table_name).where(where).limit(page.firstRow + "," + page.listRows).order(MEMBER.id + " desc").select();
		this.assign("list", JSONArray.parse(JSON.toJSONString(list)));
		this.display();
	}

	public void balancePage() throws SQLException, IOException, ServletException, TemplateException {
		Map<String, W> where = new HashMap<>();
		where.put(MEMBER.is_del, new W("eq", 0));
		where.put(MEMBER.id, new W("eq", I(MEMBER.id).trim()));
		Map<String, Object> info = new MU(MEMBER._table_name).where(where).find();
		if (info == null) {
			this.error("该用户不存在或已被删除");
			return;
		}
		if (IS_POST) {
			Integer type = T.toInt(I("type").trim());
			if (type != 1 && type != 2) {
				this.error("请选择是增加余额还是减少余额");
				return;
			}
			String money_string = String.valueOf(T.toFloat(I("money").toString()));
			BigDecimal money = new BigDecimal(money_string);
			if (type == 2) {
				money = new BigDecimal("0").subtract(money);
			}
			Balance.AdministratorAdjustmentBalance(money.toString(), I(MEMBER.id).trim(), session("admin_id").toString());
			this.success("设置成功");
		} else {
			this.display();
		}
	}
}
