package cn.xl.examination.common.lang;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

@Data
@Component
public class Result implements Serializable {

	private int code;
	private String msg;
	private Object data;
	private Object OperationLog; // 用户操作日志
	private Object OperationTime;	// 用户操作时间

	public static Result succ(Object data) {
		return succ(200, "操作成功", data);
	}

	public static Result succ(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static Result fail(String msg) {
		return fail(400, msg, null);
	}

	public static Result fail(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}
	public void setPathError(){
		 this.setCode(403);
		 this.setMsg("请求路径有误");
	}
	public void setAuthError(){
		 this.setCode(401);
		 this.setMsg("你没有权限这么做");
	}
	public void setServiceError(){
		 this.setCode(500);
		 this.setMsg("内部服务发生错误请稍后再试或联系管理员");
	}
	public void setDataBaseError(){
		 this.setCode(500);
		 this.setMsg("数据库异常请稍后再试或联系管理员");
	}
	public void setSuccess(UserDetails userDetails){
		 this.setCode(200);
		 this.setOperationLog("Operator:"+userDetails);
		 this.setOperationTime("OperationTime:"+ new SimpleDateFormat().format(new Date()));
	}

}
