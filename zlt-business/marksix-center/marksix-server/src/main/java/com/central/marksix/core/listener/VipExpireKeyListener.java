package com.central.marksix.core.listener;

import cn.hutool.core.util.StrUtil;
import com.central.common.constant.MarksixConstants;
import com.central.common.redis.template.RedisRepository;
import com.central.marksix.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class VipExpireKeyListener implements MessageListener {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        byte[] body = message.getBody();

        try {
            String p = new String(pattern, StandardCharsets.UTF_8);
            String title = new String(channel, StandardCharsets.UTF_8);
            String content = new String(body, StandardCharsets.UTF_8);
            log.info("key：" + title + ",event:" + content + ",p:" + p);
            if (content.startsWith(MarksixConstants.RedisKey.KPN_SITE_ONLINE_UNIQUE_ID_PREFIX)) {
                String[] keyItemArr = StrUtil.split(content, MarksixConstants.Symbol.COLON);
                log.info("用户:{},下线了", keyItemArr[5]);
                RedisRepository.decr(StrUtil.format(MarksixConstants.RedisKey.KPN_SITE_ONLINE_COUNT, keyItemArr[4]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
