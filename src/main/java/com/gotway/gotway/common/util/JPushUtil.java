package com.gotway.gotway.common.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
	
	
	protected static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);
	
	private   String MASTER_SECRET = "2500ecf66f138a898f48acfb";
	private   String APP_KEY = "094b625fb5de4a008d5393bf";
	private  String TAG = "pushMessage";
	private  String MSG_CONTENT = "hello,how are you ?";
	private  String FROM="XXX";
	private static JPushUtil instence;
	
	private JPushClient jpushClient=null;
	
	
	private JPushUtil() {
		super();
		ClientConfig clientConfig = ClientConfig.getInstance();
		jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
	}



	public JPushUtil(String MASTER_SECRET, String APP_KEY) {
		super();
		this.MASTER_SECRET = MASTER_SECRET;
		this.APP_KEY = APP_KEY;
		ClientConfig clientConfig = ClientConfig.getInstance();
		jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
	}

	public static JPushUtil getInstence(String MASTER_SECRET, String APP_KEY){
		if(instence==null){
			synchronized (JPushUtil.class){
				instence = new JPushUtil(MASTER_SECRET, APP_KEY);
			}
		}
		return instence;
	}


	public  boolean push(String[] alias, String message,String from) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);

		PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras(alias, message,from);
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
			return true;
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			LOG.error("Sendno: " + payload.getSendno());
			return false;
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
			LOG.error("Sendno: " + payload.getSendno());
			return false;
		}
	}

	private static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String[] alias, String MSG_CONTENT,String from) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(MSG_CONTENT))
				.setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).addExtra("from", from).build()).build();
	}


	public  void publish(String Tag, String message,String from) {
	      
			PushPayload payload = this.buildPushObject_ios_audienceMore_messageWithExtras(Tag,message,from);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            System.out.println(result);
	            LOG.info("Got result - " + result);

	        } catch (APIConnectionException e) {
	            LOG.error("Connection error. Should retry later. ", e);
	            LOG.error("Sendno: " + payload.getSendno());

	        } catch (APIRequestException e) {
	            LOG.error("Error response from JPush server. Should review and fix it. ", e);
	            LOG.info("HTTP Status: " + e.getStatus());
	            LOG.info("Error Code: " + e.getErrorCode());
	            LOG.info("Error Message: " + e.getErrorMessage());
	            LOG.info("Msg ID: " + e.getMsgId());
	            LOG.error("Sendno: " + payload.getSendno());
	        }
	    }
	   public  PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String TAG, String MSG_CONTENT,String FROM) {
	   /*    return PushPayload.newBuilder() 
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.newBuilder()
	                        .addAudienceTarget(AudienceTarget.tag(TAG))
	                        .addAudienceTarget(AudienceTarget.alias("18377613902"))
	                        .build())
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(MSG_CONTENT)
	                        .addExtra("from", FROM)
	                        .build())
	                .build();*/
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.tag(TAG))
	                .setNotification(Notification.alert(MSG_CONTENT))
	                .setMessage(Message.newBuilder()
	                	.setMsgContent(MSG_CONTENT)
	                	.setTitle("title")
	                	.addExtra("from", FROM)
	                	.addExtra("message extras key","extrasparam")
	                	.build())
	                .build();
	 	   
	    }

}
