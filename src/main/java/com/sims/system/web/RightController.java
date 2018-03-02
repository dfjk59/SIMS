package com.sims.system.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtJsonResult;
import com.sims.common.util.ExtPageable;
import com.sims.common.util.SystemLog;
import com.sims.system.entity.Right;
import com.sims.system.entity.dto.RightQueryDTO;
import com.sims.system.service.IRightService;

@Controller
@RequestMapping("/right")
public class RightController {
	@Autowired
	private IRightService rightService;

	@RequestMapping("/findAll")
	@RequiresPermissions("right:find")
	@SystemLog(module = "权限模块", action = "权限查询")
	public @ResponseBody Page<Right> findPage(RightQueryDTO rightQueryDTO, ExtPageable pageable) {
		Page<Right> page = rightService.findAll(RightQueryDTO.getSpecification(rightQueryDTO), pageable.getPageable());
		return page;
	}
	
	@RequestMapping("/findAll1")
	@RequiresPermissions("right:find")
	public @ResponseBody ExtJsonResult<Right> findAll(){
		return new ExtJsonResult<Right>(rightService.findAll());
	}

	@RequestMapping(value = "/saveOrUpdate")
	@RequiresPermissions("right:add")
	@SystemLog(module = "权限模块", action = "权限更新")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Right right) {
		try {
			rightService.saveOrUpdate(right);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "delete")
	@RequiresPermissions("right:delete")
	@SystemLog(module = "权限模块", action = "权限删除")
	public @ResponseBody ExtAjaxResponse delete(Long id) {
		try {
			Right right = rightService.findOne(id);
			if (null != right) {
				right.setRoles(null);
				rightService.delete(right);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "deletePermissions")
	@RequiresPermissions("right:delete")
	@SystemLog(module = "权限模块", action = "权限批量删除")
	public @ResponseBody ExtAjaxResponse delete(Long[] ids) {
		try {
			List<Long> list = Arrays.asList(ids);
			rightService.delete(list);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "findRight")
	public @ResponseBody Set<Right> findRight(Long id) {
		return rightService.findRightByRoleId(id);
	}
}
