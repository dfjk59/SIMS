package com.sims.system.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtPageable;
import com.sims.system.entity.Log;
import com.sims.system.entity.dto.LogQueryDTO;
import com.sims.system.service.ILogService;

@Controller
@RequestMapping(value = "log")
public class LogController {
	@Autowired
	private ILogService logService;

	@RequestMapping(value = "findAll")
	public @ResponseBody Page<Log> findPage(LogQueryDTO logQueryDTO, ExtPageable pageable) {
		Page<Log> page = logService.findAll(LogQueryDTO.getSpecification(logQueryDTO), pageable.getPageable());
		return page;
	}

	@RequestMapping(value = "delete")
	public @ResponseBody ExtAjaxResponse delete(Long id) {
		try {
			Log log = logService.findOne(id);
			if (null != log) {
				logService.delete(log);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "deleteLogs")
	public @ResponseBody ExtAjaxResponse delete(Long[] ids) {
		try {
			List<Long> list = Arrays.asList(ids);
			logService.delete(list);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}
}
