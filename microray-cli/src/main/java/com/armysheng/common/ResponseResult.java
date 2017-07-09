/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.armysheng.common;


import lombok.Data;

@Data
public class ResponseResult<T> {
    private String respCode;
    private String respMsg;
    private T data;


    private ResponseResult(String respCode, String respMsg, T data) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.data = data;

    }

    public ResponseResult(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public ResponseResult(String respMsg) {
        this(ResponseConstants.SUCCESS_CODE, respMsg);

    }

    public ResponseResult(T data) {
        this(ResponseConstants.SUCCESS_CODE, ResponseConstants.SUCCESS_MESSAGE, data);
    }

    public Boolean isSuccess(){return this.respCode.equals(ResponseConstants.SUCCESS_CODE);}

}
