package com.sentinel.configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SentinelConfiguration {

    public SentinelConfiguration() {
        FlowRule flowRule = new FlowRule();
        String resourceName = "testresource";
        flowRule.setResource(resourceName);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(50); //max 50 qps
        FlowRuleManager.loadRules(List.of(flowRule));

        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(resourceName);
        //RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO -> degrade wrt ratio of failed requests
        //RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT -> degrade wrt number of failed requests
        //RuleConstant.DEGRADE_GRADE_RT -> degrade wrt average response time
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setCount(200); //after 200 failed requests, degrade rule starts to apply
        //degradeRule.setSlowRatioThreshold(0.5);
        degradeRule.setTimeWindow(100); //recovery timeout -> 100 seconds
        DegradeRuleManager.loadRules(List.of(degradeRule));

        SystemRule rule = new SystemRule();
        rule.setResource(resourceName);
        rule.setHighestSystemLoad(2.0);
        rule.setHighestCpuUsage(0.5);
        rule.setAvgRt(2000);
        rule.setQps(25);
        rule.setMaxThread(1);
        SystemRuleManager.loadRules(List.of(rule));
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    public Converter<String, List<FlowRule>> flowConverter() {
        return source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        });
    }
}
