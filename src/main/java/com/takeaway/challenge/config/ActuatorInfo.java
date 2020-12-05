package com.takeaway.challenge.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.ApplicationInfoManager;

@Component
public class ActuatorInfo implements InfoContributor {
	@Autowired
	private ApplicationInfoManager applicationInfoManager;
	
	@Override
	public void contribute(Builder builder) {
		Map<String, Object> details = new HashMap<>();
		details.put("appName", applicationInfoManager.getInfo().getAppName());
		details.put("hostname", applicationInfoManager.getInfo().getHostName());
		details.put("ipAddress", applicationInfoManager.getInfo().getIPAddr());
		details.put("port", applicationInfoManager.getInfo().getPort());
		details.put("instanceId", applicationInfoManager.getInfo().getInstanceId());
		builder.withDetail("instanceInfo", details);
	}
}
